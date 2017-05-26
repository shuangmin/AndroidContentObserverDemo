package com.example.rander.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * MyContentProvider可以看成是对数据库操作的一个包装
 * 通过直指定的URI，调用getContext().getContentResolver().insert()方法
 * 就会调用到此处的public Uri insert(Uri uri, ContentValues values)
 * 此时我们再转化为对数据库的操作,如本类中的insert方法
 *
 * 咱么监听到数据库变化的呢？
 * 其实在数据库发生变化的时候是我们手动调用的notifyChange方法
 */
public class MyContentProvider extends ContentProvider {
    private static final String AUTHORITIES = "com.csm.provider";
    private static final String PATH = "test";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES);
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int URI_CODE = 0x123;

    static
    {
        sUriMatcher.addURI(AUTHORITIES,PATH,URI_CODE);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * 当通过getContentResolver().insert(MyContentProvider.CONTENT_URI,values)的方式插入数据库
     * 会调用到此处，此时我们将其插入数据库并且
     * 通知观察者数据　发生变化
     * @param uri 要操作的资源uri
     * @param values 要插入的内容
     * @return
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        MyDatabaseHelper db = MyDatabaseHelper.getInstance(getContext());
        long row = db.insert(values);
        if(row != -1)
        {
            getContext().getContentResolver().notifyChange(CONTENT_URI,null);
        }
        return CONTENT_URI;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
