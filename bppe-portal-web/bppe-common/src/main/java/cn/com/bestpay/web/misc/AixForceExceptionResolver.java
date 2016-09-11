package cn.com.bestpay.web.misc;


import cn.com.bestpay.exception.JsonResponseException;
import com.google.common.base.Objects;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Howell on 16/2/1.
 */
public class AixForceExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if (isAjaxRequest(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                PrintWriter out = response.getWriter();
                if (ex instanceof JsonResponseException) {
                    JsonResponseException jsonEx = (JsonResponseException) ex;
                    response.setStatus(jsonEx.getStatus());
                    out.print(ex.getMessage());
                    out.close();
                    return new ModelAndView();
                } else if(ex instanceof BindException){
                    BindException bindException = (BindException)ex;
                    response.setStatus(400);
                    BindingResult result = bindException.getBindingResult();
                    if (result.hasErrors()) {
                        out.print(result.getFieldError().getDefaultMessage());
                    }
                    out.close();
                    return new ModelAndView();
                }else{
                    return null;
                }
            } catch (IOException e) {
                //ignore
            }
        }
        return null;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return Objects.equal(request.getHeader(HttpHeaders.X_REQUESTED_WITH), "XMLHttpRequest");

    }
}

