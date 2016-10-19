package cn.com.bestpay.portal.filter;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by yfzx_gd_yanghh on 2016/10/14.
 */
public class WhiteModel implements Serializable {

    public final Set<String> httpMethods;
    public final  Pattern pattern;

    public WhiteModel(Pattern pattern, Set<String> httpMethods) {
        this.pattern = pattern;
        this.httpMethods = httpMethods;
    }
}
