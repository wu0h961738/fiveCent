package com.venom.backend.authentication.service;

import com.venom.backend.authentication.model.response.AccessToken;

/** 驗證流程*/
public interface APIService {

    /** 引導user到authentication頁面*/
    String getLoginUrl();

    /** 藉由AuthorizationCode取得accessToken*/
    AccessToken getAcTkn(String authorizationCode);
}
