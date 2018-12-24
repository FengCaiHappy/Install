package com.neusoft.installapp.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.neusoft.installapp.MainActivity;
import com.neusoft.installapp.constant.AppConstant;
import com.neusoft.installapp.util.CheckApkUtil;
import com.neusoft.installapp.util.InstallUtil;
import com.neusoft.installapp.util.SharePreferencesUtil;
import com.neusoft.installapp.util.StartActivityUtil;

import java.util.List;

public class BootBroadcastReceiver extends BroadcastReceiver {
    //重写onReceive方法  
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("TAG", "开机自动服务自动启动.....");
       //启动应用，参数为需要自动启动的应用的包名
        String appInfo = SharePreferencesUtil.get(context, AppConstant.APP_INFO, "").toString();
        Log.i("TAG", appInfo);
        boolean isAppRunning = false;
        if(appInfo != null && !"".equals(appInfo)){
            String[] info = appInfo.split(",");
            for(String str : info){
                String packageName = str.split("&")[0];
                String activityName = str.split("&")[1];
                //未安装此APP,安装
                if(!CheckApkUtil.checkApkExist(context, packageName)){
                    String path = SharePreferencesUtil.get(context, packageName, "").toString();
                    boolean resutl = InstallUtil.runShellCommand(path);
                    if(resutl){
                        //启动
                        if(activityName != null && !"".equals(activityName)){
                            isAppRunning = true;
                            StartActivityUtil.startActivity(context, packageName, activityName);
                        }
                        Log.d("TAG", "安装完成啦~~~~");
                    } else {
                        Log.d("TAG", "安装失败啦~~~~");
                    }
                    }
            }
        }
        if(!isAppRunning){
            StartActivityUtil.startActivity(context, "hkr.reachauto.com.alpsdatabridge", "HkrProtalActivity");
        }
        System.exit(0);
    }  
  
}  
