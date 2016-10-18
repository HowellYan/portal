package cn.com.bestpay.portal.config.spring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@EnableWebMvc
@Configuration
@ComponentScan({
        "cn.com.bestpay.portal.service",
        "cn.com.bestpay.portal.controller"
})
@ImportResource({ "classpath:properties/spring/mvc_config.xml" } )
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
