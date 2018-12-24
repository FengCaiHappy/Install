package com.neusoft.installapp.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2018/7/3.
 */

public class StartActivityUtil {

    public static void startActivity(Context context,  String packageName, String activityName){
        //要调用另一个APP的activity名字
        String activity = packageName + "." + activityName;
        ComponentName component = new ComponentName(packageName, activity);
        Intent in = new Intent();
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.setComponent(component);
        context.startActivity(in);
    }
}


