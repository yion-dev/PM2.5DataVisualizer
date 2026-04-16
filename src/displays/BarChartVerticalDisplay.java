package displays;

import services.ApiService;

import java.util.List;

public class BarChartVerticalDisplay extends ChartDisplay {

    private static final int CHART_HEIGHT = 12;

    @Override
    public void displayChart(List<ApiService.Pm25Data> data) {

        System.out.println("│" + "\033[36m\033[1m  PM2.5 Line Chart (avg)\033[0m"
                + padRightAnsi("\033[36m\033[1m  PM2.5 Line Chart (avg)\033[0m", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");

        // this transforms the data into int steam and then
        // extracts each item(d)'s max field and then finds it the largest
        // value but if the largest doesn't exist then its default is 1
        int max = data.stream().mapToInt(d -> d.max).max().orElse(1);

        // Plot from top to bottom
        for (int row = CHART_HEIGHT; row >= 0; row--) {

            //this map the chart height to actual data values
            //this is to find the data labels
            int threshold = Math.round((float) row / CHART_HEIGHT * max);
            System.out.printf("│ %3d │", threshold);

            for (ApiService.Pm25Data item : data) {

                String color = colorFor(item.avg);

                //this map the item's average to chart height
                int scaled = Math.round((float) item.avg * CHART_HEIGHT / max);

                boolean filled = scaled >= row;
                System.out.print(color + (filled ? "    ▒   " : "        ") + "\033[0m");
            }
            System.out.println(padRightAnsi("\033[0m" + "│",
                    width - 12 - (data.size() * 7)) + "│");
        }

        // X-axis
        System.out.print("│     └");
        System.out.println("────────".repeat(data.size()) + " ".repeat(width - 13 - 7 * data.size())  + "│");

        // Date labels
        System.out.print("│      ");
        for (ApiService.Pm25Data item : data) {

            //this makes the string to show only the month and day
            System.out.printf(" %-5s ", item.day.substring(5));
        }
        System.out.println(" ".repeat(width - data.size() * 8)  + " │");

        System.out.println("└" + "─".repeat(width) + "┘");
    }
}
