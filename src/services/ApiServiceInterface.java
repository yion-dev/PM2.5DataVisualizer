package services;

import exceptions.ApiException;

public interface ApiServiceInterface {

    //reminder: add more function interface to align with the service code
    public String getApiData() throws ApiException;
    String getApiData(String city) throws  ApiException;

}