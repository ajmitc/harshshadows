package harshshadows.game.discovery;

public class DiscoveryCard {
    private Evidence type;

    public DiscoveryCard(Evidence type){
        this.type = type;
    }

    public Evidence getType() {
        return type;
    }
}
