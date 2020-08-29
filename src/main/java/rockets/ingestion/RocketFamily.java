package rockets.ingestion;

import rockets.model.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.Validate.matchesPattern;
import static org.apache.commons.lang3.Validate.notBlank;

public class RocketFamily extends Entity {

    private String family;
    private Set<String> variations;

    public RocketFamily(String family) {
        notBlank(family,"family name cannot be null or empty");
        this.family = family;
        this.variations = new HashSet<>();
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        notBlank(family,"family name cannot be null or empty");
        this.family = family;
    }

    public Set<String> getVariations() {
        return variations;
    }

    public void setVariations(Set<String> variations) {
        this.variations = variations;
    }

    public void addVariation(String variation){
        notBlank(variation,"new added variation cannot be null or empty");
        String regex = "^"+this.family+"( [a-zA-Z0-9]+)+$";
        matchesPattern(variation,regex,"new added variation does not belong to this family");
        this.variations.add(variation);
    }
}
