package cn.com.bestpay.portal.filter.tool;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
public class ClosureJs {
    private static Logger logger = LoggerFactory.getLogger(ClosureJs.class);

    public static String miniJS(String codeStr){
        Compiler.setLoggingLevel(Level.OFF);
        Compiler compiler = new Compiler();
        //设置压缩级别
        CompilerOptions options = new CompilerOptions();

        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        //警告级别
        WarningLevel.DEFAULT.setOptionsForWarningLevel(options);
        List<JSSourceFile> externalJavascriptFiles = new ArrayList<JSSourceFile>();
        List<JSSourceFile> primaryJavascriptFiles = new ArrayList<JSSourceFile>();

        primaryJavascriptFiles.add(JSSourceFile.fromCode("", codeStr));

        compiler.compile(externalJavascriptFiles, primaryJavascriptFiles, options);

        Result result=  compiler.getResult();
        if(!result.success){
            System.out.println(result.success);

            JSError[] jsError = compiler.getErrors();
            for(int k=0;k<jsError.length;k++){
                logger.info("JS Closure Errors:", jsError[k].toString());
                logger.debug("JS Closure Errors:",compiler.getErrors()[k].toString());
            }
            return "JS Closure Errors!";
        }
        String[] strings= compiler.toSourceArray();

        return strings[0].toString();
    }
}
