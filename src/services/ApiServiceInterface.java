package services;

import java.util.List;
import java.io.IOException;

public interface ApiServiceInterface {

    void getApiData() throws IOException, InterruptedException;

    String extractCityName(String json);
    double[] extractCityGeo(String json);

    List<ApiService.Pm25Data> extractPm25Forecast(String json);
}