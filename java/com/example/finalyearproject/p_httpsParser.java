package com.example.finalyearproject;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import static android.content.ContentValues.TAG;

public class p_httpsParser {
    private StringBuilder stringBuilder = new StringBuilder();
    private static HttpsURLConnection setUpHttpsConnection(String urlString) {
        try {
            // Main connection file, sets the instance of x509 and then loads the certificate that has been stored.
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream certAuthInput = new BufferedInputStream(p_session_manager._context.getAssets().open("certificate.crt"));
            Certificate ca = cf.generateCertificate(certAuthInput);
            String kst = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(kst);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            // Sets the instance as TLS and continues, setups checks for later to look for a url string to be used when connecting.
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection urlhttpsConnection = connectionParams(urlString);
            urlhttpsConnection.setSSLSocketFactory(context.getSocketFactory());
            return urlhttpsConnection;
            // Error Reporting
        } catch (Exception ex) { Log.e(TAG, "SSL Connection Failed,  Error = " + ex.toString());
            return null; }
    }

    private static HttpsURLConnection connectionParams(String HttpUrlHolder) throws IOException {
        // Method for defining the basic parameters of a url connection.
        URL url = new URL(HttpUrlHolder);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setReadTimeout(10000);
        httpsURLConnection.setConnectTimeout(10000);
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        return httpsURLConnection;
    }

    private String DataParse(HashMap<String, String> Map) throws UnsupportedEncodingException {
        // Since data is incoming from a json-encode it needs to be parsed a certain way to be read properly.
        // this does that.
        for (Map.Entry<String, String> map_entry : Map.entrySet()) {
            stringBuilder.append("&");
            stringBuilder.append(URLEncoder.encode(map_entry.getKey(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(map_entry.getValue(), "UTF-8"));
        }
        return stringBuilder.toString();
    }

    private void readAndWriteData(HashMap<String, String> HashMapData, OutputStream ops) throws IOException {
        // Basic read and write data function.
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(ops, "UTF-8"));
        bw.write(DataParse(HashMapData));
        bw.flush();
        bw.close();
        ops.close();
    }

    public String postRequest(HashMap<String, String> HashMapData, String HttpsUrlHolder) throws IOException {
        // Method for sending the data and checking if the response message is correct if not terminate the connection.
        HttpsURLConnection httpsURLConnection = setUpHttpsConnection(HttpsUrlHolder);
        OutputStream ops = null;
        if (httpsURLConnection != null)
        { ops = httpsURLConnection.getOutputStream(); }
        readAndWriteData(HashMapData, ops);
        String HttpsData = "";
        if (httpsURLConnection != null) {
            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader;
                bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                HttpsData = bufferedReader.readLine();
            } else { HttpsData = "Connection Failed"; }
        }
        return HttpsData;
    }
}
