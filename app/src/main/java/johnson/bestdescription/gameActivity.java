package johnson.bestdescription;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.IOException;
import java.util.List;

public class gameActivity extends AppCompatActivity {

    public int currentQuestionNumber = 0;
    List<QuestionAnswer> questions;
    QuestionAnswer currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.game_activity);
        questions = setQuestionsFromDB();
        currentQuestion = setCurrentQuestion(questions);
        setQuestionWidgets(currentQuestion);
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
        buttonA.setText(currentQuestion.getAnswer());

        Button buttonB = (Button)findViewById(R.id.buttonB);
        buttonB.setText(currentQuestion.getIncorrect1());

        Button buttonC = (Button)findViewById(R.id.buttonC);
        buttonC.setText(currentQuestion.getIncorrect2());

        Button buttonD = (Button)findViewById(R.id.buttonD);
        buttonD.setText(currentQuestion.getIncorrect3());
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
