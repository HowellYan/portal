package cn.com.bestpay.template.engine.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Howell on 16/1/30.
 */

@Component
public class ExecutorFactory {
    @Autowired
    private ServiceExecutor springServiceExecutor;

    public ExecutorFactory() {
    }

    public ServiceExecutor getExecutor() {
        return this.springServiceExecutor;
    }
}

