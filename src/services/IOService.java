package services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IOService {

    public void writeCsv(String path, String city, List<ApiDataExtractionService.Pm25Data> data) throws IOException {
        FileWriter writer = new FileWriter(path);

        //City name
        writer.write(city + '\n');

        // header
        writer.write("date,avg,min,max\n");

        for (ApiDataExtractionService.Pm25Data d : data) {
            writer.write(d.day + "," + d.avg + "," + d.min + "," + d.max + "\n");
        }

        writer.close();
    }
}
