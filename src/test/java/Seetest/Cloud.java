package Seetest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.AssertJUnit;
import org.testng.annotations.*;

public class Cloud{

    private static final String APPLICATIONS_URL = "/applications";
    private static final String ACTION = "/new";
    private String host = "demo.experitest.com";//TODO: host name or ip goes here
    private String port = "443";//TODO: open port goes here
    private String webPage= "https://" + host + ":" + port + "/api/v1";
    private String authStringEnc;

    @BeforeMethod
    public void setup() {
        authStringEnc = System.getenv("accessKey");
    }


    @Test
    public void testPostNewApplication() throws IOException {

        String postURL = prepareURL();
        uploadFile(System.getenv("apkPath"), postURL);

    }
    private String prepareURL() {
        String postURL = webPage + APPLICATIONS_URL + ACTION;
        return postURL;
    }

    String uploadFile(String pathToFile, String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(url.toString());

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        /*
         *  This attaches the file to the POST:
         */
        File f = new File(pathToFile);
        builder.addTextBody(f.getName(), "yes", ContentType.TEXT_PLAIN);
        builder.addBinaryBody(
                "file",
                new FileInputStream(f),
                ContentType.APPLICATION_OCTET_STREAM,
                f.getName()
        );
        builder.addTextBody("project", "Default", ContentType.DEFAULT_TEXT);
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        uploadFile.addHeader("Authorization", "Bearer " + authStringEnc);
        System.out.println("\n Igal 1");
        // String  query = String.format("param1=%s", URLEncoder.encode("Default", "UTF-8"));
        //String query = "/projects/Default";
        //System.out.println("\n query = " + query);

        System.out.println("\nSending 'POST' request to URL : " + url + " file: " + pathToFile);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        java.io.InputStream stream = responseEntity.getContent();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        in.close();
        String finalString = responseBuffer.toString();
        System.out.println("\n Igal 2");
        System.out.println(finalString);

        System.out.println(String.format("Got response buffer: %s", responseBuffer.toString()));
        AssertJUnit.assertTrue("Did not get Success Status", responseBuffer.toString().contains("\"status\":\"SUCCESS\""));
        return finalString;
    }
    protected void printPost(URL url, HttpURLConnection httpURLConnection, String query) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        query = String.format("param1=%s&param2=%s", URLEncoder.encode("/projects", "UTF-8"), URLEncoder.encode("Default", "UTF-8"));
        System.out.println("\n query = " + query);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Sending Query : " + query);
        System.out.println("Response Code : " + responseCode);
    }
    /**
     * @param entity can be "/users" / "/projects" / "/devices" etc
     * String query = String.format("param1=%s&param2=%s", URLEncoder.encode(param1, charset), URLEncoder.encode(param2, charset));
     */
    protected String doPost(String entity , String query, String webPage, String authStringEnc) throws IOException {
        //query = String.format("param1=%s&param2=%s", URLEncoder.encode("/projects", "UTF-8"), URLEncoder.encode("Default", "UTF-8"));
        query = String.format("project=%s", URLEncoder.encode("Default", "UTF-8"));
        System.out.println("/n" + query);
        URL url = new URL(webPage+entity);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        OutputStream output = urlConnection.getOutputStream();
        output.write(query.getBytes(StandardCharsets.UTF_8.name()));

        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

        printPost(url, httpURLConnection, query);

        InputStream stream = null;

        if (httpURLConnection.getResponseCode() >= 400) {
            stream = httpURLConnection.getErrorStream();

        } else {
            stream = httpURLConnection.getInputStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(responseBuffer.toString());
        boolean isResponseValid = httpURLConnection.getResponseCode() < 300;
        AssertJUnit.assertTrue("Did not get valid response", isResponseValid);
        return responseBuffer.toString();

    }
}