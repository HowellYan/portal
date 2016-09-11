package cn.com.bestpay.template.engine.container;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by Howell on 16/1/28.
 */
@Service
public class PageRender {
    private static final Logger log = LoggerFactory.getLogger(PageRender.class);

    @Autowired
    private SiteService siteService;

    @Autowired
    private HandlebarEngine handlebarEngine;

    public PageRender() {
    }

    public boolean render(String domain, String path, Map<String, Object> context, boolean view, boolean rendHeadFoot) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(domain), "domain can not be empty!");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(path), "path can not be empty!");
        Site site = this.siteService.findByDomain(domain);
        if(site == null) {
            log.error("no site found for domain ({}) ", domain);
            throw new NotFound404Exception("site not found");
        } else {
            return this.render(site, path, context, view, rendHeadFoot);
        }
    }

    public boolean render(Site site, String path, Map<String, Object> context, boolean view, boolean rendHeadFoot) throws NotFound404Exception {
        if (view) {
            if (site.getReleaseSiteInstanceId() == null) {
                log.warn("site(id={}) not released yet, try to render layout", site.getId());
                this._naiveRender(site, path, context);
                return false;
            }
        } else {
            if (site.getDefaultSiteInstanceId() == null) {
                log.error("site(id={}) has no defaultSiteInstance.", site.getId());
                throw new NotFound404Exception("site has no defaultSiteInstance");
            }
        }
        return true;
    }

    private void _naiveRender(Site site, String path, Map<String, Object> context) throws NotFound404Exception {
        String realPath;
        if(StringUtils.isEmpty(site.getRootPath())) {
            realPath = "/layout/" + path;
        } else {
            realPath = "/layout/" + site.getRootPath() + "/" + path;
        }

        try {
            context.put("_HTML_", this.handlebarEngine.naiveExec((Widget)null, realPath.replace("//", "/"), context, false));
        } catch (FileNotFoundException var6) {
            log.error("failed to find page {},cause:{}", realPath, var6.getMessage());
            throw new NotFound404Exception("page not found");
        }
    }

}
