package cn.com.bestpay.user.base;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Howell on 16/1/30.
 */
public class InnerCookie  implements Serializable {
    private static final long serialVersionUID = -5440120009211709421L;

    private final Map<String,String> cookies;

    public InnerCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String get(String name){
        return cookies.get(name);
    }
}
