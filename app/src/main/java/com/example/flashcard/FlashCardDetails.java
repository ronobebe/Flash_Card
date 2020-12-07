package com.example.flashcard;

import java.io.Serializable;

public class FlashCardDetails implements Serializable {

    String QuestionID;
    String CreatedDate;
    String Question;
    String Answer;
    String Category;
    String SunCategory;

    // last visit Update
    String LastVisitDate;
    String TotalVisit;
    String QuestionStatus;

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSunCategory() {
        return SunCategory;
    }

    public void setSunCategory(String sunCategory) {
        SunCategory = sunCategory;
    }

    public String getLastVisitDate() {
        return LastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        LastVisitDate = lastVisitDate;
    }

    public String getTotalVisit() {
        return TotalVisit;
    }

    public void setTotalVisit(String totalVisit) {
        TotalVisit = totalVisit;
    }

    public String getQuestionStatus() {
        return QuestionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        QuestionStatus = questionStatus;
    }
}
