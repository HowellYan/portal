import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by susie on 2016/12/14.
 */
public class Hbs {
    public static void main(String[] args) {

        String handlebarsStr = "{{name}}";
        Map map = new HashMap<String, Object>();
        map.put("name", "zhanghg");



        Handlebars handlebars = new Handlebars();
        Template template = null;
        try {
            template = handlebars.compileInline(handlebarsStr);
            String result = template.apply(map);
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //此处为注册Handlebars自定义helper
    }
}
