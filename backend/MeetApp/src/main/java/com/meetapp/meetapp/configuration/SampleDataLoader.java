package com.meetapp.meetapp.configuration;

import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.*;
import com.meetapp.meetapp.service.ClientService;
import lombok.val;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@ConditionalOnProperty(name = "useSampleData", havingValue = "true")
@Component
public class SampleDataLoader implements ApplicationRunner {
    private final CityRepository cityRepository;
    private final VoivodeshipRepository voivodeshipRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final ClientRepository clientRepository;
    private final AnnouncementRepository announcementRepository;
    private final MeetingRepository meetingRepository;

    private final Byte[] sampleProfilePicture;

    public SampleDataLoader(CityRepository cityRepository, VoivodeshipRepository voivodeshipRepository,
                            LocationRepository locationRepository, CategoryRepository categoryRepository,
                            ClientRepository clientRepository, AnnouncementRepository announcementRepository,
                            MeetingRepository meetingRepository) {
        this.cityRepository = cityRepository;
        this.voivodeshipRepository = voivodeshipRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.clientRepository = clientRepository;
        this.announcementRepository = announcementRepository;
        this.meetingRepository = meetingRepository;

        this.sampleProfilePicture = ClientService.downloadPictureOrThrow(
                "https://d1csarkz8obe9u.cloudfront" +
                        ".net/posterpreviews/testing-logo-design-template-ce84480d61b3db9a8e1522a99875832f_screen" +
                        ".jpg?ts=1615794516");
    }

