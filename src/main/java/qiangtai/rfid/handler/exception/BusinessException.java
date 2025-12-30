package qiangtai.rfid.handler.exception;

import org.slf4j.helpers.MessageFormatter;


public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 9215327422367136022L;
    private int code;
    private String snapshot;
    private Boolean success;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.snapshot = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, String snapshotFormat, Object... argArray) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.snapshot = errorCode.getMessage() + MessageFormatter.arrayFormat(snapshotFormat, argArray).getMessage();
    }
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.success = false;
        this.snapshot = message;
    }
    public BusinessException(int code, String message, String snapshotFormat, Object... argArray) {
        super(message);
        this.code = code;
        this.snapshot = MessageFormatter.arrayFormat(snapshotFormat, argArray).getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getSnapshot() {
        return snapshot;
    }

}
