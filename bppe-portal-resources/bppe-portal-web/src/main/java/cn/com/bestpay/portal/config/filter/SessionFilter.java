package cn.com.bestpay.portal.config.filter;

import cn.com.bestpay.portal.config.filter.Model.ProtectModel;
import cn.com.bestpay.portal.config.filter.Model.SessionModel;
import cn.com.bestpay.portal.config.filter.Model.WhiteModel;
import cn.com.bestpay.portal.config.filter.tool.GetPermissonList;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkState;

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
        Boolean isHave = false;
        HttpSession session = httpServletRequest.getSession();
        // 请求的uri
        String requestURI = httpServletRequest.getRequestURI();
        Matcher matcher = rewritePattern.matcher(requestURI);
        requestURI = matcher.replaceAll("/");
        String method = httpServletRequest.getMethod().toLowerCase();
        logger.info("请求1：" + requestURI);

        for (SessionModel sessionItem:GetPermissonList.sessionModel) {  //method and uri matches with white list, ok
            if (sessionItem.httpMethods.contains(method) && sessionItem.pattern.matcher(requestURI).matches()) {
                isHave = true;
                break;
            }
        }

        if (session.getAttribute("userSession") == null && isHave == false) {
            logger.info("Session失效");
            logger.info("appShow session:" + session.getAttribute("userSession"));
            httpServletResponse.sendRedirect("/Login/main.html");
        }

        // 是否过滤
        boolean doFilter = true;

        for (WhiteModel whiteItem : GetPermissonList.whiteModel) {  //method and uri matches with white list, ok
            if (whiteItem.httpMethods.contains(method) && whiteItem.pattern.matcher(requestURI).matches()) {
                doFilter = false;
                break;
            }
        }

        if (doFilter) {
            // 执行过滤
            // 从session中获取登录者实体
        } else {
            if (httpServletRequest.isRequestedSessionIdFromURL()) {
                if (session != null) {
                    session.invalidate();
                }
            }
            // wrap response to remove URL encoding
            HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpServletResponse) {
                @Override
                public String encodeRedirectUrl(String url) {
                    return url;
                }
                @Override
                public String encodeRedirectURL(String url) {
                    return url;
                }
                @Override
                public String encodeUrl(String url) {
                    return url;
                }
                @Override
                public String encodeURL(String url) {
                    return url;
                }
            };
            // 如果不执行过滤，则继续
            filterChain.doFilter(httpServletRequest, wrappedResponse);
        }
    }



}
