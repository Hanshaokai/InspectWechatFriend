package com.example.han.inspectwechatfriend.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.han.inspectwechatfriend.R;
import com.example.han.inspectwechatfriend.accessibility.InspectWechatFriendService;

import utils.Utils;

public class MainActivity extends AppCompatActivity {
    public static final String LauncherUI = "com.tencent.mm.ui.LauncherUI";
    public static final String MM = "com.tencent.mm";

    /***
     * Accessibility 一般用来做辅助性的功能 如抢票插件
     * 原理： 通过Android 无障碍功能实现模拟点击控件实现
     * 检查被删好友两种方法
     * 1向好友发送一条消息 如果对方已经把你删除 则消息发送失败
     * 2 建群法 新建一个不大于40人的群 如果其中有好友已经把你删除微信会有条消息提示
     * 3 整体 执行步骤 启动微信 点击+号 发起群聊 选择35个联系人 点击确定 点击群里详情 删除并退出
     * 以此轮训执行 直到所有好友轮训结束
     * 这里采用建群方式进行检查 通过无障碍辅助进行模拟点击
     * 使用方法 登录微信 打开辅助软件 点击 打开辅助功能按钮 跳转到无障碍设置吧辅助开关打开
     * 点击开始检查按钮开始一系列模拟点击 检查完成后会跳转到一个列表会把被删除好友列表展示出来
     *
     * @param savedInstanceState
     */
    /**
     * 监测操作
     * 在与应用互动时接受通知
     * 检索窗口内容
     * 检查与此互动窗口内容
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActiviy", Utils.getVersion(this));
        findViewById(R.id.openSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, InspectWechatFriendService.class));

            }
        });
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
                accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {
                    @Override
                    public void onAccessibilityStateChanged(boolean b) {
                        if (b) {
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setClassName(MM, LauncherUI);
                            startActivity(intent);
                        } else {
                            try {

                                //打开 系统设置中辅助功能
                                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "找到检测被删除好友辅助 然后开启服务即可", Toast.LENGTH_LONG)
                                        .show();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                if (!accessibilityManager.isEnabled()) {
                    try {

                        //打开系统设置中辅助功能
                        Intent inten = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

                        startActivity(inten);
                        Toast.makeText(MainActivity.this, "找到检测被删除好友辅助，然后开启服务即可", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClassName(MM, LauncherUI);
                }


            }
        });
        final Button button = (Button) findViewById(R.id.getCount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeleteFriendListActivity.class));
            }
        });
    }
}
