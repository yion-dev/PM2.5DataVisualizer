package displays;

public class MenuDisplay {
    int width = 80;

    String[] title = {
            "┏┓┳┳┓  ┏┓┏━  ┳┳┓     •    ",
            "┃┃┃ ┃  ┏┛┗┓  ┃┃┃┏┓┏┓┓╋┏┓┏┓",
            "┣┛┛ ┗  ┗━┗┛  ┛ ┗┗┛┛┗┗┗┗┛┛ ",
    };

    public void displayMenu() {

        System.out.println("┌" + "─".repeat(width) + "┐");

        for (String line : title) {
            int padding = (width - line.length()) / 2;
            int remaining = width - line.length() - padding;

            System.out.println("│" +
                    " ".repeat(padding) +
                    line +
                    " ".repeat(remaining) +
                    "│");
        }

        System.out.println("├" + "─".repeat(width) + "┤");
        System.out.println(
                "│" + " ".repeat(4) +
                "(1) Current Location" +
                " ".repeat(width - "(1) Current Location".length() - 4) + "│"
        );
        System.out.println(
                "│" + " ".repeat(4) +
                        "(2) Search City" +
                        " ".repeat(width - "(2) Search City".length() - 4) + "│"
        );
        System.out.println(
                "│" + " ".repeat(4) +
                        "(3) Read Data from CSV" +
                        " ".repeat(width - "(3) Read Data from CSV".length() - 4) + "│"
        );
        System.out.println(
                "│" + " ".repeat(4) +
                        "(0) Exit" +
                        " ".repeat(width - "(0) Exit".length() - 4) + "│"
        );

        System.out.println("└" + "─".repeat(width) + "┘");
    }

}
