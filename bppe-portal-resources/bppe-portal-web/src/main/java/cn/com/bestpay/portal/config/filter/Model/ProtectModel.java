package cn.com.bestpay.portal.config.filter.Model;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by yfzx_gd_yanghh on 2016/10/14.
 */
public class ProtectModel implements Serializable {

    public final Pattern pattern;
    public final Set<String> httpMethods;

    public ProtectModel(Pattern pattern, Set<String> httpMethods) {
        this.pattern = pattern;
        this.httpMethods = httpMethods;
    }
}
