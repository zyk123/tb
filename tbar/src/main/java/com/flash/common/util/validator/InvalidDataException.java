package com.flash.common.util.validator;

/**
 * @author: Edward
 * Date: 13-6-9
 * Time: 上午9:32
 * Description:        数据错误错误
 */
public class InvalidDataException extends RuntimeException {

    private CommonErrorInfo commonErrorInfo;

    public InvalidDataException(CommonErrorInfo errorInfo) {
        super();
        this.commonErrorInfo = errorInfo;
    }

    public InvalidDataException(CommonErrorInfo errorInfo, Throwable cause) {
        super(cause);
        this.commonErrorInfo = errorInfo;
    }

    public CommonErrorInfo getCommonErrorInfo() {
        return commonErrorInfo;
    }

    public void setCommonErrorInfo(CommonErrorInfo commonErrorInfo) {
        this.commonErrorInfo = commonErrorInfo;
    }
}
