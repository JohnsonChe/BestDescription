package johnson.bestdescription;


public class QuestionAnswer {
    private int ID;
    private String Question;
    private String Answer;
    private String possibleAnswers;

    QuestionAnswer(){}

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

    public String getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(String possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
