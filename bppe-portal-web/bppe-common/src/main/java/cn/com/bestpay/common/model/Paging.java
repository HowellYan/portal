package cn.com.bestpay.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Howell on 16/1/28.
 */
public class Paging<T> implements Serializable {

    private static final long serialVersionUID = 755183539178076901L;

    private Long total;

    private List<T> data;

    public Paging() {
    }

    public Paging(Long total,List<T> data) {
        this.data = data;
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


}
