package com.example.sqlitedatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.session.PlaybackState;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String STUDENT_NAME = "STUDENTName";
    public static final String STUDENT_AGE = "STUDENTAge";
    public static final String ACTIVE_STUDENT = "ActiveSTUDENT";
    public static final String STUDENT_ID = "STUDENTID";
    public static final String STUDENT_TABLE = "StudentTable";

    public DbHelper(@Nullable Context context) {

        super(context, "studentDB.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableSTatementOne = "CREATE TABLE CustTable(STUDENTID Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME_FIRST + " Text, STUDENTAge Int, ActiveSTUDENT BOOL) ";
        String createTableSTatement = "CREATE TABLE " + STUDENT_TABLE + "(" + STUDENT_ID +
                " Integer PRIMARY KEY AUTOINCREMENT, " +
                STUDENT_NAME + " Text, " + STUDENT_AGE + " Int, " +
                ACTIVE_STUDENT + " BOOL) ";
        db.execSQL(createTableSTatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        onCreate(db);
    }

    public void addStudent(StudentModel STUDENTModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Hash map, as we did in bundles
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_NAME, STUDENTModel.getName());
        cv.put(STUDENT_AGE, STUDENTModel.getAge());
        cv.put(ACTIVE_STUDENT, STUDENTModel.isActive());
        db.insert(STUDENT_TABLE, null, cv);
        db.close();
    }

    public ArrayList<StudentModel> getAllStudents() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + STUDENT_TABLE, null);

        ArrayList<StudentModel> studentArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {

                studentArrayList.add(new StudentModel(cursorCourses.getString(1),
                        cursorCourses.getInt(2),
                        cursorCourses.getInt(3) == 1 ? true : false));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return studentArrayList;
    }

   /* public void updateStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, student.getName());
        values.put(STUDENT_ID, student.getId());
        values.put(ACTIVE_STUDENT, student.isActive());

        db.update(STUDENT_TABLE, values, STUDENT_ID + " = ?", new String[]{student.getId().ToString()});
        db.close();
    }*/

    public void deleteStudent(String rollNo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STUDENT_TABLE,STUDENT_ID + " = ?", new String[] {rollNo});
        db.close();
    }

    /*public List<StudentModel> selectAllStudents()
    {
        List<StudentModel> students=new List<>();
        String sql="Select * from "+STUDENT_TABLE;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                studentArrayList.add(new StudentModel(cursorCourses.))
            }
        }
    }*/
}
