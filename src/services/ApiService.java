package services;

import exceptions.ApiException;
import exceptions.ApiResponseException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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
    public String getApiData() throws ApiException {

        HttpResponse<String> response = null;

        try {
            builder.setLength(0); // Reset builder before use
            builder.append(this.apiBaseRoute);
            builder.append("/feed/here/");
            builder.append("?token=");
            builder.append(this.token);

            String url = builder.toString();
            //calling Http request here
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            response = this.client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String status = ApiDataExtractionService.extractStatus(response.body());
            if(status.equals("error")){ throw new ApiResponseException("Response Error"); }

        } catch (IOException | InterruptedException e) {
            throw new ApiException("Something Went Wrong during API call");
        }

        return response.body();
    }

    //this fetch api data using the city name

    public String getApiData(String city) throws ApiException {
        HttpResponse<String> response = null;
        try {
            builder.setLength(0); // Reset builder before use
            builder.append(this.apiBaseRoute);
            builder.append("/feed/");
            // Encode city name for URL safety
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            builder.append(encodedCity);
            builder.append("/");
            builder.append("?token=");
            builder.append(this.token);

            String url = builder.toString();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            response = this.client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String status = ApiDataExtractionService.extractStatus(response.body());
            if(status.equals("error")){
                // debug error prompt
                // System.out.println("API returned error response: " + response.body());
                throw new ApiResponseException("Response Error");
            }

        } catch (IOException | InterruptedException e) {
            throw new ApiException("Something went wrong during api call with city name", e);
        }
        return response.body();
    }

}
