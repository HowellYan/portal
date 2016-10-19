package cn.com.bestpay.portal.config.filter;


import cn.com.bestpay.portal.config.filter.tool.GetPermissonList;
import cn.com.bestpay.portal.filter.SessionModel;
import cn.com.bestpay.portal.filter.WhiteModel;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yfzx_gd_yanghh on 2016/10/14.
 */
@WebFilter(
        filterName = "SessionFilter",
        urlPatterns = {"/*"}
)
public class SessionFilter extends OncePerRequestFilter {

    private static final Pattern rewritePattern = Pattern.compile("/sites/[^/]+/");

    public SessionFilter(){
        GetPermissonList getPermissonList = new GetPermissonList();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Boolean isHave = false, doFilter = true; ;
        HttpSession session = httpServletRequest.getSession();
        // 请求的uri
        String requestURI = httpServletRequest.getRequestURI();
        Matcher matcher = rewritePattern.matcher(requestURI);
        requestURI = matcher.replaceAll("/");
        String method = httpServletRequest.getMethod().toLowerCase();
        logger.info("请求: { url:" + requestURI + ", method:" + method + " } ");

        // 过滤不存在于 white_list 文件的URL
        for (WhiteModel whiteItem : GetPermissonList.whiteModel) {  //method and uri matches with white list, ok
            if (whiteItem.httpMethods.contains(method) && whiteItem.pattern.matcher(requestURI).matches()) {
                doFilter = false;
                break;
            }
        }
        //过滤不存在于 session_list 文件的URL
        for (SessionModel sessionItem:GetPermissonList.sessionModel) {  //method and uri matches with white list, ok
            if (sessionItem.httpMethods.contains(method) && sessionItem.pattern.matcher(requestURI).matches()) {
                isHave = true;
                break;
            }
        }
        if( requestURI.equalsIgnoreCase("/") ){
            httpServletResponse.sendRedirect("/Index/main.html");
        }

        if (doFilter) {
            // 执行过滤
            // 从session中获取登录者实体
        } else {
            //Session 是否失效校验
            if (session.getAttribute("userSession") == null && isHave == false && method.equals("get")) {
                logger.info("Session失效");
                httpServletResponse.sendRedirect("/Login/main.html");
            } else if(session.getAttribute("userSession") != null &&  requestURI.equalsIgnoreCase("/Login/main.html")){
                httpServletResponse.sendRedirect("/Index/main.html");
            }
            if (httpServletRequest.isRequestedSessionIdFromURL()) {
                if (session != null) {
                    session.invalidate();
                }
            }


            // 如果不执行过滤，则继续
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

}
