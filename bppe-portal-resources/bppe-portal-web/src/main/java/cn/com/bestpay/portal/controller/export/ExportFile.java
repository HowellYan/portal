package cn.com.bestpay.portal.controller.export;

import cn.com.bestpay.portal.controller.export.model.Row;
import cn.com.bestpay.portal.controller.export.model.Worksheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by susie on 2016/12/14.
 */
@Controller
public class ExportFile {

    @RequestMapping(value="/api/export/download")
    public void downloadResource(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;

        try {
            out = response.getWriter();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=dict.txt");
            response.setContentType("application/octet-stream; charset=utf-8");
            for(int i=0; i< 6000; i++){
                out.write("asdasdasdas啊实打实大苏打士大夫但是国服进度反馈给司机的概率事件\n\t");
            }
            out.write("asdasdasdas");

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



    public static void main(String[] args) throws FileNotFoundException {
        ExportFile exportFile = new ExportFile();
        exportFile.output2();
    }

    public void output2() throws FileNotFoundException{
        long startTimne = System.currentTimeMillis();

        STGroup stGroup = new STGroupString("stringTemplate");

        //写入excel文件头部信息 /D:/howell/java/portal/bppe-portal-resources/target/classes/export
        ST head =  stGroup.getInstanceOf("head");
        File file = new File("D:/output.xls");
        PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
        writer.print(head.toString());
        writer.flush();

        int sheets = 300;
        //excel单表最大行数是65535
        int maxRowNum = 60000;

        //写入excel文件数据信息
        for(int i=0;i<sheets;i++){
            ST body =  stGroup.getInstanceOf("export/body");
            Worksheet worksheet = new Worksheet();
            worksheet.setSheet(" "+(i+1)+" ");
            worksheet.setColumnNum(3);
            worksheet.setRowNum(maxRowNum);
            List<Row> rows = new ArrayList<Row>();
            for(int j=0;j<maxRowNum;j++){
                Row row = new Row();
                row.setName1(""+new Random().nextInt(100000));
                row.setName2(""+j);
                row.setName3(i+""+j);
                rows.add(row);
            }
            worksheet.setRows(rows);
            body.add("worksheet", worksheet);
            writer.print(body.toString());
            writer.flush();
            rows.clear();
            rows = null;
            worksheet = null;
            body = null;
            Runtime.getRuntime().gc();
            System.out.println("正在生成excel文件的 sheet"+(i+1));
        }

        //写入excel文件尾部
        writer.print("</Workbook>");
        writer.flush();
        writer.close();
        System.out.println("生成excel文件完成");
        long endTime = System.currentTimeMillis();
        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
    }
}
