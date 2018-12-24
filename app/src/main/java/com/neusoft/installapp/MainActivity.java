package com.neusoft.installapp;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.neusoft.installapp.constant.AppConstant;
import com.neusoft.installapp.util.InstallUtil;
import com.neusoft.installapp.util.SharePreferencesUtil;
import com.neusoft.installapp.util.StartActivityUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG", "调用成功啦~~~~");
        Intent intent=getIntent();
        if(intent.getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK){
            String path = intent.getStringExtra("path");
            String startPackageName = intent.getStringExtra("packageName");
            String activityName = intent.getStringExtra("activityName");
            Log.i("TAG", path);
            String appInfo = SharePreferencesUtil.get(this, AppConstant.APP_INFO, "").toString();
            Log.i("TAG", appInfo);
            if(appInfo != null && !"".equals(appInfo)){
                String[] info = appInfo.split(",");
                boolean addFlag = true;
                for(String str : info){
                    String packageName = str.split("&")[0];
                    if(packageName.equals(startPackageName)){
                        addFlag = false;
                    }
                }
                if(addFlag && startPackageName != null && !"".equals(startPackageName)){
                    appInfo += "," + startPackageName + "&" + activityName;
                    SharePreferencesUtil.put(this, AppConstant.APP_INFO, appInfo);
                }

            } else{
                SharePreferencesUtil.put(this, AppConstant.APP_INFO, startPackageName + "&" + activityName);
            }
            SharePreferencesUtil.put(this, startPackageName, path);
            boolean resutl = InstallUtil.runShellCommand(path);
            if(resutl){
                //启动
                if(activityName != null && !"".equals(activityName)){
                    StartActivityUtil.startActivity(this, startPackageName, activityName);
                }
                Log.d("TAG", "安装完成啦~~~~");
            } else {
                Log.d("TAG", "安装失败啦~~~~");
            }
        }
        this.finish();
    }
}
