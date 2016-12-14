package cn.com.bestpay.portal.controller.export;


import cn.com.bestpay.portal.pojo.AppcenterModel;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.*;

/**
 * Created by susie on 2016/12/14.
 */
@Controller
public class ExportFile {

    @RequestMapping(value="/api/export/download")
    public void downloadResource(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        Handlebars handlebars = new Handlebars();
        try {
            out = response.getWriter();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=abc.xls");
            response.setContentType("application/octet-stream; charset=utf-8");

            Template template =  handlebars.compile("export/header");
            Map map = new HashMap<String, Object>();
            map.put("sheetName", "abc");
            map.put("columnNum", "12");
            map.put("rowNum", "60000");
            String result = template.apply(map);
            out.write(result);


            template =  handlebars.compile("export/body");
            for(int i=0;i<60000;i++){
                List<AppcenterModel> modelList = new ArrayList<>();
                AppcenterModel appcenterModel = new AppcenterModel();
                map = new HashMap<String, Object>();
                appcenterModel.setAppId(""+i);
                appcenterModel.setAppName("a");
                appcenterModel.setAppUrl("http://a");
                modelList.add(appcenterModel);
                map.put("dataList", modelList);
                result = template.apply(map);
                out.write(result);
            }

            template =  handlebars.compile("export/footer");
            out.write(template.text());

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (out != null) {
                out.close();
            }
        }

/*
如果文件名有中文的话，进行URL编码，让中文正常显示
response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
*/


    }

}
