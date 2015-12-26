package johnson.bestdescription;

import java.util.Random;
import java.util.ArrayList;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.List;

public class gameActivity extends AppCompatActivity {

    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    ImageButton nextButton;
    TextView scoreText;
    RelativeLayout gameBackGround;

    Drawable defaultBG;

    public final static String EXTRA_MESSAGE = "johnson.bestdescription";
    public final String TAG = "johnson.bestdescription";
    public int currentQuestionNumber = 0;
    List<QuestionAnswer> questions;
    ArrayList<String> CurrentQuestionList = new ArrayList<>(4);
    QuestionAnswer currentQuestion;

    int randNum;
    int maxNum = 4;
    int currentAnswerIndex;
    int currentPossibleAnswerIndex = 1;

    int score;
    int lastPage;

    String [] bgHexCodes = {"#009688",
                            "#E64A19",
                            "#673AB7",
                            "#009688",
                            "#ef5350",
                            "#B25B66",
                            "#85144B"};

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.game_activity);

        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);

        nextButton = (ImageButton) findViewById(R.id.nextButton);
        nextButton.setEnabled(false);

        gameBackGround = (RelativeLayout)findViewById(R.id.gameBackGround);
        scoreText = (TextView)findViewById(R.id.scoreCountText);
        defaultBG = buttonA.getBackground();

        questions = setQuestionsFromDB();
        currentQuestion = setCurrentQuestion(questions);
        setQuestionWidgets(currentQuestion);

        currentQuestionNumber = 0;

        score = 0;
        lastPage = questions.size();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public List<QuestionAnswer> setQuestionsFromDB() {
        DBHandler dbHandler = new DBHandler(this);

        try {
            dbHandler.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            dbHandler.openDataBase();
        } catch (SQLiteException sqle) {
            throw sqle;
        }

        List<QuestionAnswer> questions = dbHandler.getQuestionSet();
        dbHandler.close();

        return questions;
    }

    /*
    * onButtonClick()
    *When user presses a button, each button checks to see
    * if the current question's answer matches the button text
    * button turn green.
    * if the selected answer is wrong, the button turns red
     */

    public void onButtonClick(View V) {
        switch(V.getId()){
            case R.id.buttonA:
                if(currentQuestion.getAnswer() == buttonA.getText().toString()) {
                    buttonA.setBackgroundColor(Color.GREEN);
                    updateScore();
                }
                else {
                    buttonA.setBackgroundColor(Color.RED);
                    displayCorrectAnswer();
                }
                disableAllButtons();
                break;
            case R.id.buttonB:
                if(currentQuestion.getAnswer() == buttonB.getText().toString()) {
                    buttonB.setBackgroundColor(Color.GREEN);
                    updateScore();
                }
                else {
                    buttonB.setBackgroundColor(Color.RED);
                    displayCorrectAnswer();
                }
                disableAllButtons();
                break;
            case R.id.buttonC:
                if(currentQuestion.getAnswer() == buttonC.getText().toString()) {
                    buttonC.setBackgroundColor(Color.GREEN);
                    updateScore();
                }
                else {
                    buttonC.setBackgroundColor(Color.RED);
                    displayCorrectAnswer();
                }
                disableAllButtons();
                break;
            case R.id.buttonD:
                if(currentQuestion.getAnswer() == buttonD.getText().toString()) {
                    buttonD.setBackgroundColor(Color.GREEN);
                    updateScore();
                }
                else {
                    buttonD.setBackgroundColor(Color.RED);
                    displayCorrectAnswer();
                }
                disableAllButtons();
                break;

        }
    }

    public void disableAllButtons(){
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
        nextButton.setEnabled(true);
    }

    public void enableAllButtons(){
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
        nextButton.setEnabled(false);
    }

    /*
    * displayCorrectAnswer()
    *Sets the background color of the correct
    * choice to green
     */
    public void displayCorrectAnswer(){
        switch(currentAnswerIndex){
            case 1:
                buttonA.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                buttonB.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                buttonC.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                buttonD.setBackgroundColor(Color.GREEN);
                break;
        }
    }
    public void updateScore(){
        score++;
        scoreText.setText(String.valueOf(score));
    }

    /*
    * onNextButtonClick()
    *When the next button is clicked
    * update the current Question Number
    * If the question number is not equal to the next
    * last page, set the current question index
    * reset the color backgrounds for all the buttons
    * to default.
    *
    * if it is the last page, call EndGameActivity
     */
    public void onNextButtonClick(View v) {
        enableAllButtons();

        currentQuestionNumber++;
        if(currentQuestionNumber != lastPage) {
           currentQuestion = questions.get(currentQuestionNumber);
           setQuestionWidgets(currentQuestion);

           buttonA.setBackgroundDrawable(defaultBG);
           buttonB.setBackgroundDrawable(defaultBG);
           buttonC.setBackgroundDrawable(defaultBG);
           buttonD.setBackgroundDrawable(defaultBG);

           setGameBackground();
       }
       else {
               Intent intent = new Intent(this, EndGameActivity.class);
               String endGameScore = scoreText.getText().toString();
               intent.putExtra(EXTRA_MESSAGE, endGameScore);
               startActivity(intent);
               finish();
       }
    }

    /*
    *setGameBackground()
    *String [] bgHexCodes stores hex codes of Colors
    *Randomly generating an int from 0 to bgHexCodes.length
    *Set background color.
     */
    public void setGameBackground(){
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(bgHexCodes.length);
        gameBackGround.setBackgroundColor(Color.parseColor(bgHexCodes[randomColorIndex]));
    }

    /*
    * setCurrentQuestion()
    *Randomly generates a number index.
    * Sets the currentquestion as the index generated.
    * remove the index from questions list.
     */
    public QuestionAnswer setCurrentQuestion(List<QuestionAnswer> questions) {
        Random randIndex = new Random();

        int randomQuestionIndex = randIndex.nextInt(questions.size());
        QuestionAnswer currentQuestion = questions.get(randomQuestionIndex);
        questions.remove(randomQuestionIndex);

        return currentQuestion;
    }

    public void setQuestionWidgets(QuestionAnswer currentQuestion) {
        TextView questionText = (TextView) findViewById(R.id.questionText);
        questionText.setText(currentQuestion.getQuestion());

        CurrentQuestionList = moveToString(currentQuestion);

        buttonA.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);
        buttonB.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);
        buttonC.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);
        buttonD.setText(CurrentQuestionList.get(randomNumber()));
        removeFromQuestionList(CurrentQuestionList);

    }

    public int randomNumber() {
        Random random = new Random();
        int thisRandomNumber = random.nextInt(maxNum);
        Log.d(TAG, "randNum's Value");

        maxNum -= 1;
        if (maxNum == 0)
            maxNum = 4;

        randNum = thisRandomNumber;
        checkForAnswerIndex();

        return thisRandomNumber;
    }

    public void checkForAnswerIndex(){
        if(randNum == 0 && CurrentQuestionList.get(0) == currentQuestion.getAnswer())
            currentAnswerIndex = currentPossibleAnswerIndex;

        if(currentPossibleAnswerIndex == 4)
            currentPossibleAnswerIndex = 1;
        else
            currentPossibleAnswerIndex++;

    }

    public void removeFromQuestionList(ArrayList<String> CurrentQuestionList) {
        CurrentQuestionList.remove(randNum);
    }

    public ArrayList<String> moveToString(QuestionAnswer currentQuestion) {
        String q1 = currentQuestion.getAnswer();
        String q2 = currentQuestion.getIncorrect1();
        String q3 = currentQuestion.getIncorrect2();
        String q4 = currentQuestion.getIncorrect3();

        ArrayList<String> stringArrayList = new ArrayList<>(4);
        populateStringList(stringArrayList, q1, q2, q3, q4);

        return stringArrayList;
    }

    public void populateStringList(ArrayList<String> stringArrayList, String q1, String q2, String q3, String q4) {
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "game Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://johnson.bestdescription/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "game Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://johnson.bestdescription/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
