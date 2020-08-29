package rockets.ingestion;

import rockets.model.Entity;

import static org.apache.commons.lang3.Validate.*;

public class Payload extends Entity {

    private String name;
    private String type;
    private Double weight;
    private String mission;
    private String description;

    public Payload(String name, String type, String mission) {
        notBlank(name, "payload name cannot be empty or null");
        notBlank(name, "payload type cannot be empty or null");
        notBlank(mission, "payload mission cannot be empty or null");
        this.name = name;
        this.type = type;
        this.mission = mission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        notBlank(name, "payload name cannot be empty or null");
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        notBlank(type, "payload type cannot be empty or null");
        this.type = type;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        if (weight <= 0)
            throw new IndexOutOfBoundsException("payload weight cannot be a negative number or zero");
        this.weight =weight;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        notBlank(mission, "payload mission cannot be empty or null");
        this.mission = mission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
