package be.thomasmore.project_td;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer bevestigCorrectMediaPlayer;
    private static boolean speelIntro;
    private static boolean speelBevestiging;

    static {
        speelIntro = true;
        speelBevestiging = true;
    }

    static void speelIntroductie(Context context, Integer oefeningNummer, MediaPlayer.OnCompletionListener myCompletionListener) {
        releaseAndNullify();
        mediaPlayer = MediaPlayer.create(context, context.getResources().getIdentifier(
                "instructie_" + oefeningNummer.toString(), "raw", context.getPackageName()));
        mediaPlayer.setOnCompletionListener(myCompletionListener);
        mediaPlayer.start();
    }

    static void spreek(Context context, String tekst, MediaPlayer.OnCompletionListener myCompletionListener) {
        releaseAndNullify();
        mediaPlayer = MediaPlayer.create(context, context.getResources().getIdentifier(
                tekst, "raw", context.getPackageName()));
        mediaPlayer.setOnCompletionListener(myCompletionListener);
        mediaPlayer.start();
    }

    static void bevestigCorrectAntwoord(Context context) {
        if (MyMediaPlayer.speelBevestiging) {
            if (bevestigCorrectMediaPlayer == null) {
                bevestigCorrectMediaPlayer = MediaPlayer.create(context, context.getResources().getIdentifier(
                        "instantrapairhorn", "raw", context.getPackageName()));
                bevestigCorrectMediaPlayer.start();
            } else {
                bevestigCorrectMediaPlayer.seekTo(0);
                bevestigCorrectMediaPlayer.start();
            }
        }
    }

    private static void releaseAndNullify() {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = null;
    }

    public static boolean doeSpeelIntro() {
        return speelIntro;
    }

    public static void toggleSpeelIntro() {
        MyMediaPlayer.speelIntro = !MyMediaPlayer.speelIntro;
    }

    public static boolean doeSpeelBevestiging() {
        return speelBevestiging;
    }

    public static void toggleSpeelBevestiging() {
        MyMediaPlayer.speelBevestiging = !MyMediaPlayer.speelBevestiging;
    }
}
