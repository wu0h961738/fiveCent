package com.venom.backend.authentication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** template of authorization call back*/
@Data
public class LineAuthCodeCallBack {

    @JsonProperty("code")
    private String authorizationCode;

    @JsonProperty("state")
    private String state;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("error")
    private String error;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;
}
