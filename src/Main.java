import displays.BarChartDisplay;
import displays.BarChartVerticalDisplay;
import displays.MenuDisplay;
import java.util.List;
import java.util.Scanner;
import services.ApiService;
import services.IOService;

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
                    json = api.getApiDataWithCityName(city);
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

                        json = api.getApiDataWithCityName(userCityCsv);
                    }

                    assert json != null;
                    String cityNameForIo = api.extractCityName(json);
                    List<ApiService.Pm25Data> forecast = api.extractPm25Forecast(json);

                    iowriter.writeCsv("src/data.csv", cityNameForIo, forecast);

                    break outer;

                case "4":
                    System.out.println("│"  + "  [Enter CSV path or press Enter to use src/data.csv]"
                            + " ".repeat(80 - 45 ) + "│");
                    System.out.print("│" + " ".repeat(4) + " > ");
                    String csvPath = scanner.nextLine().trim();
                    if (csvPath.isBlank()) {
                        csvPath = "src/data.csv";
                    }

                    IOService.CsvData csvData = iowriter.readCsv(csvPath);
                    System.out.println();
                    System.out.println("┌" + "─".repeat(80) + "┐");
                    System.out.println("│   " + csvData.city + " ".repeat(80 - 3 - csvData.city.length()) + "│" );
                    System.out.println("├" + "─".repeat(80) + "┤");

                    chart.displayChart(csvData.rows);
                    lineChart.displayChart(csvData.rows);
                    return;

                default:
                    break;
            }

        } catch (Exception e) {
            //reminder: replace these exceptions with actual exceptions
            throw new RuntimeException(e);
        }

    } while (!userInput.equals("0"));

    System.out.println("└" + "─".repeat(80) + "┘");

    try {

        if (userInput.equals("3") || userInput.equals("4")){ return; }

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