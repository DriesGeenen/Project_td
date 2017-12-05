package be.thomasmore.project_td;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Oef2Activity extends AppCompatActivity {

    private List<Paar> parenLijst;
    private TextView scoreTextView;
    private int geantwoord;
    private int aantalAntwoorden;
    private int score;
    private String uitspraak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oef2);
        ((TextView) findViewById(R.id.headerTextView)).setText("Oefening 2");

        initialiseerVariabelen();

        laadAfbeeldingen();
    }

    private void initialiseerVariabelen(){
        /*List<String> nodigeParen = new ArrayList<>();
        nodigeParen.add("DT");
        Paren.maakLijst(nodigeParen, false);*/

        parenLijst = Paren.getLijst();
        geantwoord = 0;
        aantalAntwoorden = parenLijst.size();
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        Intent intent = getIntent();

        score = intent.getIntExtra("score", 0);
        scoreTextView.setText(String.valueOf(score));
    }

    private void laadAfbeeldingen(){
        Paar huidigPaar = parenLijst.get(geantwoord);

        Woord woord1 = huidigPaar.getWoorden().get(0);
        Woord woord2 = huidigPaar.getWoorden().get(1);

        ImageView afbeelding1 = (ImageView) findViewById(R.id.afbeelding1);
        ImageView afbeelding2 = (ImageView) findViewById(R.id.afbeelding2);

        afbeelding1.setImageResource(getResources().getIdentifier(woord1.getAfbeelding(), "drawable", getPackageName()));
        afbeelding2.setImageResource(getResources().getIdentifier(woord2.getAfbeelding(), "drawable", getPackageName()));

        if (Math.random() < 0.5){
            afbeelding1.setTag("correct");
            afbeelding2.setTag("");
            uitspraak = woord1.getAudio();
        } else {
            afbeelding1.setTag("");
            afbeelding2.setTag("correct");
            uitspraak = woord2.getAudio();
        }
        spreek(uitspraak);
    }

    public void afbeeldingClick(View view){
        if (view.getTag().toString() == "correct"){
            score+=8;
            scoreTextView.setText(String.valueOf(score));
            spreek(uitspraak);

        }
        geantwoord++;
        if (geantwoord == aantalAntwoorden){
            volgendeActivity();
        } else{
            laadAfbeeldingen();
        }

    }

    private void spreek(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    private void volgendeActivity(){
        Intent intent = new Intent(Oef2Activity.this, Oef3Activity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}
