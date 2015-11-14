package johnson.bestdescription;


public class QuestionAnswer {
    private int ID;
    private String Question;
    private String Answer;
    private String incorrect1;
    private String incorrect2;
    private String incorrect3;

    QuestionAnswer(){}

    QuestionAnswer(String question){
        Question = question;
    }

    QuestionAnswer(int ID, String Question, String Answer){
        ID = this.ID;
        Question = this.Question;
        Answer = this.Answer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getIncorrect1() {
        return incorrect1;
    }

    public void setIncorrect1(String incorrect1) {
        this.incorrect1 = incorrect1;
    }

    public String getIncorrect2() {
        return incorrect2;
    }

    public void setIncorrect2(String incorrect2) {
        this.incorrect2 = incorrect2;
    }

    public String getIncorrect3() {
        return incorrect3;
    }

    public void setIncorrect3(String incorrect3) {
        this.incorrect3 = incorrect3;
    }
}
