package displays;

import services.ApiService;

import java.util.List;

public class BarChartDisplay extends ChartDisplay {

    @Override
    public void displayChart(List<ApiService.Pm25Data> data) {

        System.out.println("\033[0m  \033[32mGood\033[0m: ≤100   \033[33mModerate\033[0m: 101-150   \033[31mUnhealthy\033[0m: >150");
        System.out.println("  " + "─".repeat(80));

        for (ApiService.Pm25Data item : data) {

            String date = item.day;

            int avg = item.avg;
            int max = item.max;
            int min = item.min;

            String color = colorFor(avg);
            String label = labelFor(avg);
            int barLen = Math.min(avg / 6, 50);

            System.out.printf("  %s │ %s%s\033[0m avg:%-3d max:%-3d min:%-3d (%s)\n",
                    date,
                    color,
                    "█".repeat(barLen),
                    avg, max, min,
                    label
            );
        }

        System.out.println("  " + "─".repeat(80));
    }

    public String labelFor(int value) {
        if (value > 150) return "Unhealthy";
        else if (value > 100) return "Moderate";
        else return "Good";
    }
}
