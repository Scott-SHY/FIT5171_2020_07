package rockets.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LaunchServiceProviderTest {
    private LaunchServiceProvider target;
    @BeforeEach
    void setUp() {
        target = new LaunchServiceProvider("Jiuquan",2000,"China");
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("should throw exception when founded year illegal")
    @ParameterizedTest
    @ValueSource(ints = {1900,100,2100})
    public void shouldThrowExceptionWhenFoundedYearIllegal(int num){
        assertFalse(num>=1970 && num<=2020);
    }

    @DisplayName("should throw exception when country not China!")
    @ParameterizedTest
    @ValueSource(strings = {"England","USA"})
    public void shouldThrowExceptionWhenCountryNotChina(String country){
        LaunchServiceProvider lsp = new LaunchServiceProvider("Jiuquan",2000,"China");
        lsp.setCountry(country);
        assertFalse(target.equals(lsp));
    }

    @DisplayName("should throw exception when pass a empty to setName function!")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setName(name));
        assertEquals("name can not be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setName function!")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull(){
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setName(null));
        assertEquals("name can not be null or empty",exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty to setCountry function!")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetCountryToEmpty(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setCountry(country));
        assertEquals("country can not be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setCountry function!")
    @Test
    public void shouldThrowExceptionWhenSetCountryToNull(){
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setCountry(null));
        assertEquals("country can not be null or empty",exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty to setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetHeadquartersToEmpty(String headquarters) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setHeadquarters(headquarters));
        assertEquals("headquarters can not be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setHeadquarters function")
    @Test
    public void shouldThrowExceptionWhenSetHeadquartersToNull(){
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setHeadquarters(null));
        assertEquals("headquarters can not be null or empty",exception.getMessage());
    }
}