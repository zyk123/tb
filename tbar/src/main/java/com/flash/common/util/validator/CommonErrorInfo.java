package com.flash.common.util.validator;

/**
 * @author: Edward
 * Date: 13-5-23
 * Time: 下午5:07
 * Description:
 */
public class CommonErrorInfo {

    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误地址
     */
    private String errorUrl;
    /**
     * 错误描述
     */
    private String errorDesc;

    public CommonErrorInfo() {
    }

    public CommonErrorInfo(String errorCode, String errorUrl, String errorDesc) {
        this.errorCode = errorCode;
        this.errorUrl = errorUrl;
        this.errorDesc = errorDesc;
    }

    public CommonErrorInfo(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
