import services.ApiService;

void main() {

    ApiService api = new ApiService();

    try {
        api.getApiData();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}