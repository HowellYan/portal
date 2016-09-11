package cn.com.bestpay.storage;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Howell on 16/2/2.
 */
public class StoragePolicy {

    private static Logger logger = LoggerFactory.getLogger(StoragePolicy.class);
    private Map<String, StorageChain> storageChains = Maps.newConcurrentMap();

    public StoragePolicy(String[] chainNames, int[] expiredSeconds, Storage[]... storages) {
        for (int i = 0; i < chainNames.length; i++) {
            StorageChain storageChain = this.createChain(null, storages[i], 0);
            storageChain.setExpiredSeconds(expiredSeconds[i]);
            storageChains.put(chainNames[i], storageChain);
            logger.info("StorageChain [{}] init succeed,storages:[{}]",
                    chainNames[i], Joiner.on(",").join(storages[i]));
        }
    }

    protected StorageChain getStorageChain(String chainName) {
        return storageChains.get(chainName);
    }


    private StorageChain createChain(StorageChain pre, Storage[] storages, int idx) {

        StorageChain chain = new StorageChain();
        // current
        chain.setCurrent(storages[idx]);
        // link pre
        if (idx > 0) {
            chain.setPre(pre);
        }
        // create next
        if (idx < storages.length - 1) {
            chain.setNext(createChain(chain, storages, idx + 1));
        }
        return chain;
    }

}
