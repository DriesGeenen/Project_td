package be.thomasmore.project_td;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;

public class LadenActivity extends AppCompatActivity {

    ArrayList<String> nodigeParen;
    boolean ouderDan5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_laden);

        Intent intent = getIntent();
        nodigeParen = intent.getStringArrayListExtra("nodigeParen");
        ouderDan5 = intent.getBooleanExtra("ouderDan5", false);
        new LongOperation().doInBackground();

        startOefeningen();
    }

    private void startOefeningen(){
        Intent intent = new Intent(this, Oef1Activity.class);
        startActivity(intent);
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Paren.maakLijst(nodigeParen, ouderDan5);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            startOefeningen();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
