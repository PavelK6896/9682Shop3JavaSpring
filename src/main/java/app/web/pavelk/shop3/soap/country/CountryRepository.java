package app.web.pavelk.shop3.soap.country;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ws.countries.Country;
import ws.countries.Currency;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CountryRepository {
    private   Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country spain = new Country();

        spain.setName("Spain");
        spain.setCapital("Madrid");
        spain.setCurrency(Currency.EUR);
        spain.setPopulation(46704314);

        countries.put(spain.getName(), spain);

        Country uk = new Country();
        uk.setName("United Kingdom");
        uk.setCapital("London");
        uk.setCurrency(Currency.GBP);
        uk.setPopulation(63705000);
        countries.put(uk.getName(), uk);
    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }


    public List<Country> findAll() {
        return new ArrayList<>(countries.values());
    }

}
