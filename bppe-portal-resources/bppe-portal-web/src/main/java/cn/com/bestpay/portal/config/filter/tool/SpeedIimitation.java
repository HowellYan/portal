package cn.com.bestpay.portal.config.filter.tool;

import cn.com.bestpay.portal.common.utils.TimeUtil;
import cn.com.bestpay.portal.filter.SpeedModel;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Howell on 27/10/16.
 */
public class SpeedIimitation {
    private static Logger logger = LoggerFactory.getLogger(SpeedIimitation.class);

    public void setSpeedIimitation(HttpSession session, SpeedModel speedModel) {
        ArrayList<SpeedModel> speedArrayList = (ArrayList<SpeedModel>)session.getAttribute("SpeedIimitationArrayList");
        if (speedArrayList == null){
            speedArrayList = new ArrayList<SpeedModel>();
            speedArrayList.add(speedModel);
        } else if (speedArrayList.size() == 0){
            speedArrayList.add(speedModel);
        } else {
            boolean isHaveIn = false;
            for (int i = 0; i < speedArrayList.size(); i++ ){
                SpeedModel speedModelItem = speedArrayList.get(i);
                if(speedModelItem.getRequestURI().equals(speedModel.getRequestURI())
                        && speedModelItem.getMethods().equals(speedModel.getMethods())){
                    isHaveIn = true;
                }
            }
            if(!isHaveIn){
                speedArrayList.add(speedModel);
            }
        }
        session.setAttribute("SpeedIimitationArrayList", speedArrayList);
    }



    /**
     *
     * @param session
     * @param RequestURI
     * @param Methods
     * @return true : 通过
     */
    public boolean speedIimitationAction(HttpSession session,String RequestURI, String Methods) {
        boolean isHaveIn = false;
        SpeedModel speedModel = null;
        int itemNumber = 0;
        ArrayList<SpeedModel> speedArrayList = (ArrayList<SpeedModel>)session.getAttribute("SpeedIimitationArrayList");
        if (speedArrayList == null){
            GetSpeedList getSpeedList = new GetSpeedList();
            for(SpeedModel speedModelItem : getSpeedList.getSpeedModelSet()) {
                new SpeedIimitation().setSpeedIimitation(session, speedModelItem);
            }
            speedArrayList  = new ArrayList<SpeedModel>();
        }
        for (int i = 0; i < speedArrayList.size(); i++ ) {
            SpeedModel speedModelItem = speedArrayList.get(i);
            if(speedModelItem.getRequestURI().equals(RequestURI) && speedModelItem.getMethods().equals(Methods)){
                isHaveIn = true;
                speedModel = speedModelItem;
                itemNumber = i;
            }
        }
        if(!isHaveIn){
            return true;
        } else {
            if(speedModel != null){
                int accumulationNumber = speedModel.getAccumulationNumber();
                if(accumulationNumber  == 0){
                    setStartTime(speedModel);
                    accumulationNumber(speedModel);
                } else if(accumulationNumber == -1) {
                    SpeedModel speedModelItem = cleanAction(speedModel);
                    if(speedModelItem == null){
                        return false;
                    } else {
                        speedArrayList.set(itemNumber, speedModelItem);
                        session.setAttribute("SpeedIimitationArrayList",speedArrayList);
                    }
                } else {
                    accumulationNumber(speedModel);
                    SpeedModel speedModelItem = isIimitation(speedModel);
                    if(speedModelItem != null){
                        speedArrayList.set(itemNumber, speedModelItem);
                        session.setAttribute("SpeedIimitationArrayList",speedArrayList);
                        return false;
                    }
                }
                speedArrayList.set(itemNumber, speedModel);
            }
            session.setAttribute("SpeedIimitationArrayList",speedArrayList);
        }
        return true;
    }

    /**
     *  添加累计数
     * @param speedModel
     * @return
     */
    private SpeedModel accumulationNumber(SpeedModel speedModel) {
        int accumulationNumber = speedModel.getAccumulationNumber();
        accumulationNumber++;
        speedModel.setAccumulationNumber(accumulationNumber);
        String stratTime = speedModel.getStratTime();
        String endTime = TimeUtil.formatCurrentDate("yyyyMMddHHmmss");
        long minute = TimeUtil.getMinuteToBetween(stratTime, endTime, "yyyyMMddHHmmss");
        long SpeedTime = speedModel.getSpeedTime();
        if(minute > SpeedTime){
            speedModel.setStratTime(endTime);
            speedModel.setAccumulationNumber(1);
        }
        return speedModel;
    }

    /**
     * 配置开始时间
     * @param speedModel
     * @return
     */
    private SpeedModel setStartTime(SpeedModel speedModel) {
        String stratTime = TimeUtil.formatCurrentDate("yyyyMMddHHmmss");
        speedModel.setStratTime(stratTime);
        return speedModel;
    }

    /**
     * 判断是否满足限制条件
     * @param speedModel
     * @return
     */
    private SpeedModel isIimitation(SpeedModel speedModel){
        if(speedModel.getStratTime() == null){
            return null;
        }
        String stratTime = speedModel.getStratTime();
        String endTime = TimeUtil.formatCurrentDate("yyyyMMddHHmmss");
        long minute = TimeUtil.getMinuteToBetween(stratTime, endTime, "yyyyMMddHHmmss");
        long SpeedTime = speedModel.getSpeedTime();

        int accumulationNumber = speedModel.getAccumulationNumber();
        int speedNumber = speedModel.getSpeedNumber();
        if(accumulationNumber > speedNumber &&  minute <= SpeedTime){
            speedModel.setAccumulationNumber(-1);
            speedModel.setStratTime(endTime);
            return speedModel;
        }
        return null;
    }

    private SpeedModel cleanAction(SpeedModel speedModel){
        String stratTime = speedModel.getStratTime();
        String endTime = TimeUtil.formatCurrentDate("yyyyMMddHHmmss");
        long minute = TimeUtil.getMinuteToBetween(stratTime, endTime, "yyyyMMddHHmmss");
        long limitTime = speedModel.getLimitTime();
        if(minute >= limitTime){
            speedModel.setAccumulationNumber(1);
            speedModel.setStratTime(endTime);
            return speedModel;
        } else {
            return null;
        }
    }

    public long getWaitTime(HttpSession session,String RequestURI, String Methods){
        SpeedModel speedModel = null;
        ArrayList<SpeedModel> speedArrayList = (ArrayList<SpeedModel>)session.getAttribute("SpeedIimitationArrayList");
        if (speedArrayList == null){
            speedArrayList  = new ArrayList<SpeedModel>();
        }
        for (int i = 0; i < speedArrayList.size(); i++ ) {
            SpeedModel speedModelItem = speedArrayList.get(i);
            if(speedModelItem.getRequestURI().equals(RequestURI) && speedModelItem.getMethods().equals(Methods)){
                speedModel = speedModelItem;
            }
        }
        if(speedModel != null){
            String stratTime = speedModel.getStratTime();
            String endTime = TimeUtil.formatCurrentDate("yyyyMMddHHmmss");
            long minute = TimeUtil.getMinuteToBetween(stratTime, endTime, "yyyyMMddHHmmss");
            long limitTime = speedModel.getLimitTime();
            return limitTime - minute;
        }
        return 0;
    }


}
