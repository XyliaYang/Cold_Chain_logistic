package com.example.hp.cold_chain_logistic.beans;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/4
 */

public class ActivityCollector {

    public static List<Activity> activities=new ArrayList<Activity>();

    public static void addActivity(Activity activity){

        activities.add(activity);
    }

    public  static  void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public  static  void finishAll(){

        for(Activity activity:activities){
            if(!activity.isFinishing())
                activity.finish();
        }
    }

}
