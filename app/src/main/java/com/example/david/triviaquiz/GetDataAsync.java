package com.example.david.triviaquiz;

import android.os.AsyncTask;

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

public class GetDataAsync extends AsyncTask<String, Void, ArrayList<Question>> {

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
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
                JSONArray sources = root.getJSONArray("sources");

                for (int i = 0; i < sources.length(); i++) {
                    JSONObject sourceJson = sources.getJSONObject(i);
                    Question question = new Question();
                    source.setId(sourceJson.getString("id"));
                    source.setName(sourceJson.getString("name"));

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
