package johnson.bestdescription;

import java.util.Random;
import java.util.ArrayList;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.IOException;
import java.util.List;

public class gameActivity extends AppCompatActivity {

    public final String TAG = "johnson.bestdescription";
    public int currentQuestionNumber = 0;
    List<QuestionAnswer> questions;
    ArrayList<String> CurrentQuestionList = new ArrayList<>(4);
    QuestionAnswer currentQuestion;
    int randNum;
    int maxNum = 4;


    QuestionAnswer currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.game_activity);
        questions = setQuestionsFromDB();
        currentQuestion = setCurrentQuestion(questions);
        setQuestionWidgets(currentQuestion);

        currentQuestionNumber = 0;

    }

    public List<QuestionAnswer> setQuestionsFromDB(){
        DBHandler dbHandler = new DBHandler(this);

        try{
            dbHandler.createDataBase();
        }catch(IOException ioe){
            throw new Error("Unable to create database");
        }

        try{
            dbHandler.openDataBase();
        }catch (SQLiteException sqle){
            throw sqle;
        }

        List<QuestionAnswer> questions = dbHandler.getQuestionSet();
        dbHandler.close();

        return questions;
    }

    public void onButtonClick(View V){
        currentQuestionNumber++;
        currentQuestion = questions.get(currentQuestionNumber);
        //setCurrentQuestion(questions);
        setQuestionWidgets(currentQuestion);

        //Intent i = new Intent(this,gameActivity.class);
        //startActivity(i);
    }

    public QuestionAnswer setCurrentQuestion(List<QuestionAnswer> questions){
        QuestionAnswer currentQuestion = questions.get(currentQuestionNumber);

        //currentQuestionNumber++;

        return currentQuestion;
    }

    public void setQuestionWidgets(QuestionAnswer currentQuestion){
        TextView questionText = (TextView)findViewById(R.id.questionText);
        questionText.setText(currentQuestion.getQuestion());

        Button buttonA = (Button)findViewById(R.id.buttonA);
        Button buttonB = (Button)findViewById(R.id.buttonB);
        Button buttonC = (Button)findViewById(R.id.buttonC);
        Button buttonD = (Button)findViewById(R.id.buttonD);
        //randomNumber();



        CurrentQuestionList = moveToString(currentQuestion); //CRASHES HERE

        //randomNumber();

        buttonA.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);
        buttonB.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);
        buttonC.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);
        buttonD.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);

    }

    public int randomNumber(){
        Random random = new Random();
        int thisRandomNumber = random.nextInt(maxNum);
        Log.d(TAG,"randNum's Value");


        maxNum -= 1;
        if(maxNum == 0)
            maxNum = 4;

        randNum = thisRandomNumber;
        return thisRandomNumber;
    }

    public void removeFromQuestionList (ArrayList<String> CurrentQuestionList){
        CurrentQuestionList.remove(randNum);
    }

    public ArrayList<String> moveToString(QuestionAnswer currentQuestion){
        String q1 = currentQuestion.getAnswer();
        String q2 = currentQuestion.getIncorrect1();
        String q3 = currentQuestion.getIncorrect2();
        String q4 = currentQuestion.getIncorrect3();

        ArrayList<String> stringArrayList = new ArrayList<>(4);
        populateStringList(stringArrayList, q1, q2, q3, q4);

        return stringArrayList;
    }

    public void populateStringList(ArrayList<String> stringArrayList,String q1,String q2,String q3,String q4){
        stringArrayList.add(q1);
        stringArrayList.add(q2);
        stringArrayList.add(q3);
        stringArrayList.add(q4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
