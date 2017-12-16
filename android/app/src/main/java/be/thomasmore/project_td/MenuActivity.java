package be.thomasmore.project_td;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity {

    boolean ouderDan5;
    LinearLayout rootLinearLayout;
    ArrayList<String> nodigeParen;
    ArrayList<MyCheckbox> parenCheckBoxLijst;
    RelativeLayout popup;
    MyCheckbox huidigeCheckbox;
    TextView popupTextView;
    Button jaKnop;
    Button neeKnop;
    TextView sluitTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        initialiseerVariabelen();
        haalOpCheckBoxen();
    }

    private void initialiseerVariabelen() {
        ouderDan5 = getIntent().getBooleanExtra("ouderDan5", true);
        rootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);

        (findViewById(R.id.startButton)).setOnTouchListener(new MyButtonSharpTouchListener());
        popup = (RelativeLayout) findViewById(R.id.popupviewgroup);
        popupTextView = (TextView) findViewById(R.id.popuptextview);
        jaKnop = (Button) findViewById(R.id.popupconfirmbutton);
        neeKnop = (Button) findViewById(R.id.popupcancelbutton);
        sluitTextView = (TextView) findViewById(R.id.popupcanceltextview);
        jaKnop.setOnTouchListener(new MyButtonTouchListener());
        neeKnop.setOnTouchListener(new MyButtonLightTouchListener());
    }

    private void haalOpCheckBoxen() {
        parenCheckBoxLijst = new ArrayList<>();
        int count = rootLinearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = rootLinearLayout.getChildAt(i);
            if (v instanceof LinearLayout) {
                int count2 = ((LinearLayout) v).getChildCount();
                for (int j = 0; j < count2; j++) {
                    View v2 = ((LinearLayout) v).getChildAt(j);
                    if (v2 instanceof RelativeLayout && ((RelativeLayout) v2).getChildAt(0) instanceof CheckBox) {
                        parenCheckBoxLijst.add((MyCheckbox) ((RelativeLayout) v2).getChildAt(0));
                    }
                }
            }
        }
    }

    public void startButtonClick(View view) {
        if (nodigeParen == null) {
            nodigeParen = new ArrayList<>();
        } else {
            nodigeParen.clear();
        }

        for (MyCheckbox checkBox : parenCheckBoxLijst) {
            if (checkBox.isChecked()) {
                nodigeParen.add(checkBox.getTag().toString());
            }
        }

        //Paren.maakLijst(nodigeParen, ouderDan5);
        Intent intent = new Intent(this, LadenActivity.class);
        intent.putStringArrayListExtra("nodigeParen", nodigeParen);
        intent.putExtra("ouderDan5", ouderDan5);
        startActivity(intent);
    }

    public void checkBoxClick(View v) {
        String woord1 = (((RelativeLayout)v.getParent()).getChildAt(1).getTag() != null)?
                ((RelativeLayout)v.getParent()).getChildAt(1).getTag().toString() : "";
        String woord2 = (((RelativeLayout)v.getParent()).getChildAt(2).getTag() != null)?
                ((RelativeLayout)v.getParent()).getChildAt(2).getTag().toString() : "";
        String output = "Ik maak van \"" + woord1 + "\" \"" + woord2 + "\".";
        popupTextView.setText(output);
        huidigeCheckbox = (MyCheckbox)v;
        jaKnop.setOnClickListener(new BevestigClickListener());
        neeKnop.setOnClickListener(new AnnuleerBevestigClickListener());
        sluitTextView.setOnClickListener(new SluitBevestigClickListener());
        jaKnop.setText("Oefenen");
        neeKnop.setText("Annuleren");
        popup.setVisibility(View.VISIBLE);
    }


    public void infoTextViewClick(View v) {
        popupTextView.setText((MyMediaPlayer.doeSpeelIntro()) ? "Wil je de introductie af zetten?" : "Wil je de introductie op zetten?");
        jaKnop.setOnClickListener(new InfoClickListener());
        neeKnop.setOnClickListener(new SluitInfoClickListener());
        sluitTextView.setOnClickListener(new SluitInfoClickListener());
        jaKnop.setText("Ja");
        neeKnop.setText("Nee");
        popup.setVisibility(View.VISIBLE);
    }

    class BevestigClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            huidigeCheckbox.setChecked(true);
            popup.setVisibility(View.INVISIBLE);
        }
    }

    class AnnuleerBevestigClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            huidigeCheckbox.setChecked(false);
            popup.setVisibility(View.INVISIBLE);
        }
    }

    class SluitBevestigClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            huidigeCheckbox.setChecked(!huidigeCheckbox.isChecked());
            popup.setVisibility(View.INVISIBLE);
        }
    }

    class InfoClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MyMediaPlayer.toggleSpeelIntro();
            findViewById(R.id.infoTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelIntro())?View.VISIBLE:View.INVISIBLE);
            popup.setVisibility(View.INVISIBLE);
        }
    }

    class SluitInfoClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            popup.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LeeftijdActivity.class);
        startActivity(intent);
    }
}
