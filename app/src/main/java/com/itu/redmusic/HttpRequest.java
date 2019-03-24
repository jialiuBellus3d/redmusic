package com.itu.redmusic;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Jia Liu on 3/24/2019.
 */
public class HttpRequest extends AsyncTask<Void, Void, Integer> {

    class Response{
        int code;
        String uid;
        ArrayList<SongData> songsData;
        Response(){};
        Response(int code, String uid, ArrayList<SongData> songsData){
            this.code = code;
            this.uid = uid;
            this.songsData = songsData;
        }
    }

    private MainActivity mMainActivity;
    String url;
    String userId;
    SongData currentSongData;

    public HttpRequest(MainActivity activity, String url, String uid){
        //set context variables if required
        mMainActivity = activity;
        this.url = url;
        this.userId = uid;
        currentSongData = new SongData();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return HttpPost(this.url, this.userId);

    }

    @Override
    protected void onPostExecute(Integer result) {
        if(mMainActivity == null) {
            return;
        }

        if (result.equals(1) ) {
            //2: If it already exists then prompt user
            Toast.makeText(mMainActivity, "Http request successfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mMainActivity, "Http Request Failed!", Toast.LENGTH_LONG).show();
        }
    }

    private Integer HttpPost(String myUrl, String userId) {
        try {
            URL url = new URL(myUrl);
            Log.e(TAG, url.toString());
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");

            // 2. build JSON object
            JSONObject jsonObject = buidJsonObject(userId);
            Log.e(TAG, jsonObject.toString());

            // 3. add JSON content to POST request body
            setPostRequestContent(conn, jsonObject);

            // 4. make POST request to the given URL
            conn.connect();

            switch (conn.getResponseCode()) {
                case 200:
                case 201:
                    ArrayList<SongData> songsData = parseResponse(conn.getInputStream());
                    for(int i = 0; i<songsData.size(); i++){
                        SongData data = songsData.get(i);
                        mMainActivity.mSongsData.add(new SongData(data.name, data.artist, data.score, data.rank));
                    }
                    Log.e(TAG, "~~~~~~~~~~~"+ mMainActivity.mSongsData.size());
                    return 1;
            }
            // 5. return response message
            Log.e(TAG, conn.getResponseMessage() + "");
            return 0;
        } catch (MalformedURLException e){
            e.printStackTrace();
            return 0;
        } catch (IOException e){
            e.printStackTrace();
            return 0;
        } catch (JSONException e){
            e.printStackTrace();
            return 0;
        }
    }

    private JSONObject buidJsonObject(String userId) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("user_id", userId);

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

    private void updateResponse(String header, String content) {
        if (header.equals("song")) {
            String[] temps = content.split(" - ");
            if (temps.length == 2){
                currentSongData.name = temps[1];
                currentSongData.artist = temps[0];
            }
        }
        else if (header.equals("score")) {
            currentSongData.score = Float.parseFloat(content);
        }
        else if (header.equals("ranked")) {
            currentSongData.rank = Integer.parseInt(content);
        }
    }


    private ArrayList<SongData> parseResponse(InputStream inputStream) throws IOException {
        ArrayList<SongData> mSongsData = new ArrayList<SongData>();

        BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = bReader.readLine()) != null) {
            String currentHeader = "";
            String currentContent = "";
            int counterHeader = -1;
            int counterContent = -1;
            boolean colonShownUp = false;
            int counterQ = 0;
            int counterC = 0;

            // ignore front and end {}
            for (int i = 1; i < line.length()-1;){
                char c = line.charAt(i);
                //Process char
                if (c == '{') {
                    i++;
                    continue;
                }
                else if (c == ':') {
                    counterC++;
                    if(counterC%5 != 1){
                        colonShownUp = true;
                        counterContent = 0;
                    }
                    i++;
                    continue;
                }
				else if (c == '"') {
                    counterQ++;
                    if(counterQ%14 != 1 && counterQ%14 != 2) {
                        if (colonShownUp) {
                            if (counterContent < 0) {
                                counterContent = 0;
                            }
                        } else {
                            if (counterHeader < 0) {
                                counterHeader = 0;
                            } else {
                                counterHeader = -1;
                            }
                        }
                    }
                    i++;
                    continue;
                }
				else if (c == ',') {
                    counterHeader = -1;
                    counterContent = -1;
                    colonShownUp = false;
                    /*std::cout << "currentHeader: " << currentHeader << std::endl;
                    std::cout << "currentContent: " << currentContent << std::endl;*/
                    System.out.println("currentHeader: " +currentHeader + "  currentContent: "+currentContent);

                    updateResponse(currentHeader, currentContent);

                    currentHeader = "";
                    currentContent = "";
                    i++;
                    continue;
                }
				else if (c == '}') {
                    counterContent = -1;
                    counterHeader = -1;
                    colonShownUp = false;
					/*std::cout << "currentHeader: " << currentHeader << std::endl;
					std::cout << "currentContent: " << currentContent << std::endl;*/
                    System.out.println("currentHeader: " +currentHeader + "  currentContent: "+currentContent);

                    updateResponse(currentHeader, currentContent);

                    mSongsData.add(new SongData(currentSongData.name, currentSongData.artist,
                            currentSongData.score, currentSongData.rank));
                    currentSongData = new SongData();
                    currentHeader = "";
                    currentContent = "";
                    i++;
                    continue;
                }
				else {
                    if (counterHeader >= 0) {
                        counterHeader++;
                        currentHeader = currentHeader + c;
                    }

                    if (counterContent >= 0) {
                        counterContent++;
                        currentContent = currentContent + c;
                    }
                    i++;
                }
            }

            builder.append(line);
        }
        Log.e(TAG, builder.toString());
        return mSongsData;
    }

    public SongData readResponse(JsonReader reader) throws IOException {
        reader.beginObject();
        String name = "";
        String artist = "";
        float score = 0;
        int rank = 0;
        while (reader.hasNext()) {
            String tag = reader.nextName();
            if (tag.equals("song")) {
                String temp = reader.nextString();
                String[] temps = temp.split(" - ");
                if(temps.length == 0){
                    continue;
                } else if (temps.length == 2){
                    name = temps[1];
                    artist = temps[0];
                } else if(temps.length == 1){
                    artist = temps[0];
                }
            } else if (tag.equals("score")) {
                score = (float) reader.nextDouble();
            } else if (tag.equals("ranked")) {
                rank = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new SongData(name, artist, score, rank);
    }

}