    @Override
    public void run(ApplicationArguments args) {
        locationRepository.saveAll(getLocations());
        categoryRepository.saveAll(getCategories());
        clientRepository.saveAll(getClients());
        announcementRepository.saveAll(getAnnouncements());
        meetingRepository.saveAll(getMeetings());
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

    private List<Category> getCategories() {
        return Arrays.asList(new Category("Sport"), new Category("Gry"), new Category("Informatyka"),
                new Category("Polityka"), new Category("Motoryzacja"), new Category("Muzyka"),
                new Category("Nauka"), new Category("Środowisko"), new Category("Hobby"), new Category("Dla dzieci"),
                new Category("Podróże"));
    }

    private List<Client> getClients() {
        return Arrays.asList(newClient("meetapp.zpi@gmail.com", "Jan", "Testowicz"),
                newClient("fanatyk.rolkarstwa@rolki.pl", "Rolkowy", "Świrus"),
                newClient("prawdziwy.polityk@prawdziwysejm.gov.pl", "Prawdziwy", "Polityk"),
                newClient("janusz75@buziaczek.pl", "Fanatyk", "Wędkarstwa"),
                newClient("palsie@koniu.org", "Norbert", "G"));
    }

    private List<Announcement> getAnnouncements() {
        return Arrays.asList(
                new Announcement(getClientOrThrow("meetapp.zpi@gmail.com"),
                        getLocationOrThrow("Wrocław", "dolnośląskie"), "Testowe Ogłoszenie",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce ut elit metus. Ut vel urna " +
                                "fermentum, elementum neque quis, malesuada ante. Donec vehicula dui vitae tincidunt " +
                                "venenatis. Curabitur orci."),
                new Announcement(getClientOrThrow("fanatyk.rolkarstwa@rolki.pl"),
                        getLocationOrThrow("Trzebnica", "dolnośląskie"), "Mocna ekipa szuka rolkarza",
                        "Naprawdę mocna ekipa roklarzy szuka rekruta który nie będzie bał się ekstremalnie jeździć po" +
                                " mieście."),
                new Announcement(getClientOrThrow("prawdziwy.polityk@prawdziwysejm.gov.pl"),
                        getLocationOrThrow("Białystok", "podlaskie"), "Nie będzie niczego",
                        "Jak już będę prezydentem, to będzie zupełnie inaczej. Nie będzie sejmu i senatu. Polska " +
                                "będzie od morza do morza. Żeby nie było bandyctwa, żeby nie było złodziejstwa, żeby " +
                                "nie było niczego."), new Announcement(getClientOrThrow("janusz75@buziaczek.pl"),
                        getLocationOrThrow("Bydgoszcz", "kujawsko-pomorskie"), "Szukam partnera do polowania na suma",
                        "Sum grasuje w rzece pod Bydgoszczą. Jest ogromny i już kilka razy mnie pogryzł. Cena nie gra" +
                                " roli, musimy go złapać."),
                new Announcement(getClientOrThrow("palsie@koniu.org"), getLocationOrThrow("Warszawa", "mazowieckie"),
                        "Leśnik potrzebny w aleji!",
                        "Już od 10 minut tu siedzę a dalej nie widziałem żadnego leśnika. Słychać tylko jakąś żabę. " +
                                "Mój oponent ma dużo więcej złota, a to wszystko przez różnicę dżungli. POMOCY P.S> " +
                                "Wszyscy oprócz Shyvanny ;>"));
    }

    private List<Meeting> getMeetings() {
        return Arrays.asList(new Meeting(getClientOrThrow("meetapp.zpi@gmail.com"),
                        getLocationOrThrow("Poznań", "wielkopolskie"), "Testowe Spotkanie",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas a lacus interdum, pulvinar" +
                                " ex a, luctus nulla. Orci varius natoque penatibus et magnis dis parturient montes, " +
                                "nascetur ridiculus mus. Quisque facilisis lectus ac vulputate turpis duis.",
                        Instant.parse("2023-02-25T21:37:00.000Z"), 200),
                new Meeting(getClientOrThrow("fanatyk.rolkarstwa@rolki.pl"),
                        getLocationOrThrow("Wrocław", "dolnośląskie"),
                        "Nocny przejazd przez centrum Wrocławia w styczniu.",
                        "Kochani zapraszam Was na epicki przejazd centrum Wrocławia w Sobotę 7 stycznia!!! 💪💪🚩💯 " +
                                "Zaczynamy o 18:00. Czołówki obowiązkowe ;) Zbiórka przed NFM.",
                        Instant.parse("2023-01-07T18:00:00.000Z"), 35),
                new Meeting(getClientOrThrow("prawdziwy.polityk@prawdziwysejm.gov.pl"),
                        getLocationOrThrow("Białystok", "podlaskie"), "Wiec Wyborczy! W grudniu",
                        "Po pierwsze: policja na ulice. I koniecznie zmienię im mundury, bo te niebieskie nie " +
                                "podobają mi się. Po drugie: zakłady muszą powstać państwowe, a nie zagraniczne. " +
                                "Wszyscy ludzie muszą mieć chleb, żeby nie głodowali. Rynek.",
                        Instant.parse("2022-12-20T12:30:00.000Z")),
                new Meeting(getClientOrThrow("janusz75@buziaczek.pl"), getLocationOrThrow("Wrocław", "dolnośląskie"),
                        "Wielki połów karpia w martwej Odrze, 23 grudnia",
                        "W odrze dzięki śnięciu ryb bardzo łatwo teraz złapać pysznego karpika na wigilijny stół. " +
                                "Umówmy się na 17:00 na łowienie. Już czuję ten smak w ustach.",
                        Instant.parse("2022-12-23T17:00:00.000Z")),
                new Meeting(getClientOrThrow("palsie@koniu.org"), getLocationOrThrow("Częstochowa", "śląskie"),
                        "Atak na Niebieskiego Strażnika",
                        "Niebieski strażnik pojawi się w dżungli pierwszego kwietnia o 14:20. Potrzebne 4 osoby aby " +
                                "go pokonać.", Instant.parse("2023-04-01T14:20:00.000Z"), 4));
    }

    private Client getClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow();
    }

    private Location getLocationOrThrow(String cityName, String voivodeshipName) {
        return locationRepository.findByCityNameAndVoivodeshipName(cityName, voivodeshipName).orElseThrow();
    }

    private Location newLocation(String cityName, String voivodeshipName, Double latitude, Double longitude) {
        val city = cityRepository.findByName(cityName).orElseGet(() -> cityRepository.save(new City(cityName)));
        val voivodeship = voivodeshipRepository.findByName(voivodeshipName)
                .orElseGet(() -> voivodeshipRepository.save(new Voivodeship(voivodeshipName)));

        return new Location(city, voivodeship, latitude, longitude);
    }

    private Client newClient(String email, String firstName, String lastName) {
        return new Client(email, firstName, lastName, this.sampleProfilePicture);
    }
}
