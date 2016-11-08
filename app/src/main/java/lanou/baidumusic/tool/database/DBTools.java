package lanou.baidumusic.tool.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import lanou.baidumusic.tool.bean.ListBean;

/**
 * Created by ggs on 16/9/26.
 */
public class DBTools {
    private SQLiteDatabase database;

    public DBTools(Context context) {
        MyHelper helper = new MyHelper(context, DBValues.DB_NAME , null , 1);
        database = helper.getWritableDatabase();
    }

    // 删除列表
    public void deleteSongTable(ListBean bean) {
        database.delete(DBValues.TABLE_SONG, DBValues.SONG_TABLE_TITLE + " = ?",
                new String[]{bean.getTitle()});
    }

    // 删除表内的全部数据
    public void deleteAllSong() {
        database.execSQL("delete from " + DBValues.TABLE_SONG);
    }

    // 插入列表
    public void insertSongTable(ListBean bean) {
        ContentValues values = new ContentValues();
        values.put(DBValues.SONG_TABLE_TITLE, bean.getTitle());
        values.put(DBValues.SONG_TABLE_AUTHOR, bean.getAuthor());
        database.insert(DBValues.TABLE_SONG, null, values);
    }

    // 查询列表
    public ArrayList<ListBean> QueryAllSong() {
        ArrayList<ListBean> listBeanArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBValues.TABLE_SONG, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(DBValues.SONG_TABLE_TITLE));
                String author = cursor.getString(cursor.getColumnIndex(DBValues.SONG_TABLE_AUTHOR));

                ListBean bean = new ListBean();
                bean.setTitle(title);
                bean.setAuthor(author);
                listBeanArrayList.add(bean);
            }
            cursor.close();
        }
        return listBeanArrayList;
    }

}
