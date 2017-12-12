package be.thomasmore.project_td;

public class Woord {
    private String tekst;

    public Woord(String tekst) {
        this.tekst = tekst;
    }

    public String getTekst() {
        return tekst;
    }

    public String getResource() {
        return tekst.toLowerCase() + "_";
    }

    public String getContextResource() {
        return tekst.toLowerCase() + "_context";
    }

}
