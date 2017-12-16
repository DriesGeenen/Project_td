package be.thomasmore.project_td;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Oef2Activity extends AppCompatActivity {

    private List<Paar> parenLijst;
    private TextView scoreTextView;
    private int geantwoord;
    private int aantalAntwoorden;
    private int score;
    private String uitspraak;
    List<ImageView> afbeeldingen;
    private Result resultaat;
    Woord juistWoord;
    Woord foutWoord;
    private MyCompletionListener_EnableImages myCompletionListener_enableImages;
    private MyCompletionListener_LoadNextImages myCompletionListener_loadNextImages;
    private MyCompletionListener_SayFirstWord myCompletionListener_sayFirstWord;
    private MyCompletionListener_SayLastWord myCompletionListener_sayLastWord;
    private RelativeLayout popup;
    private TextView popupTextView;
    private Button jaKnop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oef2);
        ((TextView) findViewById(R.id.headerTextView)).setText("Oefening 2");

        initialiseerVariabelen();

        laadWoorden();
        laadAfbeeldingen();

        setEnabledAfbeeldingen(false);
        if (MyMediaPlayer.doeSpeelIntro()) {
            MyMediaPlayer.speelIntroductie(this, 2, myCompletionListener_sayFirstWord);
        } else {
            MyMediaPlayer.spreek(Oef2Activity.this, uitspraak, myCompletionListener_enableImages);
        }
    }

    private void initialiseerVariabelen() {
        /*List<String> nodigeParen = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            nodigeParen.add("LW");
        }
        Paren.maakLijst(nodigeParen, true);*/

        parenLijst = Paren.getLijst();
        geantwoord = 0;
        aantalAntwoorden = parenLijst.size();
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        //Intent intent = getIntent();

        //score = intent.getIntExtra("score", 0);
        score = 0;
        scoreTextView.setText(String.valueOf(score));

        afbeeldingen = new ArrayList<>();
        afbeeldingen.add((ImageView) findViewById(R.id.afbeelding1));
        afbeeldingen.add((ImageView) findViewById(R.id.afbeelding2));

        myCompletionListener_enableImages = new MyCompletionListener_EnableImages();
        myCompletionListener_loadNextImages = new MyCompletionListener_LoadNextImages();
        myCompletionListener_sayFirstWord = new MyCompletionListener_SayFirstWord();
        myCompletionListener_sayLastWord = new MyCompletionListener_SayLastWord();

        (findViewById(R.id.popupconfirmbutton)).setOnTouchListener(new MyButtonTouchListener());
        (findViewById(R.id.popupcancelbutton)).setOnTouchListener(new MyButtonLightTouchListener());

        popup = (RelativeLayout) findViewById(R.id.popupviewgroup);
        popupTextView = (TextView)findViewById(R.id.popuptextview);
        jaKnop = (Button) findViewById(R.id.popupconfirmbutton);
        jaKnop.setOnTouchListener(new MyButtonTouchListener());
        findViewById(R.id.infoTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelIntro()) ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.bevestigingTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelBevestiging()) ? View.VISIBLE : View.INVISIBLE);
        if (!User.hasToken()){
            findViewById(R.id.logoutTextView).setVisibility(View.INVISIBLE);
        }
    }

    private void laadWoorden() {
        Coin coin = new Coin();

        Paar huidigPaar = parenLijst.get(geantwoord);
        List<Woord> woorden = huidigPaar.getWoorden();

        juistWoord = woorden.get(coin.getTopDrieVierde());
        foutWoord = woorden.get(coin.getBottomEenVierde());
        uitspraak = juistWoord.getResource();
    }

    private void laadAfbeeldingen() {
        Coin coin = new Coin();

        for (ImageView afbeelding : afbeeldingen) {
            afbeelding.setBackgroundColor(0x00000000);
        }

        afbeeldingen.get(coin.getTop()).setImageResource(getResources().getIdentifier(juistWoord.getResource(), "drawable", getPackageName()));
        afbeeldingen.get(coin.getBottom()).setImageResource(getResources().getIdentifier(foutWoord.getResource(), "drawable", getPackageName()));

        afbeeldingen.get(coin.getTop()).setTag("correct");
        afbeeldingen.get(coin.getBottom()).setTag("");

        laadResultaat(juistWoord, foutWoord);
    }

    public void afbeeldingClick(View view) {
        if (view.getTag().toString().equals("correct")) {
            score += 10000;
            scoreTextView.setText(String.valueOf(score));
            resultaat.verhoogAmountCorrect();
            view.setBackgroundResource(R.drawable.backgroundshapegreen);
            MyMediaPlayer.bevestigCorrectAntwoord(this);
        } else {
            resultaat.verhoogAmountWrong();
            view.setBackgroundResource(R.drawable.backgroundshapered);
        }
        HttpPOSTer.postResult(resultaat);
        geantwoord++;
        if (geantwoord == aantalAntwoorden) {
            setEnabledAfbeeldingen(false);
            MyMediaPlayer.spreek(this, uitspraak, myCompletionListener_sayLastWord);
        } else {
            setEnabledAfbeeldingen(false);
            MyMediaPlayer.spreek(this, uitspraak, myCompletionListener_loadNextImages);
        }
    }

    private void laadResultaat(Woord juistWoord, Woord foutWoord) {
        resultaat = new Result(2);
        resultaat.setWord(juistWoord.getTekst() + "/(" + foutWoord.getTekst() + ")");
    }

    private void setEnabledAfbeeldingen(boolean value) {
        for (ImageView imageView : afbeeldingen) {
            imageView.setEnabled(value);
            imageView.setAlpha((value) ? 1f : .5f);
        }
    }

    private void volgendeActivity() {
        Intent intent = new Intent(Oef2Activity.this, Oef3Activity.class);
        //intent.putExtra("score", score);
        startActivity(intent);
    }

    private final class MyCompletionListener_EnableImages implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            setEnabledAfbeeldingen(true);
        }
    }

    private final class MyCompletionListener_LoadNextImages implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            laadWoorden();
            laadAfbeeldingen();
            MyMediaPlayer.spreek(Oef2Activity.this, uitspraak, myCompletionListener_enableImages);
        }
    }

    private final class MyCompletionListener_SayFirstWord implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            MyMediaPlayer.spreek(Oef2Activity.this, uitspraak, myCompletionListener_enableImages);
        }
    }

    private final class MyCompletionListener_SayLastWord implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            volgendeActivity();
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
            Intent intent = new Intent(Oef2Activity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    class LogoutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            User.logOut(Oef2Activity.this);
            Intent intent = new Intent(Oef2Activity.this, LoginActivity.class);
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
