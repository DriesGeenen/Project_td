package be.thomasmore.project_td;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity {

    boolean ouderDan5;
    LinearLayout rootLinearLayout;
    ArrayList<String> nodigeParen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        ouderDan5 = getIntent().getBooleanExtra("ouderDan5", true);
        rootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);
        nodigeParen = new ArrayList<>();
        (findViewById(R.id.startButton)).setOnTouchListener(new MyButtonSharpTouchListener());
    }

    public void startButtonClick(View view){
        int count = rootLinearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = rootLinearLayout.getChildAt(i);
            if (v instanceof LinearLayout) {
                int count2 = ((LinearLayout) v).getChildCount();
                for (int j = 0; j < count2; j++) {
                    View v2 = ((LinearLayout) v).getChildAt(j);
                    if (v2 instanceof CheckBox && ((CheckBox) v2).isChecked()) {
                        nodigeParen.add(v2.getTag().toString());
                    }
                }
            }
        }

        Paren.maakLijst(nodigeParen, ouderDan5);
        Intent intent = new Intent(this, Oef1Activity.class);
        startActivity(intent);
    }
}
