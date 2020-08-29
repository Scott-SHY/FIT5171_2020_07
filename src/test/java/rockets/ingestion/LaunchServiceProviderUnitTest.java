package rockets.ingestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rockets.model.LaunchServiceProvider;

import static org.junit.jupiter.api.Assertions.*;



public class LaunchServiceProviderUnitTest {
    private rockets.model.LaunchServiceProvider serviceProvider;

    @BeforeEach
    public void setUp() {
        serviceProvider = new LaunchServiceProvider("SpaceX", 2002, "USA");
    }

    // name tests
    @DisplayName("should throw exception when passing an empty name to setName method")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void testNameNotEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> serviceProvider.setName(name));
        assertEquals("name can not be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when passing null value to setName function")
    @Test
    public void testNameNotNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> serviceProvider.setName(null));
        assertEquals("name can not be null or empty", exception.getMessage());
    }

    // yearFounded tests
//    @DisplayName("should throw exception when passing negative number or beyond current year to setYear function")
//    @ParameterizedTest
//    @ValueSource(ints = {-1, -2,-10, 2021})
//    public void testYearNotNegative(int yearFounded) {
//        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> serviceProvider.setYearFounded(yearFounded));
//        assertEquals("The selected year is not valid, maybe negative or beyond current year", exception.getMessage());
//    }

    // country tests
    @DisplayName("should throw exception when passing an empty country to setCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void testCountryNotEmpty(String country)
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> serviceProvider.setCountry(country));
        assertEquals("country can not be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when passing null to setCountry function")
    @Test
    public void testCountryNotNull()
    {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> serviceProvider.setCountry(null));
        assertEquals("country can not be null or empty", exception.getMessage());
    }

    // headquarters tests
    @DisplayName("should throw exception when passing an empty headquarters to setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void testHeadquartersNotEmpty(String headquarters)
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> serviceProvider.setHeadquarters(headquarters));
        assertEquals("headquarters can not be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when passing null to setHeadquarters function")
    @Test
    public void testHeadquartersNotNull()
    {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> serviceProvider.setHeadquarters(null));
        assertEquals("headquarters can not be null or empty", exception.getMessage());
    }

    // rockets tests
//    @DisplayName("should throw exception when pass null to setRockets")
//    @Test
//    public void testRocketsNotNull()
//    {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> serviceProvider.setRockets(null));
//        assertEquals("Rockets cannot be null", exception.getMessage());
//    }
}
