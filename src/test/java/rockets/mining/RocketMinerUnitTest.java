package rockets.mining;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RocketMinerUnitTest {
    Logger logger = LoggerFactory.getLogger(RocketMinerUnitTest.class);

    private DAO dao;
    private RocketMiner miner;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;

    @BeforeEach
    public void setUp() {
        dao = mock(Neo4jDAO.class);
        miner = new RocketMiner(dao);
        rockets = Lists.newArrayList();

        lsps = Arrays.asList(
                new LaunchServiceProvider("ULA", 1990, "USA"),
                new LaunchServiceProvider("SpaceX", 2002, "USA"),
                new LaunchServiceProvider("ESA", 1975, "Europe ")
        );

        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};
        // 5 rockets
        for (int i = 0; i < 5; i++) {
            rockets.add(new Rocket("rocket_" + i, "USA", lsps.get(lspIndex[i])));
        }
        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};

        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

//        // index of price of each launch
//        int[] priceIndex = new int[]{200000, 500000, 500000, 900000, 100000, 400000, 300000, 800000, 500000, 200000};

        // index of payload of each launch
        int[] payloadsIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 2};

        // prices of launches
        BigDecimal[] price = new BigDecimal[10];
        int[] priceFactor = new int[]{7,9,8,5,4,3,2,5,8,12};
        for (int i = 0; i < priceFactor.length; i++){
            price[i] = new BigDecimal(priceFactor[i]*1000);
        }
        // index of payload of each launch
        //String[] payloadIndex = new String[]{"100", "400", "300", "900", "100", "500", "500", "800", "400", "500"};

        Launch.LaunchOutcome[] launchOutcomes = new Launch.LaunchOutcome[]{
                Launch.LaunchOutcome.FAILED,
                Launch.LaunchOutcome.SUCCESSFUL,
                Launch.LaunchOutcome.SUCCESSFUL,
                Launch.LaunchOutcome.FAILED,
                Launch.LaunchOutcome.SUCCESSFUL,
                Launch.LaunchOutcome.FAILED,
                Launch.LaunchOutcome.SUCCESSFUL,
                Launch.LaunchOutcome.SUCCESSFUL,
                Launch.LaunchOutcome.FAILED,
                Launch.LaunchOutcome.SUCCESSFUL
        };

        // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
            logger.info("create " + i + " launch in month: " + months[i]);
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(2017, months[i], 1));
            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchOutcome(launchOutcomes[i]); // Set Launch Outcome to Launch
            l.setPrice(price[i]);
            l.setLaunchServiceProvider(rockets.get(rocketIndex[i]).getManufacturer());
            l.setLaunchOutcome(launchOutcomes[i]);
            l.setLaunchSite("VAFB");
            l.setOrbit("LEO");
            spy(l);
            return l;
        }).collect(Collectors.toList());

        rockets.get(0).setLaunches(Sets.newHashSet(launches.subList(0, 4)));
        rockets.get(1).setLaunches(Sets.newHashSet(launches.subList(4, 7)));
        rockets.get(2).setLaunches(Sets.newHashSet(launches.subList(7, 9)));
        rockets.get(3).setLaunches(Sets.newHashSet(launches.get(9)));

    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostRecentLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate()));
        List<Launch> loadedLaunches = miner.mostRecentLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostLaunchedRockets(int k){
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> listLaunches = new ArrayList<>(launches);
        // Get all Rockets sorted by Number of Launches
        List<Rocket> sortedRockets = getSortedRocketsByLaunches(listLaunches);
        // Get all k-most launched rockets
        List<Rocket> loadedRockets = miner.mostLaunchedRockets(k);
        assertEquals(k, loadedRockets.size());
        assertEquals(sortedRockets.subList(0, k), loadedRockets);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostReliableLsp(int k){
        when(dao.loadAll(LaunchServiceProvider.class)).thenReturn(lsps);
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        // Get all Launches sorted by Number of lsps
        List<LaunchServiceProvider> sortedLaunches = new ArrayList<>(lsps);
        //use mostReliableLaunchServiceProviders to get all k-most reliable launch record
        List<LaunchServiceProvider> launchServiceProviders = miner.mostReliableLaunchServiceProviders(k);
        assertEquals(k, launchServiceProviders.size());
        assertEquals(sortedLaunches.subList(0, k), launchServiceProviders);
    }

    //Dominant The dominant country in an orbit who has launched the most rockets.
    @ParameterizedTest
    @ValueSource(strings = "LEO")
    public void shouldReturnDominantCountry(String orbit){
        String country1= "USA";

        when(dao.loadAll(Rocket.class)).thenReturn(rockets);
        when(dao.loadAll(Launch.class)).thenReturn(launches);

        List<Launch> listLaunches = new ArrayList<>(launches);
        String country = miner.dominantCountry(orbit);
        assertEquals(country1,country);
        //assertEquals(listLaunches.subList(0, k), country);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostExpensiveLaunches(int k){
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getPrice().compareTo(b.getPrice()));
        List<Launch> loadedLaunches = miner.mostExpensiveLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    //Hotshots The top-k launch service providers that has the highest sales revenue in a year.
    @ParameterizedTest
    @CsvSource({"1,2017","2,2017"})
    public void shouldReturnTopLspWithHighestRevenues(int k, int year){
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        // Get Top K List of LSPs from Miner
        List<LaunchServiceProvider> loadedLSPs= miner.highestRevenueLaunchServiceProviders(k,year);
        List<Launch> listLaunches = new ArrayList<>(launches);
        // Get Sorted List of All LSPs
        List<LaunchServiceProvider> sortedLSPs = getSortedLspByRevenue(getRevenuePerLspInYear(listLaunches,year));
        assertEquals(k, loadedLSPs.size());
        assertEquals(sortedLSPs.subList(0, k), loadedLSPs);
    }

    public static Map<LaunchServiceProvider, BigDecimal> getRevenuePerLspInYear(Collection<Launch> launches, int year){
        // FILTER LAUNCHES PER YEAR
        List<Launch> filteredLaunchList = launches.stream().filter(Launch -> Launch.getLaunchDate().getYear() == year).collect(Collectors.toList());
        Map<LaunchServiceProvider, BigDecimal> mapByLsp = new HashMap<>();
        for (Launch l : filteredLaunchList) {
            if (mapByLsp.containsKey(l.getLaunchServiceProvider())){
                BigDecimal bd = mapByLsp.get(l.getLaunchServiceProvider()).add(l.getPrice());
                mapByLsp.put(l.getLaunchServiceProvider(),bd);
            } else {
                BigDecimal tmp = l.getPrice();
                mapByLsp.put(l.getLaunchServiceProvider(),tmp);
            }
        }
        return mapByLsp;
    }

    // Get List of LSP Sorted (descending) by the Revenue Amount
    public static List<LaunchServiceProvider> getSortedLspByRevenue(Map<LaunchServiceProvider,BigDecimal> mapUnsorted){
        Map<LaunchServiceProvider, BigDecimal> sortedLSP = mapUnsorted
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
        return new ArrayList<>(sortedLSP.keySet());
    }

    public static List<Rocket> getSortedRocketsByLaunches (List<Launch> listLaunches){
        Map<Rocket,Integer> mapRockets = new HashMap<>();
        for (Launch launch: listLaunches){
            Rocket rocket = launch.getLaunchVehicle();
            if (mapRockets.containsKey(rocket)){
                int number_launches = mapRockets.get(rocket);
                mapRockets.put(rocket,++number_launches);
            } else {
                mapRockets.put(rocket,1);
            }
        }
        Map<Rocket, Integer> sortedRocket = mapRockets
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
        List<Rocket> topRockets = new ArrayList<>();
        for (Rocket rocket : sortedRocket.keySet()){
            topRockets.add(rocket);
        }
        return topRockets;
    }
}