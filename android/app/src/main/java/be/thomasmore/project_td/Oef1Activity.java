package be.thomasmore.project_td;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

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
    private List<TouchableImageView> afbeeldingen;
    private RelativeLayout middenveld;

    private Result resultaat;

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
        }
    }


    private void initialiseerVariabelen() {
        /*List<String> nodigeParen = new ArrayList<>();
        for (int i = 0; i<10;i++){
            nodigeParen.add("DT");
        }
        Paren.maakLijst(nodigeParen, false);*/

        parenLijst = Paren.getLijst();
        score = 0;
        geantwoord = 0;
        paarIndex = 0;
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        myTouchListener = new MyTouchListener();
        OnDragListener myDragListener = new MyDragListener();

        afbeeldingen = new ArrayList<>();
        afbeeldingen.add((TouchableImageView) findViewById(R.id.afbeelding1));
        afbeeldingen.add((TouchableImageView) findViewById(R.id.afbeelding2));

        for (TouchableImageView a : afbeeldingen){
            a.setOnDragListener(myDragListener);
        }
    }

    private void laadAfbeeldingen() {
        laadContextAfbeeldingen();
        laadMiddenveldAfbeeldingen();
        laadResultaat();
    }

    private void laadContextAfbeeldingen() {
        int coinToss = (Math.random() < 0.5) ? 0 : 1;
        int reverseCoinToss = (coinToss == 0) ? 1 : 0;

        aantalAntwoorden = parenLijst.size() * 8;
        Paar huidigPaar = parenLijst.get(paarIndex);

        woord1 = huidigPaar.getWoorden().get(coinToss);
        woord2 = huidigPaar.getWoorden().get(reverseCoinToss);

        afbeeldingen.get(0).setImageResource(getResources().getIdentifier(woord1.getContextAfbeelding(), "drawable", getPackageName()));
        afbeeldingen.get(1).setImageResource(getResources().getIdentifier(woord2.getContextAfbeelding(), "drawable", getPackageName()));

        afbeeldingen.get(0).setTag(woord1.getAudio());
        afbeeldingen.get(1).setTag(woord2.getAudio());
    }

    private void laadMiddenveldAfbeeldingen() {
        middenveld = (RelativeLayout) findViewById(R.id.middenveld);
        ConstraintLayout achtergrond = (ConstraintLayout) findViewById(R.id.backgroundLayout);

        achtergrond.setOnDragListener(new BackgroundDragListener());

        Random rand = new Random();
        
        for (int i = 0; i < 8; i++) {
            TouchableImageView antwoord = new TouchableImageView(this);

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
                antwoord.setImageResource(getResources().getIdentifier(woord1.getAfbeelding(), "drawable", getPackageName()));
                antwoord.setTag(woord1.getAudio());
            } else {
                antwoord.setImageResource(getResources().getIdentifier(woord2.getAfbeelding(), "drawable", getPackageName()));
                antwoord.setTag(woord2.getAudio());
            }
            middenveld.addView(antwoord);
        }
    }


    private void laadResultaat(){
        resultaat = new Result(1);
        resultaat.setWord(woord1.getTekst() + "/" + woord2.getTekst());
    }

    private void spreek(String tekst) {
        //Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    private void volgendeActivity(){
        Intent intent = new Intent(Oef1Activity.this, Oef2Activity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    private void postResult(){
        if (User.hasToken()){
            Log.i("Token", User.getToken());
            Gson gson = new Gson();
            HttpPOSTer httpPost = new HttpPOSTer();
            httpPost.setJsonObject(gson.toJson(resultaat));

            Log.i("Tokengson", gson.toJson(resultaat));

            httpPost.setOnResultReadyListener(new HttpPOSTer.OnResultReadyListener() {
                @Override
                public void resultReady(String result) {
                    // Is goed zo
                }
            });
            httpPost.execute(Config.backendServer + "/results");
        }
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                // Originele afbeelding verdwijnt
                view.setVisibility(View.INVISIBLE);
                spreek(view.getTag().toString());
                middenveld.setEnabled(false);
                return true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
            }

            return false;
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
                    resultaat.increaseAmountCorrect();
                    score++;
                    scoreTextView.setText(String.valueOf(score));
                } else {
                    // Fout gedropt
                    resultaat.increaseAmountWrong();
                }

                if (geantwoord == aantalAntwoorden) {
                    // Oefening voorbij
                    // Resultaat opslaan
                    postResult();
                    // Naar de volgende oefening
                    volgendeActivity();
                } else if (geantwoord % 8 == 0) {
                    // Volgend paar
                    paarIndex++;
                    // Resultaat opslaan
                    postResult();
                    // Laad nieuwe afbeeldingen
                    laadAfbeeldingen();
                }
            }
            return true;
        }
    }

    class BackgroundDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                // Afbeelding wordt terug zichtbaar
                View view = (View) event.getLocalState();
                view.setVisibility(View.VISIBLE);
                middenveld.setEnabled(true);
            }
            return true;
        }
    }
}
