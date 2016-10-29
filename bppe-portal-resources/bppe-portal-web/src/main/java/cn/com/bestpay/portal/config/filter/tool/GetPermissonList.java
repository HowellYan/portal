package cn.com.bestpay.portal.config.filter.tool;


import cn.com.bestpay.portal.filter.ProtectModel;
import cn.com.bestpay.portal.filter.SessionModel;
import cn.com.bestpay.portal.filter.WhiteModel;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by yfzx_gd_yanghh on 2016/10/14.
 */
public class GetPermissonList {
    private static Logger logger = LoggerFactory.getLogger(GetPermissonList.class);

    public static Set<WhiteModel> whiteModel = null;
    public static Set<ProtectModel> protectModel = null;
    public static Set<SessionModel> sessionModel = null;

    public GetPermissonList(){
        protectModel = Sets.newHashSet();
        whiteModel = Sets.newHashSet();
        sessionModel = Sets.newHashSet();
        getVerificationList();
    }

    /**
     * 获取请求校验的列表
     */
    private void getVerificationList(){
        try {
            Resources.readLines(Resources.getResource("/properties/white_list"), Charsets.UTF_8, new LineProcessor<Void>() {
                @Override
                public boolean processLine(String line) throws IOException {
                    if (!Strings.isNullOrEmpty(line)) {
                        Iterable<String> parts = Splitter.on(':').trimResults().split(line);
                        checkState(Iterables.size(parts) == 2, "illegal white_list configuration [%s]", line);
                        Pattern urlPattern = Pattern.compile("^"+Iterables.get(parts, 0)+"$");
                        String methods = Iterables.get(parts, 1).toLowerCase();
                        ImmutableSet.Builder<String> httpMethods = ImmutableSet.builder();
                        for (String method : Splitter.on(',').omitEmptyStrings().trimResults().split(methods)) {
                            httpMethods.add(method);
                        }
                        whiteModel.add(new WhiteModel(urlPattern, httpMethods.build()));
                    }
                    return true;
                }
                @Override
                public Void getResult() {
                    return null;
                }
            });
        } catch (IOException e) {
            logger.debug("protected_list:"+e.getMessage().toString());
        }

        try {
            Resources.readLines(Resources.getResource("/properties/protected_list"), Charsets.UTF_8, new LineProcessor<Void>() {
                @Override
                public boolean processLine(String line) throws IOException {
                    if (!Strings.isNullOrEmpty(line)) {
                        Iterable<String> parts = Splitter.on(':').trimResults().split(line);
                        checkState(Iterables.size(parts) == 2, "illegal perfect_list configuration [%s]", line);
                        Pattern urlPattern = Pattern.compile("^"+Iterables.get(parts, 0)+"$");
                        String methods = Iterables.get(parts, 1).toLowerCase();
                        ImmutableSet.Builder<String> httpMethods = ImmutableSet.builder();
                        for (String method : Splitter.on(',').omitEmptyStrings().trimResults().split(methods)) {
                            httpMethods.add(method);
                        }
                        protectModel.add(new ProtectModel(urlPattern, httpMethods.build()));
                    }
                    return true;
                }
                @Override
                public Void getResult() {
                    return null;
                }
            });
        } catch (IOException e) {
            logger.debug("perfect_list:"+e.getMessage().toString());
        }

        sessionModel = Sets.newHashSet();
        try {
            Resources.readLines(Resources.getResource("/properties/session_list"), Charsets.UTF_8, new LineProcessor<Void>() {
                @Override
                public boolean processLine(String line) throws IOException {
                    if (!Strings.isNullOrEmpty(line)) {
                        Iterable<String> parts = Splitter.on(':').trimResults().split(line);
                        checkState(Iterables.size(parts) == 2, "illegal white_list configuration [%s]", line);
                        Pattern urlPattern = Pattern.compile("^"+Iterables.get(parts, 0)+"$");
                        String methods = Iterables.get(parts, 1).toLowerCase();
                        ImmutableSet.Builder<String> httpMethods = ImmutableSet.builder();
                        for (String method : Splitter.on(',').omitEmptyStrings().trimResults().split(methods)) {
                            httpMethods.add(method);
                        }
                        sessionModel.add(new SessionModel(urlPattern, httpMethods.build()));
                    }
                    return true;
                }
                @Override
                public Void getResult() {
                    return null;
                }
            });
        } catch (IOException e) {
            logger.debug("protected_list:"+e.getMessage().toString());
        }
    }


}
