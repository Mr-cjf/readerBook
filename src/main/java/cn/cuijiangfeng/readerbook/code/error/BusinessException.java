package cn.cuijiangfeng.readerbook.code.error;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author lty
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private static final String DELIMITER = AppSeparatorConst.SPACE;
    
    private final ErrorCode errorCode;
    
    private final transient Object data; // 将 data 标记为 transient
    
    private final String description;
    
    public BusinessException(ErrorCode errorCode) {
        super(String.join(DELIMITER, errorCode.getCode(), errorCode.getMsg()));
        this.errorCode = errorCode;
        this.data = null;
        this.description = null;
    }
    
    public BusinessException(ErrorCode errorCode, String description) {
        super(String.join(DELIMITER, errorCode.getCode(), errorCode.getMsg(), description));
        this.errorCode = errorCode;
        this.description = description;
        this.data = null;
    }
    
    public BusinessException(ErrorCode errorCode, Throwable throwable) {
        super(String.join(DELIMITER, errorCode.getCode(), errorCode.getMsg()), throwable);
        this.errorCode = errorCode;
        this.data = null;
        this.description = null;
    }
    
    public BusinessException(ErrorCode errorCode, String description, Throwable throwable) {
        super(String.join(DELIMITER, errorCode.getCode(), errorCode.getMsg(), description), throwable);
        this.errorCode = errorCode;
        this.description = description;
        this.data = null;
    }
    
    public BusinessException(ErrorCode errorCode, String description, Object data) {
        super(String.join(DELIMITER, errorCode.getCode(), errorCode.getMsg(), description));
        this.errorCode = errorCode;
        this.description = description;
        this.data = data;
    }
    
    public String getCode() {
        return errorCode.getCode();
    }
    
    public String getMsg() {
        return errorCode.getMsg();
    }
}
   