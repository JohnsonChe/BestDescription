package johnson.bestdescription;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questionDB";
    private static  String DATABASE_PATH = "";
    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_QUESTION = "Question";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";

        /*
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        */
        this.myContext = context;
    }

    public void createDataBase() throws IOException{

        Boolean dbExist = checkDataBase();
        if(!dbExist){
            this.getReadableDatabase().close();
            //this.close();
            try{
                copyDataBase();
            }catch(IOException e){
                throw new Error(e);
            }
        }
    }

    private boolean checkDataBase(){

        File dbFile = myContext.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();

    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLiteException{
        //Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    *Retrieves a the questions from database
    * Create Arraylist of QuestionAnswer objects
    * One by one, reads in each index of information into Arraylist questionset.
    * Returns questionset
     */
    public List<QuestionAnswer> getQuestionSet(){
        List<QuestionAnswer> questionSet = new ArrayList<QuestionAnswer>();
        Cursor c = myDataBase.rawQuery("SELECT * FROM " + DATABASE_NAME , null);
        while (c.moveToNext()){
        //Log.d("QUESTION", "Question Found in DB: " + c.getString(1));
            QuestionAnswer q = new QuestionAnswer();
            q.setQuestion(c.getString(1));
            q.setAnswer(c.getString(2));
            q.setIncorrect1(c.getString(3));
            q.setIncorrect2(c.getString(4));
            q.setIncorrect3(c.getString(5));
            questionSet.add(q);
        }
        return questionSet;
    }

}
