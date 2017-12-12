package be.thomasmore.project_td;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {
    private static MediaPlayer mp;

    static void speelIntroductie(Context context, Integer oefeningNummer, MediaPlayer.OnCompletionListener myCompletionListener) {
        if (mp != null) mp.release();
        mp = null;
        mp = MediaPlayer.create(context, context.getResources().getIdentifier(
                        "instructie_" + oefeningNummer.toString(), "raw", context.getPackageName()));
        mp.setOnCompletionListener(myCompletionListener);
        mp.start();
    }

    static void spreek(Context context, String tekst, MediaPlayer.OnCompletionListener myCompletionListener) {
        mp.release();
        mp = null;
        mp = MediaPlayer.create(context, context.getResources().getIdentifier(
                tekst, "raw", context.getPackageName()));
        mp.setOnCompletionListener(myCompletionListener);
        mp.start();
    }

}
