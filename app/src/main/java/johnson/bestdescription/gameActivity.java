package johnson.bestdescription;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class gameActivity extends AppCompatActivity {

    TextView questionText;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.game_activity);
        questionText = (TextView)findViewById(R.id.questionText);
        addQuestionToDB();
    }

    public void addQuestionToDB(){
       QuestionAnswer question = new QuestionAnswer(getString(R.string.question1));
       dbHandler = new DBHandler(this,null,null,1);
       dbHandler.addQuestion(question);
       printQuestions(dbHandler);
    }

    public void printQuestions(DBHandler dbHandler){
        String questionString = dbHandler.databaseToString();
        questionText.setText(questionString);
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
