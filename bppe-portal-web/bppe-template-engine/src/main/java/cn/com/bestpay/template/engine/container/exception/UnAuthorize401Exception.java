package cn.com.bestpay.template.engine.container.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Howell on 16/1/28.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorize401Exception extends RuntimeException {
    public UnAuthorize401Exception() {
    }

    public UnAuthorize401Exception(String message) {
        super(message);
    }

    public UnAuthorize401Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorize401Exception(Throwable cause) {
        super(cause);
    }
}
