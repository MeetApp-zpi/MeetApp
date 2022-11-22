package com.meetapp.meetapp.configuration;

import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.*;
import lombok.val;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final EventRepository eventRepository;

    private final String sampleProfilePicture;
    private List<Category> categories;

    public SampleDataLoader(CityRepository cityRepository, VoivodeshipRepository voivodeshipRepository,
                            LocationRepository locationRepository, CategoryRepository categoryRepository,
                            ClientRepository clientRepository, AnnouncementRepository announcementRepository,
                            MeetingRepository meetingRepository, EventRepository eventRepository) {
        this.cityRepository = cityRepository;
        this.voivodeshipRepository = voivodeshipRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.clientRepository = clientRepository;
        this.announcementRepository = announcementRepository;
        this.meetingRepository = meetingRepository;
        this.eventRepository = eventRepository;

        this.sampleProfilePicture = "https://d1csarkz8obe9u.cloudfront" +
                ".net/posterpreviews/testing-logo-design-template-ce84480d61b3db9a8e1522a99875832f_screen" +
                ".jpg?ts=1615794516";
    }

    @Override
    public void run(ApplicationArguments args) {
        locationRepository.saveAll(getLocations());
        categories = categoryRepository.saveAll(getCategories());
        clientRepository.saveAll(getClients());
        announcementRepository.saveAll(getAnnouncements());
        meetingRepository.saveAll(getMeetings());
        eventRepository.saveAll(getEvents());
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
                new Category("Polityka"), new Category("Motoryzacja"), new Category("Muzyka"), new Category("Nauka"),
                new Category("Środowisko"), new Category("Hobby"), new Category("Dla dzieci"), new Category("Podróże"));
    }

    private List<Client> getClients() {
        val meetAppClient = newClient("meetapp.zpi@gmail.com", "Jan", "Testowicz");
        meetAppClient.setInterests(new HashSet<>(getCategories(new HashSet<>(Arrays.asList(1, 3, 5, 7)))));

        return Arrays.asList(meetAppClient,
                newClient("fanatyk.rolkarstwa@rolki.pl", "Rolkowy", "Świrus"),
                newClient("prawdziwy.polityk@prawdziwysejm.gov.pl", "Prawdziwy", "Polityk"),
                newClient("janusz75@buziaczek.pl", "Fanatyk", "Wędkarstwa"),
                newClient("palsie@koniu.org", "Norbert", "G"));
    }

    private List<Announcement> getAnnouncements() {
        return Arrays.asList(new Announcement(getClientOrThrow("meetapp.zpi@gmail.com"),
                        getLocationOrThrow("Wrocław", "dolnośląskie"), "Testowe Ogłoszenie",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce ut elit metus. Ut vel urna " +
                                "fermentum, elementum neque quis, malesuada ante. Donec vehicula dui vitae tincidunt " +
                                "venenatis. Curabitur orci.",
                        getCategories(new HashSet<>(Arrays.asList(1, 3)))),
                new Announcement(getClientOrThrow("fanatyk" + ".rolkarstwa@rolki.pl"),
                        getLocationOrThrow("Trzebnica", "dolnośląskie"), "Mocna ekipa szuka rolkarza",
                        "Naprawdę mocna ekipa roklarzy szuka rekruta który nie będzie bał się ekstremalnie jeździć po" +
                                " mieście.",
                        getCategories(new HashSet<>(Arrays.asList(2, 5)))),
                new Announcement(getClientOrThrow("prawdziwy.polityk@prawdziwysejm.gov" + ".pl"),
                        getLocationOrThrow("Białystok", "podlaskie"), "Nie będzie niczego",
                        "Jak już będę prezydentem, to będzie zupełnie inaczej. Nie będzie sejmu i senatu. Polska " +
                                "będzie od morza do morza. Żeby nie było bandyctwa, żeby nie było złodziejstwa, żeby " +
                                "nie było niczego.",
                        getCategories(new HashSet<>(Arrays.asList(1)))),
                new Announcement(getClientOrThrow("janusz75@buziaczek.pl"),
                        getLocationOrThrow("Bydgoszcz", "kujawsko-pomorskie"), "Szukam partnera do polowania na suma",
                        "Sum grasuje w rzece pod Bydgoszczą. Jest ogromny i już kilka razy mnie pogryzł. Cena nie gra" +
                                " roli, musimy go złapać.",
                        getCategories(new HashSet<>(Arrays.asList(1, 3, 4, 6)))),
                new Announcement(getClientOrThrow("palsie@koniu.org"), getLocationOrThrow("Warszawa", "mazowieckie"),
                        "Leśnik potrzebny w aleji!",
                        "Już od 10 minut tu siedzę a dalej nie widziałem żadnego leśnika. Słychać tylko jakąś żabę. " +
                                "Mój oponent ma dużo więcej złota, a to wszystko przez różnicę dżungli. POMOCY P.S> " +
                                "Wszyscy oprócz Shyvanny ;>",
                        getCategories(new HashSet<>(Arrays.asList(1, 3)))));
    }

    private List<Meeting> getMeetings() {
        return Arrays.asList(
                new Meeting(getClientOrThrow("meetapp.zpi@gmail.com"), getLocationOrThrow("Poznań", "wielkopolskie"),
                        "Testowe Spotkanie",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas a lacus interdum, pulvinar" +
                                " ex a, luctus nulla. Orci varius natoque penatibus et magnis dis parturient montes, " +
                                "nascetur ridiculus mus. Quisque facilisis lectus ac vulputate turpis duis.",
                        Instant.parse("2023-02-25T21:37:00.000Z"), getCategories(new HashSet<>(Arrays.asList(1, 3))),
                        200),
                new Meeting(getClientOrThrow("fanatyk.rolkarstwa@rolki.pl"),
                        getLocationOrThrow("Wrocław", "dolnośląskie"),
                        "Nocny przejazd przez centrum Wrocławia w styczniu.",
                        "Kochani zapraszam Was na epicki przejazd centrum Wrocławia w Sobotę 7 stycznia!!! 💪💪🚩💯 " +
                                "Zaczynamy o 18:00. Czołówki obowiązkowe ;) Zbiórka przed NFM.",
                        Instant.parse("2023-01-07T18:00:00.000Z"), getCategories(new HashSet<>(Arrays.asList(3))), 35),
                new Meeting(getClientOrThrow("prawdziwy.polityk@prawdziwysejm.gov.pl"),
                        getLocationOrThrow("Białystok", "podlaskie"), "Wiec Wyborczy! W grudniu",
                        "Po pierwsze: policja na ulice. I koniecznie zmienię im mundury, bo te niebieskie nie " +
                                "podobają mi się. Po drugie: zakłady muszą powstać państwowe, a nie zagraniczne. " +
                                "Wszyscy ludzie muszą mieć chleb, żeby nie głodowali. Rynek.",
                        Instant.parse("2022-12-20T12:30:00.000Z"), getCategories(new HashSet<>(Arrays.asList(2, 4)))),
                new Meeting(getClientOrThrow("janusz75@buziaczek.pl"), getLocationOrThrow("Wrocław", "dolnośląskie"),
                        "Wielki połów karpia w martwej Odrze, 23 grudnia",
                        "W odrze dzięki śnięciu ryb bardzo łatwo teraz złapać pysznego karpika na wigilijny stół. " +
                                "Umówmy się na 17:00 na łowienie. Już czuję ten smak w ustach.",
                        Instant.parse("2022-12-23T17:00:00.000Z"), getCategories(new HashSet<>(Arrays.asList(5)))),
                new Meeting(getClientOrThrow("palsie@koniu.org"), getLocationOrThrow("Częstochowa", "śląskie"),
                        "Atak na Niebieskiego Strażnika",
                        "Niebieski strażnik pojawi się w dżungli pierwszego kwietnia o 14:20. Potrzebne 4 osoby aby " +
                                "go pokonać.", Instant.parse("2023-04-01T14:20:00.000Z"),
                        getCategories(new HashSet<>(Arrays.asList(2))), 4));
    }

    private List<Event> getEvents() {
        Event withPhoto = new Event(getClientOrThrow("palsie@koniu.org"), getLocationOrThrow("Siedlce", "mazowieckie"),
                "lorem ipsum title again", "Lorem ipsum dolor sit amet consectetur et description",
                Instant.parse("2023-06-06T06:06:06.666Z"), Instant.parse("2023-07-07T07:07:07.777Z"),
                getCategories(new HashSet<>(Arrays.asList(1))), 4200);

        withPhoto.setPicture("pictures\\202211\\pietrucha.jpg");

        return Arrays.asList(
                new Event(getClientOrThrow("meetapp.zpi@gmail.com"), getLocationOrThrow("Wrocław", "dolnośląskie"),
                        "Testowe Wydarzenie", "Lorem ipsum dolor sit amet, " +
                        "consectetur adipiscing elit. Praesent eu massa cursus, ultricies purus sit amet, faucibus " +
                        "lacus. " +
                        "Donec euismod velit est, vitae vestibulum urna volutpat eu. Aliquam varius nisl metus, vel " +
                        "cursus " +
                        "lorem faucibus eget. Etiam justo felis, varius non enim at, cursus malesuada nisl. Vivamus " +
                        "dignissim" +
                        " finibus est id placerat. Nulla ultricies volutpat turpis, sit amet bibendum nibh rutrum et." +
                        " " + "Suspendisse egestas non augue vitae porta.\n" + "\n" +
                        "Aliquam efficitur massa ac maximus placerat. Nulla vehicula, metus non imperdiet mollis, " +
                        "turpis " +
                        "mauris bibendum ex, quis euismod magna odio vitae dui. Nullam rhoncus turpis tellus, ac " +
                        "volutpat " +
                        "mauris faucibus vitae. Nulla vel tincidunt purus. Morbi hendrerit diam sit amet purus " +
                        "iaculis " +
                        "convallis. Aliquam a odio blandit, tincidunt nisi sed, hendrerit urna. Quisque interdum " +
                        "ultricies " +
                        "lacus, sit amet ullamcorper libero interdum quis. Suspendisse egestas enim eleifend, varius " +
                        "justo " +
                        "ut, tristique velit. Fusce lobortis, ipsum sit amet faucibus gravida, orci ligula cursus " +
                        "nisi, id " +
                        "tempus tortor tortor vitae nulla. Quisque eu diam id nisl pulvinar gravida non accumsan " +
                        "massa. Nunc " +
                        "id nisi metus. Nullam at mi ullamcorper, sodales ligula in, molestie tortor. Pellentesque " +
                        "convallis " +
                        "risus ac tellus venenatis feugiat. Sed interdum tempor vulputate. Pellentesque habitant " +
                        "morbi " + "tristique senectus et netus et malesuada fames ac turpis egestas.\n" + "\n" +
                        "Sed sit amet mollis turpis. Duis interdum nisi eget blandit tempus. Integer semper velit nec" +
                        " nibh " +
                        "laoreet sodales. Nunc non libero molestie, auctor magna vitae, molestie leo. Vivamus at " +
                        "purus id " +
                        "massa egestas condimentum. Integer id egestas metus, at pretium elit. Nam dapibus enim in " +
                        "eros " +
                        "iaculis efficitur. In dignissim tellus a tristique tempor. Aliquam erat volutpat. Etiam in " +
                        "arcu elit" + ". Quisque commodo tellus vitae augue fringilla vehicula.\n" + "\n" +
                        "Proin fermentum quam ac libero vulputate, ut pretium turpis condimentum. Proin suscipit est " +
                        "sed " +
                        "purus interdum auctor. In non dictum dui. Quisque efficitur pharetra sodales. Ut efficitur " +
                        "nec risus" +
                        " sit amet fermentum. Donec tempor iaculis dolor eu euismod. Praesent et magna non leo " +
                        "placerat " +
                        "volutpat. Integer eu viverra urna. Pellentesque in finibus mi. Morbi blandit faucibus nulla," +
                        " eu " + "cursus nisl condimentum ac.\n" + "\n" +
                        "Quisque non arcu quam. Praesent venenatis sed sem sit amet ultrices. Vestibulum nec eros " +
                        "convallis, " +
                        "scelerisque ipsum ultricies, cursus tellus. Proin sodales turpis ac malesuada faucibus. " +
                        "Fusce nec " +
                        "porttitor lorem. Nulla semper non metus a auctor. Vivamus sed turpis sagittis, finibus metus" +
                        " eget, " +
                        "placerat libero. Morbi id libero et mi cursus placerat a sit amet ante. Suspendisse egestas " +
                        "tortor " + "ac neque semper finibus.\n" + "\n" +
                        "In tempor velit id ipsum porta euismod. Sed rhoncus feugiat libero vel dignissim. Sed " +
                        "rhoncus " +
                        "posuere purus, vitae elementum augue sollicitudin sit amet. Aliquam consectetur ornare urna " +
                        "eu " +
                        "aliquet. Proin rhoncus, purus ut iaculis dignissim, augue nisl ultrices felis, eu feugiat " +
                        "magna " +
                        "lectus in orci. Maecenas vel neque augue. Etiam aliquet eget libero eu ultrices. Vestibulum " +
                        "tempor " +
                        "sollicitudin nibh eget ornare. Nunc semper nisl metus, quis vehicula diam euismod placerat. " +
                        "Praesent" + " eu venenatis odio. In vitae finibus est, sit amet fermentum nisi.\n" + "\n" +
                        "Duis libero orci, scelerisque vitae neque pellentesque, consequat aliquam nibh. Phasellus " +
                        "lobortis " +
                        "feugiat est malesuada feugiat. Suspendisse potenti. Donec dictum odio sit amet eros dictum, " +
                        "et " +
                        "consectetur quam sagittis. Phasellus vestibulum, nibh ut tempus ultrices, mi mi placerat " +
                        "neque, at " +
                        "facilisis mi velit a purus. Aliquam rutrum velit eu diam semper vulputate. Nunc cursus " +
                        "tincidunt " +
                        "purus, non dapibus neque volutpat quis. Nunc venenatis massa in odio varius, sed dignissim " +
                        "tortor " +
                        "dignissim. Vestibulum suscipit elementum nisi, ut consequat arcu ultricies vel. Etiam " +
                        "elementum " +
                        "egestas sem, ut fringilla est luctus at. Integer in elementum sem, quis imperdiet velit. " +
                        "Praesent " +
                        "consequat, felis et posuere facilisis, libero magna pretium odio, non iaculis ligula lectus " +
                        "eget " + "dolor.\n" + "\n" +
                        "Integer ut egestas arcu. Nam aliquet magna vel leo ultricies, tincidunt finibus felis " +
                        "accumsan. " +
                        "Proin a turpis eu augue varius gravida ac nec metus. Donec urna augue, mattis sed odio in, " +
                        "sodales " +
                        "faucibus dui. Donec in gravida arcu, eu tincidunt magna. Maecenas in porta nulla, et laoreet" +
                        " sapien." +
                        " Nullam id fringilla massa. Morbi condimentum lectus quis magna imperdiet, a viverra massa " +
                        "ultrices." +
                        " Nulla justo magna, mollis eget justo ac, iaculis congue justo. Maecenas semper quam sit " +
                        "amet " + "sollicitudin euismod.\n" + "\n" +
                        "Nulla a urna rutrum tortor sagittis vestibulum. Mauris in lorem nulla. Proin bibendum enim " +
                        "enim, et " +
                        "ultricies lacus condimentum at. Nam nec ligula dapibus, malesuada sapien in, mollis magna. " +
                        "Sed " +
                        "rhoncus maximus nulla a viverra. Nulla et massa ut libero vestibulum molestie ac vitae ipsum" +
                        ". " + "Aliquam at semper mi, nec mattis nunc.\n" + "\n" +
                        "Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed sodales odio ut magna " +
                        "ornare, et " +
                        "aliquet leo porta. Praesent faucibus finibus odio, vitae facilisis libero. Vestibulum " +
                        "elementum, " +
                        "risus sed sollicitudin aliquet, nisl lorem placerat augue, ac volutpat ante magna vitae leo." +
                        " Duis " +
                        "condimentum varius velit, ut iaculis mi luctus sed. Praesent dictum sagittis augue, at " +
                        "accumsan diam" +
                        " rutrum vitae. Ut consectetur mauris ligula, sed blandit eros tempor non.\n" + "\n" +
                        "Phasellus et congue arcu. Nulla nec sapien sapien. Vivamus blandit nisi ac odio aliquam, in " +
                        "sodales " +
                        "elit molestie. Maecenas ultricies sollicitudin nisi non condimentum. Mauris eu enim vel elit" +
                        " " +
                        "pulvinar aliquam. Nam convallis porta lacus sed fringilla. Cras vel sem sit amet tortor " +
                        "faucibus " + "consectetur.\n" + "\n" +
                        "Curabitur ut arcu vitae libero interdum eleifend. Nunc quam elit, molestie sed tortor sed, " +
                        "sollicitudin auctor velit. Quisque accumsan mi at mauris molestie, non malesuada lectus " +
                        "congue. Duis" +
                        " tristique dui consequat, ullamcorper erat sed, scelerisque augue. Vivamus commodo dignissim" +
                        " erat ac" +
                        " ornare. Vivamus tristique suscipit augue, id gravida nulla volutpat nec. Quisque non massa " +
                        "eget " +
                        "augue viverra placerat. Proin turpis eros, varius nec elit vitae, iaculis posuere massa. " +
                        "Suspendisse" + " potenti.\n" + "\n" +
                        "In mattis at dui non molestie. Nam quis auctor sem. Fusce fermentum nibh eros, cursus " +
                        "volutpat lacus" +
                        " imperdiet quis. Aliquam a porttitor purus. Etiam felis ligula, venenatis nec aliquam nec, " +
                        "mollis " +
                        "semper mi. Duis faucibus viverra est vel euismod. Etiam aliquet tortor ac fermentum " +
                        "convallis. " +
                        "Praesent varius, quam ut facilisis aliquet, massa orci eleifend ligula, nec egestas sapien " +
                        "nisl " + "sagittis lacus.\n" + "\n" +
                        "Duis pulvinar nunc a urna feugiat mollis. Sed porta libero vestibulum, efficitur sem sit " +
                        "amet, " +
                        "efficitur nisl. Sed tempus lectus sed posuere laoreet. In vel libero ut felis commodo " +
                        "bibendum ac ut" +
                        " urna. Integer ac erat porttitor purus molestie faucibus. Nam convallis justo commodo, " +
                        "rhoncus " +
                        "tortor ac, euismod velit. Nunc eu placerat leo. Quisque magna mi, tincidunt non pellentesque" +
                        " in, " +
                        "dignissim sit amet leo. Aenean lacinia malesuada cursus. Suspendisse aliquam porta interdum." +
                        " Aliquam" + " sed tellus lectus.\n" + "\n" +
                        "Duis sollicitudin tortor vulputate tortor cursus, nec tempor nisl tristique. Curabitur quis " +
                        "est vel " +
                        "orci commodo varius at non mauris. Vestibulum luctus quam a congue feugiat. Curabitur nec " +
                        "augue eget" +
                        " neque hendrerit vestibulum et a erat. Nam porttitor bibendum velit vel rutrum. Suspendisse " +
                        "quis " +
                        "dignissim dolor, non iaculis neque. Sed rutrum ac odio eu consectetur. Nullam ultricies " +
                        "faucibus " +
                        "faucibus. Nulla non velit in ligula posuere elementum sed non risus. Morbi sit amet velit " +
                        "posuere " +
                        "orci pulvinar consequat. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices " +
                        "posuere " + "cubilia curae;\n" + "\n" +
                        "In lobortis magna eu purus hendrerit pretium. Sed convallis augue at justo rutrum, non " +
                        "ornare magna " +
                        "hendrerit. In pellentesque lacus ut dui maximus, vel tincidunt sem gravida. Nullam nec " +
                        "pretium dui. " +
                        "Vivamus tempus dui mi, pharetra varius neque volutpat nec. Quisque vel pharetra sapien, quis" +
                        " " + "sollicitudin arcu. Cras eu pharetra purus.\n" + "\n" +
                        "Sed euismod vel purus nec efficitur. Vestibulum ante ipsum primis in faucibus orci luctus et" +
                        " " + "ultrices posuere cubilia curae; Pellentesque bibendum risus leo, sed porttitor justo " +
                        "consequat sed. " +
                        "Morbi sit amet augue mattis lacus laoreet porttitor vitae in orci. Aliquam erat volutpat. Ut" +
                        " id " +
                        "metus elit. Sed ac orci et odio tempor sagittis. Curabitur hendrerit justo eget ligula " +
                        "accumsan, id " +
                        "tincidunt purus vestibulum. In consectetur, erat sed pellentesque pharetra, odio ante " +
                        "commodo augue," +
                        " eu porta nunc justo sed risus. Quisque venenatis, nulla eget faucibus ultricies, lorem " +
                        "massa " +
                        "blandit lectus, ut porttitor augue est porta felis. Fusce aliquam tincidunt efficitur. In " +
                        "hac " +
                        "habitasse platea dictumst. Vivamus blandit, velit sit amet tempor tempus, risus dui volutpat" +
                        " nisi, " + "sed luctus ante elit eget metus.\n" + "\n" +
                        "Aliquam condimentum, nunc id vehicula pellentesque, lacus nunc maximus dui, nec porta ex " +
                        "quam id " +
                        "velit. Quisque suscipit vehicula ipsum, nec elementum urna sodales vel. Pellentesque " +
                        "habitant morbi " +
                        "tristique senectus et netus et malesuada fames ac turpis egestas. Orci varius natoque " +
                        "penatibus et " +
                        "magnis dis parturient montes, nascetur ridiculus mus. Fusce nec lorem velit. Vivamus id " +
                        "ullamcorper " +
                        "tortor. Donec ut finibus augue. Proin quis mauris at turpis eleifend sollicitudin. Integer " +
                        "at sem " +
                        "semper, rutrum turpis eu, aliquam quam. Aliquam non ipsum nibh. Morbi non felis at elit " +
                        "commodo " +
                        "iaculis vitae in massa. Curabitur venenatis ex purus, pharetra luctus nunc interdum non. " +
                        "Donec justo" +
                        " tellus, aliquam id finibus et, sodales eget quam. Phasellus dictum varius ipsum, ac aliquam" +
                        " dolor " + "placerat a. Aliquam erat volutpat.\n" + "\n" +
                        "Donec placerat, leo et blandit aliquet, urna nunc laoreet nisl, eu sollicitudin nunc diam at" +
                        " tortor." +
                        " Quisque ac egestas justo. Morbi bibendum dictum tellus, in sagittis libero dapibus ac. Sed " +
                        "cursus " +
                        "nulla sit amet mauris interdum auctor. Nulla ut faucibus turpis. Nullam elementum ante eu " +
                        "nunc " + "cursus, non dignissim vel vel.", Instant.parse("2022-12-20T10:00:00.000Z"),
                        Instant.parse("2022-12-22T17:00:00.000Z"), getCategories(new HashSet<>(Arrays.asList(1, 3))),
                        200,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ligula arcu, pulvinar " +
                                "eget " +
                                "enim quis, posuere eleifend leo. Mauris sed nisi aliquet, consequat nibh ac, " +
                                "convallis erat." +
                                " Nulla in ornare felis. Sed tristique ultrices quam id tempus. Nunc mi augue, " +
                                "porttitor vel " +
                                "risus id, dapibus aliquam mauris. Ut tincidunt suscipit elit, id finibus lacus " +
                                "aliquam quis." +
                                " Aenean ultrices ut metus nec scelerisque. Aenean lectus nisl, condimentum id sem " +
                                "ac, " + "interdum mattis mauris.\n" + "\n" +
                                "Curabitur et accumsan purus. Integer gravida lectus a mauris sodales condimentum. " +
                                "Praesent " +
                                "consectetur tincidunt diam ut suscipit. Phasellus sed orci ex. Curabitur tempus nunc" +
                                " at " + "metus " +
                                "mollis, eget vestibulum eros elementum. Maecenas elementum, ipsum quis fermentum " +
                                "cursus, " + "purus metus" +
                                " laoreet turpis, a finibus augue ex sit amet magna. Proin hendrerit ut tellus ac " +
                                "dignissim. " + "Donec " +
                                "aliquet mauris arcu, quis semper leo iaculis id. Nullam malesuada posuere diam. " +
                                "Vivamus in " + "finibus " +
                                "felis, id pulvinar metus. Ut eget interdum nisi. Cras facilisis mi et quam viverra " +
                                "scelerisque " + "feugiat et lectus.\n" + "\n" +
                                "Nulla in condimentum urna. Maecenas nec volutpat diam. Duis ut neque nec felis " +
                                "aliquet " + "tempus vel " +
                                "vitae ipsum. Phasellus accumsan velit eu auctor mollis. Sed eget lectus metus. Ut " +
                                "pulvinar " + "mollis " +
                                "mi, ut tristique massa pellentesque id. Suspendisse luctus venenatis arcu, quis " +
                                "viverra nunc" + " " +
                                "imperdiet vitae. Cras sed nisi velit. Donec tincidunt blandit congue. Fusce et lacus" +
                                " urna. " +
                                "Suspendisse potenti. Aliquam consequat varius tellus sit amet hendrerit. Proin " +
                                "ornare massa " + "et " +
                                "posuere porttitor. Curabitur lectus orci, maximus in ex vitae, dignissim ultrices " +
                                "sapien. " + "Praesent " +
                                "dictum elit at tincidunt suscipit. Cras lorem est, feugiat et placerat molestie, " +
                                "dapibus vel" + " neque" + ".\n" + "\n" +
                                "Suspendisse aliquam placerat lacus at facilisis. Integer a pellentesque erat. " +
                                "Quisque ante " + "mauris, " +
                                "volutpat eget diam dictum, aliquam pulvinar ligula. Donec ut mi sit amet enim " +
                                "finibus " + "consequat. " +
                                "Nulla bibendum mauris nisi, vel consequat velit varius vulputate. Praesent lobortis " +
                                "mauris " + "urna, sit" +
                                " amet venenatis augue sagittis sed. Nulla facilisis diam eu tempus iaculis. In ut " +
                                "tellus " + "mollis, " +
                                "malesuada ligula sed, bibendum tortor. Etiam tincidunt, lorem vel pharetra sodales, " +
                                "libero " + "nulla " +
                                "vestibulum tellus, a tempor eros elit imperdiet augue. Morbi quis vulputate leo. " +
                                "Proin " + "maximus " +
                                "placerat dictum. Praesent blandit neque sed libero varius, eget malesuada metus " +
                                "iaculis. " + "Morbi a " +
                                "lectus eu libero venenatis fringilla. Nullam bibendum faucibus aliquet. Ut nisl " +
                                "libero, " + "semper vitae" + " elit eu, porta maximus dui.\n" + "\n" +
                                "Curabitur lobortis sem nisl, quis vulputate orci luctus at. Interdum et malesuada " +
                                "fames ac " + "ante " +
                                "ipsum primis in faucibus. Duis vehicula sapien quis neque hendrerit sagittis. Donec " +
                                "quis " + "semper " +
                                "nibh, quis laoreet lorem. Proin tortor turpis, aliquet vel est at, mollis suscipit " +
                                "dolor. " +
                                "Pellentesque congue vestibulum quam vel ornare. Donec ultrices ante lorem, et " +
                                "accumsan felis" + " " +
                                "accumsan ut. Nullam id posuere nulla. Phasellus sed diam sodales, ornare orci nec, " +
                                "commodo " + "ligula. " +
                                "Sed eu odio viverra, sollicitudin leo sed, feugiat purus. Class aptent taciti " +
                                "sociosqu ad " + "litora " +
                                "torquent per conubia nostra, per inceptos himenaeos. Morbi lorem dui, vehicula et " +
                                "quam eu, " + "pharetra " +
                                "fringilla justo. Aenean tristique convallis velit in placerat.\n" + "\n" +
                                "Integer sollicitudin felis sed sem facilisis mattis. Nullam et magna in quam " +
                                "tristique " + "dignissim. " +
                                "Nulla tempus tincidunt orci, sed convallis orci facilisis vel. Fusce tincidunt metus" +
                                " a " + "laoreet " +
                                "molestie. Nulla laoreet vestibulum molestie. Vivamus lacinia vitae lorem sed " +
                                "porttitor. " + "Pellentesque" +
                                " vulputate ultricies metus quis efficitur. Phasellus a massa ullamcorper felis " +
                                "vulputate " + "molestie. " +
                                "Etiam auctor rutrum mauris, ac lobortis tellus consequat in. Integer finibus nisi " +
                                "sed " + "fringilla " + "vestibulum.\n" + "\n" +
                                "Morbi nec volutpat enim, nec sagittis elit. Integer semper placerat magna. Donec eu " +
                                "libero " + "nibh. " +
                                "Curabitur pellentesque placerat placerat. Nam tincidunt arcu quis posuere finibus. " +
                                "Praesent " + "in " +
                                "elementum urna, non maximus nisi. Pellentesque tincidunt turpis massa, ut rhoncus " +
                                "enim " + "molestie nec." +
                                " Quisque sodales interdum dolor, et maximus dui hendrerit nec. Vivamus non " +
                                "pellentesque urna" + ".\n" + "\n" +
                                "Vivamus sit amet condimentum arcu. Aenean malesuada enim mauris, vitae rutrum lorem " +
                                "accumsan" + " sed. " +
                                "Donec non scelerisque leo, id fermentum dolor. Suspendisse fermentum nunc sed " +
                                "vestibulum " + "dignissim. " +
                                "Integer vitae nisl lacus. Aliquam sagittis est dui, in feugiat urna venenatis sed. " +
                                "Sed " + "dictum elit " +
                                "mi, eget rutrum libero condimentum eu. Mauris ante ipsum, varius vitae placerat eu, " +
                                "sollicitudin non" +
                                " purus. Ut lobortis, massa nec accumsan viverra, elit ante consectetur ante, " +
                                "consequat " + "consectetur " +
                                "dui tortor id nunc. Fusce dignissim neque ipsum, eget imperdiet risus placerat ut. " +
                                "Nulla " + "tincidunt, " +
                                "orci vel congue imperdiet, lectus sem pellentesque erat, id vestibulum libero turpis" +
                                " quis " + "velit. In quam ornare mauris, at tellus."),
                new Event(getClientOrThrow("fanatyk.rolkarstwa@rolki.pl"),
                        getLocationOrThrow("Trzebnica", "dolnośląskie"),
                        "Powiatowe mistrzostwa \"Najszybszka Rolka Burmistrza M.D. (zbieżność inicjałów przypadkowa) " +
                                "2023\"",
                        "W imieniu naszego przewspaniałeg burmistrza pragnę Was serdecznie zaprosić na przyszłoroczne" +
                                " mistrzostwa \"Najszybsza Rolka Burmistrza M.D. (zbieżność inicjałów przypadkowa) " +
                                "2023\"! Odbędą się one już w terminie 17-18 maja. Dla wszystkich uczestników " +
                                "przewidziane są nagrody, a dla zwycięzcy - udział w chórku nowej piosenki " +
                                "Powiatowej!!! 😎🆒 dozo 😁", Instant.parse("2023-05-17T10:00:00.000Z"),
                        Instant.parse("2023-05-18T16:00:00.000Z"), getCategories(new HashSet<>(Arrays.asList(4))), 300,
                        "17.\n10:00 - Uroczyste otwarcie. przecięcie wstęgi przez Pana burmistrza\n11:00 - " +
                                "Eliminacje\n" +
                                "14:00 - Ćwierćfinały\n\n18.\n10:00 - Otwarcie dnia\n11:00 - Finały 🏁💪🦾\n14:00 - " +
                                "Uroczyste" + " przyznanie nagród, poczęstunek 🎂🌭🥂"),
                new Event(getClientOrThrow("prawdziwy.polityk@prawdziwysejm.gov.pl"),
                        getLocationOrThrow("Szczecin", "zachodniopomorskie"), "Lorem ipsum ipsum dolor et cetera",
                        "A long, detailed description of the event", Instant.parse("2022-12-21T10:00:00.000Z"),
                        Instant.parse("2022-12-28T22:00:00.000Z"), getCategories(new HashSet<>(Arrays.asList(2))), 20,
                        "A schedule describing what's planned for each day of the event."),
                new Event(getClientOrThrow("janusz75@buziaczek.pl"), getLocationOrThrow("Katowice", "śląskie"),
                        "lorem ipsum title", "Lorem ipsum dolor sit amet consectetur et description",
                        Instant.parse("2023-06-06T06:06:06.666Z"), Instant.parse("2023-07-07T07:07:07.777Z"),
                        getCategories(new HashSet<>(Arrays.asList(3))),
                        "Lorem ipsum schedule"),
                withPhoto);
    }

    private Client getClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow();
    }

    private Location getLocationOrThrow(String cityName, String voivodeshipName) {
        return locationRepository.findByCityNameAndVoivodeshipName(cityName, voivodeshipName).orElseThrow();
    }

    private Set<Category> getCategories(Set<Integer> categoryIds) {
        HashSet<Category> categoriesInSet = new HashSet<>();
        for (Integer i : categoryIds) {
            categoriesInSet.add(categories.get(i));
        }
        return new HashSet<>(categoriesInSet);
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
