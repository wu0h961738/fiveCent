package com.venom.backend.authentication.interfacePack;

import com.venom.backend.authentication.model.response.AccessToken;

import java.io.IOException;
import java.util.List;

/**
 * 驗證interface*/
public interface Authentication {

    /** 引導user到authentication頁面*/
    String getLoginUrl(List<String> scopes);

    /** 藉由AuthorizationCode取得accessToken*/
    AccessToken getAcTkn(String authorizationCode) throws IOException;

}
