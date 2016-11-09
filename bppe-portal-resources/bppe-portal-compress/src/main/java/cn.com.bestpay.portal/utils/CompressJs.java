package cn.com.bestpay.portal.utils;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import org.apache.maven.plugin.logging.SystemStreamLog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by susie on 2016/11/9.
 */
public class CompressJs {
    public static String CompressJs(String codeStr){
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
                new SystemStreamLog().error("JS Closure Errors:"+ jsError[k].toString());
            }
            return "JS Closure Errors!";
        }
        String[] strings= compiler.toSourceArray();

        return strings[0].toString();
    }

}
