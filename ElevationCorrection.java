import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


//import org.json.JSONObject;



public class ElevationCorrection {

    
    //URL Request Method
    
    public static String excutePost(String targetURL, String urlParameters)
    {
      URL url;
      HttpURLConnection connection = null;  
      try {
        //Create connection
        url = new URL(targetURL);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", 
             "application/x-www-form-urlencoded");
              
        connection.setRequestProperty("Content-Length", "" + 
                 Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");  
              
        connection.setUseCaches (false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
        wr.writeBytes (urlParameters);
        wr.flush ();
        wr.close ();

        //Get Response    
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer(); 
        while((line = rd.readLine()) != null) {
          response.append(line);
          response.append('\r');
        }
        rd.close();
        return response.toString();

      } catch (Exception e) {

        e.printStackTrace();
        return null;

      } finally {

        if(connection != null) {
          connection.disconnect(); 
        }
      }
    }

    public static String constructURL(ArrayList<String> lat, ArrayList<String> lng, int index){

        String API_KEY = "AIzaSyD7EDp31VN01zkyMOSLrvkb5ehfobXOTxI"; 
        String lnglat = ""; 
        int maxCoords = 90; //number of coords in string

        for (int i = index; i < maxCoords; i++){
            lnglat = lnglat+lat.get(i)+","+lng.get(i)+"|";
        }

        lnglat = lnglat.substring(0,lnglat.length()-1); //removes last |
        
        
        String url = "https://maps.googleapis.com/maps/api/elevation/json?locations="+lnglat+"&key="+API_KEY;
        return url; 

    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        String filename = "6.6.14ModifiedFullPoints.csv";
        
        BufferedReader CSVFile = new BufferedReader(new FileReader(filename));

        String dataRow = CSVFile.readLine(); // Read first line.
        // The while checks to see if the data is null. If
        // it is, we've hit the end of the file. If not,
        // process the data.

        int i = 0;
        
        ArrayList<String> arrLat = new ArrayList<String>();
        ArrayList<String> arrLong = new ArrayList<String>();
        
        while (dataRow != null) {
            if (i == 7) {
                i = 0;
            }
            String[] dataArray = dataRow.split(",");
            for (String item : dataArray) {
                if (item.equals("")) {
                    item = "NA";
                }
                // System.out.print(item + "\t");

                if (i == 0) {
                    arrLat.add(item);
                } else if (i == 1) {
                    arrLong.add(item);
                }
                i++;
            }
            //System.out.println(); // Print the data line.
            dataRow = CSVFile.readLine(); // Read next line of data.
        }
        
        // Close the file once all data has been read.
        CSVFile.close();
    
        // End the printout with a blank line.
        //System.out.println();


       
        
       
       
        
        //URL Request 
        
        String url = constructURL(arrLat, arrLong, 0); 

        System.out.println(url);
        System.out.println("The length of the url is: " +url.length());

        FileWriter saveFile = new FileWriter("TestSave.txt");
        
        /*
        
        for (int k=0; k <= movAvg_5_Spd.size()-1; k++){
            saveFile.write(String.valueOf(k)+"," +String.valueOf(movAvg_5_Spd.get(k)));
            saveFile.write("\n");
        }

        */

        saveFile.write(url);
        
        saveFile.flush();
        saveFile.close();



        
        
        
                
        //System.out.println(excutePost(url , urlParameters));
        
        //Parse JSON output using org.json 
        // http://theoryapp.com/parse-json-in-java/

        /*

        String urlParameters = "fName=" + URLEncoder.encode("???", "UTF-8") +"&lName=" + URLEncoder.encode("???", "UTF-8");

        String str = excutePost(url , urlParameters);
        JSONObject obj = new JSONObject(str);
        JSONObject res = obj.getJSONArray("results").getJSONObject(0);
        System.out.println(res.getDouble("elevation"));

        */
        

    }

}