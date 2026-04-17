package services;

import java.util.List;

public interface ApiDataExtractionServiceInterface {

    public static String extractStatus(String json) { return null; }

    public static String extractCityName(String json) { return null; }

    public static double[] extractCityGeo(String json) { return null; }

    public static List<ApiDataExtractionService.Pm25Data> extractPm25Forecast(String json){ return null; }
}
