package johnson.bestdescription;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questions.db";
    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_QUESTION = "Question";
    public static final String COLUMN_ANSWER = "Answer";
    public static final String COLUMN_POSSIBLEANSWERS = "Possible Answers";

    public DBHandler(Context context,String name,SQLiteDatabase.CursorFactory factor, int version){
        super(context, DATABASE_NAME, factor, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_QUESTIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                COLUMN_QUESTION + " TEXT " +
                COLUMN_ANSWER + " TEXT " +
                COLUMN_POSSIBLEANSWERS + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
