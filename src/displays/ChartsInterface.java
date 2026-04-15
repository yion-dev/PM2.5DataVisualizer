package displays;

import services.ApiService;
import java.util.List;

public interface ChartsInterface {

    public String colorFor(int value);
    public void printLegend();

    public void displayChart(List<ApiService.Pm25Data> data);

}