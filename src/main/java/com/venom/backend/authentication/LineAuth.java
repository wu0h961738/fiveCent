package com.venom.backend.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.venom.backend.authentication.interfacePack.Authentication;
import com.venom.backend.authentication.interfacePack.OIDCAbstract;
import com.venom.backend.authentication.model.LineAPI;
import com.venom.backend.authentication.model.response.AccessToken;
import com.venom.backend.authentication.model.response.IdToken;
import com.venom.backend.http.ClientToService;
import com.venom.backend.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Function;

@Service
public class LineAuth{

    private static final String LINE_WEB_LOGIN_STATE = "lineWebLoginState";

    private static final String NONCE = "nonce";

    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";

    @Value("${linecorp.platform.channel.callbackUrl}")
    private String callbackUrl;

    @Value("${linecorp.platform.channel.channelId}")
    private String channelId;

    @Value("${linecorp.platform.channel.channelSecret}")
    private String channelSecret;

    /**組合導入third party Login流程 url
     *
     * @param scopes 請求開通權限
     * */
    public String getLoginUrl(HttpSession session, List<String> scopes){
        String state = CommonUtils.getToken();
        String nonce = CommonUtils.getToken();
        session.setAttribute(LINE_WEB_LOGIN_STATE, state);
        session.setAttribute(NONCE, nonce);
        final String encodedCallbackUrl;
        final String scope = String.join("%20", scopes);

        try {
            encodedCallbackUrl = URLEncoder.encode(callbackUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return "https://access.line.me/oauth2/v2.1/authorize?response_type=code"
                + "&client_id=" + channelId
                + "&redirect_uri=" + encodedCallbackUrl
                + "&state=" + state
                + "&scope=" + scope
                + "&nonce=" + nonce;

    }

    /** parsing and returning payload*/
    public IdToken idToken(String id_token) {
        try {
            DecodedJWT jwt = JWT.decode(id_token);
            return new IdToken(
                    jwt.getClaim("iss").asString(),
                    jwt.getClaim("sub").asString(),
                    jwt.getClaim("aud").asString(),
                    jwt.getClaim("ext").asLong(),
                    jwt.getClaim("iat").asLong(),
                    jwt.getClaim("nonce").asString(),
                    jwt.getClaim("name").asString(),
                    jwt.getClaim("picture").asString());
        } catch (JWTDecodeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取得accessToken
     *
     * @param authCode authorization code*/
    public AccessToken getAcTkn(String authCode) throws IOException {
        return getClient(t -> t.accessToken(GRANT_TYPE_AUTHORIZATION_CODE, channelId, channelSecret, callbackUrl, authCode));
    }

    /** 打api到line platform
     * @param function  callback func with template*/
    private <R> R getClient(final Function<LineAPI, Call<R>> function) throws IOException {
        return ClientToService.getLineService(LineAPI.class, function);
    }
}
