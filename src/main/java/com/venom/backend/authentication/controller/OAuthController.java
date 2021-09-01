package com.venom.backend.authentication.controller;

import com.venom.backend.authentication.LineAuth;
import com.venom.backend.authentication.model.response.AccessToken;
import com.venom.backend.authentication.model.response.IdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class OAuthController {

    private static final String LINE_WEB_LOGIN_STATE = "lineWebLoginState";
    static final String ACCESS_TOKEN = "accessToken";
    private static final String NONCE = "nonce";

    @Autowired
    private LineAuth lineAuth;

    /**
     * <p>LINE Login Button Page
     * <p>Login Type is to log in on any desktop or mobile website
     */
    @RequestMapping("/")
    public String login() {
        return "user/login";
    }

    /**
     * <p>Redirect to LINE Login Page</p>
     */
    @RequestMapping(value = "/gotoauthpage")
    public String goToAuthPage(HttpSession session){
        return "redirect:" + lineAuth.getLoginUrl(session, Arrays.asList("openid", "profile"));
    }

    /**
     * <p>Redirect Page from Platform</p>
     * <p>Login Type is to log in on any desktop or mobile website
     */
    @RequestMapping("/auth")
    public String auth(
            HttpSession httpSession,
            @RequestParam(value = "code", required = true) String code, // code = authorization code
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "errorCode", required = false) String errorCode,
            @RequestParam(value = "errorMessage", required = false) String errorMessage) throws IOException {

        if (error != null || errorCode != null || errorMessage != null){
            return "redirect:/loginCancel";
        }

        if (!state.equals(httpSession.getAttribute(LINE_WEB_LOGIN_STATE))){
            return "redirect:/sessionError";
        }
        httpSession.removeAttribute(LINE_WEB_LOGIN_STATE);
        AccessToken token = lineAuth.getAcTkn(code);

        httpSession.setAttribute(ACCESS_TOKEN, token);
        return "redirect:/success";
    }

    /**
     * <p>login success Page
     */
    @RequestMapping("/success")
    public String success(HttpSession httpSession, Model model) {

        AccessToken token = (AccessToken)httpSession.getAttribute(ACCESS_TOKEN);
        if (token == null){
            return "redirect:/";
        }

//        if (!lineAPIService.verifyIdToken(token.id_token, (String) httpSession.getAttribute(NONCE))) {
//            // verify failed
//            return "redirect:/";
//        }

        httpSession.removeAttribute(NONCE);
        IdToken idToken = lineAuth.idToken(token.id_token);
        model.addAttribute("idToken", idToken);
        return "user/success";
    }

}
