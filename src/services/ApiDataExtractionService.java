package services;

import java.util.ArrayList;
import java.util.List;

public class ApiDataExtractionService implements ApiDataExtractionServiceInterface {

    public static String extractStatus(String json) {
        String[] parts = json.split("\"status\"\\s*:\\s*\"");
        return parts[1].split("\"")[0];
    }

    //this extract city name from given string using regex
    public static String extractCityName(String json) {
        return json.split("\"city\":\\{")[1]
                .split("}")[0]
                .split("\"name\":\"")[1]
                .split("\"")[0];
    }

    //this extract the PM25 data from pm25 block of JSON body
    //by splitting the string and using regex
    //and this return the data in a list with the Pm25Data as generics
    public static List<Pm25Data> extractPm25Forecast(String json) {

        List<Pm25Data> list = new ArrayList<>();

        int dailyStart = json.indexOf("\"daily\":{");
        if (dailyStart == -1) return list;

        int pm25Start = json.indexOf("\"pm25\":[", dailyStart);
        if (pm25Start == -1) return list;

        int pm25End = json.indexOf("]", pm25Start);
        if (pm25End == -1) return list;

        String pm25Block = json.substring(pm25Start + 8, pm25End);

        String[] items = pm25Block.split("\\},\\s*\\{");

        for (String item : items) {
            Pm25Data data = new Pm25Data();

            data.avg = Integer.parseInt(item.split("\"avg\":")[1].split(",")[0]);
            data.min = Integer.parseInt(item.split("\"min\":")[1].split("}")[0]);
            data.max = Integer.parseInt(item.split("\"max\":")[1].split(",")[0]);
            data.day = item.split("\"day\":\"")[1].split("\"")[0];

            list.add(data);
        }

        if(list.size() > 7) {
            return list.subList(0, 7);
        }

        return list;

    }

    //this function extract the latitude ana longitude from the api data
    public double[] extractCityGeo(String json) {
        String geoPart = json.split("\"geo\":\\[")[1].split("]")[0];
        String[] parts = geoPart.split(",");

        double lat = Double.parseDouble(parts[0]);
        double lon = Double.parseDouble(parts[1]);

        return new double[]{lat, lon};
    }

    //reminder : move this class to different file (bec this class has to be used across the codebase)
    //this is going to be used across the app
    //this is the outline of PM25data refer to sample.md for more information
    public static class Pm25Data {
        public int avg;
        public int min;
        public int max;
        public String day;
    }


}
