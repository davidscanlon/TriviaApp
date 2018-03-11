package com.example.david.triviaquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by David on 3/8/18.
 */

public class GetDataAsync extends AsyncTask<String, Integer, ArrayList<Question>> {

    ProgressDialog pd;
    static String QUESTION_ARRAY = "";

    public void sendData(ArrayList<Question> questions) {

    }

    @Override
    protected void onPreExecute() {
        //pd = new ProgressDialog(MainActivity.class);
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        sendData(questions);
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        HttpURLConnection connection = null;
        ArrayList<Question> result = new ArrayList<>();
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF-8");

                JSONObject root = new JSONObject(json);
                JSONArray questions = root.getJSONArray("questions");

                for (int i = 0; i < questions.length(); i++) {
                    JSONObject questionJson = questions.getJSONObject(i);
                    Question question = new Question();
                    question.setId(questionJson.getString("id"));
                    question.setText(questionJson.getString("text"));
                    //TODO: Get that image
                    JSONObject choicesJson = questionJson.getJSONObject("choices");
                    JSONArray choicesArray = choicesJson.getJSONArray("choice");
                    question.setAnswer(choicesJson.getString("answer"));
                    for (int j = 0; j < choicesArray.length(); j++) {
                        question.addAnswerChoice(choicesArray.getString(j));
                    }
                    result.add(question);
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}
