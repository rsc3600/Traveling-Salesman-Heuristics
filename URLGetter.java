/*
 *
 *This class will use the HTTP protocol to get a webpage
 *
 */

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.*;
import java.io.*;
import java.util.*;

public class URLGetter {
    private URL url;
    private HttpURLConnection connection;
    
    /*Creates a URL Object from the given string.
     *Opens the connection that will be used later.
     *@param website URL to get info from
     */
    
    public URLGetter(String website) {
        try {
            url = new URL(website);
            
            //this shit is wrong diff than regular Objects -> connection = new HttpUrlConnection(url);
            //we do it this other way bc: URL connection and HTTP connections are not always the same thing
            URLConnection urlConnection = this.url.openConnection();
            connection = (HttpURLConnection) urlConnection;
            
            
        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void printStatusCode() {
        try{
            int code = connection.getResponseCode();
            String message = connection.getResponseMessage();
            
            System.out.println(code + " : " + message);
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    public ArrayList<String> getContents() {
        ArrayList<String> contents = new ArrayList<String>();
        try {
            Scanner in = new Scanner(connection.getInputStream());
            while(in.hasNextLine()) {
                String line = in.nextLine();
                contents.add(line);
            }
        } catch(IOException e) {
            System.out.println(e);
        }
        
        return contents;
    }
}