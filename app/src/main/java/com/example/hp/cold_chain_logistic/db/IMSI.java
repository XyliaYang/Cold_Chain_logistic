package com.example.hp.cold_chain_logistic.db;

import org.litepal.crud.DataSupport;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/25
 */

public class IMSI extends DataSupport {
    private int id;  //自增字段
    private  String frameNum;
    private  String box_id;
    private  String serverIP;
    private  String serverPort;
    private  String sendTime;
    private  String signalPower;
    private  String bright;
    private  String box_type;
    private  String electric;
    private  String lock1_order;
    private  String lock1_state;
    private  String lock1_order_result;
    private  String lock2_order;
    private  String lock2_state;
    private  String lock2_order_result;
    private  String temperature1;
    private  String temperature2;
    private  String wetness1;
    private  String wetness2;
    private  String acceleration;
    private  String latitude;
    private  String longitude;
    private  String height;
    private  String speed;
    private  String password;
    private  String lid_state;
    private int userId; //不同用户id


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(String frameNum) {
        this.frameNum = frameNum;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSignalPower() {
        return signalPower;
    }

    public void setSignalPower(String signalPower) {
        this.signalPower = signalPower;
    }

    public String getBright() {
        return bright;
    }

    public void setBright(String bright) {
        this.bright = bright;
    }

    public String getBox_type() {
        return box_type;
    }

    public void setBox_type(String box_type) {
        this.box_type = box_type;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getLock1_order() {
        return lock1_order;
    }

    public void setLock1_order(String lock1_order) {
        this.lock1_order = lock1_order;
    }

    public String getLock1_state() {
        return lock1_state;
    }

    public void setLock1_state(String lock1_state) {
        this.lock1_state = lock1_state;
    }

    public String getLock1_order_result() {
        return lock1_order_result;
    }

    public void setLock1_order_result(String lock1_order_result) {
        this.lock1_order_result = lock1_order_result;
    }

    public String getLock2_order() {
        return lock2_order;
    }

    public void setLock2_order(String lock2_order) {
        this.lock2_order = lock2_order;
    }

    public String getLock2_state() {
        return lock2_state;
    }

    public void setLock2_state(String lock2_state) {
        this.lock2_state = lock2_state;
    }

    public String getLock2_order_result() {
        return lock2_order_result;
    }

    public void setLock2_order_result(String lock2_order_result) {
        this.lock2_order_result = lock2_order_result;
    }

    public String getTemperature1() {
        return temperature1;
    }

    public void setTemperature1(String temperature1) {
        this.temperature1 = temperature1;
    }

    public String getTemperature2() {
        return temperature2;
    }

    public void setTemperature2(String temperature2) {
        this.temperature2 = temperature2;
    }

    public String getWetness1() {
        return wetness1;
    }

    public void setWetness1(String wetness1) {
        this.wetness1 = wetness1;
    }

    public String getWetness2() {
        return wetness2;
    }

    public void setWetness2(String wetness2) {
        this.wetness2 = wetness2;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLid_state() {
        return lid_state;
    }

    public void setLid_state(String lid_state) {
        this.lid_state = lid_state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
