package com.example.rander.myapplication;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.os.Looper;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;


import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyDatabaseHelper mMyDatabaseHelper;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 定义一个内容观察者
     */
    private ContentObserver mContentObserver = new ContentObserver(mHandler) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyDatabaseHelper = MyDatabaseHelper.getInstance(this);

        //注册内容观察者,它监听的是MyContentProvider.CONTENT_URI，对应的字符串是＂content://com.csm.provider＂
        //根据Uri的格式如下
        //scheme://host:port/path
        //该Uri对应的host为com.csm.provider,也就是我们在AndroidManifest.xml自定义MyContentProvider的authorities
        //所以该CONTENT_URI对应的就是MyContentProvider提供的资源
        getContentResolver().registerContentObserver(MyContentProvider.CONTENT_URI,true,mContentObserver);

        /**
         * 顺循环想数据库中插入数据
         * getContentResolver().insert第一个参数为MyContentProvider.CONTENT_URI,
         * 因为MyContentProvider.CONTENT_URI对应的是MyContentProvider的资源，所以
         * 该insert的方法会调用到MyContentProvider的insert
         * 在insert方法里面再进行数据库操作．
         * 插入成功的话，再调用notifyChange方法通知观察者
         */
        new Thread()
        {
            @Override
            public void run() {
                int i = 0;
                mMyDatabaseHelper.deleteAll();
                while (true)
                {
                    ContentValues values = new ContentValues();
                    values.put(MyDatabaseHelper.COLUMN_ID,i);
                    values.put(MyDatabaseHelper.COLUMN_NAME,i+"");
                    getContentResolver().insert(MyContentProvider.CONTENT_URI,values);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        }.start();
    }
}
