
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import java.util.TimeZone;

public class WeatherStation {

    private JSONObject weatherObject;
    private JSONObject sysObject;
    private JSONObject currentWeatherObject;
    private JSONObject windObject;
    private boolean ready;


    WeatherStation(int interval){
        WeatherWorker(interval);
    }
    private void WeatherWorker(int time) {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    //System.out.println("pulling fresh data");
                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Tacoma,USA&APPID=75ec4ff690501bd5f4572e39713bca2f&units=imperial");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    int responsecode = conn.getResponseCode();
                    StringBuilder response = new StringBuilder();
                    if(responsecode != 200)
                        throw new RuntimeException("HttpResponseCode: " +responsecode);
                    else {

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                        String inputLine;

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        in.close();

                    }
                    JSONObject request = new JSONObject(response.toString());
                    weatherObject = request.getJSONObject("main");
                    sysObject = request.getJSONObject("sys");
                    JSONArray weatherDescripObject  = request.getJSONArray("weather");
                    currentWeatherObject = weatherDescripObject.getJSONObject(0);
                    windObject = request.getJSONObject("wind");
                    ready = true;
                } catch(Exception e){
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }

            }
        }, 0, time * 1000);
    }


    public String getCurrentTemp() throws JSONException {
        return ready ? weatherObject.get("temp").toString() + " F" : "";
    }
    public String getCurrentTempMin() throws JSONException {
        return ready ? weatherObject.get("temp_min").toString() + " F" : "";
    }
    public String getCurrentTempMax() throws JSONException {
        return ready ? weatherObject.get("temp_max").toString() + " F" : "";
    }
    public String getCurrentFeelsLike() throws JSONException {
        return ready ? weatherObject.get("feels_like").toString() + " F" : "";
    }
    public String getCurrentHumid() throws JSONException {
        return ready ? weatherObject.get("humidity").toString() + " %" : "";
    }
    public String getCurrentPressure() throws JSONException {
        return ready ? weatherObject.get("pressure").toString() : "";
    }

    public String getSunrise() throws JSONException {

        if(ready){
            Date dateSunrise = new Date(sysObject.getLong("sunrise") * 1000);
            SimpleDateFormat sunriseSDF = new SimpleDateFormat("hh:mm:ss a");
            sunriseSDF.setTimeZone(TimeZone.getTimeZone("PST"));
            return sunriseSDF.format(dateSunrise);
        } else {
            return "";
        }


    }

    public String getSunset() throws JSONException {
        if(ready){
            Date dateSunset = new Date(sysObject.getLong("sunset") * 1000);
            SimpleDateFormat sunsetSDF = new SimpleDateFormat("hh:mm:ss a");
            sunsetSDF.setTimeZone(TimeZone.getTimeZone("PST"));
            return sunsetSDF.format(dateSunset);
        } else {
            return "";
        }

    }

    public String getWeatherIcon() throws JSONException {
        if(ready){
            String weatherIcon = currentWeatherObject.getString("icon");
            return "https://openweathermap.org/img/wn/" + weatherIcon +"@2x.png";
        } else {
            return "https://openweathermap.org/img/wn/03d@2x.png";
        }
    }

    public String getWeatherDescrip() throws JSONException {
        if(ready){
            return currentWeatherObject.getString("description");
        } else {
            return "";
        }
    }
    public String getWeatherMain() throws JSONException {
        if(ready){
            return currentWeatherObject.getString("main");
        } else {
            return "";
        }
    }


    public Double getWindDir() throws JSONException {
        if(ready){
            return windObject.getDouble("deg");
        } else {
            return 0.0;
        }
    }
    public String getWindSpeed() throws JSONException {
        if(ready){
            return Integer.toString((int)Math.ceil(windObject.getDouble("speed")));
        } else {
            return "";
        }
    }
}
