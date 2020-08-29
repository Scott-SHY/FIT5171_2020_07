package rockets.ingestion;

import org.apache.commons.lang3.Validate;
import rockets.model.Entity;
import rockets.model.LaunchServiceProvider;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.*;

public class Rocket extends Entity {

    private String name;

    private String country;

    private rockets.model.LaunchServiceProvider manufacturer;

    private String massToLEO;

    private String massToGTO;

    private String massToOther;

    /**
     * All parameters shouldn't be null.
     *
     * @param name
     * @param country
     * @param manufacturer
     */
    public Rocket(String name, String country, rockets.model.LaunchServiceProvider manufacturer) {
        notBlank(name, "Rocket name cannot be null or empty");
        notBlank(country, "Rocket country cannot be null or empty");
        notNull(manufacturer, "Rocket manufacturer cannot be null");
        this.name = name;
        this.country = country;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public LaunchServiceProvider getManufacturer() {
        return manufacturer;
    }

    public String getMassToLEO() {
        return massToLEO;
    }

    public String getMassToGTO() {
        return massToGTO;
    }

    public String getMassToOther() {
        return massToOther;
    }

    public void setMassToLEO(String massToLEO) {
        isMassValid(massToLEO);
        this.massToLEO = massToLEO;
    }

    public void setMassToGTO(String massToGTO) {
        isMassValid(massToGTO);
        this.massToGTO = massToGTO;
    }

    public void setMassToOther(String massToOther) {
        isMassValid(massToOther);
        this.massToOther = massToOther;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;
        return Objects.equals(name, rocket.name) &&
                Objects.equals(country, rocket.country) &&
                Objects.equals(manufacturer, rocket.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, manufacturer);
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", massToLEO='" + massToLEO + '\'' +
                ", massToGTO='" + massToGTO + '\'' +
                ", massToOther='" + massToOther + '\'' +
                '}';
    }

    public static void isMassValid(String str){
        notNull(str, "Mass cannot be null");
        try {
            Integer number = Integer.parseInt(str);
        }   catch (NumberFormatException e) {
            isTrue(false,"Mass should be an Integer greater than 0 and less than 150000");
        }
        inclusiveBetween(1, 149999, Integer.parseInt(str), "Mass should be an Integer greater than 0 and less than 150000");
    }
}
