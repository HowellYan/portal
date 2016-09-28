package cn.com.bestpay.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by susie on 2016/9/28.
 */

public class ConfigProperty {
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getDebug(){
        return properties.getProperty("debug");
    }
}
