package be.thomasmore.project_td;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPOSTer extends AsyncTask<String, Void, String> {

    private String jsonObject;

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public HttpPOSTer() {
    }

    public interface OnResultReadyListener {
        public void resultReady(String result);
    }

    private OnResultReadyListener onResultReadyListener;

    public void setOnResultReadyListener(OnResultReadyListener listener) {
        onResultReadyListener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        return postTextToUrl(urls[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        onResultReadyListener.resultReady(result);
    }

    private String postTextToUrl(String urls) {
        String text = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Accept", "application/json");
            if (User.hasToken())
                urlConnection.setRequestProperty("Authorization", User.getToken());
            urlConnection.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(jsonObject);
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();

                text = sb.toString();
            } else {
                Log.e("Error: ", urlConnection.getResponseMessage());
            }

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());

        }

        return text;
    }

}
