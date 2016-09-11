package cn.com.bestpay.storage.impl;


import cn.com.bestpay.storage.Storable;
import cn.com.bestpay.storage.Storage;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Howell on 16/2/2.
 */
@Component("inMemoryStore")
public class InMemoryStore implements Storage {
    private static Map<String, Storable> data = Maps.newConcurrentMap();

    @Override
    public Storable get(String key) {
        return data.get(key);
    }


    @Override
    public boolean put(Storable storable) {

        data.put(storable.key(), storable);
        return true;
    }

    @Override
    public void remove(String key) {
        data.remove(key);
    }

}
