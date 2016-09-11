package cn.com.bestpay.template.engine.handlebars;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Howell on 16/1/28.
 */
@Component
public class LinnerHelper {
    @Autowired
    private HandlebarEngine handlebarEngine;
    private Map<String, String> assertPaths;

    public LinnerHelper() {
    }

    @PostConstruct
    public void init() throws IOException {
        File manifest = new File(this.handlebarEngine.getBaseDir() + "/../manifest.yml");
        if(manifest.exists()) {
            String fileContent = Files.toString(manifest, Charset.defaultCharset());
            this.assertPaths = (Map)(new Yaml()).loadAs(fileContent, Map.class);
        }

        this.handlebarEngine.registerHelper("fillPath", new Helper() {
            @Override
            public CharSequence apply(Object context, Options options) throws IOException {
                String assertPath = String.valueOf(context);
                return (CharSequence)(LinnerHelper.this.assertPaths != null && LinnerHelper.this.assertPaths.get(assertPath) != null?(CharSequence)LinnerHelper.this.assertPaths.get(assertPath):assertPath);
            }
        });
    }
}
