package cn.com.bestpay.portal.filter;

import cn.com.bestpay.portal.filter.tool.CharResponseWrapper;
import cn.com.bestpay.portal.filter.tool.ClosureJs;
import cn.com.bestpay.portal.filter.tool.SetVersion;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@WebFilter(
        filterName = "ResourcesResponseFilter",
        urlPatterns = {"*.html","*.js","*.css","*.hbs"}
)
public class ResourcesResponseFilter  extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(ResourcesResponseFilter.class);

    public ResourcesResponseFilter() {
    }

    protected void doFilterInternal(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        // 请求的uri
        String url = httpServletRequest.getRequestURI();
        if (!url.equals("/healthcheck.html")){
            logger.info("请求：" + url);
        }

        CharResponseWrapper crw = new CharResponseWrapper((HttpServletResponse)httpServletResponse);
        filterChain.doFilter(httpServletRequest, crw);

        String content = crw.getContent();//response流的内容
        //此处可以对content做处理,然后再把content写回到输出流中
        String extensionName = getExtensionName(url);
        if(extensionName.equals("html") || extensionName.equals("js") ||  extensionName.equals("css") ||  extensionName.equals("hbs")){
            content = SetVersion.setFileVersion(content);
        }
        if(extensionName.equals("js")){
            content = ClosureJs.miniJS(content);
        }
        if(!extensionName.equals("map") && !extensionName.equals("jsp")){
            if(!extensionName.equals("html")){
                httpServletResponse.setContentLength(content.length());
            }else {
                httpServletResponse.setContentLength(-1);
            }
            PrintWriter out = httpServletResponse.getWriter();
            out.write(content);
            out.flush();
            out.close();
        }
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
