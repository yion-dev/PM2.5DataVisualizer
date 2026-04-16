package displays;

public abstract class ChartDisplay implements ChartsInterface {

    //reminder: force the children classes to override some of the functions for the sake of polymorphism
    //reminder: also put some abstract functions by refactoring children's code
    protected int width = 80;
    protected String dataLabel = "\033[0m  \033[32mGood\033[0m: ≤100  \033[33mModerate\033[0m: 101-150   \033[31mUnhealthy\033[0m: >150";

    public static String stripAnsi(String s) {
        return s.replaceAll("\033\\[[;\\d]*m", "");
    }

    public static String padRightAnsi(String text, int width) {
        int visibleLength = stripAnsi(text).length();
        int padding = Math.max(0, width - visibleLength);
        return " ".repeat(padding);
    }

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
