package cn.com.bestpay.portal.View;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.cache.HighConcurrencyTemplateCache;
import com.github.jknack.handlebars.cache.TemplateCache;
import com.github.jknack.handlebars.helper.DefaultHelperRegistry;
import com.github.jknack.handlebars.springmvc.HandlebarsView;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Howell on 4/11/16.
 */
public class HbsViewResolver extends HandlebarsViewResolver {
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";
    private Handlebars handlebars;
    private ValueResolver[] valueResolvers;
    private boolean failOnMissingFile;
    private HelperRegistry registry;
    private boolean registerMessageHelper;
    private TemplateCache templateCache;

    public HbsViewResolver(Class<? extends HandlebarsView> viewClass) {
        this.valueResolvers = ValueResolver.VALUE_RESOLVERS;
        this.failOnMissingFile = true;
        this.registry = new DefaultHelperRegistry();
        this.registerMessageHelper = true;
        this.templateCache = new HighConcurrencyTemplateCache();
        this.setViewClass(viewClass);
        this.setContentType("text/html;charset=UTF-8");
        this.setPrefix("/");
        this.setSuffix(".hbs");
        this.setHelpers(this.setHelperMap());
    }

    public HbsViewResolver() {
        this(HandlebarsView.class);
    }

    public HbsViewResolver(Handlebars handlebars) {
        this(handlebars, HandlebarsView.class);
    }

    public HbsViewResolver(Handlebars handlebars, Class<? extends HandlebarsView> viewClass) {
        this(viewClass);
        this.handlebars = handlebars;
    }

    public Map<String, Helper<?>> setHelperMap(){
        Map<String, Helper<?>> helperMap = new HashMap<>();
        helperMap.put("if", new Helper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException {
                CharSequence result = "";
                String right = context.toString(), symbol="", left="";
                if(options.params.length == 2){
                    symbol = options.param(0).toString();
                    left = options.param(1).toString();
                } else {
                    return null;
                }
                if ( (symbol.equals("==")) && (right != null) && (left != null)) {
                    if (right.equals(left)) {
                        result = options.fn(context);
                    } else {
                        result = options.inverse(context);
                    }
                    return result;
                } else if((symbol.equals(">")) && (right != null) && (left != null)){
                    if (Integer.parseInt(right) > Integer.parseInt(left)) {
                        result = options.fn(context);
                    } else {
                        result = options.inverse(context);
                    }
                    return result;
                } else if((symbol.equals(">=")) && (right != null) && (left != null)){
                    if (Integer.parseInt(right) >= Integer.parseInt(left)) {
                        result = options.fn(context);
                    } else {
                        result = options.inverse(context);
                    }
                    return result;
                } else if((symbol.equals("<")) && (right != null) && (left != null)){
                    if (Integer.parseInt(right) < Integer.parseInt(left)) {
                        result = options.fn(context);
                    } else {
                        result = options.inverse(context);
                    }
                    return result;
                } else if((symbol.equals("<=")) && (right != null) && (left != null)){
                    if (Integer.parseInt(right) <= Integer.parseInt(left)) {
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

        return helperMap;
    }


}
