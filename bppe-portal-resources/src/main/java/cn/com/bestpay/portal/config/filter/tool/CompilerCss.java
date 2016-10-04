package cn.com.bestpay.portal.config.filter.tool;

import com.yahoo.platform.yui.compressor.CssCompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Howell on 4/10/16.
 */
public class CompilerCss {
    private static Logger logger = LoggerFactory.getLogger(CompilerCss  .class);
    public static String miniCSS(String codeStr){
        Reader in = new StringReader(codeStr);
        try {
            CssCompressor cssCompressor = new CssCompressor(in);
            StringWriter writer = new StringWriter();
            cssCompressor.compress(writer,1);
            codeStr = writer.getBuffer().toString();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage().toString());
        }
        return codeStr;
    }
}
