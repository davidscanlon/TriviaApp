package com.example.david.triviaquiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by David on 3/4/18.
 */

public class Question implements Parcelable {

    ArrayList<String> answerChoices;
    String id;
    String text;
    //TODO: figure out how to get the image
    String answer; // should this be int?

    public Question () {

    }


    protected Question(Parcel in) {
        answerChoices = in.createStringArrayList();
        id = in.readString();
        text = in.readString();
        answer = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public void addAnswerChoice(String choice) {
        answerChoices.add(choice);
    }

    public ArrayList<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(ArrayList<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(answerChoices);
        parcel.writeString(id);
        parcel.writeString(text);
        parcel.writeString(answer);
    }
}
