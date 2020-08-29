package rockets.ingestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayloadUnitTest {
    private Payload payload;

    @BeforeEach
    public void setUp() {
        payload = new Payload("Beidou", "satellite", "navigation guiding and positioning");
    }

    @DisplayName("should throw exception when pass an empty name to setName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> payload.setName(name));
        assertEquals("payload name cannot be empty or null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setName function")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> payload.setName(null));
        assertEquals("payload name cannot be empty or null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty type to setType function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetTypeToEmpty(String type) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> payload.setType(type));
        assertEquals("payload type cannot be empty or null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setType function")
    @Test
    public void shouldThrowExceptionWhenSetTypeToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> payload.setType(null));
        assertEquals("payload type cannot be empty or null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a negative number or zero to setWeight function")
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -100.1, -0.1})
    public void shouldThrowExceptionWhenSetWeightToEmpty(Double weight) {
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> payload.setWeight(weight));
        assertEquals("payload weight cannot be a negative number or zero", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty mission to setMission function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetMissionToEmpty(String mission) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> payload.setMission(mission));
        assertEquals("payload mission cannot be empty or null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setMission function")
    @Test
    public void shouldThrowExceptionWhenSetMissionToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> payload.setMission(null));
        assertEquals("payload mission cannot be empty or null", exception.getMessage());
    }
}
