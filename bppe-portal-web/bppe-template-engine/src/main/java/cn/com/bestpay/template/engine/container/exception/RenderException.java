package cn.com.bestpay.template.engine.container.exception;

/**
 * Created by Howell on 16/1/28.
 */
public class RenderException  extends RuntimeException {
    public RenderException() {
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderException(Throwable cause) {
        super(cause);
    }
}
