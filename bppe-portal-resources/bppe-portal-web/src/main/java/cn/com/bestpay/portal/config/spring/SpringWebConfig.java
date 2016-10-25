package cn.com.bestpay.portal.config.spring;


import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@EnableWebMvc
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan({
        "cn.com.bestpay.portal.aspects",
        "cn.com.bestpay.portal.service",
        "cn.com.bestpay.portal.SecurityScript",
        "cn.com.bestpay.portal.controller"
})
@ImportResource({ "classpath:properties/spring/mvc_config.xml" } )
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
