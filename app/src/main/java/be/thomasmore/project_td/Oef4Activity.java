package be.thomasmore.project_td;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Oef4Activity extends AppCompatActivity {

    private List<Paar> parenLijst;
    private TextView scoreTextView;
    private int geantwoord;
    private int aantalAntwoorden;
    private int score;
    private ImageView afbeelding;
    private Woord juistWoord;
    private Woord foutWoord;
    private View.OnTouchListener correctTouchListener;
    private View.OnTouchListener foutTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oef4);
        ((TextView) findViewById(R.id.headerTextView)).setText("Oefening 4");

        initialiseerVariabelen();

        laadAfbeeldingen();
    }

    private void initialiseerVariabelen() {
        /*score = 0;
        List<String> nodigeParen = new ArrayList<>();
        nodigeParen.add("DT");
        nodigeParen.add("DT");
        nodigeParen.add("DT");
        Paren.maakLijst(nodigeParen, false);*/

        parenLijst = Paren.getLijst();
        geantwoord = 0;
        aantalAntwoorden = parenLijst.size();
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        Intent intent = getIntent();
        //score = intent.getExtras().getInt("score", 0);
        scoreTextView.setText(String.valueOf(score));
        afbeelding = (ImageView) findViewById(R.id.afbeelding);
        correctTouchListener = new CorrectTouchListener();
        foutTouchListener = new FoutTouchListener();
    }

    private void laadAfbeeldingen() {
        List<Woord> woorden = parenLijst.get(geantwoord).getWoorden();

        int coinToss = (Math.random() < 0.5) ? 0 : 1;
        int reverseCointToss = (coinToss == 0) ? 1 : 0;
        juistWoord = woorden.get(coinToss);
        foutWoord = woorden.get(reverseCointToss);

        afbeelding.setImageResource(getResources().getIdentifier(juistWoord.getAfbeelding(), "drawable", getPackageName()));
        spreek(juistWoord.getContextAudio());

        TouchableButton knop1 = (TouchableButton) findViewById(R.id.knop1);
        TouchableButton knop2 = (TouchableButton) findViewById(R.id.knop2);
        knop1.setBackgroundResource(android.R.drawable.btn_default);
        knop2.setBackgroundResource(android.R.drawable.btn_default);

        if (Math.random() < 0.5) {
            knop1.setText(foutWoord.getTekst());
            knop2.setText(juistWoord.getTekst());
            knop1.setOnTouchListener(foutTouchListener);
            knop2.setOnTouchListener(correctTouchListener);
        } else {
            knop1.setText(juistWoord.getTekst());
            knop2.setText(foutWoord.getTekst());
            knop1.setOnTouchListener(correctTouchListener);
            knop2.setOnTouchListener(foutTouchListener);
        }

    }

    private void spreek(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    class CorrectTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                view.setBackgroundColor(Color.GREEN);
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                score += 8;
                scoreTextView.setText(String.valueOf(score));
                view.performClick();
            }
            return true;
        }
    }

    class FoutTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                view.setBackgroundColor(Color.RED);
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                view.performClick();
            }
            return true;
        }
    }

    public void buttonClick(View v) {
        geantwoord++;
        if (geantwoord == aantalAntwoorden) {
            Intent intent = new Intent(this, Oef5Activity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        } else {
            laadAfbeeldingen();
        }
    }
}
