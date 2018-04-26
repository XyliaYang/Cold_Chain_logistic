package com.example.hp.cold_chain_logistic.db;

import org.litepal.crud.DataSupport;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/26
 */

public class IMSI_to_User extends DataSupport {

    private  int id;
    private int IMSI_id;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIMSI_id() {
        return IMSI_id;
    }

    public void setIMSI_id(int IMSI_id) {
        this.IMSI_id = IMSI_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
