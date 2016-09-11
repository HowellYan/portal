package cn.com.bestpay.storage;

import java.io.Serializable;

/**
 * Created by Howell on 16/2/2.
 */
public class Storable implements Serializable {

    private static final long serialVersionUID = 10101L;

    private String key;
    protected final Object target;
    private final Long expiredSeconds;

    public Storable(String key, Object target, Long expiredSeconds) {
        this.key = key;
        this.target = target;
        if (expiredSeconds > 0) {
            this.expiredSeconds = System.currentTimeMillis() + expiredSeconds * 1000;
        } else {
            this.expiredSeconds = -1L;
        }
    }

    public boolean isExpired() {
        return this.expiredSeconds != -1 && System.currentTimeMillis() > this.expiredSeconds;
    }

    public String key() {
        return key;
    }
}
