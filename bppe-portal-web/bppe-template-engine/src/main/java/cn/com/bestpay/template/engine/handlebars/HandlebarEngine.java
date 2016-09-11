package cn.com.bestpay.template.engine.handlebars;


import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Howell on 16/1/28.
 */
@Component
public class HandlebarEngine {
    private static Logger logger = LoggerFactory.getLogger(HandlebarEngine.class);
    private Handlebars handlebars;

    private final TemplateLoader templateLoader;
    private final ComponentService componentService;
    private ExecutorFactory executorFactory;

    private Cache<String, String> executedComponentCache;
    private String baseDir;

    @Autowired
    public HandlebarEngine(ComponentService componentService, ExecutorFactory executorFactory,@Value("#{app.baseDir}") String baseDir, ServletContext servletContext) {
        this.baseDir = baseDir;
        logger.debug("baseDir : " + baseDir);
        this.componentService = componentService;
        this.executorFactory = executorFactory;
        this.templateLoader = new ServletAndFileTemplateLoader(servletContext, "/views", ".hbs", baseDir);
    }

    @PostConstruct
    public void init() {
        this.executedComponentCache = CacheBuilder.newBuilder().maximumSize(1000L).expireAfterWrite(10L, TimeUnit.MINUTES).build();
        this.handlebars = new Handlebars(this.templateLoader);
        this.handlebars.registerHelper("inject", new Helper<String>() {
            @Override
            public CharSequence apply(String compPath, Options options) throws IOException {
                logger.debug("compPath : " + compPath);
                HashMap tempContext = Maps.newHashMap();
                if(options.context.model() instanceof Map) {
                    tempContext.putAll((Map)options.context.model());
                }

                if(options.tagType == TagType.SECTION && StringUtils.isNotBlank(options.fn.text())) {
                    Map firstParam = (Map) JsonMapper.nonEmptyMapper().fromJson(options.fn.text(), Map.class);
                    if(firstParam != null) {
                        tempContext.putAll(firstParam);
                    }
                }

                Object firstParam1 = options.param(0, (Object)null);
                if(firstParam1 != null) {
                    cn.pojo.templateEngine.model.redis.Component component;
                    if(firstParam1 instanceof Boolean && ((Boolean)firstParam1).booleanValue()) {
                        component = HandlebarEngine.this.componentService.findByPath(compPath);
                        if(component == null) {
                            HandlebarEngine.logger.warn("call api failed, can\'t find component config for path:" + compPath);
                            return new SafeString(HandlebarEngine.this.exec((Widget)null, compPath, tempContext));
                        }

                        return new SafeString(HandlebarEngine.this.execComponent((Widget)null, component, tempContext));
                    }

                    if(firstParam1 instanceof String && StringUtils.isNotBlank((String)firstParam1)) {
                        component = new cn.pojo.templateEngine.model.redis.Component();
                        component.setPath(compPath);
                        component.setApis(ImmutableMap.of("default", (String)firstParam1));
                        return new SafeString(HandlebarEngine.this.execComponent((Widget)null, component, tempContext));
                    }
                }

                return new SafeString(HandlebarEngine.this.exec((Widget)null, compPath, tempContext));
            }
        });
        this.handlebars.registerHelper("component", new Helper<String>() {
            @Override
            public CharSequence apply(String className, Options options) throws IOException {
                StringBuilder compOpenTag = (new StringBuilder("<div class=\"")).append(className).append(" js-comp\"");
                Object id = options.context.get("_ID_");
                if(id != null) {
                    compOpenTag.append(" id=\"").append(id).append("\"");
                }

                Object style = options.context.get("_STYLE_");
                if(style != null) {
                    compOpenTag.append(" style=\"").append(style).append("\"");
                }

                Object compId = options.context.get("_COMP_ID_");
                if(compId != null) {
                    compOpenTag.append(" data-comp-id=\"").append(compId).append("\"");
                }

                Object compPath = options.context.get("_COMP_PATH_");
                if(compPath != null) {
                    compOpenTag.append(" data-comp-path=\"").append(compPath).append("\"");
                }

                compOpenTag.append(" >");
                return new SafeString(compOpenTag.toString() + "\n" + options.fn() + "\n</div>");
            }
        });
    }


    public String exec(Widget widget, String path, Map<String, Object> params) {
        try {
            return this.naiveExec(widget, path, params, true);
        } catch (FileNotFoundException var5) {
            logger.error("exec handlebars\' template failed: " + path, var5);
            return "";
        }
    }

    public String naiveExec(Widget widget, String path, Map<String, Object> params, boolean isComponent) throws FileNotFoundException {
        try {
            if(params == null) {
                params = Maps.newHashMap();
            }

            Template template;
            if(isComponent) {
                template = this.handlebars.compile(path + "/view");
                ((Map)params).put("_COMP_PATH_", path);
                if(widget != null) {
                    ((Map)params).put("_STYLE_", widget.getStyle());
                    ((Map)params).put("_ID_", widget.getId());
                    ((Map)params).put("_COMP_ID_", widget.getCompId());
                }
            } else {
                template = this.handlebars.compile(path);
                if(!((Map)params).containsKey("_PAGE_")) {
                    Page e = new Page();
                    e.setPath(path);
                    ((Map)params).put("_PAGE_", e);
                }
            }

            ((Map)params).put("_USER_", UserUtil.getCurrentUser());
            return template.apply(params);
        } catch (Exception var7) {

            logger.error("exec handlebars\' template failed: " + path, var7);
            return "";
        }
    }

    public String execComponent(final Widget widget, final cn.pojo.templateEngine.model.redis.Component comp, final Map<String, Object> context) {
        if(comp == null) {
            logger.debug("component is null for compId : {}", widget.getCompId());
            return "";
        } else {
            String key = null;
            if(widget != null && Objects.equal(Integer.valueOf(CacheBy.Widget.value()), comp.getCachedBy())) {
                key = "Wid:" + widget.getId();
            } else if(Objects.equal(Integer.valueOf(CacheBy.Component.value()), comp.getCachedBy())) {
                key = "Comp:" + comp.getId();
            }

            if(key != null) {
                try {
                    return (String)this.executedComponentCache.get(key, new Callable() {
                        public String call() {
                            return HandlebarEngine.this._execComponent(widget, comp, context);
                        }
                    });
                } catch (ExecutionException var6) {
                    logger.error("ExecComponent {} failed: {}", comp.getPath(), var6.getMessage());
                    return null;
                }
            } else {
                return this._execComponent(widget, comp, context);
            }
        }
    }

    private String _execComponent(Widget widget, cn.pojo.templateEngine.model.redis.Component comp, Map<String, Object> context) {
        if(comp.getApis() != null) {
            Object object = this.executorFactory.getExecutor().exec((String)comp.getApis().get("default"), context);
            context.put("_DATA_", object);
        } else {
            logger.warn("can\'t find api config for component:" + comp.getPath());
        }

        return this.exec(widget, comp.getPath(), context);
    }

    public <T> void registerHelper(String name, Helper<T> helper) {
        this.handlebars.registerHelper(name, helper);
    }



    public String getBaseDir() {
        return this.baseDir;
    }
}
