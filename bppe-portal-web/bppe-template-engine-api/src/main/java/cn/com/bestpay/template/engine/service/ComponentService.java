package cn.com.bestpay.template.engine.service;



import cn.com.bestpay.template.engine.model.redis.Component;

import java.util.List;

/**
 * Created by Howell on 16/1/30.
 */
public interface ComponentService {

    Component findById(Long var1);

    List<Component> findByCategoryId(Long var1);

    Component findByPath(String var1);

    void create(Component var1);

    void delete(Long var1);

    void update(Component var1);

    List<Component> all();
}
