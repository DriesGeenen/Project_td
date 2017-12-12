package be.thomasmore.project_td;

public class Coin {
    private double value;

    public Coin(){
        toss();
    }

    public void toss(){
        value = Math.random();
    }

    public int getTop() {
        return (value < 0.5)? 1 : 0;
    }

    public int getBottom() {
        return (value < 0.5)? 0 : 1;
    }

    public int getTopDrieVierde(){
        return (value < 0.75)? 1 : 0;
    }

    public int getBottomEenVierde(){
        return (value < 0.75)? 0 : 1;
    }
}
