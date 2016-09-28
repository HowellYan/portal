package cn.com.bestpay.portal.filter;

import cn.com.bestpay.portal.filter.tool.CharResponseWrapper;
import cn.com.bestpay.portal.filter.tool.ClosureJs;
import cn.com.bestpay.portal.filter.tool.SetVersion;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.velocity.CssCompressorDirective;
import com.yahoo.platform.yui.compressor.CssCompressor;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
public class ResourcesResponseFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ResourcesResponseFilter.class);

    public ResourcesResponseFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         // 请求的uri
            String url = ((HttpServletRequest)servletRequest).getRequestURI();
            if (!url.equals("/healthcheck.html")){
                logger.info("请求：" + url);
            }

            CharResponseWrapper crw = new CharResponseWrapper((HttpServletResponse)servletResponse);
            filterChain.doFilter(servletRequest, crw);

            String content = crw.getContent();//response流的内容
            int replaceNumer = 0, oldNumer = content.length();
            String[] strArray = content.split("v=&version&");

            //此处可以对content做处理,然后再把content写回到输出流中
            String extensionName = getExtensionName(url);
            if(extensionName.equals("html") || extensionName.equals("js") ||  extensionName.equals("hbs")){
                content = SetVersion.setFileVersion(content);
            }
            if(extensionName.equals("html")){
                HtmlCompressor htmlCompressor = new HtmlCompressor();
                content = htmlCompressor.compress(content);
            }
            if(extensionName.equals("css")){
                content = SetVersion.chinaToUnicode(content);
                content = SetVersion.setFileVersion(content);
            }
            if(extensionName.equals("js")){
                String miniJS = ClosureJs.miniJS(content);
                if (!miniJS.equals("JS Closure Errors!")){
                    content = miniJS;
                }
            }
            if(!extensionName.equals("map") && !extensionName.equals("jsp")){
                if(extensionName.equals("html") || extensionName.equals("hbs")){
                    servletResponse.setContentLength(-1);
                } else {
                    servletResponse.setContentLength(content.length());
                }
                PrintWriter out = servletResponse.getWriter();
                out.write(content);
                out.flush();
                out.close();
            }
    }

    public void destroy() {

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
