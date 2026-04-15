import displays.BarChartDisplay;
import services.ApiService;

void main() {

    String jsonCity;

    List<ApiService.Pm25Data> jsonCityData;

    ApiService api = new ApiService();
    BarChartDisplay chart = new BarChartDisplay();

    try {
        jsonCity = api.getApiDataWithCityName("yangon");

        jsonCityData = api.extractPm25Forecast(jsonCity);

        System.out.println("  " + "─".repeat(80));
        System.out.println();
        System.out.println(" ".repeat(35) + api.extractCityName(jsonCity));
        System.out.println();
        System.out.println("  " + "─".repeat(80));

        chart.displayChart(jsonCityData);

    } catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException(e);
    }
}