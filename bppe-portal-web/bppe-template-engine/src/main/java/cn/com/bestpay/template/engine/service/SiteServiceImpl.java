package cn.com.bestpay.template.engine.service;


import cn.com.bestpay.annotations.ParamInfo;
import cn.com.bestpay.common.model.Paging;
import cn.com.bestpay.template.engine.model.redis.Site;
import cn.com.bestpay.template.engine.model.redis.SiteInstance;
import cn.com.bestpay.user.base.BaseUser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by Howell on 16/1/28.
 */
@Service("siteService")
public class SiteServiceImpl implements SiteService  {


    @PostConstruct
    public void init() {

    }


    public Long create(Long var1, Site var2) {
        return null;
    }


    public void delete(Long var1, Long var2) {

    }


    public SiteInstance createWithoutPagePart(Long var1, Site var2) {
        return null;
    }


    public void update(Site var1) {

    }


    public void release(Site var1) {

    }


    public Site findByDomain(String var1) {
        return null;
    }


    public List<Site> findByUser(@ParamInfo("user") BaseUser var1) {
        return null;
    }


    public List<Site> findByUserId(Long var1) {
        return null;
    }


    public Site findShopByUserId(Long var1) {
        return null;
    }


    public void setDefault(Long var1, Long var2) {

    }


    public Site findById(Long var1) {
        return null;
    }


    public Map<String, Object> findBySite(@ParamInfo("site") Site var1) {
        return null;
    }


    public void changeOwner(@ParamInfo("baseUser") BaseUser var1, @ParamInfo("siteId") Long var2, @ParamInfo("ownerId") Long var3) {

    }


    public Paging<Site> pagination(@ParamInfo("pageNo") Integer var1, @ParamInfo("size") Integer var2) {
        return null;
    }
}
