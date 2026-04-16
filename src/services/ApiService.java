package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.lang.StringBuilder;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiService implements ApiServiceInterface {


    private final StringBuilder builder;

    private final String apiBaseRoute;
    private final String token;

    private final HttpClient client;

    public ApiService() {

        builder = new StringBuilder();
        apiBaseRoute = "https://api.waqi.info";
        //reminder: remove this hardcoded token
        token = "1af64f336714096d0da632ce95708b3a65bfd5db";
        client = HttpClient.newHttpClient();
    }

    //this fetch api data using the query "here" which means current location
    public String getApiData() throws IOException, InterruptedException {

        builder.append(this.apiBaseRoute);
        builder.append("/feed/here/");
        builder.append("?token=");
        builder.append(this.token);

        String url = builder.toString();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = this.client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    //this fetch api data using the city name
    public String getApiDataWithCityName(String city) throws IOException, InterruptedException {

        builder.append(this.apiBaseRoute);
        builder.append("/feed/");
        builder.append(city);
        builder.append("/");
        builder.append("?token=");
        builder.append(this.token);

        String url = builder.toString();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = this.client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    //this extract city name from given string using regex
    public String extractCityName(String json) {
        return json.split("\"city\":\\{")[1]
                .split("}")[0]
                .split("\"name\":\"")[1]
                .split("\"")[0];
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

    //this extract the PM25 data from pm25 block of json body
    //by splitting the string and using regex
    //and this return the data in a list with the Pm25Data as generics
    public List<Pm25Data> extractPm25Forecast(String json) {

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
}
