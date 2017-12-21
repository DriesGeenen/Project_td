package be.thomasmore.project_td;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Paren{
    private static List<Paar> lijst;

    public static String maakLijst(List<String> nodigeParen, boolean ouderDan5){

        if (lijst == null){
            lijst = new ArrayList<>();
        }else{
            lijst.clear();
        }

        Woord woord1;
        Woord woord2;
        Paar paar;

        Coin coin = new Coin();

        for (String p : nodigeParen) {
            switch (p) {
                case "LJ":
                    coin.toss();
                    if (coin.getTopDrieVierde() == 1 || nodigeParen.size() <= 2) {
                        woord1 = new Woord("Lacht");
                        woord2 = new Woord("Jacht");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    if (ouderDan5) {
                        coin.toss();
                        if (coin.getTopDrieVierde() == 1 || nodigeParen.size() <= 2) {
                            woord1 = new Woord("Lager");
                            woord2 = new Woord("Jager");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }
                    }

                    break;
                case "LW":
                    coin.toss();
                    if (coin.getTopDrieVierde() == 1){
                        woord1 = new Woord("Lacht");
                        woord2 = new Woord("Wacht");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    if (ouderDan5) {
                        coin.toss();
                        if (coin.getTopDrieVierde() == 1) {
                            woord1 = new Woord("Lijst");
                            woord2 = new Woord("Wijst");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }

                        coin.toss();
                        if (coin.getTopDrieVierde() == 1) {
                            woord1 = new Woord("Lijken");
                            woord2 = new Woord("Wijken");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }

                        coin.toss();
                        if (coin.getTopDrieVierde() == 1) {
                            woord1 = new Woord("Lang");
                            woord2 = new Woord("Wang");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }
                    }
                    break;
                case "RJ":
                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Roker");
                        woord2 = new Woord("Joker");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    if (ouderDan5) {
                        coin.toss();
                        if (coin.getTop() == 1) {
                            woord1 = new Woord("Rodelen");
                            woord2 = new Woord("Jodelen");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }
                    }

                    break;
                case "RW":
                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Rol");
                        woord2 = new Woord("Wol");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Rij");
                        woord2 = new Woord("Wei");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    if (ouderDan5) {
                        coin.toss();
                        if (coin.getTop() == 1) {
                            woord1 = new Woord("Rapen");
                            woord2 = new Woord("Wapen");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }

                        coin.toss();
                        if (coin.getTopDrieVierde() == 1) {
                            woord1 = new Woord("Rekker");
                            woord2 = new Woord("Wekker");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }
                    }
                    break;
                case "BP":
                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Beer");
                        woord2 = new Woord("Peer");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTop() == 1) {
                        woord1 = new Woord("Bak");
                        woord2 = new Woord("Pak");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Baard");
                        woord2 = new Woord("Paard");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Bed");
                        woord2 = new Woord("Pet");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Boot");
                        woord2 = new Woord("Poot");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Bijl");
                        woord2 = new Woord("Pijl");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    if (ouderDan5) {
                        coin.toss();
                        if (coin.getTopDrieVierde() == 1) {
                            woord1 = new Woord("Bad");
                            woord2 = new Woord("Pad");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }

                        coin.toss();
                        if (coin.getTop() == 1) {
                            woord1 = new Woord("Beter");
                            woord2 = new Woord("Peter");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }

                        coin.toss();
                        if (coin.getTop() == 1) {
                            woord1 = new Woord("Bot");
                            woord2 = new Woord("Pot");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }
                    }
                    break;
                case "VF":
                    coin.toss();
                    if (coin.getTopDrieVierde() == 1 || nodigeParen.size() <= 2) {
                        woord1 = new Woord("Vier");
                        woord2 = new Woord("Fier");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    break;
                case "DT":
                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Dak");
                        woord2 = new Woord("Tak");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTop() == 1) {
                        woord1 = new Woord("Daken");
                        woord2 = new Woord("Taken");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTopDrieVierde() == 1) {
                        woord1 = new Woord("Dop");
                        woord2 = new Woord("Top");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    coin.toss();
                    if (coin.getTop() == 1) {
                        woord1 = new Woord("Dam");
                        woord2 = new Woord("Tam");
                        paar = new Paar(woord1, woord2, p);
                        lijst.add(paar);
                    }

                    if (ouderDan5) {
                        coin.toss();
                        if (coin.getTopDrieVierde() == 1) {
                            woord1 = new Woord("Das");
                            woord2 = new Woord("Tas");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }

                        coin.toss();
                        if (coin.getTop() == 1) {
                            woord1 = new Woord("Deken");
                            woord2 = new Woord("Teken");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }

                        coin.toss();
                        if (coin.getTop() == 1) {
                            woord1 = new Woord("Denen");
                            woord2 = new Woord("Tenen");
                            paar = new Paar(woord1, woord2, p);
                            lijst.add(paar);
                        }
                    }
                    break;
            }
        }
        Collections.shuffle(lijst);
        return "";
    }

    public static List<Paar> getLijst() {
        return lijst;
    }

}
