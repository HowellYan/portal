package cn.com.bestpay.portal.config.filter;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Howell on 10/10/16.
 */

public class DubboLogFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(DubboLogFilter.class);

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String methodName = invocation.getMethodName();
        String className = invoker.getInterface().getSimpleName();
        String serverAddress = invoker.getUrl().getAddress();
        Object[] args = invocation.getArguments();
        if (args.length > 0) {
            logger.info("[{}.{}] Start to calling remote [{}] . send request:[{}] ", className, methodName, serverAddress, JSONObject.toJSON(args));
        } else {
            logger.info("[{}.{}] Start to calling remote [{}] . ",className, methodName, serverAddress);
        }
        Result result = null;
        Long startTime = System.currentTimeMillis();
        try {
            result = invoker.invoke(invocation);
        } finally {
            Long endTime = System.currentTimeMillis();
            Long elapsed = endTime - startTime;
            if (result != null && result.getValue() != null) {
                logger.info("[{}.{}]  Finish calling remote . receive response:[{}].", className, methodName, JSONObject.toJSON(result.getValue()));
            } else {
                logger.info("[{}.{}]  Finish calling remote .", className, methodName);
            }
            logger.info("[{}.{}]  Elapsed:{} ms.",className, methodName,elapsed);
        }
        return result;
    }
}
