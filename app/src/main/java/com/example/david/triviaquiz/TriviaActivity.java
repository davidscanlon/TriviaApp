package com.example.david.triviaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {


    ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        if (getIntent() != null && getIntent().getExtras() != null) {
          //  questions = getIntent().getExtras().getParcelableArrayList(MainActivity.QUESTIONS_ARRAY);
        }







    }
}
