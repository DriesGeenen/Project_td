package be.thomasmore.project_td;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Oef1Activity extends AppCompatActivity {

    private TextView scoreTextView;
    private List<Paar> parenLijst;
    private Paar huidigPaar;
    private int score;
    private int geantwoord;
    private int paarIndex;
    private int aantalAntwoorden;
    private Woord woord1;
    private Woord woord2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oef1);
        ((TextView) findViewById(R.id.headerTextView)).setText("Oefening 1");

        initialiseerVariabelen();

        if (parenLijst.size() == 0){
            Intent intent = new Intent(this, LeeftijdActivity.class);
            startActivity(intent);
        } else{
            laadAfbeeldingen();
        }
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void initialiseerVariabelen() {
        Bundle bundle = getIntent().getExtras();
        boolean ouderDan5 = bundle.getBoolean("ouderDan5");
        ArrayList<String> nodigeParen = bundle.getStringArrayList("nodigeParen");

        Paren.maakLijst(nodigeParen, ouderDan5);
        parenLijst = Paren.getLijst();
        score = 0;
        geantwoord = 0;
        paarIndex = 0;
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
    }

    private void laadAfbeeldingen() {
        laadContextAfbeeldingen();
        laadMiddenveldAfbeeldingen();
    }

    private void laadContextAfbeeldingen() {
        aantalAntwoorden = parenLijst.size() * 8;
        huidigPaar = parenLijst.get(paarIndex);
        woord1 = huidigPaar.getWoorden().get(0);
        woord2 = huidigPaar.getWoorden().get(1);

        ImageView afbeelding1 = (ImageView) findViewById(R.id.afbeelding1);
        ImageView afbeelding2 = (ImageView) findViewById(R.id.afbeelding2);

        afbeelding1.setImageResource(getResources().getIdentifier(woord1.getContextAfbeelding(), "drawable", getPackageName()));
        afbeelding2.setImageResource(getResources().getIdentifier(woord2.getContextAfbeelding(), "drawable", getPackageName()));

        afbeelding1.setTag(woord1.getAudio());
        afbeelding2.setTag(woord2.getAudio());

        afbeelding1.setOnDragListener(new MyDragListener());
        afbeelding2.setOnDragListener(new MyDragListener());
    }

    private void laadMiddenveldAfbeeldingen() {
        RelativeLayout middenveld = (RelativeLayout) findViewById(R.id.middenveld);
        ConstraintLayout achtergrond = (ConstraintLayout) findViewById(R.id.backgroundLayout);

        achtergrond.setOnDragListener(new BackgroundDragListener());

        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            ImageView antwoord = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );

            int imageSize = 100;
            int left = rand.nextInt(imageSize);
            int top = rand.nextInt(imageSize);
            int right = imageSize - left;
            int bottom = imageSize - top;

            params.setMargins(dpToPx(left), dpToPx(top), dpToPx(right), dpToPx(bottom));
            antwoord.setLayoutParams(params);
            antwoord.setOnTouchListener(new MyTouchListener());

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

    private void spreek(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
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
                return true;
            } else {
                return false;
            }
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
                ImageView dropped = (ImageView) view;

                // View verwijderen voor performance / anti-crash
                ((ViewGroup) view.getParent()).removeView(view);

                geantwoord++;

                if (dropped.getTag().toString().equals(dropTarget.getTag().toString())) {
                    score++;
                    scoreTextView.setText(String.valueOf(score));
                }

                if (geantwoord == aantalAntwoorden) {
                    Intent intent = new Intent(Oef1Activity.this, Oef2Activity.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                } else if (geantwoord % 8 == 0) {
                    paarIndex++;
                    laadAfbeeldingen();
                }
            }

            /*switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                case DragEvent.ACTION_DROP:
                    break;
                default:
                    break;
            }*/
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
