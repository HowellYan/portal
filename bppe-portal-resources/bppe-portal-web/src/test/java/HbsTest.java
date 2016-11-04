import cn.com.bestpay.portal.pojo.AppcenterModel;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Howell on 4/11/16.
 */
public class HbsTest {
    private static Handlebars handlebars = new Handlebars();

    public HbsTest(){
        HbsTest.registerAllHelpers(handlebars);
    }

    public  String replace(String handlebarsStr, Map<String, Object> obj) {
        String result = "";
        try {
            Template template = handlebars.compileInline(handlebarsStr);
            //此处为注册Handlebars自定义helper

            result = template.apply(obj);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void registerAllHelpers(Handlebars handlebars) {
        handlebars.registerHelper("if", new Helper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException {
                CharSequence result = "";

                System.out.println(options.params.length);
                System.out.println(options.param(0) +";"+options.hash.size());

                System.out.println(options.param(1) +";");
              //  String left = options.hash.get("compare").toString();


                String right = context.toString();
                if (right != null) {
                    if (right.equals("true")) {
                        result = options.fn(context);
                    } else {
                        result = options.inverse(context);
                    }
                    return result;
                } else {
                    return null;
                }
            }

        });

        handlebars.registerHelper("unless_eq", new Helper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException {
                CharSequence result = "";
                String right = context.toString();
                String left = options.param(0);
                if ((right != null) && (left != null)) {
                    if (right.equals(left)) {
                        result = options.inverse(context);
                    } else {
                        result = options.fn(context);
                    }
                    return result;
                } else {
                    return result;
                }
            }
        });
    }

    public static void main(String[] args) {
        String handlebarsStr= "{{name}} {{#if isTrue '==' 't '}} 123  {{else}} 321 {{/if}}  <ul>\n" +
                "            {{#each _DATA_}}\n" +
                "            <li>{{appId}}-{{appName}}</li>\n" +
                                " {{#if isTrue 'true' 'ss'}} 123  {{else}} 321 {{/if}}"+
                "            {{/each}}\n" +
                "        </ul>  您好： 您有{{count}} 信息";
        Map map = new HashMap<String ,Object>() ;
        map.put("isTrue", false) ;
        map.put("name", "zhanghg") ;
        map.put("count", 10) ;
        map.put("userName", "'Hi',首页!");

        List<AppcenterModel> modelList = new ArrayList<>();
        AppcenterModel appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("1");
        appcenterModel.setAppName("yang");
        appcenterModel.setAppUrl("123123");
        appcenterModel.setIsTrue("true");
        modelList.add(appcenterModel);
        appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("2");
        appcenterModel.setAppName("tang");
        appcenterModel.setAppUrl("123123");
        appcenterModel.setIsTrue("false");
        modelList.add(appcenterModel);
        appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("3");
        appcenterModel.setAppName("tang");
        appcenterModel.setAppUrl("123123");
        appcenterModel.setIsTrue("false");
        modelList.add(appcenterModel);
        map.put("_DATA_", modelList);

        HbsTest hbsTest = new HbsTest();
        String  ste = hbsTest.replace(handlebarsStr, map);
        System.out.println(ste);
    }

}
