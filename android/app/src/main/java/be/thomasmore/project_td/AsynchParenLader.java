package be.thomasmore.project_td;

import android.os.AsyncTask;

import java.util.ArrayList;

public class AsynchParenLader extends AsyncTask<String, Void, String> {

    private ArrayList<String> nodigeParen;
    private boolean ouderDan5;

    public AsynchParenLader(ArrayList<String> nodigeParen, boolean ouderDan5, OnResultReadyListener onResultReadyListener) {
        this.nodigeParen = nodigeParen;
        this.ouderDan5 = ouderDan5;
        this.onResultReadyListener = onResultReadyListener;
    }

    public interface OnResultReadyListener {
        void resultReady(String result);
    }

    private OnResultReadyListener onResultReadyListener;

    public void setOnResultReadyListener(OnResultReadyListener listener) {
        onResultReadyListener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        return maakLijst();
    }

    @Override
    protected void onPostExecute(String result) {
        onResultReadyListener.resultReady(result);
    }

    private String maakLijst() {
        Paren.maakLijst(nodigeParen, ouderDan5);
        return null;
    }

}
