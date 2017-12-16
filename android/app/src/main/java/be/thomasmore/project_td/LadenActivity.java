package be.thomasmore.project_td;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;

public class LadenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_laden);

        Intent intent = getIntent();
        ArrayList<String> nodigeParen = intent.getStringArrayListExtra("nodigeParen");
        boolean ouderDan5 = intent.getBooleanExtra("ouderDan5", false);
        Paren.maakLijst(nodigeParen, ouderDan5);

        startOefeningen();
    }

    private void startOefeningen(){
        Intent intent = new Intent(this, Oef1Activity.class);
        startActivity(intent);
    }

}
