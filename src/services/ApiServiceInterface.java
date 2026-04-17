package services;

import exceptions.ApiException;

import java.util.List;

public interface ApiServiceInterface {

    //reminder: add more function interface to align with the service code
    String getApiData() throws ApiException;
    String getApiData(String city) throws  ApiException;

}