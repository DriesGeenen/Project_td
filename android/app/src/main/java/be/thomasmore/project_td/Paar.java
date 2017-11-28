package be.thomasmore.project_td;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Paar {
    private List<Woord> woorden;
    private String fonologishProcess;
    private String klanken;

    public Paar(Woord woord1, Woord woord2, String klanken) {
        this.woorden = new ArrayList<>();
        this.woorden.add(woord1);
        this.woorden.add(woord2);
        this.klanken = klanken;
    }

    public List<Woord> getWoorden() {
        return woorden;
    }

    public String getFonologishProcess() {
        return fonologishProcess;
    }

    public String getKlanken() {
        return klanken;
    }




}
