package services;

import java.io.IOException;
import java.util.List;

public interface IOServiceInterface {
    void writeCsv(String path, String city, List<ApiService.Pm25Data> data) throws IOException;
    CsvData readCsv(String path) throws IOException;

    class CsvData {
        public String city;
        public List<ApiService.Pm25Data> rows;
    }
}
