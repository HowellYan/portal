package cn.com.bestpay.portal.controller.export.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susie on 2016/12/14.
 */
public class Student {
    private String name;

    private int age;

    private List<String> hobbys = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
    }
}
