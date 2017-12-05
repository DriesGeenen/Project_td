package be.thomasmore.project_td;

public class Result {

    int exerciseNr;
    String word;
    int amountCorrect;
    int amountWrong;

    Result(int exerciseNr){
        this.exerciseNr = exerciseNr;
        amountCorrect = 0;
        amountWrong = 0;
    }

    public int getExerciseNr() {
        return exerciseNr;
    }

    public void setExerciseNr(int exerciseNr) {
        this.exerciseNr = exerciseNr;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getAmountCorrect() {
        return amountCorrect;
    }

    public void setAmountCorrect(int amountCorrect) {
        this.amountCorrect = amountCorrect;
    }

    public int getAmountWrong() {
        return amountWrong;
    }

    public void setAmountWrong(int amountWrong) {
        this.amountWrong = amountWrong;
    }

    public void increaseAmountCorrect(){
        amountCorrect++;
    }

    public void increaseAmountWrong(){
        amountWrong++;
    }
}
