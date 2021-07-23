package harshshadows.game.location;

import harshshadows.game.discovery.DiscoveryCard;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private LocationName name;
    private boolean activated = false;
    private boolean trackable = true;

    private List<DiscoveryCard> discoveryCards = new ArrayList<>();

    public Location(LocationName name){
        this(name, true);
    }

    public Location(LocationName name, boolean trackable){
        this.name = name;
        this.trackable = trackable;
    }

    public void reset(){
        activated = false;
    }

    public LocationName getName() {
        return name;
    }

    public void setName(LocationName name) {
        this.name = name;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isTrackable() {
        return trackable;
    }

    public void setTrackable(boolean trackable) {
        this.trackable = trackable;
    }

    public List<DiscoveryCard> getDiscoveryCards() {
        return discoveryCards;
    }
}
