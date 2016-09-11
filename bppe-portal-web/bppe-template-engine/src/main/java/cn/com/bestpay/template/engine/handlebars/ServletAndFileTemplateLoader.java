package cn.com.bestpay.template.engine.handlebars;

import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import org.apache.commons.lang3.Validate;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Created by Howell on 16/1/28.
 */
public class ServletAndFileTemplateLoader extends ServletContextTemplateLoader {
    private FileTemplateLoader fileTemplateLoader;

    public ServletAndFileTemplateLoader(ServletContext servletContext, String prefix, String suffix, String baseDir) {
        super(servletContext, prefix, suffix);
        this.fileTemplateLoader = new IndexFileTemplateLoader(baseDir);
    }

    public TemplateSource sourceAt(String uri) throws IOException {
        Validate.notEmpty(uri, "The uri is required.", new Object[0]);
        uri = this.normalize(uri);
        return uri.startsWith("resource:")?super.sourceAt(uri.substring(9)):this.fileTemplateLoader.sourceAt(uri);
    }
}
