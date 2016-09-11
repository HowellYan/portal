package cn.com.bestpay.template.engine.handlebars;

import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.jknack.handlebars.io.URLTemplateSource;
import org.apache.commons.lang3.Validate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Howell on 16/1/28.
 */
public class IndexFileTemplateLoader extends FileTemplateLoader {
    public IndexFileTemplateLoader(String basedir) {
        super(basedir);
    }

    public TemplateSource sourceAt(String uri) throws IOException {
        Validate.notEmpty(uri, "The uri is required.", new Object[0]);
        String location = this.resolve(this.normalize(uri));
        URL resource = this.getResource(location);
        if(resource != null) {
            return new URLTemplateSource(location, resource);
        } else {
            location = this.resolve(this.normalize(uri + "/index"));
            resource = this.getResource(location);
            if(resource == null) {
                throw new FileNotFoundException(location);
            } else {
                return new URLTemplateSource(location, resource);
            }
        }
    }
}
