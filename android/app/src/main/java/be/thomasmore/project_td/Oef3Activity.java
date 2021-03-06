package be.thomasmore.project_td;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Oef3Activity extends AppCompatActivity {

    private List<Paar> parenLijst;
    private TextView scoreTextView;
    private int geantwoord;
    private int aantalAntwoorden;
    private int score;

    private List<ImageView> mainImageViews;
    private List<ImageView> kleineImageViews;
    private List<TouchableTextView> kaartTextViews;

    private OnDragListener myDragListener;
    private OnTouchListener myTouchListener;
    private Result resultaat;

    private MyCompletionListener myCompletionListener;
    private boolean dragComplete;
    private boolean playAudioComplete;

    private RelativeLayout popup;
    private TextView popupTextView;
    private Button jaKnop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oef3);
        ((TextView) findViewById(R.id.headerTextView)).setText("Oefening 3");

        initialiseerVariabelen();

        haalViews();

        laadAfbeeldingen();

        if (MyMediaPlayer.doeSpeelIntro()){
            dragComplete = true;
            setEnabledKaarten(false);
            MyMediaPlayer.speelIntroductie(this, 3, myCompletionListener);
        }

    }

    private void initialiseerVariabelen() {
        /*List<String> nodigeParen = new ArrayList<>();
        for (int i=0;i<11;i++){
            nodigeParen.add("DT");
        }
        Paren.maakLijst(nodigeParen, true);*/

        parenLijst = Paren.getLijst();
        geantwoord = 0;
        // Beperkt het aantal antwoorden op deelbaar door 2
        aantalAntwoorden = (parenLijst.size() / 2) * 4;
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        //Intent intent = getIntent();
        //score = intent.getIntExtra("score", 0);
        score = 0;
        scoreTextView.setText(String.valueOf(score));

        myDragListener = new MyDragListener();
        myTouchListener = new MyTouchListener();
        ConstraintLayout achtergrond = (ConstraintLayout) findViewById(R.id.backgroundLayout);
        achtergrond.setOnDragListener(new BackgroundDragListener());

        myCompletionListener = new MyCompletionListener();

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


    private void haalViews() {
        mainImageViews = new ArrayList<>();
        kleineImageViews = new ArrayList<>();
        kaartTextViews = new ArrayList<>();
        LinearLayout rootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);
        int count = rootLinearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View v1 = rootLinearLayout.getChildAt(i);
            if (v1 instanceof LinearLayout) {
                int countV1 = ((LinearLayout) v1).getChildCount();
                for (int j = 0; j < countV1; j++) {
                    View v2 = ((LinearLayout) v1).getChildAt(j);
                    if (v2 instanceof ImageView) {
                        mainImageViews.add((ImageView) v2);
                    } else if (v2 instanceof RelativeLayout) {
                        int countV2 = ((RelativeLayout) v2).getChildCount();
                        for (int k = 0; k < countV2; k++) {
                            View v3 = ((RelativeLayout) v2).getChildAt(k);
                            if (v3 instanceof ImageView) {
                                kleineImageViews.add((ImageView) v3);
                            } else if (v3 instanceof TouchableTextView) {
                                kaartTextViews.add((TouchableTextView) v3);
                            }
                        }
                    }
                }
            }
        }
    }

    private void laadAfbeeldingen() {
        if (aantalAntwoorden != 0) {
            Integer[] indexArray = {0, 1, 2, 3};
            Integer[] kleurArray = {0x9900CC00, 0x990000CC, 0x9900CCCC, 0x99CC00CC};
            List<Integer> indexLijst = Arrays.asList(indexArray);
            Collections.shuffle(indexLijst);
            List<Woord> woordenLijst = new ArrayList<>();

            int startIndex = geantwoord / 2;
            for (int i = startIndex; i < startIndex + 2; i++) {
                for (int j = 0; j <= 1; j++) {
                    woordenLijst.add(parenLijst.get(i).getWoorden().get(j));
                }
            }

            for (int i = 0; i < mainImageViews.size(); i++) {
                ImageView mainImageView = mainImageViews.get(indexLijst.get(i));

                mainImageView.setImageResource(getResources()
                        .getIdentifier(woordenLijst.get(i)
                                .getResource(), "drawable", getPackageName()));

                mainImageView.setTag(woordenLijst.get(i).getResource());
                mainImageView.setOnDragListener(myDragListener);
            }

            Collections.shuffle(indexLijst);

            for (int i = 0; i < kaartTextViews.size(); i++) {
                TouchableTextView kaartTextView = kaartTextViews.get(indexLijst.get(i));
                kaartTextView.setTag(woordenLijst.get(i).getResource());
                kaartTextView.setOnTouchListener(myTouchListener);
                kaartTextView.setVisibility(View.VISIBLE);
                kaartTextView.setBackgroundColor(kleurArray[indexLijst.get(i)]);
            }

            for(ImageView kleineImageView: kleineImageViews){
                kleineImageView.setImageDrawable(null);
                kleineImageView.setBackgroundColor(Color.TRANSPARENT);
            }

            laadResultaat(woordenLijst);
        } else {
            volgendeActivity();
        }

    }

    private void laadResultaat(List<Woord> woordenLijst) {
        resultaat = new Result(3);
        resultaat.setWord(woordenLijst.get(0).getTekst() + "/" + woordenLijst.get(1).getTekst()
                + " & " + woordenLijst.get(2).getTekst() + "/" + woordenLijst.get(3).getTekst());
    }

    private void volgendeActivity() {
        Intent intent = new Intent(Oef3Activity.this, Oef4Activity.class);
        //intent.putExtra("score", score);
        startActivity(intent);
    }

    private void setEnabledKaarten(boolean value){
        for(TouchableTextView kaart : kaartTextViews){
            kaart.setEnabled(value);
            kaart.setAlpha((value)? 1f : .5f);
        }
    }

    private final class MyCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mediaPlayer){
            if (dragComplete)
                setEnabledKaarten(true);
            playAudioComplete = true;
        }
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_MOVE:
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    // Originele afbeelding verdwijnt
                    //view.setVisibility(View.INVISIBLE);

                    //setEnabledKaarten(false);
                    dragComplete = false;
                    playAudioComplete = false;
                    MyMediaPlayer.spreek(Oef3Activity.this, view.getTag().toString(), myCompletionListener);
                    break;
                default:break;

            }
            return true;
        }
    }

    class MyDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {

                if (playAudioComplete)
                    setEnabledKaarten(true);
                dragComplete = true;

                //handle the dragged view being dropped over a target view
                View view = (View) event.getLocalState();
                //stop displaying the view where it was before it was dragged
                view.setVisibility(View.INVISIBLE);
                //view dragged item is being dropped on
                ImageView dropTarget = (ImageView) v;
                //view being dragged and dropped
                TextView dropped = (TextView) view;

                geantwoord++;

                // Achtergrond weg halen
                for(ImageView imageView : kleineImageViews){
                    imageView.setBackgroundColor(Color.TRANSPARENT);
                }

                // Wanneer correct
                if (dropped.getTag().toString().equals(dropTarget.getTag().toString())) {
                    score += 100;
                    scoreTextView.setText(String.valueOf(score));
                    resultaat.verhoogAmountCorrect();

                    kleineImageViews.get(mainImageViews.indexOf(dropTarget)).setBackgroundResource(R.drawable.backgroundshapegreensemitransparent);
                    MyMediaPlayer.bevestigCorrectAntwoord(Oef3Activity.this);
                } else{
                    resultaat.verhoogAmountWrong();

                    kleineImageViews.get(mainImageViews.indexOf(dropTarget)).setBackgroundResource(R.drawable.backgroundshaperedsemitransparent);
                }

                kleineImageViews.get(mainImageViews.indexOf(dropTarget))
                            .setImageResource(getResources()
                                    .getIdentifier(dropped.getTag().toString(), "drawable", getPackageName()));

                if (geantwoord == aantalAntwoorden) {
                    HttpPOSTer.postResult(resultaat);
                    volgendeActivity();
                } else if (geantwoord % 4 == 0) {
                    HttpPOSTer.postResult(resultaat);
                    laadAfbeeldingen();
                }
            }
            return true;
        }
    }

    class BackgroundDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState();
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    setEnabledKaarten(false);
                    view.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    if (playAudioComplete)
                        setEnabledKaarten(true);
                    dragComplete = true;

                    // Afbeelding wordt terug zichtbaar

                    view.setVisibility(View.VISIBLE);
                    break;
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
            Intent intent = new Intent(Oef3Activity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    class LogoutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            User.logOut(Oef3Activity.this);
            Intent intent = new Intent(Oef3Activity.this, LoginActivity.class);
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
