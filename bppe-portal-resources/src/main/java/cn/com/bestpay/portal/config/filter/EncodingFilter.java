package cn.com.bestpay.portal.config.filter;

import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Created by susie on 2016/9/28.
 */
@WebFilter(
        filterName = "encodingFilter",
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "encoding",value = "UTF-8"),
                @WebInitParam(name = "forceEncoding",value = "true")
        }
)

public class EncodingFilter extends CharacterEncodingFilter {
}
