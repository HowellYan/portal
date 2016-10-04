package cn.com.bestpay.portal.config.filter;

import cn.com.bestpay.portal.config.filter.tool.CharResponseWrapper;
import cn.com.bestpay.portal.config.filter.tool.ClosureJs;
import cn.com.bestpay.portal.config.filter.tool.SetVersion;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
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
        try {
            String url = ((HttpServletRequest)servletRequest).getRequestURI();
            if (!url.equals("/healthcheck.html")){
                //logger.debug("请求：" + url);
                logger.info("请求：" + ((HttpServletRequest)servletRequest).getRequestURL()+"?" + ((HttpServletRequest)servletRequest).getQueryString());
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
            if(extensionName.equals("html")||extensionName.equals("hbs")){
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
                servletResponse.setContentLength(content.getBytes().length);
//                if(extensionName.equals("html")){
//                    servletResponse.setContentLength(-1);
//                } else if(extensionName.equals("hbs")){
//                    servletResponse.setContentLength(content.getBytes().length);
//                } else {
//                    servletResponse.setContentLength(content.length());
//                }
                ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin","*");
                PrintWriter out = servletResponse.getWriter();
                out.write(content);
                out.flush();
                out.close();
            }
        } catch (Exception e){
            PrintWriter out = servletResponse.getWriter();
            out.write("");
            out.flush();
            out.close();
            logger.error(e.getMessage().toString());
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
