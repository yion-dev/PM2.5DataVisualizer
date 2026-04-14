package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.lang.StringBuilder;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiService implements ApiServiceInterface {


    private StringBuilder builder = null;

    private String apiBaseRoute;
    private final String token;

    private HttpClient client;
    private HttpRequest request;


    public ApiService() {

        builder = new StringBuilder();
        apiBaseRoute = "https://api.waqi.info/feed";
        token = "1af64f336714096d0da632ce95708b3a65bfd5db";

        client = HttpClient.newHttpClient();
    }

    public void getApiData() throws IOException, InterruptedException {

        builder.append(this.apiBaseRoute);
        builder.append("/shanghai/");
        builder.append("?token=");
        builder.append(this.token);

        String url = builder.toString();

        this.request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = this.client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        String json = response.body();

        System.out.println('\n' + "City: " + extractCityName(json) + '\n');

        double[] latlng = extractCityGeo(json);
        System.out.println("Lat: " + latlng[0] + '\n' + "Lang: " + latlng[1] + '\n');

        List<Pm25Data> pm25 = extractPm25Forecast(json);
        for (Pm25Data pm25Data : pm25) {
            System.out.println("Average: " + pm25Data.avg);
            System.out.println("Max: " + pm25Data.max);
            System.out.println("Min: " + pm25Data.min);
            System.out.println("Date: " + pm25Data.day);
            System.out.println();
        }
    }

    @Override
    public String extractCityName(String json) {
        return json.split("\"city\":\\{")[1]
                .split("}")[0]
                .split("\"name\":\"")[1]
                .split("\"")[0];
    }

    @Override
    public double[] extractCityGeo(String json) {
        String geoPart = json.split("\"geo\":\\[")[1].split("]")[0];
        String[] parts = geoPart.split(",");

        double lat = Double.parseDouble(parts[0]);
        double lon = Double.parseDouble(parts[1]);

        return new double[]{lat, lon};
    }

    public static class Pm25Data {
        public int avg;
        public int min;
        public int max;
        public String day;
    }

    @Override
    public List<Pm25Data>   extractPm25Forecast(String json) {
        List<Pm25Data> list = new ArrayList<>();

        String pm25Block = json.split("\"pm25\":\\[")[1].split("]")[0];
        String[] items = pm25Block.split("\\},\\{");

        for (String item : items) {
            Pm25Data data = new Pm25Data();

            data.avg = Integer.parseInt(item.split("\"avg\":")[1].split(",")[0]);
            data.min = Integer.parseInt(item.split("\"min\":")[1].split("}")[0]);
            data.max = Integer.parseInt(item.split("\"max\":")[1].split(",")[0]);
            data.day = item.split("\"day\":\"")[1].split("\"")[0];

            list.add(data);
        }

        return list;
    }


}
