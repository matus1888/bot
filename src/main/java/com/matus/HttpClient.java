package com.matus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public  class HttpClient {
    URL u;
    HttpURLConnection connection;
    String response;
    public HttpClient(String url) {
        try {
            u= new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
             connection = (HttpURLConnection) u.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        int status = 0;
        try {
            status = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (status != HttpURLConnection.HTTP_OK) {
            in = connection.getErrorStream();
        } else {
            try {
                in = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
             response = convertStreamToString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String convertStreamToString(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();

        return sb.toString();
    }
}