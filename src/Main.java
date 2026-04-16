import displays.BarChartDisplay;
import displays.BarChartVerticalDisplay;
import displays.MenuDisplay;
import services.ApiService;

import java.awt.*;
import java.util.List;

void main() {

    String userInput;
    String json = null;

    ApiService api = new ApiService();
    Scanner scanner = new Scanner(System.in);

    BarChartDisplay chart = new BarChartDisplay();
    BarChartVerticalDisplay lineChart = new BarChartVerticalDisplay();

    MenuDisplay menu = new MenuDisplay();
    menu.displayMenu();

    System.out.println("┌" + "─".repeat(80) + "┐");

    do {
        System.out.println("│"  + "  [Choose an operation < 1 | 2 | 3 | 0 >]"
                + " ".repeat(80 - 41 ) + "│");
        System.out.print("│" + " ".repeat(4) + " > ");
        userInput = scanner.nextLine();
        try {
            switch (userInput){
                case "1":
                    json = api.getApiData();
                    userInput = "0";
                    break;

                case "2":
                    System.out.println("│"  + "  [Enter city name]"
                            + " ".repeat(80 - 19 ) + "│");
                    System.out.print("│" + " ".repeat(4) + " > ");
                    String city = scanner.nextLine();
                    json = api.getApiDataWithCityName(city);
                    userInput = "0";
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    } while (!userInput.equals("0"));

    System.out.println("└" + "─".repeat(80) + "┘");

    try {
        assert json != null;

        String cityName = api.extractCityName(json);
        List<ApiService.Pm25Data> jsonBodyPM25 = api.extractPm25Forecast(json);

        System.out.println();
        System.out.println("┌" + "─".repeat(80) + "┐");
        System.out.println("│   " + cityName + " ".repeat(80 - 3 - cityName.length()) + "│" );
        System.out.println("├" + "─".repeat(80) + "┤");

        chart.displayChart(jsonBodyPM25);
        lineChart.displayChart(jsonBodyPM25);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}