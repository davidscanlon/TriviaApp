package com.example.david.triviaquiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Question> questionList = new ArrayList<Question>();
    Button exitButton;
    Button startButton;
    TextView readyText;
    ImageView triviaImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exitButton = findViewById(R.id.button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        
        startButton = findViewById(R.id.button_startTrivia);
        startButton.setEnabled(false);
        readyText = findViewById(R.id.textView_ready);
        readyText.setVisibility(View.INVISIBLE);
        triviaImage = findViewById(R.id.imageView_trivia);
        triviaImage.setVisibility(View.INVISIBLE);


        if (isConnected()) {
            new GetDataAsync().execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }



    } // end onCreate


    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    } //end isConnected

    public void sendData(ArrayList<Question> questions) {
        questionList = questions;
    }

    public class GetDataAsync extends AsyncTask<String, Integer, ArrayList<Question>> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading Trivia");
            pd.setMax(100);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Question> questions) {
            sendData(questions);
            pd.dismiss();
            startButton.setEnabled(true);
            readyText.setVisibility(View.VISIBLE);
            triviaImage.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
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
                        //JSONObject choicesJson = questionJson.getJSONObject("choices");
                        JSONArray choicesArray = questionJson.getJSONArray("choice");
                        question.setAnswer(questionJson.getString("answer"));

                        for (int j = 0; j < choicesArray.length(); j++) {
                            //publishProgress(j);
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
            publishProgress(100);
            return result;
        }
    } // end Async class

} // end MainActivity Class
