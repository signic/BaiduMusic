package lanou.baidumusic.tool.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 16/9/23.
 */
public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 在这个方法里创建表
    // 这个方法在初始化helper类的时候会主动调用
    // 这个方法只有在第一次初始化的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DBValues.TABLE_SONG
                + "(id integer primary key autoincrement, "
                + DBValues.SONG_TABLE_TITLE + " text,"
                + DBValues.SONG_TABLE_AUTHOR + " text)");
    }

    // 这个方法是用来更新数据库的,做数据库版本升级用的
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
