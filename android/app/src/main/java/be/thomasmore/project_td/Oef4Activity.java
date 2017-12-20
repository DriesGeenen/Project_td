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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Oef4Activity extends AppCompatActivity {

    private List<Paar> parenLijst;
    private TextView scoreTextView;
    private int geantwoord;
    private int aantalAntwoorden;
    private int score;
    private ImageView afbeelding;
    private View.OnTouchListener juistTouchListener;
    private View.OnTouchListener foutTouchListener;
    private Result resultaat;
    private List<TouchableButton> knoppen;
    private String uitspraak;
    private MyCompletionListener_EnableButtons myCompletionListener_enableButtons;
    private MyCompletionListener_SayFirstWord myCompletionListener_sayFirstWord;
    private RelativeLayout popup;
    private TextView popupTextView;
    private Button jaKnop;

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

        setEnabledKnoppen(false);
        if (MyMediaPlayer.doeSpeelIntro()){
            MyMediaPlayer.speelIntroductie(this, 4, myCompletionListener_sayFirstWord);
        } else {
            MyMediaPlayer.spreek(Oef4Activity.this, uitspraak, myCompletionListener_enableButtons);
        }
    }

    private void initialiseerVariabelen() {
        /*List<String> nodigeParen = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            nodigeParen.add("DT");
        }
        Paren.maakLijst(nodigeParen, false);*/

        parenLijst = Paren.getLijst();
        geantwoord = 0;
        aantalAntwoorden = parenLijst.size();
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        //Intent intent = getIntent();
        //score = intent.getIntExtra("score", 0);
        score = 0;
        scoreTextView.setText(String.valueOf(score));
        afbeelding = (ImageView) findViewById(R.id.afbeelding);
        juistTouchListener = new JuistTouchListener();
        foutTouchListener = new FoutTouchListener();
        knoppen = new ArrayList<>();
        knoppen.add((TouchableButton) findViewById(R.id.knop1));
        knoppen.add((TouchableButton) findViewById(R.id.knop2));

        myCompletionListener_enableButtons = new MyCompletionListener_EnableButtons();
        myCompletionListener_sayFirstWord = new MyCompletionListener_SayFirstWord();

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

    private void laadAfbeeldingen() {
        List<Woord> woorden = parenLijst.get(geantwoord).getWoorden();

        Coin coin = new Coin();
        Woord juistWoord = woorden.get(coin.getTopDrieVierde());
        Woord foutWoord = woorden.get(coin.getBottomEenVierde());

        afbeelding.setImageResource(getResources().getIdentifier(juistWoord.getResource(), "drawable", getPackageName()));

        // todo replace with getContextResource()
        uitspraak = juistWoord.getContextResource();

        // Als de geen aanvulzin beschikbaar is, worden de woorden omgewisselt
        if (getResources().getIdentifier(uitspraak, "raw", getPackageName()) == 0){
            Woord wisselWoord = juistWoord;
            juistWoord = foutWoord;
            foutWoord = wisselWoord;
            uitspraak = juistWoord.getContextResource();
        }

        for (TouchableButton touchableButton : knoppen) {
            touchableButton.setBackgroundResource(R.drawable.buttonshape);
        }

        coin.toss();
        knoppen.get(coin.getTop()).setText(juistWoord.getTekst());
        knoppen.get(coin.getBottom()).setText(foutWoord.getTekst());
        knoppen.get(coin.getTop()).setOnTouchListener(juistTouchListener);
        knoppen.get(coin.getBottom()).setOnTouchListener(foutTouchListener);

        laadResultaat(juistWoord, foutWoord);
    }

    private void laadResultaat(Woord juistWoord, Woord foutWoord) {
        resultaat = new Result(4);
        resultaat.setWord(juistWoord.getTekst() + "/(" + foutWoord.getTekst() + ")");
    }

    private void volgendeActivity() {
        Intent intent = new Intent(Oef4Activity.this, Oef5Activity.class);
        //intent.putExtra("score", score);
        startActivity(intent);
    }

    private void setEnabledKnoppen(boolean value) {
        for (TouchableButton touchableButton : knoppen) {
            touchableButton.setEnabled(value);
            touchableButton.setAlpha((value) ? 1f : .5f);
        }
    }

    private final class MyCompletionListener_EnableButtons implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            setEnabledKnoppen(true);
        }
    }

    private final class MyCompletionListener_SayFirstWord implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            MyMediaPlayer.spreek(Oef4Activity.this, uitspraak, myCompletionListener_enableButtons);
        }
    }

    class JuistTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                view.setBackgroundResource(R.drawable.buttonshapegreen);
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                score += 100;
                scoreTextView.setText(String.valueOf(score));
                resultaat.verhoogAmountCorrect();
                MyMediaPlayer.bevestigCorrectAntwoord(Oef4Activity.this);
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

    public void gaVerder() {
        HttpPOSTer.postResult(resultaat);
        geantwoord++;
        if (geantwoord == aantalAntwoorden) {
            volgendeActivity();
        } else {
            laadAfbeeldingen();
            setEnabledKnoppen(false);
            MyMediaPlayer.spreek(this, uitspraak, myCompletionListener_enableButtons);
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
            Intent intent = new Intent(Oef4Activity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    class LogoutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            User.logOut(Oef4Activity.this);
            Intent intent = new Intent(Oef4Activity.this, LoginActivity.class);
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
