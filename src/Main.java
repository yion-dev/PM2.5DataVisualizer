import displays.BarChartDisplay;
import displays.BarChartVerticalDisplay;
import services.ApiService;

void main() {

    String jsonCity;

    List<ApiService.Pm25Data> jsonCityData;

    ApiService api = new ApiService();
    BarChartDisplay chart = new BarChartDisplay();
    BarChartVerticalDisplay lineChart = new BarChartVerticalDisplay();

    try {
        jsonCity = api.getApiDataWithCityName("peru");

        jsonCityData = api.extractPm25Forecast(jsonCity);

        System.out.println("  " + "─".repeat(80));
        System.out.println();
        System.out.println("  " + api.extractCityName(jsonCity));
        System.out.println();
        System.out.println("  " + "─".repeat(80));

        chart.displayChart(jsonCityData);
        lineChart.displayChart(jsonCityData);

    } catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException(e);
    }
}