package com.fenix.worldweather;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by fenix on 21.07.2015.
 */

public class WeatherTask extends AsyncTask<String,Void,WeatherData> {
    private String response;
    private WeatherData weatherData;
    private WeakReference<Activity> activityWeakReference;

    private static final String TEST = "WeatherTask";


    WeatherTask(Activity activity, WeatherData data){
        this.weatherData = data;
        this.activityWeakReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected WeatherData doInBackground(String... params) {
        WeatherData locWeatherData = new WeatherData();
        response = (new WeatherClient()).getWeatherData(params[0]);
        if(response==null){
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activityWeakReference.get().getBaseContext(),
                            "По вашому запиту нічого не знайдено", Toast.LENGTH_LONG).show();

                }
            });
            return null;
        }
        try {
            locWeatherData = (new JSONWeatherParser()).getWeatherData(response);
            Bitmap bitmap = (new WeatherClient()).getImage(locWeatherData.weather.getIcon());
            locWeatherData.setImage( bitmap );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locWeatherData;
    }

    @Override
    protected  void onPostExecute(WeatherData data){
       if(data.coord.getLon()!=null) {
           weatherData = data;
           ((MapsActivity) activityWeakReference.get()).showWeather(data);
       }
    }

    class WeatherClient {

        private static final String TEST = "WeatherClient";
        private static final  String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
        private static final String IMG_URL = "http://openweathermap.org/img/w/";
        private static final String APP_KEY = "6de029d9cc6eeb5e266e308d56f8e56a";


        //  http://api.openweathermap.org/data/2.5/find?q=London&units=metric&lang=ua&mode=json
        public String getWeatherData(String params) {


            BufferedReader reader = null;
            URLConnection conn = null;
            //Recipe[] recipeArray = new Recipe[30];

            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL(BASE_URL+"?q="+params+"&type=accurate&units=metric&lang=ua&mode=json"+"&APPID="+APP_KEY);
                // Send POST data request
                conn = url.openConnection();
                conn.setDoOutput(true);
                //conn.setDoInput(true);
                //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                //wr.write();
                //Log.d(TEST, "Serch params = " + params[1]);
                //wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "");
                }
                Log.d(TEST, "JSON = " + sb.toString());
                return sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


            return null;
        }

        public Bitmap getImage(String params){


            BufferedReader reader = null;
            URLConnection conn = null;
            JSONObject jsonResponse;
            //Recipe[] recipeArray = new Recipe[30];

            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL(IMG_URL+params+".png");
                // Send POST data request
                conn = url.openConnection();
                //conn.setDoOutput(true);
                //conn.setDoInput(true);
                //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                //wr.write(params);
                //Log.d(TEST, "Serch params = " + params[1]);
                //wr.flush();

                // Get the server response


                Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());


                // Read Server Response

                return bmp;

            } catch (MalformedURLException e) {
                Log.d(TEST, "Crach1 ");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TEST, "Crach3 ");
                e.printStackTrace();}
            return null;
        }

    }

    class JSONWeatherParser{

        public WeatherData getWeatherData(String s) throws JSONException {
            WeatherData weather = new WeatherData();
            Log.d(TEST,s.toString());
            JSONObject jsonObject = new JSONObject(s);
            if(jsonObject.optString("message").equals("bad request")){
                return null;
            }
            JSONObject obj = jsonObject.getJSONObject("coord");
            weather.coord.setLat(getFloat("lat",obj));
            weather.coord.setLon(getFloat("lon", obj));

            JSONArray arr = jsonObject.getJSONArray("weather");
            obj = arr.getJSONObject(0);
            weather.weather.setId(getInt("id", obj));
            weather.weather.setMain(getString("main", obj));
            weather.weather.setDescription(getString("description", obj));
            weather.weather.setIcon(getString("icon", obj));
            weather.setBase(getString("base", jsonObject));

            obj = getObject("main",jsonObject);
            weather.main.setTemp(getFloat("temp", obj));
            weather.main.setPressure(getFloat("pressure", obj));
            weather.main.setHumidity(getInt("humidity", obj));
            weather.main.setTemp_min(getFloat("temp_min", obj));
            weather.main.setTemp_max(getFloat("temp_max", obj));

            obj = getObject("wind",jsonObject);
            weather.wind.setSpeed(getFloat("speed", obj));
            weather.wind.setDirection(getFloat("deg", obj));

            weather.setClouds(getString("all", getObject("clouds", jsonObject)));
            weather.setRain(getString("3h", getObject("rain", jsonObject)));
            weather.setData(getLong("dt", jsonObject));

            obj = getObject("sys",jsonObject);
            weather.sys.setType(getString("type",obj));
            weather.sys.setId(getInt("id", obj));
            weather.sys.setMessage(getString("message", obj));
            weather.sys.setCounty(getString("country", obj));
            weather.sys.setSunrise(getLong("sunrise", obj));
            weather.sys.setSunset(getLong("sunset", obj));

            weather.setCityID(getInt("id", jsonObject));
            weather.setCityName(getString("name", jsonObject));
            weather.setCod(getInt("cod",jsonObject));

            return weather;
        }
        private String getString(String tag, JSONObject jObj)throws JSONException{
            if(jObj==null) return null;
            return jObj.optString(tag);
        }

        private Float getFloat(String tag, JSONObject jObj)throws JSONException{
            if(jObj==null) return null;
            return (float)jObj.optDouble(tag);
        }

        private Integer getInt(String tag, JSONObject jObj)throws JSONException{
            if(jObj==null) return null;
            return jObj.optInt(tag);
        }

        private JSONObject getObject(String tag, JSONObject jObj)throws JSONException{
            if(jObj==null) return null;
            return jObj.optJSONObject(tag);
        }

        private Long getLong(String tag, JSONObject jObj)throws JSONException{
            if(jObj==null) return null;
            return jObj.getLong(tag);
        }


    }






}





