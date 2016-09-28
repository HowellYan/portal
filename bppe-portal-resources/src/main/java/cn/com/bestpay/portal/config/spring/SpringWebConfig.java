package cn.com.bestpay.portal.config.spring;


import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "cn.com.bestpay.portal.controller" })
@PropertySource(name = "system",value = {"classpath:properties/system.properties" })
@ImportResource({ "classpath:properties/spring/mvc_config.xml" } )
public class SpringWebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
