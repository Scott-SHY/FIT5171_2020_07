package rockets.ingestion;

import com.google.common.collect.Sets;
import rockets.model.Entity;
import rockets.model.Rocket;

import java.util.Calendar;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.*;

import java.util.regex.*;

public class LaunchServiceProvider extends Entity {
    private String name;

    private int yearFounded;

    private String country;

    private String headquarters;

    private Set<Rocket> rockets;

    public LaunchServiceProvider(String name, int yearFounded, String country){
        notBlank(name);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        if(yearFounded > currentYear || yearFounded < 0)
            throw new IllegalArgumentException("The selected year is not valid, maybe negative or beyond current year");
        notBlank(country);

        this.name = name;
        this.yearFounded = yearFounded;
        this.country = country;

        rockets = Sets.newLinkedHashSet();
    }

    public String getName() {
        return name;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public Set<Rocket> getRockets() {
        return rockets;
    }

    public void setRockets(Set<Rocket> rockets) {
        notNull((rockets), "Rockets cannot be null");
        this.rockets = rockets;
    }

    public void setName(String name) {
        notBlank(name, "Rocket name cannot be empty or null");
        this.name = name;
    }

    public void setYearFounded(int yearFounded){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        if(yearFounded < 0 || yearFounded > currentYear) {
            throw new IndexOutOfBoundsException("The selected year is not valid, maybe negative or beyond current year");
        }
        this.yearFounded = yearFounded;
    }

    public void setCountry(String country) {
        notBlank((country), "Country cannot be empty or null");
        this.country = country;
    }

    public void setHeadquarters(String headquarters) {
        notBlank((headquarters), "Headquarters cannot be empty or null");
        this.headquarters = headquarters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaunchServiceProvider that = (LaunchServiceProvider) o;
        return yearFounded == that.yearFounded &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, yearFounded, country);
    }
}
