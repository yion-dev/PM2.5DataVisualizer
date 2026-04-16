package displays;

import services.ApiService;

import java.util.List;

public class BarChartDisplay extends ChartDisplay {

    @Override
    public void displayChart(List<ApiService.Pm25Data> data) {

        System.out.println("│" + dataLabel +  padRightAnsi(dataLabel, width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");

        for (ApiService.Pm25Data item : data) {

            String date = item.day;

            int avg = item.avg;
            int max = item.max;
            int min = item.min;

            String color = colorFor(avg);
            String label = labelFor(avg);
            int barLen = Math.min(avg / 6, 50);

//            System.out.printf("│" + "  %s │ %s%s\033[0m avg:%-3d max:%-3d min:%-3d (%s)\n",
//                    date,
//                    color,
//                    "█".repeat(barLen),
//                    avg, max, min,
//                    label
//            );

            String content = String.format(
                    "  %s │ %s%s\033[0m avg:%-3d max:%-3d min:%-3d (%s)",
                    date,
                    color,
                    "█".repeat(barLen),
                    avg, max, min,
                    label
            );

            int visibleLength = stripAnsi(content).length();
            int padding = Math.max(0, 80 - visibleLength);

            System.out.println("│" + content + " ".repeat(padding) + "│");
        }

        System.out.println("├" + "─".repeat(width) + "┤");
    }

    public String labelFor(int value) {
        if (value > 150) return "Unhealthy";
        else if (value > 100) return "Moderate";
        else return "Good";
    }
}
