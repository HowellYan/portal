package cn.com.bestpay.portal.config.filter.tool;

import cn.com.bestpay.portal.filter.SpeedModel;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Howell on 28/10/16.
 */
public class GetSpeedList {
    private static Logger logger = LoggerFactory.getLogger(GetSpeedList.class);

    public Set<SpeedModel> speedModelSet = null;

    public GetSpeedList(){
        speedModelSet = Sets.newHashSet();
        getVerificationList();
    }

    public Set<SpeedModel> getSpeedModelSet(){
        return speedModelSet;
    }

    /**
     * 获取列表
     */
    private void getVerificationList(){
        try {
            Resources.readLines(Resources.getResource("/properties/speed_list"), Charsets.UTF_8, new LineProcessor<Void>() {
                @Override
                public boolean processLine(String line) throws IOException {
                    if (!Strings.isNullOrEmpty(line)) {
                        logger.info("speed_list:"+line);
                        try {
                            JSONObject jsonObject = new JSONObject(line);
                            SpeedModel speedModel = new SpeedModel();
                                speedModel.setRequestURI(jsonObject.getString("RequestURI"));
                                speedModel.setMethods(jsonObject.getString("Methods"));
                                speedModel.setSpeedNumber(jsonObject.getInt("SpeedNumber"));
                                speedModel.setSpeedTime(jsonObject.getLong("SpeedTime"));
                                speedModel.setLimitTime(jsonObject.getInt("LimitTime"));
                            speedModelSet.add(speedModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            logger.error(e.getMessage().toString());
                        }
                    }
                    return true;
                }
                @Override
                public Void getResult() {
                    return null;
                }
            });
        } catch (IOException e) {
            logger.debug("protected_list:"+e.getMessage().toString());
        }
    }

}
