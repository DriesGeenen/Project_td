package be.thomasmore.project_td;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences("user",0);
        String token = settings.getString("token", null);
        if (token != null){
            User.setToken(token);
            volgendeActivity();
        }
    }

    public void loginButtonClick(View view) {
        TextView gebruikersnaamTextView = (TextView) findViewById(R.id.gebruikersnaam);
        TextView wachtwoordTextView = (TextView) findViewById(R.id.wachtwoord);

        String loginJSON = "{\"username\":\"" + gebruikersnaamTextView.getText() + "\",\"password\":\"" + wachtwoordTextView.getText() + "\"}";
        HttpPOSTer httpPost = new HttpPOSTer();
        httpPost.setJsonObject(loginJSON);

        httpPost.setOnResultReadyListener(new HttpPOSTer.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                Log.i("RESULT: ", result);
                Gson gson = new Gson();
                AuthResult authResult = gson.fromJson(result, AuthResult.class);
                if (authResult.isSuccess()) {
                    settings.edit().putString("token", authResult.getToken()).apply();
                    User.setToken(authResult.getToken());
                    volgendeActivity();
                } else {
                    toon("Ongeldige login");
                }
            }
        });

        httpPost.execute(Config.backendServer + "/users/authenticate");
    }

    private void volgendeActivity(){
        Intent intent = new Intent(LoginActivity.this, LeeftijdActivity.class);
        startActivity(intent);
    }

    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}
