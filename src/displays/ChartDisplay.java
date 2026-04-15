package displays;

public abstract class ChartDisplay implements ChartsInterface {

    public String colorFor(int value) {
        if (value > 150) return "\033[31m";
        else if (value > 100) return "\033[33m";
        else return "\033[32m";
    }

    public void printLegend() {
        System.out.println("\033[0m  " +
                "\033[32mGood\033[0m: ≤100   " +
                "\033[33mModerate\033[0m: 101-150   " +
                "\033[31mUnhealthy\033[0m: >150");
    }
}
