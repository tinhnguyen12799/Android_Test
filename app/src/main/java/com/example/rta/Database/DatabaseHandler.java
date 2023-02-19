package com.example.rta.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.rta.Model.FileTable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FileManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "fileTable";

    private static final String KEY_ID = "id";
    private static final String KEY_PATH = "path";
    private static final String KEY_NAME = "file_name";


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)", TABLE_NAME,KEY_ID, KEY_PATH, KEY_NAME);
        sqLiteDatabase.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_students_table);

        onCreate(sqLiteDatabase);
    }

    public ArrayList<FileTable> getData(){
        ArrayList<FileTable> files = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, path, file_name from fileTable", null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int file = cursor.getInt(0);
            String fileName = cursor.getString(1);
            String filePath = cursor.getString(2);

            files.add(new FileTable(file, fileName, filePath));
            cursor.moveToNext();
        }
        cursor.close();
        return files;
    }

    public void insertFile(FileTable file) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO fileTable (path, file_name ) VALUES (?,?)",
                new String[]{file.getPath(), file.getInstanceName() + ""});
    }
    public void clearFile() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM fileTable");
    }
}
