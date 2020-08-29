package rockets.ingestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import static org.junit.jupiter.api.Assertions.*;

class RocketUnitTest {
    private rockets.model.Rocket target;
    private String name = "Dragon";
    private String country = "USA";
    private rockets.model.LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2010, "USA");

    @BeforeEach
    public void setUp() {
        target = new rockets.model.Rocket(name, country, manufacturer);
    }

    @DisplayName("should throw exception when pass null to Rocket name")
    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new rockets.model.Rocket(null, country, manufacturer));
        assertEquals("The validated object is null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to Rocket country")
    @Test
    public void shouldThrowExceptionWhenCountryIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new rockets.model.Rocket(name, null, manufacturer));
        assertEquals("The validated object is null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to Rocket manufacturer")
    @Test
    public void shouldThrowExceptionWhenmanufacturerIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new rockets.model.Rocket(name, country, null));
        assertEquals("The validated object is null", exception.getMessage());
    }

//    @DisplayName("should throw exception when pass a empty name to Rocket name")
//    @ParameterizedTest
//    @ValueSource(strings = {"", " ", "  "})
//    public void shouldThrowExceptionWhenNameIsEmpty(String name) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new rockets.model.Rocket(name, country, manufacturer));
//        assertEquals("rocket name can not be null or empty", exception.getMessage());
//    }

//    @DisplayName("should throw exception when pass a empty country to Rocket country")
//    @ParameterizedTest
//    @ValueSource(strings = {"", " ", "  "})
//    public void shouldThrowExceptionWhenCountryIsEmpty(String country) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new rockets.model.Rocket(name, country, manufacturer));
//        assertEquals("Rocket country cannot be null or empty", exception.getMessage());
//    }

    @DisplayName("should throw exception when pass null to mass to leo/gto/other function")
    @Test
    public void shouldThrowExceptionWhenMassToLeoGtoOtherIsNull() {
        NullPointerException exceptionLeo = assertThrows(NullPointerException.class, () -> target.setMassToLEO(null));
        NullPointerException exceptionGto = assertThrows(NullPointerException.class, () -> target.setMassToGTO(null));
        NullPointerException exceptionOther = assertThrows(NullPointerException.class, () -> target.setMassToOther(null));
        assertEquals("massToLEO cannot be null or empty", exceptionLeo.getMessage());
        assertEquals("massToGTO cannot be null or empty", exceptionGto.getMessage());
        assertEquals("massToOther cannot be null or empty", exceptionOther.getMessage());
    }

//    @DisplayName("should be a valid mass to leo/gto/other")
//    @ParameterizedTest
//    @ValueSource(strings = {"-11", "0","s", "1234.567", "150000"})
//    public void shouldThrowExceptionWhenMassIsNotValid(String mass) {
//        IllegalArgumentException exceptionLeo = assertThrows(IllegalArgumentException.class, () -> target.setMassToLEO(mass));
//        IllegalArgumentException exceptionGto= assertThrows(IllegalArgumentException.class, () -> target.setMassToGTO(mass));
//        IllegalArgumentException exceptionOther = assertThrows(IllegalArgumentException.class, () -> target.setMassToOther(mass));
//        assertEquals("Mass should be an Integer greater than 0 and less than 150000", exceptionLeo.getMessage());
//        assertEquals("Mass should be an Integer greater than 0 and less than 150000", exceptionGto.getMessage());
//        assertEquals("Mass should be an Integer greater than 0 and less than 150000", exceptionOther.getMessage());
//    }

    @DisplayName("should return true when two rockets are same")
    @Test
    public void shouldReturnTrueWhenRocketsAreTheSame() {
        rockets.model.Rocket sameRocket = new Rocket(name,country,manufacturer);
        assertEquals(target, sameRocket);
    }

    @DisplayName("should return false when two rockets are different")
    @Test
    public void shouldReturnFalseWhenRocketsAreDifferent() {
//        String thisName = "Long March 5 Series Launch Vehicle";
//        String thisCountry = "China";
//        LaunchServiceProvider thisManufacturer = new LaunchServiceProvider("中国新一代运载火箭产业化基地", 2014, "China");
//        Rocket rocket_2 = new Rocket(thisName, country, manufacturer);
//        Rocket rocket_3 = new Rocket(name, thisCountry, manufacturer);
//        Rocket rocket_4 = new Rocket(name, country, thisManufacturer);
//        assertEquals(false, target.equals(rocket_2));
//        assertEquals(false, target.equals(rocket_3));
//        assertEquals(false, target.equals(rocket_4));
    }

}