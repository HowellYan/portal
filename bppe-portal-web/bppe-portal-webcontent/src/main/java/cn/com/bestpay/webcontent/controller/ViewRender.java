package cn.com.bestpay.webcontent.controller;


import cn.com.bestpay.template.engine.container.PageRender;
import cn.com.bestpay.template.engine.handlebars.HandlebarEngine;
import cn.com.bestpay.template.engine.model.redis.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Howell on 16/1/27.
 */
@Component
public class ViewRender {
    private static final Logger log = LoggerFactory.getLogger(ViewRender.class);

    @Autowired
    private PageRender pageRender;

    @Autowired
    private HandlebarEngine handlebarEngine;

    public String view(String domain, String path, HttpServletRequest request,
                       HttpServletResponse response, Map<String, Object> context, boolean rendHeadFoot) {

        if(rendHeadFoot){
            path += domain;
        } else {
            path += "/view";
        }

        if (request != null) {
            for (Object name : request.getParameterMap().keySet()) {
                context.put((String) name, request.getParameter((String) name));
            }
        }

        boolean isClassic = false;
//        try {
//            isClassic = pageRender.render(domain, path, context, true, rendHeadFoot);
//        } catch (Exception e) {
//            log.error(e.getMessage().toString());
//        }
        if (isClassic) {
            return "redirect:/login";
        } else {
            try {
                return handlebarEngine.naiveExec((Widget)null,path,context,false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log.error(e.getMessage().toString());
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage().toString());
            }
            return null;
        }
    }
}
