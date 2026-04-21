import displays.BarChartDisplay;
import displays.BarChartVerticalDisplay;
import displays.MenuDisplay;
import exceptions.ApiException;
import java.util.List;
import java.util.Scanner;
import services.ApiDataExtractionService;
import services.ApiService;
import services.IOService;
import services.IOServiceInterface;

void main() {

    String userInput;
    String json = null;

    ApiService api = new ApiService();
    IOService iowriter = new IOService();
    Scanner scanner = new Scanner(System.in);

    BarChartDisplay chart = new BarChartDisplay();
    BarChartVerticalDisplay lineChart = new BarChartVerticalDisplay();

    MenuDisplay menu = new MenuDisplay();

 while (true) {
        menu.displayMenu();
        System.out.println("┌" + "─".repeat(80) + "┐");
        System.out.println("│" + "  [Choose an operation < 1 | 2 | 3 | 4 | 0 >]"
                + " ".repeat(80 - 45) + "│");
        System.out.print("│" + " ".repeat(4) + " > ");
        userInput = scanner.nextLine();

        // Validate main menu input
        if (!(userInput.equals("0") || userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4"))) {
            System.out.println("│ Invalid input. Please enter 0, 1, 2, 3, or 4. " + " ".repeat(80 - 44) + "│");
            continue;
        }

        // Early exit for '0'
        if (userInput.equals("0")) {
            break;
        }

        boolean showResult = false;
        switch (userInput) {
                case "1" -> {
                    try {
                        json = api.getApiData();
                        showResult = true;
                    } catch (ApiException e) {
                        System.out.println("│ Sorry, could not retrieve data for your location. It may not be available. " + " ".repeat(80 - 67) + "│");
                    }
            }
                case "2" -> {
                    System.out.println("│" + "  [Enter city name]"
                            + " ".repeat(80 - 19) + "│");
                    System.out.print("│" + " ".repeat(4) + " > ");
                    String city = scanner.nextLine();
                    try {
                        json = api.getApiData(city);
                        showResult = true;
                    } catch (ApiException e) {
                        System.out.println("│ Sorry, that city is not in the database or data is unavailable. " + " ".repeat(80 - 61) + "│");
                    }
            }
                case "3" -> {
                    menu.displayMenuCSV();
                    menu.displayUserInput();
                    String userChoiceCsv = scanner.nextLine();
                    // Validate submenu input
                    while (!(userChoiceCsv.equals("1") || userChoiceCsv.equals("2"))) {
                        System.out.println("│ Invalid input. Please enter 1 or 2. " + " ".repeat(80 - 36) + "│");
                        menu.displayMenuCSV();
                        menu.displayUserInput();
                        userChoiceCsv = scanner.nextLine();
                    }
                    if (userChoiceCsv.equals("1")) {
                        try {
                            json = api.getApiData();
                            showResult = true;
                        } catch (Exception e) {
                            System.out.println("│ Sorry, could not retrieve data for your location. It may not be available. " + " ".repeat(80 - 67) + "│");
                        }
                    } else {
                        System.out.println("│" + "  [Enter city name]"
                                + " ".repeat(80 - 19) + "│");
                        System.out.print("│" + " ".repeat(4) + " > ");
                        String userCityCsv = scanner.nextLine();
                        try {
                            json = api.getApiData(userCityCsv);
                            showResult = true;
                        } catch (Exception e) {
                            System.out.println("│ Sorry, that city is not in the database or data is unavailable. " + " ".repeat(80 - 61) + "│");
                        }
                    }
                    if (showResult) {
                        try {
                            String cityNameForIo = ApiDataExtractionService.extractCityName(json);
                            List<ApiDataExtractionService.Pm25Data> forecast = ApiDataExtractionService.extractPm25Forecast(json);
                            iowriter.writeCsv("src/data.csv", cityNameForIo, forecast);
                            System.out.println("│" + "  [Data has been saved to our system]"
                                    + " ".repeat(80 - 39) + "│");
                        } catch (Exception e) {
                            System.out.println("│ Sorry, failed to parse or save the data. " + " ".repeat(80 - 39) + "│");
                        }
                    }
            }
                case "4" -> {
                    System.out.println("└" + "─".repeat(80) + "┘");
                    try {
                        IOServiceInterface.CsvData csvdata = iowriter.readCsv("src/data.csv");
                        String cityName = csvdata.city;
                        System.out.println("┌" + "─".repeat(80) + "┐");
                        System.out.println("│   " + cityName + " ".repeat(80 - 3 - cityName.length()) + "│" );
                        System.out.println("├" + "─".repeat(80) + "┤");
                        chart.displayChart(csvdata.rows);
                        lineChart.displayChart(csvdata.rows);
                    } catch (Exception e) {
                        System.out.println("│ Sorry, failed to load or display saved data. " + " ".repeat(80 - 44) + "│");
                    }
            }
            }
        if (showResult) {
            try {
                String cityName = ApiDataExtractionService.extractCityName(json);
                List<ApiDataExtractionService.Pm25Data> jsonBodyPM25 = ApiDataExtractionService.extractPm25Forecast(json);
                System.out.println();
                System.out.println("┌" + "─".repeat(80) + "┐");
                System.out.println("│   " + cityName + " ".repeat(80 - 3 - cityName.length()) + "│");
                System.out.println("├" + "─".repeat(80) + "┤");
                chart.displayChart(jsonBodyPM25);
                lineChart.displayChart(jsonBodyPM25);
            } catch (Exception e) {
                System.out.println("│ Sorry, failed to parse or display the data. " + " ".repeat(80 - 39) + "│");
            }
        }
    }
    scanner.close();
}