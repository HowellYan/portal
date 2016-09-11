package cn.com.bestpay.storage;

/**
 * Created by Howell on 16/2/2.
 */
public interface Storage {

    Storable get(String key);

    boolean put(Storable storable);

    void remove(String key);

}
