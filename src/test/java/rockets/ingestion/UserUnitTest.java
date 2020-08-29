package rockets.ingestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rockets.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {
    private rockets.model.User target;

    @BeforeEach
    public void setUp() {
        target = new rockets.model.User();
    }

    @DisplayName("should throw exceptions when pass an empty first name/last name")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmpty(String name) {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(name));
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> target.setLastName(name));
        assertEquals("first name cannot be null or empty", exception1.getMessage());
        assertEquals("last name cannot be null or empty", exception2.getMessage());
    }

    @DisplayName("should throw exceptions when pass null to setFirstName/setLastName function")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull() {
        NullPointerException exception1 = assertThrows(NullPointerException.class, () -> target.setFirstName(null));
        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> target.setLastName(null));
        assertEquals("first name cannot be null or empty", exception1.getMessage());
        assertEquals("last name cannot be null or empty", exception2.getMessage());
    }

//    @DisplayName("should throw exception when name is invalid")
//    @ParameterizedTest
//    @ValueSource(strings = {"white123", "white@#$", "wh ite"})
//    public void shouldThrowExceptionWhenNameIsInvalid(String name) {
//        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(name));
//        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> target.setLastName(name));
//        assertEquals("first name can not contain non-alphabetic characters", exception1.getMessage());
//        assertEquals("last name can not contain non-alphabetic characters", exception2.getMessage());
//    }

    @DisplayName("should throw exception when pass an empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEmailToEmpty(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setEmail function")
    @Test
    public void shouldThrowExceptionWhenSetEmailToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setEmail(null));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

//    @DisplayName("should throw exception when email is invalid")
//    @ParameterizedTest
//    @ValueSource(strings = {"abc", "abc@monash", "abc@student.monash@edu"})
//    public void shouldThrowExceptionWhenEmailIsInvalid(String email) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
//        assertEquals("email is invalid", exception.getMessage());
//    }

    @DisplayName("should throw exception when pass an empty password to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEPasswordToEmpty(String password) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an null password to setPassword function")
    @Test
    public void shouldThrowExceptionWhenSetPasswordToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setPassword(null));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

//    @DisplayName("should throw exception when password is invalid")
//    @ParameterizedTest
//    @ValueSource(strings = {"abcdef", "123456", "abc12", "123456789qwertyuiop"})
//    public void shouldThrowExceptionWhenPasswordIsInvalid(String password) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
//        assertEquals("password must contain numbers and letters, and the length is between 6 and 18", exception.getMessage());
//    }

    @DisplayName("should return true when two users have the same email")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameEmail() throws Exception {
        String email = "abc@example.com";
        target.setEmail(email);
        rockets.model.User anotherUser = new rockets.model.User();
        anotherUser.setEmail(email);
        assertTrue(target.equals(anotherUser));
    }

    @DisplayName("should return false when two users have different emails")
    @Test
    public void shouldReturnFalseWhenUsersHaveDifferentEmails() throws Exception {
        target.setEmail("abc@example.com");
        rockets.model.User anotherUser = new User();
        anotherUser.setEmail("def@example.com");
        assertFalse(target.equals(anotherUser));
    }
}