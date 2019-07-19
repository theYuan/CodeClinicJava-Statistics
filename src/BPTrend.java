import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BPTrend {

    ArrayList<WeatherEntry> collectedData = new ArrayList<WeatherEntry>();

    //collect information from tab tab spaced files and loads into array
    public void readData(String fileName){
        DateFormat format = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String line = null;
            String[] wordsArray;
            boolean skipFirstLine = true;

            while(true){
                line = buf.readLine();
                if (skipFirstLine){
                    skipFirstLine = false;
                    continue;
                }

                if(line == null){
                    break;
                }else{
                    wordsArray = line.split("\t");
                    wordsArray[]


                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // filters the data based on a specified date range
    public String doCalc(String from, String to){

    }

    // manages the loading and performing, and output
    public static void main(String[] args) {

        System.out.println("Hello World!");
    }
}
