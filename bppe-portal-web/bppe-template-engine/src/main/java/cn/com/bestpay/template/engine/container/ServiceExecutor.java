package cn.com.bestpay.template.engine.container;

import java.util.Map;

/**
 * Created by Howell on 16/1/30.
 */
public interface ServiceExecutor {
    Object exec(String var1, Map<String, Object> var2);
}