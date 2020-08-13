package rockets.model;

import com.google.common.collect.Sets;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import java.util.Objects;
import java.util.Set;

public class LaunchServiceProvider extends Entity {

    public void setName(String name) {
        notBlank(name, "name can not be null or empty");
        this.name = name;
    }

    private String name;

    public void setYearFounded(int yearFounded) {
        notNull(yearFounded);
        this.yearFounded = yearFounded;
    }

    private int yearFounded;

    public void setCountry(String country) {
        notBlank(country, "country can not be null or empty");
        this.country = country;
    }

    private String country;

    private String headquarters;

    private Set<Rocket> rockets;

    public LaunchServiceProvider(String name, int yearFounded, String country) {
        notNull(name);
        notNull(yearFounded);
        notNull(country);
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

    public void setHeadquarters(String headquarters) {
        notBlank(headquarters, "headquarters can not be null or empty");
        this.headquarters = headquarters;
    }

    public void setRockets(Set<Rocket> rockets) {
        this.rockets = rockets;
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

    @Override
    public String toString() {
        return "LaunchServiceProvider{" +
                ", name" + name + '\'' +
                ", yearFound" + '\'' +
                ", country" + '\'' +
                ", headquarters" + '\'' +
                "}";
    }
}