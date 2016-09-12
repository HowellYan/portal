package cn.com.bestpay.template.engine.service;


import cn.com.bestpay.common.utils.JsonMapper;
import cn.com.bestpay.template.engine.dao.redis.RedisComponentDao;
import cn.com.bestpay.template.engine.model.redis.Component;
import cn.com.bestpay.template.engine.model.redis.ComponentCategory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Howell on 16/1/30.
 */
@Service("componentService")
public class ComponentServiceImpl implements ComponentService {
    private static Logger logger = LoggerFactory.getLogger(ComponentServiceImpl.class);
    @Autowired
    private RedisComponentDao componentDao;
    @Autowired
    private RedisComponentCategoryDao componentCategoryDao;

    public ComponentServiceImpl() {
    }

    public Component findById(Long compId) {
        Preconditions.checkNotNull(compId);
        Component component = this.componentDao.findById(compId);
        this.buildApis(component);
        return component;
    }

    public List<Component> findByCategoryId(Long categoryId) {
        Preconditions.checkNotNull(categoryId);
        return this.componentDao.findByCategoryId(categoryId);
    }

    public Component findByPath(String path) {
        Preconditions.checkNotNull(path);
        Component component = this.componentDao.findByPath(path);
        this.buildApis(component);
        return component;
    }

    public void create(Component comp) {
        Preconditions.checkArgument(comp.getId() == null);
        this.componentDao.create(comp);
        logger.debug("component created - [id:{},name:{}]", comp.getId(), comp.getName());
    }

    public void delete(Long compId) {
        Preconditions.checkNotNull(compId);
        this.componentDao.delete(compId);
    }

    public void update(Component comp) {
        Preconditions.checkNotNull(comp.getId());
        this.componentDao.update(comp);
    }

    public List<Component> all() {
        List categories = this.componentCategoryDao.findByParentId(Long.valueOf(0L));
        ArrayList all = Lists.newArrayList(categories);
        all.addAll(this.componentCategoryDao.findByParentId(Long.valueOf(2L)));
        ArrayList components = Lists.newArrayList();
        Iterator i$ = all.iterator();

        while(i$.hasNext()) {
            ComponentCategory category = (ComponentCategory)i$.next();
            components.addAll(this.componentDao.findByCategoryId(category.getId()));
        }

        return components;
    }

    private void buildApis(Component component) {
        if(component != null && !Strings.isNullOrEmpty(component.getApi())) {
            Object _APIs = (Map) JsonMapper.nonDefaultMapper().fromJson(component.getApi(), JsonMapper.nonDefaultMapper().createCollectionType(Map.class, new Class[]{String.class, String.class}));
            if(_APIs == null) {
                _APIs = Maps.newHashMap();
                ((Map)_APIs).put("default", component.getApi());
            }

            component.setApi((String)null);
            component.setApis((Map)_APIs);
        }

    }
}

