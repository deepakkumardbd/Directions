package com.api.google;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MapActivity {

    private String uri;

    private String getDirectionsUrl(double originLat, double originLong, double destLat, double destLong) throws IOException {

        // Origin of route
        String str_origin = "origin="+originLat+","+originLong;

        // Destination of route
        String str_dest = "destination="+destLat+","+destLong;

        Properties prop = readPropertiesFile("C:\\Users\\d.bd.kumar\\Desktop\\GApps\\src\\main\\resources\\application.properties");

        String key = "key=" + prop.getProperty("microserviceurl");

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }

    public static void main(String args[]) throws IOException, JSONException {
        MapActivity mapActivity = new MapActivity();
        String url = mapActivity.getDirectionsUrl(12.93175,77.62872, 12.92662, 77.63696);
        String data = mapActivity.downloadUrl(url);
        DirectionsParser dp = new DirectionsParser();
        JSONObject jObject = new JSONObject(data);
        List<HashMap<String, String>> coordinates =  dp.parse(jObject);
        for(int i = 0; i < coordinates.size(); i++){
            System.out.println(coordinates.get(i).get("lat") + " " + coordinates.get(i).get("lng"));
        }
    }
}