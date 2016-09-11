package cn.com.bestpay.template.engine.container.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Howell on 16/1/28.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class Server500Exception  extends RuntimeException {
    public Server500Exception() {
    }

    public Server500Exception(String message) {
        super(message);
    }

    public Server500Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Server500Exception(Throwable cause) {
        super(cause);
    }
}
