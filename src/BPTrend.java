import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BPTrend {
    // use this data structure to store our data entries
    ArrayList<WeatherEntry> collectedData = new ArrayList<WeatherEntry>();

    //collect information from tab spaced files and loads into array
    public void readData(String fileName){
        DateFormat format = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String line = null;
            String[] wordsArray;
            boolean skipFirstLine = true;

            while(true){
                // read lines into line
                line = buf.readLine();

                if (skipFirstLine){
                    skipFirstLine = false;
                    continue;
                }

                if(line == null){
                    break;

                }else{
                    wordsArray = line.split("\t");
                    // desired fields from input and WeatherEntry instance

                    WeatherEntry entry = new WeatherEntry();
                    entry.when = format.parse(wordsArray[0]);
                    entry.pressure = Float.valueOf(wordsArray[2]);
                    entry.humidity = Float.valueOf(wordsArray[4]);
                    collectedData.add(entry);
                }
            }

            buf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // filters the data based on a specified date range
    public String doCalc(String from, String to){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(from);
            d2 = format.parse(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = "From" + format.format(d1) + "to" + format.format(d2) + "\n";

        WeatherEntry y1 = null;
        WeatherEntry y2 = null;
        int idx = 0;
        int x1=0;
        int x2=0;

        for(WeatherEntry x: collectedData){
            if((y1==null)&& x.when.compareTo(d1)>=0){
                y1 = x;
                x1 = idx;
            }
            if(x.when.compareTo(d2)>=0){
                y2 = x;
                x2 = idx;
                break;
            }
            idx+=1;
        }
        // formula, slope variable
        float slope = (y2.pressure - y1.pressure) / (x2-x1);
        result = result + "The barometric pressure slope is: "+ String.format("%.6f",slope)+"\nthe forcast is: ";

        // Trend analysis
        if(slope < 0) result= result + "inclement weather is closing in.\n";
        if(slope == 0) result = result + "current conditions likely persists.\n";
        if(slope > 0) result = result + "conditions are improving.\n";

        return result;

    }

    // manages the loading and performing, and output
    public static void main(String[] args) {
        BPTrend calcTrend = new BPTrend();

        System.out.println("Reading data---");
        calcTrend.readData("data\\Environmental_Data_Deep_Moor_2012.txt");
        calcTrend.readData("data\\Environmental_Data_Deep_Moor_2013.txt");
        calcTrend.readData("data\\Environmental_Data_Deep_Moor_2014.txt");
        calcTrend.readData("data\\Environmental_Data_Deep_Moor_2015.txt");
        System.out.println("Done reading data!");

        System.out.println("Total number of weather data entries: " + calcTrend.collectedData.size());

        String from = "";
        String to = "";

        System.out.println("Test case #1: ");
        from = "2012/01/01 00:30:00";
        to = "2012/01/01 04:30:00";

        System.out.println( calcTrend.doCalc(from,to));

        System.out.println("Test case #2: ");
        from = "2013/04/01 00:30:00";
        to = "2013/04/05 04:30:00";
        System.out.println( calcTrend.doCalc(from,to));

    }
}


