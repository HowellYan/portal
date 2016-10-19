package cn.com.bestpay.portal.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by yfzx_gd_yanghh pec_masteron 2016/9/28.
 */
@Configuration
@ComponentScan({

})
@ImportResource({ "classpath:properties/spring/root_config.xml" } )
public class SpringRootConfig {
}
