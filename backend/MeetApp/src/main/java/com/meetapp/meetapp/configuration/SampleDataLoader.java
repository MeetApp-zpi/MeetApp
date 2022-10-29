package com.meetapp.meetapp.configuration;

import com.meetapp.meetapp.model.City;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.model.Voivodeship;
import com.meetapp.meetapp.repository.CityRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.repository.VoivodeshipRepository;
import lombok.val;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@ConditionalOnProperty(name = "useSampleData", havingValue = "true")
@Component
public class SampleDataLoader implements ApplicationRunner {
    private CityRepository cityRepository;
    private VoivodeshipRepository voivodeshipRepository;
    private LocationRepository locationRepository;

    public SampleDataLoader(CityRepository cityRepository, VoivodeshipRepository voivodeshipRepository,
                            LocationRepository locationRepository) {
        this.cityRepository = cityRepository;
        this.voivodeshipRepository = voivodeshipRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
    }

    private List<Location> getLocations() {
        return Arrays.asList(newLocation("Wrocław", "dolnośląskie", 51.10587, 17.04155),
                newLocation("Trzebnica", "dolnośląskie", 51.31045, 17.06343),
                newLocation("Warszawa", "mazowieckie", 52.26973, 21.01346),
                newLocation("Siedlce", "mazowieckie", 52.20544, 22.30045),
                newLocation("Białystok", "podlaskie", 53.18720, 23.14314),
                newLocation("Poznań", "wielkopolskie", 52.52602, 16.87818),
                newLocation("Kraków", "małopolskie", 50.13486, 19.87007),
                newLocation("Gdańsk", "pomorskie", 54.21722, 18.62889),
                newLocation("Gdynia", "pomorskie", 54.51966, 18.79412),
                newLocation("Szczecin", "zachodniopomorskie", 53.38185, 14.93994),
                newLocation("Bydgoszcz", "kujawsko-pomorskie", 53.22306, 17.99808),
                newLocation("Katowice", "śląskie", 50.25388, 18.45389),
                newLocation("Lublin", "lubelskie", 51.37397, 22.52138),
                newLocation("Częstochowa", "śląskie", 50.90658, 19.40467),
                newLocation("Olsztyn", "warmińsko-mazurskie", 53.79784, 20.48296),
                newLocation("Rzeszów", "podkarpackie", 50.11339, 21.99975));
    }

    private Location newLocation(String cityName, String voivodeshipName, Double latitude, Double longitude) {
        val city = cityRepository.findByName(cityName).orElse(cityRepository.save(new City(cityName)));
        val voivodeship = voivodeshipRepository.findByName(voivodeshipName)
                .orElse(voivodeshipRepository.save(new Voivodeship(voivodeshipName)));

        return new Location(city, voivodeship, latitude, longitude);
    }
}
