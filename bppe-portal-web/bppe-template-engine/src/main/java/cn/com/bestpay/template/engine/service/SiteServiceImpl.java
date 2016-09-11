package cn.com.bestpay.template.engine.service;


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

    @Override
    public Long create(Long var1, Site var2) {
        return null;
    }

    @Override
    public void delete(Long var1, Long var2) {

    }

    @Override
    public SiteInstance createWithoutPagePart(Long var1, Site var2) {
        return null;
    }

    @Override
    public void update(Site var1) {

    }

    @Override
    public void release(Site var1) {

    }

    @Override
    public Site findByDomain(String var1) {
        return null;
    }

    @Override
    public List<Site> findByUser(@ParamInfo("user") BaseUser var1) {
        return null;
    }

    @Override
    public List<Site> findByUserId(Long var1) {
        return null;
    }

    @Override
    public Site findShopByUserId(Long var1) {
        return null;
    }

    @Override
    public void setDefault(Long var1, Long var2) {

    }

    @Override
    public Site findById(Long var1) {
        return null;
    }

    @Override
    public Map<String, Object> findBySite(@ParamInfo("site") Site var1) {
        return null;
    }

    @Override
    public void changeOwner(@ParamInfo("baseUser") BaseUser var1, @ParamInfo("siteId") Long var2, @ParamInfo("ownerId") Long var3) {

    }

    @Override
    public Paging<Site> pagination(@ParamInfo("pageNo") Integer var1, @ParamInfo("size") Integer var2) {
        return null;
    }
}
