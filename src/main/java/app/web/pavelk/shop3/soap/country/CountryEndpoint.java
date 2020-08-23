package app.web.pavelk.shop3.soap.country;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ws.countries.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "ws/countries";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        System.out.println("CountryEndpoint");
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryAll")
    @ResponsePayload
    public GetCountryAllyResponse getCountryAll(@RequestPayload GetCountryAll request) {
        GetCountryAllyResponse getCountryAllyResponse = new GetCountryAllyResponse();

        List<Country> country = getCountryAllyResponse.getCountry();
        countryRepository.findAll().forEach(f -> {
            country.add(f);
        });

        return getCountryAllyResponse;
    }
}
