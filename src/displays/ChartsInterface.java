package displays;

import services.ApiDataExtractionService;

import java.util.List;

public interface ChartsInterface {

    public String colorFor(int value);
    public void printLegend();

    public void displayChart(List<ApiDataExtractionService.Pm25Data> data);

}

