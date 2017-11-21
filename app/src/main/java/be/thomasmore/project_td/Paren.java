package be.thomasmore.project_td;

import java.util.ArrayList;
import java.util.List;

public class Paren{
    private static List<Paar> lijst;

    public static void maakLijst(List<String> nodigeParen, boolean ouderDan5){

        if (lijst == null){
            lijst = new ArrayList<>();
        }else{
            lijst.clear();
        }

        Woord woord1;
        Woord woord2;
        Paar paar;

        for (String p : nodigeParen) {
            switch (p) {
                case "LJ":

                    break;
                case "LW":
                    woord1 = new Woord("Leeg");
                    woord2 = new Woord("Weeg");
                    paar = new Paar(woord1, woord2, p);
                    lijst.add(paar);

                    if (ouderDan5) {
                        woord1 = new Woord("Lacht");
                        woord2 = new Woord("Wacht");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }
                    break;
                case "RJ":

                    break;
                case "RW":
                    woord1 = new Woord("Rol");
                    woord2 = new Woord("Wol");
                    paar = new Paar(woord1, woord2, p);
                    lijst.add(paar);

                    if (ouderDan5) {
                        woord1 = new Woord("Rapen");
                        woord2 = new Woord("Wapen");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }
                    break;
                case "BP":
                    woord1 = new Woord("Beer");
                    woord2 = new Woord("Peer");
                    paar = new Paar(woord1, woord2, p);
                    lijst.add(paar);

                    if (ouderDan5) {
                        woord1 = new Woord("Bad");
                        woord2 = new Woord("Pad");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }
                    break;
                case "VF":

                    break;
                case "ZS":

                    break;
                case "DT":
                    woord1 = new Woord("Dak");
                    woord2 = new Woord("Tak");
                    paar = new Paar(woord1, woord2, p);
                    lijst.add(paar);

                    if (ouderDan5) {
                        woord1 = new Woord("Das");
                        woord2 = new Woord("Tas");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }
                    break;
            }
        }
    }

    public static List<Paar> getLijst() {
        return lijst;
    }

}
