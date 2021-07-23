package harshshadows.game.spy;

public class SpyMovementCard {
    private SpyMovement primaryMovement;
    private SpyMovement secondaryMovement;

    public SpyMovementCard(SpyMovement primaryMovement, SpyMovement secondaryMovement){
        this.primaryMovement = primaryMovement;
        this.secondaryMovement = secondaryMovement;
    }

    public SpyMovement getPrimaryMovement() {
        return primaryMovement;
    }

    public SpyMovement getSecondaryMovement() {
        return secondaryMovement;
    }
}
