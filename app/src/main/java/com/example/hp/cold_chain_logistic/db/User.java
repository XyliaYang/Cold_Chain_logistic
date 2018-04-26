package com.example.hp.cold_chain_logistic.db;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/26
 */

public class User extends DataSupport {

    private int id;

    @Column(unique = true)  //唯一
    private String accout;

    @Column(nullable = false)
    private  String pwd;

    @Column(nullable = false) //非空
    private String signature;
    private String nickname;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
