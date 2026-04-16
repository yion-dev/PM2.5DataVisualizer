import displays.BarChartDisplay;
import displays.BarChartVerticalDisplay;
import displays.MenuDisplay;

import exceptions.ApiException;
import exceptions.ApiResponseException;

import services.ApiDataExtractionService;
import services.ApiService;
import services.IOService;
import services.IOServiceInterface;

import java.util.List;

void main() {

    String userInput;
    String json = null;

    ApiService api = new ApiService();
    IOService iowriter = new IOService();
    Scanner scanner = new Scanner(System.in);

    BarChartDisplay chart = new BarChartDisplay();
    BarChartVerticalDisplay lineChart = new BarChartVerticalDisplay();

    MenuDisplay menu = new MenuDisplay();
    menu.displayMenu();

    System.out.println("┌" + "─".repeat(80) + "┐");

    outer:
    do {
        System.out.println("│"  + "  [Choose an operation < 1 | 2 | 3 | 4 | 0 >]"
                + " ".repeat(80 - 45 ) + "│");
        System.out.print("│" + " ".repeat(4) + " > ");
        userInput = scanner.nextLine();

        try {

            //reminder: put the actual functions and add a new case for csv output
            switch (userInput){
                case "1":
                    json = api.getApiData();
                    break outer;

                case "2":
                    System.out.println("│"  + "  [Enter city name]"
                            + " ".repeat(80 - 19 ) + "│");
                    System.out.print("│" + " ".repeat(4) + " > ");
                    String city = scanner.nextLine();
                    json = api.getApiData(city);
                    break outer;

                case "3":

                    menu.displayMenuCSV();
                    menu.displayUserInput();
                    String userChoiceCsv = scanner.nextLine();

                    if(userChoiceCsv.equals("1")){
                        json = api.getApiData();

                    } else if(userChoiceCsv.equals("2")) {

                        System.out.println("│"  + "  [Enter city name]"
                                + " ".repeat(80 - 19 ) + "│");
                        System.out.print("│" + " ".repeat(4) + " > ");
                        String userCityCsv = scanner.nextLine();

                        json = api.getApiData(userCityCsv);
                    }

                    assert json != null;
                    String cityNameForIo = ApiDataExtractionService.extractCityName(json);
                    List<ApiDataExtractionService.Pm25Data> forecast = ApiDataExtractionService.extractPm25Forecast(json);

                    iowriter.writeCsv("src/data.csv", cityNameForIo, forecast);

                    break outer;

                case "4":

                    System.out.println("└" + "─".repeat(80) + "┘");

                    IOServiceInterface.CsvData csvdata = iowriter.readCsv("src/data.csv");
                    String cityName = csvdata.city;

                    System.out.println("┌" + "─".repeat(80) + "┐");
                    System.out.println("│   " + cityName + " ".repeat(80 - 3 - cityName.length()) + "│" );
                    System.out.println("├" + "─".repeat(80) + "┤");

                    chart.displayChart(csvdata.rows);
                    lineChart.displayChart(csvdata.rows);

                    break outer;

                default:
                    break;
            }

        } catch (ApiResponseException e) {
            System.out.println("API Response Error: " + e);
        } catch (ApiException e) {
            System.out.println("API Error: " + e);
        } catch (IOException e) {
            System.out.println("IO Error: " + e);
        }

    } while (!userInput.equals("0"));

    System.out.println("└" + "─".repeat(80) + "┘");

    try {

        if (userInput.equals("3") || userInput.equals("4")){ return; }

        assert json != null;

        String cityName = ApiDataExtractionService.extractCityName(json);
        List<ApiDataExtractionService.Pm25Data> jsonBodyPM25 = ApiDataExtractionService.extractPm25Forecast(json);

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