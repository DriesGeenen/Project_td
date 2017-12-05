package be.thomasmore.project_td;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class LeeftijdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leeftijd);
    }

    public void leeftijdButtonClick(View view){
        boolean ouderDan5 = (view.getTag().toString().equals("1"));

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("ouderDan5", ouderDan5);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // doe niks
    }
}
