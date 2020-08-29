package rockets.ingestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RocketFamilyUnitTest {

    private RocketFamily target;

    @BeforeEach
    public void setUp(){
        target = new RocketFamily("Ariane");
    }

    @DisplayName("should throw exception when passing an empty family name to setFamily method")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetFamilyToEmpty(String familyName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFamily(familyName));
        assertEquals("family name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when passing null to setFamily method")
    @Test
    public void shouldThrowExceptionWhenSetFamilyToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setFamily(null));
        assertEquals("family name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when adding an empty variation to addVariation function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenAddEmptyVariationToFamily(String variation) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.addVariation(variation));
        assertEquals("new added variation cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when passing null to addVariation method")
    @Test
    public void shouldThrowExceptionWhenAddVariationToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.addVariation(null));
        assertEquals("new added variation cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when adding a wrong variation to addVariation function")
    @ParameterizedTest
    @ValueSource(strings = {"Arne 5 ECA", "Falcon 1 ", "ads Ariane"})
    public void shouldThrowExceptionWhenAddWrongVariationToFamily(String variation) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.addVariation(variation));
        assertEquals("new added variation does not belong to this family", exception.getMessage());
    }

}
