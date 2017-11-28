package be.thomasmore.project_td;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        ConstraintLayout achtergrond = (ConstraintLayout) findViewById(R.id.backgroundLayout);

        achtergrond.setOnDragListener(new BackgroundDragListener());
    }

    private void initialiseerVariabelen() {
        parenLijst = Paren.getLijst();
        geantwoord = 0;
        // Beperkt het aantal antwoorden op deelbaar door 2
        aantalAntwoorden = (parenLijst.size()/2) * 4;
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        scoreTextView.setText(String.valueOf(score));

        myDragListener = new MyDragListener();
        myTouchListener = new MyTouchListener();
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
                            } else if (v3 instanceof TouchableTextView){
                                kaartTextViews.add((TouchableTextView)v3);
                            }
                        }
                    }
                }
            }
        }
    }

    private void laadAfbeeldingen() {
        if(aantalAntwoorden != 0) {
            Integer[] indexArray = {0, 1, 2, 3};
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

                mainImageView.setImageResource(getResources().
                        getIdentifier(woordenLijst.get(i)
                                .getAfbeelding(), "drawable", getPackageName()));

                mainImageView.setTag(woordenLijst.get(i).getAfbeelding());

                mainImageView.setOnDragListener(myDragListener);
            }

            Collections.shuffle(indexLijst);

            for (int i = 0; i < kaartTextViews.size(); i++) {
                TouchableTextView kaartTextView = kaartTextViews.get(indexLijst.get(i));

                kaartTextView.setTag(woordenLijst.get(i).getAfbeelding());
                kaartTextView.setOnTouchListener(myTouchListener);
            }
        } else{
            volgendeActivity();
        }

    }

    private void spreek(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    private void volgendeActivity(){
        Intent intent = new Intent(Oef3Activity.this, Oef4Activity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                // Originele afbeelding verdwijnt
                view.setVisibility(View.INVISIBLE);

                spreek(view.getTag().toString() + ".mp3");

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
                ImageView dropTarget = (ImageView) v;

                //view being dragged and dropped
                TextView dropped = (TextView) view;

                // View verwijderen voor performance / anti-crash
                ((ViewGroup) view.getParent()).removeView(view);

                geantwoord++;

                // Wanneer correct
                if (dropped.getTag().toString().equals(dropTarget.getTag().toString())) {
                    score+=4;
                    scoreTextView.setText(String.valueOf(score));

                    kleineImageViews.get(mainImageViews.indexOf(dropTarget))
                            .setImageResource(getResources()
                                    .getIdentifier(dropTarget.getTag().toString(), "drawable", getPackageName()));
                }

                if (geantwoord == aantalAntwoorden) {
                    volgendeActivity();
                } else if(geantwoord % 4 == 0){
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
            }
            return true;
        }
    }
}
