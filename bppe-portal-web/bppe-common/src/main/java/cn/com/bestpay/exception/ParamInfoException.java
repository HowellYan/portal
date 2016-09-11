package cn.com.bestpay.exception;

/**
 * Created by Howell on 16/1/30.
 */
public class ParamInfoException extends RuntimeException {
    private static final long serialVersionUID = 6295717443044895278L;

    public ParamInfoException() {
    }

    public ParamInfoException(String message) {
        super(message);
    }

    public ParamInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamInfoException(Throwable cause) {
        super(cause);
    }
}
