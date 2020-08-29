package rockets.ingestion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.time.LocalDate;
import java.util.stream.Stream;

class LaunchUnitTest {
    private rockets.model.Launch launch;
    private rockets.model.LaunchServiceProvider manufacturer = new rockets.model.LaunchServiceProvider("SpaceX", 2010, "USA");
    private rockets.model.Rocket rocket = new rockets.model.Rocket("Dragon", "Usa", manufacturer);


    @BeforeEach
    public void setUp() {
        launch = new rockets.model.Launch();
        launch.setLaunchServiceProvider(manufacturer);
        launch.setLaunchVehicle(rocket);
    }

    @DisplayName("should throw exception when pass null to setLaunchDate")
    @Test
    public void testLaunchDateNotNull()
    {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchDate(null));
        assertEquals("The validated object is null", exception.getMessage());
    }

//    @DisplayName("should throw exception when pass null to setLaunchVehicle")
//    @Test
//    public void testLaunchVehicleNotNull()
//    {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchVehicle(null));
//        assertEquals("LaunchVehicle cannot be null", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass null to setLaunchServiceProvider")
//    @Test
//    public void testLaunchServiceProviderNotNull()
//    {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchServiceProvider(null));
//        assertEquals("LaunchServiceProvider cannot be null", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass null to setPayload function")
//    @Test
//    public void shouldThrowExceptionWhenSetPayloadToNull() {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setPayload(null));
//        assertEquals("payload cannot be null", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass null to LaunchSite")
//    @Test
//    public void shouldThrowExceptionWhenLaunchSiteIsNull() {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchSite(null));
//        assertEquals("LaunchSite cannot be null or empty", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass a empty name to LaunchSite")
//    @ParameterizedTest
//    @ValueSource(strings = {"", " ", "  "})
//    public void shouldThrowExceptionWhenLaunchSiteIsEmpty(String name) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setLaunchSite(name));
//        assertEquals("LaunchSite cannot be null or empty", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass null to Orbit")
//    @Test
//    public void shouldThrowExceptionWhenOrbitIsNull() {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setOrbit(null));
//        assertEquals("Orbit cannot be null or empty", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass a empty name to Orbit")
//    @ParameterizedTest
//    @ValueSource(strings = {"", " ", "  "})
//    public void shouldThrowExceptionWhenOrbitIsEmpty(String name) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setOrbit(name));
//        assertEquals("Orbit cannot be null or empty", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass null to Function")
//    @Test
//    public void shouldThrowExceptionWhenFunctionsNull() {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setFunction(null));
//        assertEquals("Function cannot be null or empty", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass a empty name to Function")
//    @ParameterizedTest
//    @ValueSource(strings = {"", " ", "  "})
//    public void shouldThrowExceptionWhenFunctionIsEmpty(String name) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setFunction(name));
//        assertEquals("Function cannot be null or empty", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass null to price")
//    @Test
//    public void testLaunchPriceNotNull()
//    {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setPrice(null));
//        assertEquals("Price cannot be null", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass null to LaunchOutcome")
//    @Test
//    public void testLaunchOutcomeNotNull()
//    {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchOutcome(null));
//        assertEquals("LaunchOutcome cannot be null", exception.getMessage());
//    }

    @Test
    public void testSetOutcomeSucceed(){
        launch.setLaunchOutcome(rockets.model.Launch.LaunchOutcome.SUCCESSFUL);
        assertEquals(launch.getLaunchOutcome(), rockets.model.Launch.LaunchOutcome.SUCCESSFUL);
    }

    @DisplayName("should be the same when test the getLaunchVehicle function")
    @Test
    public void shouldBeTheSameWhenTestThegetLaunchVehicleFunction(){
        assertEquals(rocket, launch.getLaunchVehicle());
    }

    @DisplayName("should be the same when test the getLaunchServiceProvider function")
    @Test
    public void shouldBeTheSameWhenTestThegetLaunchServiceProviderFunction(){
        assertEquals(manufacturer, launch.getLaunchServiceProvider());
    }

    @DisplayName("should return false when launch object is different")
    @ParameterizedTest
    @MethodSource("LaunchObjectProvider")
    public void shouldReturnFalseWhenLaunchObjectIsDifferent(rockets.model.Launch testLaunch){
        rockets.model.LaunchServiceProvider provider = new rockets.model.LaunchServiceProvider("SpaceX", 2010, "USA");
        rockets.model.Rocket rocket = new rockets.model.Rocket("Haha", "China", provider);

        launch.setLaunchDate(LocalDate.of(2010,10,25));
        launch.setLaunchVehicle(rocket);
        launch.setLaunchServiceProvider(provider);
        launch.setOrbit("Other");
        assertNotEquals(launch, testLaunch);
    }

    @DisplayName("should return true when launch object is equal")
    @Test
    public void shouldReturnTrueWhenLaunchObjectIsEqual(){
        rockets.model.LaunchServiceProvider provider = new rockets.model.LaunchServiceProvider("SpaceX", 2002, "USA");
        rockets.model.Rocket rocket = new rockets.model.Rocket("Dragon", "Usa", provider);

        launch.setLaunchDate(LocalDate.of(2010,10,25));
        launch.setLaunchVehicle(rocket);
        launch.setLaunchServiceProvider(provider);
        launch.setOrbit("Other");

        rockets.model.LaunchServiceProvider provider1 = new rockets.model.LaunchServiceProvider("SpaceX", 2002, "USA");
        rockets.model.Rocket rocket1 = new rockets.model.Rocket("Dragon", "Usa", provider1);

        rockets.model.Launch testLaunch = new rockets.model.Launch();
        testLaunch.setLaunchDate(LocalDate.of(2010,10,25));
        testLaunch.setLaunchVehicle(rocket1);
        testLaunch.setLaunchServiceProvider(provider1);
        testLaunch.setOrbit("Other");
        assertEquals(launch, testLaunch);
    }

    private static Stream<Arguments> LaunchObjectProvider() {
        rockets.model.LaunchServiceProvider provider1 = new rockets.model.LaunchServiceProvider("SpaceX", 2010, "USA");
        rockets.model.Rocket rocket1  = new rockets.model.Rocket("Haha2","Sorth Korea", provider1);

        rockets.model.Launch launch1 = new rockets.model.Launch();
        launch1.setLaunchDate(LocalDate.of(2010,10,25));
        launch1.setLaunchServiceProvider(provider1);
        launch1.setLaunchVehicle(rocket1);
        launch1.setOrbit("Other");

        rockets.model.LaunchServiceProvider provider2 = new LaunchServiceProvider("Space", 2010, "USA");
        rockets.model.Rocket rocket2  = new Rocket("Haha","North Korea", provider2);

        rockets.model.Launch launch2 = new Launch();
        launch1.setLaunchDate(LocalDate.of(2011,10,25));
        launch1.setLaunchServiceProvider(provider2);
        launch1.setLaunchVehicle(rocket2);
        launch1.setOrbit("Other");

        return Stream.of(Arguments.of(launch1), Arguments.of(launch2));
    }
}
