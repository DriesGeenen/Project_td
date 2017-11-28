package be.thomasmore.project_td;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Oef5Activity extends AppCompatActivity {

    private List<Paar> parenLijst;
    private TextView scoreTextView;
    private int geantwoord;
    private int aantalAntwoorden;
    private int score;

    List<Woord> woordenLijst;
    private List<ImageView> vraagtekenImageViews;
    private Button juistKnop;
    private Button foutKnop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oef5);
        ((TextView) findViewById(R.id.headerTextView)).setText("Oefening 5");

        initialiseerVariabelen();

        haalImageViews();
    }

    private void initialiseerVariabelen() {

        List<String> nodigeParen = new ArrayList<>();
        nodigeParen.add("RW");
        //nodigeParen.add("BP");
        nodigeParen.add("DT");
        Paren.maakLijst(nodigeParen, false);

        parenLijst = Paren.getLijst();
        geantwoord = 0;
        // Beperkt het aantal antwoorden op deelbaar door 3
        aantalAntwoorden = (parenLijst.size()/3)*6;
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        scoreTextView.setText(String.valueOf(score));

        juistKnop = (Button) findViewById(R.id.juistKnop);
        foutKnop = (Button) findViewById(R.id.foutKnop);

        if (aantalAntwoorden == 0){
            volgendeActivity();
        } else {
            disableJuistFoutClick();
            laadAntwoorden();
        }
    }

    private void haalImageViews(){
        vraagtekenImageViews = new ArrayList<>();

        LinearLayout rootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);
        int count = rootLinearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View v1 = rootLinearLayout.getChildAt(i);
            if (v1 instanceof LinearLayout) {
                int countV1 = ((LinearLayout) v1).getChildCount();
                for (int j = 0; j < countV1; j++) {
                    View v2 = ((LinearLayout) v1).getChildAt(j);
                    if (v2 instanceof ImageView) {
                        vraagtekenImageViews.add((ImageView) v2);
                    }
                }
            }
        }
    }

    private void laadAntwoorden(){
        woordenLijst = new ArrayList<>();

        int startIndex = geantwoord / 2;
        for (int i = startIndex; i < startIndex + 3; i++) {
            for (int j = 0; j <= 1; j++) {
                woordenLijst.add(parenLijst.get(i).getWoorden().get(j));
            }
        }
        Collections.shuffle(woordenLijst);
    }

    private void enableVraagtekenClick(){
        for(ImageView iv : vraagtekenImageViews){
            iv.setClickable(true);
        }
    }

    private void disableVraagtekenClick(){
        for(ImageView iv : vraagtekenImageViews){
            iv.setClickable(false);
        }
    }

    private void enableJuistFoutClick(){
        juistKnop.setClickable(true);
        foutKnop.setClickable(true);
    }

    private void disableJuistFoutClick(){
        juistKnop.setClickable(false);
        foutKnop.setClickable(false);
    }

    public void vraagtekenClick(View v){
        disableVraagtekenClick();

        ((ImageView)v).setImageResource(getResources().getIdentifier(woordenLijst.get(geantwoord).getAfbeelding(), "drawable", getPackageName()));

        enableJuistFoutClick();
    }

    public void juistClick(View v){
        score += 4;
        scoreTextView.setText(String.valueOf(score));
        gaVerder();
    }

    public void foutClick(View v){
        gaVerder();
    }

    private void gaVerder(){
        geantwoord++;
        if (geantwoord == aantalAntwoorden){
            volgendeActivity();
        }else if(geantwoord%6==0){
            laadAntwoorden();
        }
        disableJuistFoutClick();
        enableVraagtekenClick();
    }

    private void volgendeActivity(){
        Intent intent = new Intent(this, LeeftijdActivity.class);
        startActivity(intent);
    }
}
