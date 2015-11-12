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
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT " +
                COLUMN_ANSWER + " TEXT " +
                COLUMN_POSSIBLEANSWERS + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public void addQuestion(QuestionAnswer question){
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question.getQuestion());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
                if(c.getString(c.getColumnIndex("Question"))!= null){
                    dbString = c.getString(c.getColumnIndex("Question"));
            }
            c.moveToNext();
        }

        db.close();
        return dbString;
    }
}
