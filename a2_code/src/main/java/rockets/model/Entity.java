package rockets.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import static org.apache.commons.lang3.Validate.notNull;

public abstract class Entity {
    @Id
    @GeneratedValue
    protected Long id;

    @Property(name = "wikilink")
    private String wikilink;

    public Entity() {
    }

    public String getWikilink() {
        return wikilink;
    }

    public void setWikilink(String wikiLink) {
        notNull(wikiLink);
        this.wikilink = wikiLink;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
