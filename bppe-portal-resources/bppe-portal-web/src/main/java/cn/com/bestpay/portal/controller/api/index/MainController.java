package cn.com.bestpay.portal.controller.api.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Howell on 6/10/16.
 */
@RestController
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);


    @RequestMapping(value = "/api/index/main",method = RequestMethod.POST)
    public String main(@RequestBody String body){
        logger.info("RequestBody:"+body);
        return "abc";
    }
}
