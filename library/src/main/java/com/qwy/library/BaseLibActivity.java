package com.qwy.library;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qwy.library.utils.L;
import com.qwy.library.utils.WindowUtil;

/**
 * activity基类
 */
public abstract class BaseLibActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.a(getLocalClassName() + ".java", "初始化活动");
        L.a(getResources().getResourceEntryName(setContentView()) + ".xml", "初始化Layout");
        setContentView(setContentView());
        getSupportActionBar().hide();//隐藏标题栏
        WindowUtil.hideWindowStatusBar(this, true);
        onCreate();
    }

    protected abstract int setContentView();

    //初始化事件
    protected abstract void onCreate();
}
