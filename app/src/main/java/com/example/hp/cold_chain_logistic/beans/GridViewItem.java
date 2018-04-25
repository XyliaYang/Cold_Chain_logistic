package com.example.hp.cold_chain_logistic.beans;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/8
 */

public class GridViewItem {

    private  int imgId;
    private  String IMSI;

    public GridViewItem(int imgId, String IMSI) {
        this.imgId = imgId;
        this.IMSI = IMSI;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }
}
