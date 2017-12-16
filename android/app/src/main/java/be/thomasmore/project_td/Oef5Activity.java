package be.thomasmore.project_td;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Oef5Activity extends AppCompatActivity {

    private List<Paar> parenLijst;
    private TextView score1TextView;
    private TextView score2TextView;
    private int geantwoord;
    private int aantalAntwoorden;
    private int score1;
    private int score2;
    private boolean aanDeBeurt;

    List<Woord> woordenLijst;
    private List<ImageView> vraagtekenImageViews;
    private TouchableButton juistKnop;
    private TouchableButton foutKnop;
    private Result resultaat;
    private RelativeLayout popup;
    private TextView popupTextView;
    private Button jaKnop;

    private MediaPlayer.OnCompletionListener myCompletionListener;

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

        if (aantalAntwoorden == 0) {
            volgendeActivity();
        } else {
            setEnabledJuistFout(false);
            laadAntwoorden();
            if (MyMediaPlayer.doeSpeelIntro()){
                disableVraagtekens(null);
                MyMediaPlayer.speelIntroductie(this, 5, myCompletionListener);
            }
        }
    }

    private void initialiseerVariabelen() {

        /*List<String> nodigeParen = new ArrayList<>();
        nodigeParen.add("RW");
        nodigeParen.add("BP");
        nodigeParen.add("DT");
        Paren.maakLijst(nodigeParen, false);*/

        parenLijst = Paren.getLijst();
        geantwoord = 0;
        // Beperkt het aantal antwoorden op deelbaar door 3
        aantalAntwoorden = (parenLijst.size() / 3) * 6;
        score1TextView = (TextView) findViewById(R.id.score1TextView);
        score2TextView = (TextView) findViewById(R.id.score2TextView);
        //Intent intent = getIntent();
        //score = intent.getIntExtra("score", 0);
        score1 = 0;
        score2 = 0;
        score1TextView.setText(String.valueOf(score1));
        score2TextView.setText(String.valueOf(score2));

        View.OnTouchListener juistTouchListener = new JuistTouchListener();
        View.OnTouchListener foutTouchListener = new FoutTouchListener();

        juistKnop = (TouchableButton) findViewById(R.id.juistKnop);
        foutKnop = (TouchableButton) findViewById(R.id.foutKnop);

        juistKnop.setOnTouchListener(juistTouchListener);
        foutKnop.setOnTouchListener(foutTouchListener);

        myCompletionListener = new MyCompletionListener();
        aanDeBeurt = true;

        popup = (RelativeLayout) findViewById(R.id.popupviewgroup);
        popupTextView = (TextView)findViewById(R.id.popuptextview);
        jaKnop = (Button) findViewById(R.id.popupconfirmbutton);
        jaKnop.setOnTouchListener(new MyButtonTouchListener());
        (findViewById(R.id.popupconfirmbutton)).setOnTouchListener(new MyButtonTouchListener());
        (findViewById(R.id.popupcancelbutton)).setOnTouchListener(new MyButtonLightTouchListener());

        findViewById(R.id.infoTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelIntro())?View.VISIBLE:View.INVISIBLE);
        findViewById(R.id.bevestigingTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelBevestiging())?View.VISIBLE:View.INVISIBLE);
        if (!User.hasToken()){
            findViewById(R.id.logoutTextView).setVisibility(View.INVISIBLE);
        }
    }

    private void haalImageViews() {
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

    private void laadAntwoorden() {
        woordenLijst = new ArrayList<>();

        int startIndex = geantwoord / 2;
        for (int i = startIndex; i < startIndex + 3; i++) {
            for (int j = 0; j <= 1; j++) {
                woordenLijst.add(parenLijst.get(i).getWoorden().get(j));
            }
        }
        laadResultaat(woordenLijst);
        Collections.shuffle(woordenLijst);
    }

    private void enableVraagtekens() {
        for (ImageView imageView : vraagtekenImageViews) {
            imageView.setEnabled(true);
            imageView.setAlpha(1f);
        }
    }

    private void disableVraagtekens(View not) {
        for (ImageView imageView : vraagtekenImageViews) {
            if (imageView != not) {
                imageView.setEnabled(false);
                imageView.setAlpha(.5f);
            }
        }
    }

    private void setEnabledJuistFout(boolean value) {
        if (!value) {
            juistKnop.setBackgroundResource(R.drawable.buttonshape);
            foutKnop.setBackgroundResource(R.drawable.buttonshape);
        }
        juistKnop.setEnabled(value);
        foutKnop.setEnabled(value);
        juistKnop.setAlpha((value) ? 1 : .5f);
        foutKnop.setAlpha((value) ? 1 : .5f);
    }

    public void vraagtekenClick(View v) {
        disableVraagtekens(v);
        ((ImageView) v).setImageResource(getResources().getIdentifier(woordenLijst.get(geantwoord).getResource(), "drawable", getPackageName()));
        setEnabledJuistFout(true);
    }

    class JuistTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                view.setBackgroundResource(R.drawable.buttonshapegreen);
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (aanDeBeurt) {
                    score1 += 5000;
                    score1TextView.setText(String.valueOf(score1));
                } else {
                    score2 += 5000;
                    score2TextView.setText(String.valueOf(score2));
                }
                aanDeBeurt = !aanDeBeurt;
                resultaat.verhoogAmountCorrect();
                MyMediaPlayer.bevestigCorrectAntwoord(Oef5Activity.this);
                gaVerder();
                view.performClick();
            }
            return true;
        }
    }

    class FoutTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                view.setBackgroundResource(R.drawable.buttonshapered);
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                resultaat.verhoogAmountWrong();
                gaVerder();
                view.performClick();
            }
            return true;
        }
    }

    private void gaVerder() {
        HttpPOSTer.postResult(resultaat);
        geantwoord++;
        if (geantwoord == aantalAntwoorden) {
            volgendeActivity();
        } else if (geantwoord % 6 == 0) {
            laadAntwoorden();
        }
        setEnabledJuistFout(false);
        enableVraagtekens();
    }

    private void volgendeActivity() {
        Intent intent = new Intent(Oef5Activity.this, LeeftijdActivity.class);
        startActivity(intent);
    }

    private void laadResultaat(List<Woord> woordenLijst) {
        resultaat = new Result(5);
        resultaat.setWord(woordenLijst.get(0).getTekst() + "/" + woordenLijst.get(1).getTekst()
                + " & " + woordenLijst.get(2).getTekst() + "/" + woordenLijst.get(3).getTekst()
                + " & " + woordenLijst.get(4).getTekst() + "/" + woordenLijst.get(5).getTekst());
    }

    private final class MyCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            enableVraagtekens();
        }
    }

    public void sluitButtonClick(View v) {
        popup.setVisibility(View.INVISIBLE);
    }

    public void homeTextViewClick(View v) {
        popupTextView.setText("Wil je naar de beginpagina?");
        popup.setVisibility(View.VISIBLE);
        jaKnop.setOnClickListener(new HomeClickListener());
    }

    public void logoutTextViewClick(View v) {
        popupTextView.setText("Ben je zeker dat je wil afmelden?");
        popup.setVisibility(View.VISIBLE);
        jaKnop.setOnClickListener(new LogoutClickListener());
    }

    public void infoTextViewClick(View v) {
        popupTextView.setText((MyMediaPlayer.doeSpeelIntro()) ? "Wil je de introductie af zetten?" : "Wil je de introductie op zetten?");
        popup.setVisibility(View.VISIBLE);
        jaKnop.setOnClickListener(new InfoClickListener());
    }

    public void bevestigTextViewClick(View v) {
        popupTextView.setText((MyMediaPlayer.doeSpeelBevestiging()) ? "Wil je het geluidje af zetten?" : "Wil je het geluidje op zetten?");
        popup.setVisibility(View.VISIBLE);
        jaKnop.setOnClickListener(new BevestigClickListener());
    }

    class HomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Oef5Activity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    class LogoutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            User.logOut(Oef5Activity.this);
            Intent intent = new Intent(Oef5Activity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    class InfoClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MyMediaPlayer.toggleSpeelIntro();
            findViewById(R.id.infoTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelIntro()) ? View.VISIBLE : View.INVISIBLE);
            popup.setVisibility(View.INVISIBLE);
        }
    }

    class BevestigClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MyMediaPlayer.toggleSpeelBevestiging();
            findViewById(R.id.bevestigingTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelBevestiging()) ? View.VISIBLE : View.INVISIBLE);
            popup.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        // doe niks
    }
}
