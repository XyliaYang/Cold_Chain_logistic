package com.example.hp.cold_chain_logistic.db;

import org.litepal.crud.DataSupport;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/25
 */

public class IMSI extends DataSupport {
    private int id;  //自增字段
    private  String box_id; //IMSI
    private int userId; //不同用户id


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
