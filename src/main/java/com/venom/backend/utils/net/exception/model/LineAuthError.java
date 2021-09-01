package com.venom.backend.utils.net.exception.model;

import com.google.gson.annotations.SerializedName;
import com.venom.backend.utils.net.exception.ErrorResponse;
import lombok.Data;

/**
 *  line OAuth exception response 的 template
 *  */
@Data
public class LineAuthError implements ErrorResponse {

    /**錯誤代碼*/
    @SerializedName("error")
    private String error;

    /**錯誤內容*/
    @SerializedName("error_description")
    private String errorDetail;

    @SerializedName("state")
    private String state;
}
