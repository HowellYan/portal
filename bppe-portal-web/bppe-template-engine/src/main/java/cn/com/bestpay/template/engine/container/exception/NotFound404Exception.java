package cn.com.bestpay.template.engine.container.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Howell on 16/1/28.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFound404Exception extends RuntimeException  {
    public NotFound404Exception() {
    }

    public NotFound404Exception(Throwable cause) {
        super(cause);
    }

    public NotFound404Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFound404Exception(String message) {
        super(message);
    }
}
