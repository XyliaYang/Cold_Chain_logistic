package com.example.hp.cold_chain_logistic.db;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/10
 */

public class Para {

    public String name;
    public String type;
    public String value;

    public String getString(){
        return "\nname:"+getName()+"\nvalue:"+getValue();
    }


    public Para(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
