package com.howell.action.equals;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by Susie on 2016/8/25.
 */

public class EqualsJson {
    private static String log1;
    private String message;
    private static Logger log = (Logger) LoggerFactory.getLogger(EqualsJson.class);

    public EqualsJson() {
        // TODO Auto-generated constructor stub
        System.out.println("init!");
    }

    private  String equalsJsonValue(JSONObject standardJson, JSONObject responseJson) {//输入两个json，判断第一个里面的所有字段在第二个中的类型是否相同
        String err_message = "";
        Iterator it = standardJson.keys(); // 储存所有要验证的Key
        while (it.hasNext()) {
            String key = (String) it.next();

            if(standardJson.isNull(key)){ // 如果当前字段值为NULL,判断响应字段是否也为NULL
                if(!responseJson.isNull(key)){
                    log1 = key + " should be NULL!";
                    log.info("!!Failed: " + log1);
                    err_message = (err_message + "\n" + log1);
                }
            }
            else{
                String thisKeyValue = standardJson.get(key).toString(); //获取当前Key的标准值
                if(responseJson.isNull(key)) { //判断response中有无当前Key
                    if(responseJson.has(key)){
                        log1 = "------ExistError : " + key + " Can found, but value Not found";
                    } else{
                        log1 = "------ExistError : " + key + " Not found.";
                    }
                    err_message = (err_message + "\n" + log1);
                }
                else{  //response中找到Key了，再判断value
                    if(thisKeyValue.equals("org.json.JSONObject")){ //object类型的字段继续往内层判断
                        err_message += equalsJsonValue(standardJson.getJSONObject(key), responseJson.getJSONObject(key)); //!!进入递归时，保存当前错误信息
                    }
                    else{
                        String respKeyValue = responseJson.get(key).toString(); //获取响应的字段值
                        if(!respKeyValue.equals(thisKeyValue)){
                            String log1 = "------ValueError : " + key + " is " + respKeyValue + " (should be " + thisKeyValue + ")";
                            log.info("!!Failed: " + log1);
                            err_message = (err_message + "\n" + log1);
                        }
                    }
                }
            }
        }
        return err_message;
    }
    public Boolean respValueAssertion(String standardData, String resData) { //输入标准响应，转为json并调用比较函数，得到断言结果
        log.info("res: " + resData);
        JSONObject standardJson = new JSONObject(standardData);
        JSONObject jo = new JSONObject(resData);
        JSONObject responseJson = jo.getJSONObject("data");
        message = equalsJsonValue(standardJson, responseJson);
        System.out.println(message);
        log.info("------------------------ResultMessage--------------------" + message);
        if(message == ""){    //如果错误信息是空，说明断言结果通过
            return true;
        }
        return false;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
