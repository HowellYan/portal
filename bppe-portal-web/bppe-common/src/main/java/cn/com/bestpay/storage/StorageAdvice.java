package cn.com.bestpay.storage;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Howell on 16/2/2.
 */
@Component
public class StorageAdvice {

    private static Logger logger = LoggerFactory.getLogger(StorageAdvice.class);

    @Autowired
    private StoragePolicy storagePolicy;


    public Object storageGetAround(final ProceedingJoinPoint jp) throws Throwable {

        Class clazz = ((MethodSignature) jp.getSignature()).getReturnType();

        //List, get inner object's simple name as storage chain name

        //Object
        StorageChain chain = this.storagePolicy.getStorageChain(clazz.getName());      //TODO not return type

        if (chain == null || chain.getCurrent() == null) {
            return jp.proceed();
        } else {
            String key = clazz.getSimpleName() + "@" + jp.getArgs()[0];//Joiner.on("-").join(jp.getArgs()); only one arg
            Storable storable = chain.get(key);

            if (storable == null || storable.isExpired()) {
                if (storable != null && storable.isExpired()) {
                    logger.debug("Object [{}] is expired!,call service:[{}]", key, jp.getTarget().getClass().getSimpleName());
                } else {
                    logger.debug("Object [{}] not found in storage,call service:[{}]", key, jp.getTarget().getClass().getSimpleName());
                }
                Object obj = jp.proceed();
                logger.debug("Object[{}] Result form API:[{}]", key, obj);
                if (obj != null)
                    chain.putForward(new Storable(key, obj, chain.getExpiredSeconds()));
                return obj;
            } else {
                return storable.target;
            }
        }
    }

    public void storageUpdateAround(final ProceedingJoinPoint jp) throws Throwable {

        jp.proceed();

        if (jp.getArgs().length != 1) {
            return;
        }

        Object updateTarget = jp.getArgs()[0];
        Class clazz = updateTarget.getClass();

        //Object
        StorageChain chain = this.storagePolicy.getStorageChain(clazz.getName());      //TODO not return type

        //缓存实效而不是直接更新，防止数据不完整
        if (chain != null) {
            String key = clazz.getSimpleName() + "@" + BeanUtils.getProperty(updateTarget, "id");

            chain.remove(key);

            logger.debug("Object[{}] removed from storage.", key);
        }
    }
}
