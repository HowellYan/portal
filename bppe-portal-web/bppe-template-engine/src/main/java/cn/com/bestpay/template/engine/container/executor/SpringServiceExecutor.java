package cn.com.bestpay.template.engine.container.executor;


import cn.com.bestpay.annotations.ParamInfo;
import cn.com.bestpay.exception.ParamInfoException;
import cn.com.bestpay.template.engine.container.ServiceExecutor;
import cn.com.bestpay.template.engine.model.redis.Site;
import cn.com.bestpay.template.engine.model.redis.SiteInstance;
import cn.com.bestpay.user.base.BaseUser;
import cn.com.bestpay.user.base.InnerCookie;
import cn.com.bestpay.user.base.UserUtil;
import com.google.common.base.Defaults;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Howell on 16/1/30.
 */

@Component("springServiceExecutor")
public class SpringServiceExecutor implements ServiceExecutor {
    private static Logger logger = LoggerFactory.getLogger(SpringServiceExecutor.class);
    private final LoadingCache<String, ServiceInfo> serviceInfos;
    private DefaultConversionService converter = new DefaultConversionService();
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired(
            required = false
    )
    private ResultResolver resultResolver;

    public SpringServiceExecutor() {
        CacheLoader loader = new CacheLoader() {
            @Override
            public Object load(Object key) throws Exception {
                return null;
            }

            public ServiceInfo load(String key) throws Exception {
                Iterable parts = Splitter.on(':').trimResults().omitEmptyStrings().split(key);
                if(Iterables.size(parts) != 2) {
                    throw new IllegalArgumentException("bad api format,should be interfaceName:methodName,but is: " + key);
                } else {
                    Class klass = Class.forName((String)Iterables.get(parts, 0));
                    Method method = SpringServiceExecutor.this.findMethodByName(klass, (String)Iterables.get(parts, 1));
                    if(method == null) {
                        throw new NoSuchMethodException("failed to find method: " + key);
                    } else {
                        Class[] types = method.getParameterTypes();
                        String[] names = this.findNamesByAnnotation(method, types.length);
                        return new ServiceInfo(klass, method, types, names);
                    }
                }
            }

            private String[] findNamesByAnnotation(Method method, int length) {
                String[] names = new String[length];
                Annotation[][] all = method.getParameterAnnotations();

                for(int i = 0; i < length; ++i) {
                    Annotation[] annotations = all[i];
                    names[i] = null;
                    Annotation[] arr$ = annotations;
                    int len$ = annotations.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        Annotation annotation = arr$[i$];
                        if(annotation.annotationType() == ParamInfo.class) {
                            ParamInfo paramInfo = (ParamInfo)annotation;
                            names[i] = paramInfo.value();
                            break;
                        }
                    }

                    if(names[i] == null) {
                        throw new ParamInfoException("all arguments should has ParamInfo annotation,the method is:" + method.getDeclaringClass() + ":" + method.getName());
                    }
                }

                return names;
            }
        };
        this.serviceInfos = CacheBuilder.newBuilder().build(loader);
    }

    public Object exec(String api, Map<String, Object> params) {
        if(Strings.isNullOrEmpty(api)) {
            return null;
        } else {
            try {
                ServiceInfo e = (ServiceInfo)this.serviceInfos.getUnchecked(api);
                Class[] types = e.getTypes();
                Object[] concernedParams = new Object[types.length];

                for(int bean = 0; bean < types.length; ++bean) {
                    Class method = types[bean];
                    String object = e.getNames()[bean];
                    concernedParams[bean] = this.getParamObject(method, object, params);
                }

                Object var12 = this.applicationContext.getBean(e.getKlass());
                Method var13 = e.getMethod();
                Object var14 = var13.invoke(var12, concernedParams);
                if(this.resultResolver != null) {
                    return this.resultResolver.resolver(var14);
                }

                return var14;
            } catch (IllegalAccessException var9) {
                logger.error("illegal access service method,,", var9);
            } catch (Exception var11) {
                logger.error("service call failed,", var11);
            }

            return null;
        }
    }

    private Method findMethodByName(Class<?> beanClazz, String methodName) {
        Method[] methods = beanClazz.getMethods();
        Method[] arr$ = methods;
        int len$ = methods.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Method method = arr$[i$];
            if(method.getName().equals(methodName)) {
                return method;
            }
        }

        return null;
    }

    private Object getParamObject(Class<?> paramType, String paramName, Map<String, Object> params) {
        if(BaseUser.class.isAssignableFrom(paramType)) {
            if(paramName.equals("seller")) {
                return this.findSellerBySite(params.get("_SITE_"));
            } else {
                BaseUser _param2 = UserUtil.getCurrentUser();
                if(_param2 == null) {
                    logger.error("user not login.");
                } else {
                    return _param2;
                }
            }
        } else if(paramType == InnerCookie.class) {
            return UserUtil.getInnerCookie();
        } else if(paramType == Site.class && params.get("_SITE_") != null) {
            return params.get("_SITE_");
        } else if(paramType == SiteInstance.class && params.get("_SITE_INSTANCE_") != null) {
            return params.get("_SITE_INSTANCE_");
        } else if(Map.class.isAssignableFrom(paramType)) {
            HashMap _param1 = Maps.newHashMap();
            Iterator i$ = params.keySet().iterator();

            while(i$.hasNext()) {
                String key = (String)i$.next();
                Object value = params.get(key);
                if(value != null && value instanceof String) {
                    _param1.put(key, value.toString());
                }
            }

            return _param1;
        } else {
            Object _param = params.get(paramName);
            return _param == null? Defaults.defaultValue(paramType):this.converter.convert(_param, paramType);
        }
        return null;
    }

    private Object findSellerBySite(Object site) {
        if(site != null) {
            Site _site = (Site)site;
            return new BaseUser(_site.getUserId(), (String)null, BaseUser.TYPE.SELLER);
        } else {
            return null;
        }
    }

    protected static class ServiceInfo {
        private final Class<?> klass;
        private final Method method;
        private final Class<?>[] types;
        private final String[] names;

        public ServiceInfo(Class<?> klass, Method method, Class<?>[] types, String[] names) {
            this.klass = klass;
            this.method = method;
            this.types = types;
            this.names = names;
        }

        public Class<?> getKlass() {
            return this.klass;
        }

        public Method getMethod() {
            return this.method;
        }

        public Class<?>[] getTypes() {
            return this.types;
        }

        public String[] getNames() {
            return this.names;
        }
    }
}
