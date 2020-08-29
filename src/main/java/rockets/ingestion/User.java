package rockets.ingestion;

import rockets.model.Entity;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.matchesPattern;
import static org.apache.commons.lang3.Validate.notBlank;

public class User extends Entity {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        //validate that the FirstName can not be null or empty
        notBlank(firstName, "first name cannot be null or empty");
        //validate that the FirstName can not contain non-alphabetic characters
        String firstNameRegex = "^[a-zA-Z]+$";
        matchesPattern(firstName,firstNameRegex,"first name can not contain non-alphabetic characters");
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {
        //validate that the LastName can not be null or empty
        notBlank(lastName, "last name cannot be null or empty");
        //validate that the LastName can not contain non-alphabetic characters
        String lastNameRegex = "^[a-zA-Z]+$";
        matchesPattern(lastName,lastNameRegex,"last name can not contain non-alphabetic characters");
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //validate that the email can not be null or empty
        notBlank(email, "email cannot be null or empty");
        //validate that the email address is valid
        String emailRegex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        matchesPattern(email,emailRegex,"email is invalid");
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //validate that the password can not be null or empty
        notBlank(password, "password cannot be null or empty");
        //validate that the password is valid
        String passwordRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,18}$";
        matchesPattern(password,passwordRegex,"password must contain numbers and letters, and the length is between 6 and 18");
        this.password = password;
    }

    // match the given password against user's password and return the result
    public boolean isPasswordMatch(String password) {
        return this.password.equals(password.trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
