package cn.com.bestpay.portal.config.filter.tool;

import cn.com.bestpay.portal.common.utils.TimeUtil;
import cn.com.bestpay.portal.filter.SpeedModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Howell on 27/10/16.
 */
public class SpeedIimitation {

    public void setSpeedIimitation(HttpSession session,String RequestURI, String Methods, int SpeedNumber,
                                   long SpeedTime, int LimitTime) {
        SpeedModel speedModel = new SpeedModel();
        speedModel.setRequestURI(RequestURI);
        speedModel.setMethods(Methods);
        speedModel.setSpeedNumber(SpeedNumber);
        speedModel.setSpeedTime(SpeedTime);
        speedModel.setLimitTime(LimitTime);
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
                if(speedModelItem.getRequestURI().equals(RequestURI) && speedModelItem.getMethods().equals(Methods)){
                    isHaveIn = true;
                }
            }
            if(!isHaveIn){
                speedArrayList.add(speedModel);
            }
        }
        session.setAttribute("SpeedIimitationArrayList", speedArrayList);
    }

    public String speedIimitationAction(HttpSession session,String RequestURI, String Methods) {
        boolean isHaveIn = false;
        SpeedModel speedModel = null;
        ArrayList<SpeedModel> speedArrayList = (ArrayList<SpeedModel>)session.getAttribute("SpeedIimitationArrayList");
        if (speedArrayList == null){
            return "000000";
        }
        for (int i = 0; i < speedArrayList.size(); i++ ) {
            SpeedModel speedModelItem = speedArrayList.get(i);
            if(speedModelItem.getRequestURI().equals(RequestURI) && speedModelItem.getMethods().equals(Methods)){
                isHaveIn = true;
                speedModel = speedModelItem;
            }
        }
        if(!isHaveIn){
            return "000000";
        } else {
            if(speedModel != null){
                int accumulationNumber = speedModel.getAccumulationNumber();
                if(accumulationNumber  == 0){
                    setStartTime(speedModel);
                } else if(accumulationNumber == -1) {
                    cleanAction(speedModel);
                } else {
                    accumulationNumber(speedModel);
                    if(isIimitation(speedModel)){
                        return "111111";
                    }
                }
            }
        }
        return "000000";
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
    private boolean isIimitation(SpeedModel speedModel){
        if(speedModel.getStratTime() == null){
            return false;
        }
        String stratTime = speedModel.getStratTime();
        String endTime = TimeUtil.formatCurrentDate("yyyyMMddHHmmss");
        long minute = TimeUtil.getMinuteToBetween(stratTime, endTime, "yyyyMMddHHmmss");
        long SpeedTime = speedModel.getSpeedTime();

        int accumulationNumber = speedModel.getAccumulationNumber();
        int speedNumber = speedModel.getSpeedNumber();
        if(accumulationNumber >= speedNumber &&  minute <= SpeedTime){
            speedModel.setAccumulationNumber(-1);
            return true;
        }
        return false;
    }

    private void cleanAction(SpeedModel speedModel){
        String stratTime = speedModel.getStratTime();
        String endTime = TimeUtil.formatCurrentDate("yyyyMMddHHmmss");
        long minute = TimeUtil.getMinuteToBetween(stratTime, endTime, "yyyyMMddHHmmss");
        long SpeedTime = speedModel.getSpeedTime();
        long limitTime = speedModel.getLimitTime();
        if((minute - SpeedTime) >= limitTime){
            speedModel.setAccumulationNumber(0);
            speedModel.setStratTime(endTime);
        }
    }


}
