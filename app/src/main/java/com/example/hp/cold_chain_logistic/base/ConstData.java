package com.example.hp.cold_chain_logistic.base;

/**
 * save some global data
 * @Createdby LizYang
 * @Version: V 1.0
 * @Date: 2018/5/1
 * @Description:
 */

public class ConstData {

    public  static  String USER_ITERFACE_SERVER="http://192.168.56.1:8080/project/Cold_Chain_Logistic/";
    public static  String AHL_INTERFACE_SERVER="https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?";
    public static String SIGNATURE="stay hungry stay foolish.";
    public static  String NICKNAME="Liz";
    public static  String account;

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        ConstData.account = account;
    }

    public static String getAhlInterfaceServer() {
        return AHL_INTERFACE_SERVER;
    }

    public static void setAhlInterfaceServer(String ahlInterfaceServer) {
        AHL_INTERFACE_SERVER = ahlInterfaceServer;
    }

    public static String getSIGNATURE() {
        return SIGNATURE;
    }

    public static void setSIGNATURE(String SIGNATURE) {
        ConstData.SIGNATURE = SIGNATURE;
    }

    public static String getNICKNAME() {
        return NICKNAME;
    }

    public static void setNICKNAME(String NICKNAME) {
        ConstData.NICKNAME = NICKNAME;
    }

    public static String getUSER_ITERFACE_SERVER() {
        return USER_ITERFACE_SERVER;
    }

    public void setUSER_ITERFACE_SERVER(String USER_ITERFACE_SERVER) {
        this.USER_ITERFACE_SERVER = USER_ITERFACE_SERVER;
    }
}
