package com.example.rander.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 自己撞创建一个数据库
 * 数据库中存在表csm_test_database
 * 表中有三个字段，分别如下:
 * _id,_name,_age
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mSQLiteDatabase;
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "csm";
    private static final String TABLE_NAME = "csm_test_database";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_AGE = "_age";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT ," +
            COLUMN_AGE + " INTEGER ) ";

    private static MyDatabaseHelper mMyDatabaseHelper;

    private SQLiteDatabase mDb;

    public static MyDatabaseHelper getInstance(final Context context) {
        if (mMyDatabaseHelper == null) {
            synchronized (MyDatabaseHelper.class) {
                if (mMyDatabaseHelper == null) {
                    mMyDatabaseHelper = new MyDatabaseHelper(context);
                }
            }
        }

        return mMyDatabaseHelper;
    }


    private MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //$NON-NLS-1$
        onCreate(db);
    }

    /**
     * 插入一个数据库项
     * @param values
     * @return
     */
    public long insert(ContentValues values)
    {
        mDb = mMyDatabaseHelper.getWritableDatabase();
        return mDb.insert(TABLE_NAME,null,values);
    }

    public void deleteAll()
    {
        mDb = mMyDatabaseHelper.getWritableDatabase();
        mDb.delete(TABLE_NAME,null,null);
    }
}
