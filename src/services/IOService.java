package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOService implements IOServiceInterface {

    @Override
    public void writeCsv(String path, String city, List<ApiDataExtractionService.Pm25Data> data) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("CSV path cannot be null or blank");
        }

        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Failed to create directories for CSV output: " + parent.getAbsolutePath());
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(escapeCsv(city));
            writer.write(System.lineSeparator());
            writer.write("date,avg,min,max");
            writer.write(System.lineSeparator());

            if (data != null) {
                for (ApiDataExtractionService.Pm25Data row : data) {
                    writer.write(formatRow(row));
                }
            }
        }
    }

    @Override
    public CsvData readCsv(String path) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("CSV path cannot be null or blank");
        }

        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("CSV file not found: " + path);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String city = reader.readLine();
            if (city == null) {
                throw new IOException("CSV file is empty: " + path);
            }

            String header = reader.readLine();
            if (header == null || !header.trim().equalsIgnoreCase("date,avg,min,max")) {
                throw new IOException("CSV file header is missing or invalid: " + path);
            }

            List<ApiDataExtractionService.Pm25Data> rows = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                List<String> values = parseCsvLine(line);
                if (values.size() < 4) {
                    continue;
                }

                ApiDataExtractionService.Pm25Data row = new ApiDataExtractionService.Pm25Data();
                row.day = values.get(0);
                row.avg = parseInt(values.get(1));
                row.min = parseInt(values.get(2));
                row.max = parseInt(values.get(3));
                rows.add(row);
            }

            CsvData result = new CsvData();
            result.city = city;
            result.rows = rows;
            return result;
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private List<String> parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        values.add(current.toString());
        return values;
    }

    private String formatRow(ApiDataExtractionService.Pm25Data row) {
        if (row == null) {
            return "";
        }
        return String.join(",",
                escapeCsv(row.day),
                String.valueOf(row.avg),
                String.valueOf(row.min),
                String.valueOf(row.max))
                + System.lineSeparator();
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n") || escaped.contains("\r")) {
            return '"' + escaped + '"';
        }
        return escaped;
    }
}
