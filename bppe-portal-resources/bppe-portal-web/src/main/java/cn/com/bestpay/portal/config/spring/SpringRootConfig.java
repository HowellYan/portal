package cn.com.bestpay.portal.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@Configuration
@ComponentScan({
        "cn.com.bestpay.portal.service"
})
public class SpringRootConfig {
}
