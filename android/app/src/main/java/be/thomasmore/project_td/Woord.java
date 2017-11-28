package be.thomasmore.project_td;

public class Woord {
    private String tekst;

    public Woord(String tekst) {
        this.tekst = tekst;
    }

    public String getTekst() {
        return tekst;
    }

    public String getAfbeelding() {
        return tekst.toLowerCase();
    }

    public String getAudio() {
        return tekst.toLowerCase() + ".mp3";
    }

    public String getContextAfbeelding() {
        return tekst.toLowerCase() + "_context";
    }

    public String getContextAudio() {
        return tekst.toLowerCase() + "_context.mp3";
    }
}
