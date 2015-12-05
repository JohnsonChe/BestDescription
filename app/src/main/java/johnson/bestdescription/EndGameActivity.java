package johnson.bestdescription;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;



public class EndGameActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame_activity);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String endGameScore = intent.getStringExtra(gameActivity.EXTRA_MESSAGE);

        TextView endScore = (TextView)findViewById(R.id.gameScoreText);
        endScore.setText(endGameScore);
    }

    public void onReplayClick(){

    }

    public void onQuitClick(View v){
        finish();
    }
}
