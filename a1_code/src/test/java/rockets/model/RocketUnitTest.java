/*
 * @Author: Rouzip
 * @Date: 2020-07-27 00:58:31
 * @LastEditTime: 2020-07-27 09:52:32
 * @LastEditors: Rouzip
 * @Description: Unit test for Rocket class
 */
package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.ObjectUtils.Null;

public class RocketUnitTest {
    private Rocket target = null;

    @BeforeEach
    public void setup() {
        this.target = new Rocket("Wen", "China", "American");
    }

    @DisplayName("should throw exception when pass null to Rocket function")
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  " })
    public void shouldThrowExceptionWhenSetUpNewRocketWithEmptyName(String name) {
        String country = "Austrilia";
        String manufacturer = "Amercia";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Rocket(null, country, manufacturer));
        assertEquals("Rocket name cannot be null or emtpy", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty to setMassToLEO function")
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  " })
    public void shouldThrowExceptionWhenSetmassToLEOtoEmpty(String massToLEO) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> target.setMassToLEO(massToLEO));
        assertEquals("massToLEO cannot be null or emtpy", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setMassToLEO function")
    @Test
    public void shouldThrowExceptionWhenSetmassToLEOtoNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setMassToLEO(null));
        assertEquals("massToLEO cannot be null or emtpy", exception.getMessage());
    }
}