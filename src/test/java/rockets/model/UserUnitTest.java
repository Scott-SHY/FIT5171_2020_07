package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {
    private User target;

    @BeforeEach
    public void setUp() {
        target = new User();
    }


    @DisplayName("should throw exception when pass a empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEmailToEmpty(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"123", "task", "task123"})
    public void shouldThrowExceptionWhenSetEmailUnreasonable(String email) {
        Exception exception = assertThrows(Exception.class, () -> target.setEmail(email));
        assertEquals("email is not available", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setEmail function")
    @Test
    public void shouldThrowExceptionWhenSetEmailToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setEmail(null));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exceptions when pass a null password to setPassword function")
    @Test
    public void shouldThrowExceptionWhenSetPasswordToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setPassword(null));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should return true when two users have the same email")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameEmail() throws Exception {
        String email = "abc@example.com";
        target.setEmail(email);
        User anotherUser = new User();
        anotherUser.setEmail(email);
        assertTrue(target.equals(anotherUser));
    }


    @DisplayName("should return false when two users have different emails")
    @Test
    public void shouldReturnFalseWhenUsersHaveDifferentEmails() throws Exception {
        target.setEmail("abc@example.com");
        User anotherUser = new User();
        anotherUser.setEmail("def@example.com");
        assertFalse(target.equals(anotherUser));

    }

    @DisplayName("should throw exception when pass a empty first name to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  " })
    public void shouldThrowExceptionWhenSetFirstNameToEmpty(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> target.setFirstName(firstName));
        assertEquals("first name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setFirstName function")
    @Test
    public void shouldThrowExceptionWhenSetFirstNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setFirstName(null));
        assertEquals("first name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty last name to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  " })
    public void shouldThrowExceptionWhenSetLastNameToEmpty(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> target.setLastName(firstName));
        assertEquals("last name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setLastName function")
    @Test
    public void shouldThrowExceptionWhenSetLastNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setLastName(null));
        assertEquals("last name cannot be null or empty", exception.getMessage());
    }
}