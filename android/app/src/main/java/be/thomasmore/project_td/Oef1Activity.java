package be.thomasmore.project_td;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Oef1Activity extends AppCompatActivity {

    private TextView scoreTextView;
    private List<Paar> parenLijst;
    private int score;
    private int geantwoord;
    private int paarIndex;
    private int aantalAntwoorden;
    private Woord woord1;
    private Woord woord2;
    private OnTouchListener myTouchListener;
    public MyCompletionListener myCompletionListener;
    private List<ImageView> afbeeldingen;
    private RelativeLayout middenveld;

    private boolean dragComplete;
    private boolean playAudioComplete;

    private Result resultaat;

    private RelativeLayout popup;
    private TextView popupTextView;
    private Button jaKnop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oef1);
        ((TextView) findViewById(R.id.headerTextView)).setText("Oefening 1");

        initialiseerVariabelen();

        if (parenLijst.size() == 0) {
            Intent intent = new Intent(this, LeeftijdActivity.class);
            startActivity(intent);
        } else {
            laadAfbeeldingen();
            if (MyMediaPlayer.doeSpeelIntro()) {
                dragComplete = true;
                setEnabledAfbeeldingen(false);
                MyMediaPlayer.speelIntroductie(this, 1, myCompletionListener);
            }

        }
    }


    private void initialiseerVariabelen() {
        /*List<String> nodigeParen = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            nodigeParen.add("BP");
        }
        Paren.maakLijst(nodigeParen, true);*/

        parenLijst = Paren.getLijst();
        score = 0;
        geantwoord = 0;
        paarIndex = 0;
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        myTouchListener = new MyTouchListener();
        OnDragListener myDragListener = new MyDragListener();
        myCompletionListener = new MyCompletionListener();

        afbeeldingen = new ArrayList<>();
        afbeeldingen.add((ImageView) findViewById(R.id.afbeelding1));
        afbeeldingen.add((ImageView) findViewById(R.id.afbeelding2));

        for (ImageView a : afbeeldingen) {
            a.setOnDragListener(myDragListener);
        }

        findViewById(R.id.backgroundLayout).setOnDragListener(new BackgroundDragListener());
        popupTextView = (TextView) findViewById(R.id.popuptextview);
        jaKnop = (Button) findViewById(R.id.popupconfirmbutton);
        jaKnop.setOnTouchListener(new MyButtonTouchListener());
        (findViewById(R.id.popupcancelbutton)).setOnTouchListener(new MyButtonLightTouchListener());
        popup = (RelativeLayout) findViewById(R.id.popupviewgroup);
        findViewById(R.id.infoTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelIntro())?View.VISIBLE:View.INVISIBLE);
        findViewById(R.id.bevestigingTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelBevestiging())?View.VISIBLE:View.INVISIBLE);
        if (!User.hasToken()){
            findViewById(R.id.logoutTextView).setVisibility(View.INVISIBLE);
        }
    }

    private void laadAfbeeldingen() {
        laadContextAfbeeldingen();
        laadMiddenveldAfbeeldingen();
        laadResultaat();
    }

    private void laadContextAfbeeldingen() {
        Coin coin = new Coin();

        aantalAntwoorden = parenLijst.size() * 8;
        Paar huidigPaar = parenLijst.get(paarIndex);

        // TODO: test
        woord1 = huidigPaar.getWoorden().get(0);
        woord2 = huidigPaar.getWoorden().get(1);

        afbeeldingen.get(coin.getTop()).setImageResource(getResources().getIdentifier(woord1.getContextResource(), "drawable", getPackageName()));
        afbeeldingen.get(coin.getBottom()).setImageResource(getResources().getIdentifier(woord2.getContextResource(), "drawable", getPackageName()));

        afbeeldingen.get(coin.getTop()).setTag(woord1.getResource());
        afbeeldingen.get(coin.getBottom()).setTag(woord2.getResource());

        setTransparentAchtergrondAfbeeldingen();
    }

    private void laadMiddenveldAfbeeldingen() {
        middenveld = (RelativeLayout) findViewById(R.id.middenveld);

        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            TouchableImageView antwoord = new TouchableImageView(Oef1Activity.this);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );

            int imageMargin = 100;
            int left = rand.nextInt(imageMargin);
            int top = rand.nextInt(imageMargin);
            int right = imageMargin - left;
            int bottom = imageMargin - top;

            params.setMargins(dpToPx(left), dpToPx(top), dpToPx(right), dpToPx(bottom));
            antwoord.setLayoutParams(params);
            antwoord.setOnTouchListener(myTouchListener);

            if (i % 2 == 0) {
                antwoord.setImageResource(getResources().getIdentifier(woord1.getResource(), "drawable", getPackageName()));
                antwoord.setTag(woord1.getResource());
            } else {
                antwoord.setImageResource(getResources().getIdentifier(woord2.getResource(), "drawable", getPackageName()));
                antwoord.setTag(woord2.getResource());
            }
            middenveld.addView(antwoord);
        }
    }

    private void laadResultaat() {
        resultaat = new Result(1);
        resultaat.setWord(woord1.getTekst() + "/" + woord2.getTekst());
    }

    private void volgendeActivity() {
        Intent intent = new Intent(Oef1Activity.this, Oef2Activity.class);
        //intent.putExtra("score", score);
        startActivity(intent);
    }

    private void setEnabledAfbeeldingen(boolean value) {
        int count = middenveld.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = middenveld.getChildAt(i);
            v.setEnabled(value);
            v.setAlpha((value) ? 1f : .5f);
        }
    }

    private void setTransparentAchtergrondAfbeeldingen() {
        for (ImageView imageView : afbeeldingen) {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private final class MyCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (dragComplete)
                setEnabledAfbeeldingen(true);
            playAudioComplete = true;
        }
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    // Originele afbeelding verdwijnt
                    view.setVisibility(View.INVISIBLE);
                    setEnabledAfbeeldingen(false);
                    dragComplete = false;
                    playAudioComplete = false;
                    MyMediaPlayer.spreek(Oef1Activity.this, view.getTag().toString(), myCompletionListener);
                    setTransparentAchtergrondAfbeeldingen();
                    break;
                default:
                    break;

            }
            return true;
        }
    }


    class MyDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                //handle the dragged view being dropped over a target view
                View view = (View) event.getLocalState();
                //stop displaying the view where it was before it was dragged
                view.setVisibility(View.INVISIBLE);
                //view dragged item is being dropped on
                TouchableImageView dropTarget = (TouchableImageView) v;
                //view being dragged and dropped
                TouchableImageView dropped = (TouchableImageView) view;
                // View verwijderen voor performance / anti-crash
                ((ViewGroup) view.getParent()).removeView(view);

                geantwoord++;

                if (dropped.getTag().toString().equals(dropTarget.getTag().toString())) {
                    // Correct gedropt
                    resultaat.verhoogAmountCorrect();
                    score += 1250;
                    scoreTextView.setText(String.valueOf(score));
                    dropTarget.setBackgroundResource(R.drawable.backgroundshapegreensemitransparent);
                    MyMediaPlayer.bevestigCorrectAntwoord(Oef1Activity.this);
                } else {
                    // Fout gedropt
                    resultaat.verhoogAmountWrong();
                    dropTarget.setBackgroundResource(R.drawable.backgroundshaperedsemitransparent);
                }

                if (geantwoord == aantalAntwoorden) {
                    // Oefening voorbij
                    // Resultaat opslaan
                    HttpPOSTer.postResult(resultaat);
                    // Naar de volgende oefening
                    volgendeActivity();
                } else if (geantwoord % 8 == 0) {
                    // Volgend paar
                    paarIndex++;
                    // Resultaat opslaan
                    HttpPOSTer.postResult(resultaat);
                    // Laad nieuwe afbeeldingen
                    laadAfbeeldingen();
                }
                if (playAudioComplete)
                    setEnabledAfbeeldingen(true);
                dragComplete = true;
            }
            return true;
        }
    }

    class BackgroundDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {

                if (playAudioComplete)
                    setEnabledAfbeeldingen(true);
                dragComplete = true;

                // Afbeelding wordt terug zichtbaar
                View view = (View) event.getLocalState();
                view.setVisibility(View.VISIBLE);

            }
            return true;
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
            Intent intent = new Intent(Oef1Activity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    class LogoutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            User.logOut(Oef1Activity.this);
            Intent intent = new Intent(Oef1Activity.this, LoginActivity.class);
            startActivity(intent);
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

    class BevestigClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MyMediaPlayer.toggleSpeelBevestiging();
            findViewById(R.id.bevestigingTextViewKruis).setVisibility((MyMediaPlayer.doeSpeelBevestiging())?View.VISIBLE:View.INVISIBLE);
            popup.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        // doe niks
    }
}
