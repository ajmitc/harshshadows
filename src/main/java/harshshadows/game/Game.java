package harshshadows.game;

import harshshadows.game.discovery.DiscoveryCard;
import harshshadows.game.discovery.Evidence;
import harshshadows.game.location.Location;
import harshshadows.game.location.LocationName;
import harshshadows.game.spy.SpyMovement;
import harshshadows.game.spy.SpyMovementCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private Phase phase = Phase.SETUP;
    private PhaseStep phaseStep = PhaseStep.START_PHASE;

    private Location[][] locationGrid = new Location[3][3];
    private Point agentLocation = new Point(2, 2);
    private Point spyLocation = new Point(0, 0);
    private int onTheRunCountdown = 4;
    private boolean onTheRun = false;
    private boolean defusingKitAvailable = true;

    private LocationName trackingBugLocation = null;
    private boolean trackingBugPlacedOnSpy = false;
    private int trackingBugUsesLeft = 2;

    private Deck<DiscoveryCard> discoveryDeck = new Deck<>();
    private Deck<SpyMovementCard> spyMovementDeck = new Deck<>();

    private List<Location> locations = new ArrayList<>();

    private List<DiscoveryCard> discoveryCollection = new ArrayList<>();

    public Game(){
        for (LocationName locationName: LocationName.values())
            locations.add(new Location(locationName));

        for (Evidence evidence: Evidence.values())
            discoveryDeck.add(new DiscoveryCard(evidence));
        // add 8 more Clue cards
        for (int i = 0; i < 8; ++i)
            discoveryDeck.add(new DiscoveryCard(Evidence.CLUE));
        // add 3 more Bomb cards
        for (int i = 0; i < 3; ++i)
            discoveryDeck.add(new DiscoveryCard(Evidence.BOMB));
        discoveryDeck.setShuffleDiscardIntoDeckWhenEmpty(false);

        spyMovementDeck.add(new SpyMovementCard(SpyMovement.DOWN, SpyMovement.RIGHT));
        spyMovementDeck.add(new SpyMovementCard(SpyMovement.UP_LEFT, SpyMovement.DOWN_LEFT));
        spyMovementDeck.add(new SpyMovementCard(SpyMovement.LEFT, SpyMovement.UP_RIGHT));
        spyMovementDeck.add(new SpyMovementCard(SpyMovement.RIGHT, SpyMovement.DOWN));
        spyMovementDeck.add(new SpyMovementCard(SpyMovement.UP_RIGHT, SpyMovement.UP));
        spyMovementDeck.add(new SpyMovementCard(SpyMovement.DOWN, SpyMovement.UP_LEFT));
        spyMovementDeck.add(new SpyMovementCard(SpyMovement.DOWN_RIGHT, SpyMovement.LEFT));
        spyMovementDeck.add(new SpyMovementCard(SpyMovement.UP_RIGHT, SpyMovement.DOWN_RIGHT));
    }

    public void setup(){
        for (Location location: locations){
            location.reset();
        }
        Collections.shuffle(locations);
        int i = 0;
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                locationGrid[row][col] = locations.get(i);
            }
        }
        locationGrid[1][1].setActivated(true);

        // Add a Discovery Card to each location
        discoveryDeck.shuffleDiscardIntoDeck();
        List<DiscoveryCard> locationCards = new ArrayList<>();
        boolean hasBomb = false;
        while (true){
            DiscoveryCard card = discoveryDeck.draw();
            if (card.getType() == Evidence.BOMB){
                if (!hasBomb){
                    locationCards.add(card);
                    hasBomb = true;
                }
                else
                    discoveryDeck.discard(card);
            }
            else {
                locationCards.add(card);
            }
            if (locationCards.size() == 9)
                break;
        }
        Collections.shuffle(locationCards);

        // Add 1 location card to each location
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                locationGrid[row][col].getDiscoveryCards().add(locationCards.remove(0));
            }
        }

        // Shuffle spy movement cards
        spyMovementDeck.shuffleDiscardIntoDeck();

        trackingBugLocation = null;
        trackingBugPlacedOnSpy = false;
        trackingBugUsesLeft = 2;
        defusingKitAvailable = true;
        onTheRunCountdown = 4;

        agentLocation = new Point(2, 2);
        spyLocation = new Point(0, 0);
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
        this.phaseStep = PhaseStep.START_PHASE;
    }

    public PhaseStep getPhaseStep() {
        return phaseStep;
    }

    public void setPhaseStep(PhaseStep phaseStep) {
        this.phaseStep = phaseStep;
    }

    public Location getLocation(int row, int col) {
        if (row < 0 || col < 0 || row > 2 || col > 2)
            return null;
        return locationGrid[row][col];
    }

    public Point getAgentLocationPoint() {
        return agentLocation;
    }

    public Location getAgentLocation(){
        return getLocation(agentLocation.y, agentLocation.x);
    }

    public Point getSpyLocationPoint() {
        return spyLocation;
    }

    public Location getSpyLocation(){
        return getLocation(spyLocation.y, spyLocation.x);
    }

    public int getOnTheRunCountdown() {
        return onTheRunCountdown;
    }

    public void adjOnTheRunCountdown(int amount){
        onTheRunCountdown += amount;
    }

    public boolean isOnTheRun() {
        return onTheRun;
    }

    public void setOnTheRun(boolean onTheRun) {
        this.onTheRun = onTheRun;
    }

    public boolean isDefusingKitAvailable() {
        return defusingKitAvailable;
    }

    public void setDefusingKitAvailable(boolean defusingKitAvailable) {
        this.defusingKitAvailable = defusingKitAvailable;
    }

    public LocationName getTrackingBugLocation() {
        return trackingBugLocation;
    }

    public void setTrackingBugLocation(LocationName trackingBugLocation) {
        this.trackingBugLocation = trackingBugLocation;
    }

    public boolean isTrackingBugPlacedOnSpy() {
        return trackingBugPlacedOnSpy;
    }

    public void setTrackingBugPlacedOnSpy(boolean trackingBugPlacedOnSpy) {
        this.trackingBugPlacedOnSpy = trackingBugPlacedOnSpy;
    }

    public int getTrackingBugUsesLeft() {
        return trackingBugUsesLeft;
    }

    public void adjTrackingBugUsesLeft(int amount){
        trackingBugUsesLeft += amount;
    }

    public Deck<DiscoveryCard> getDiscoveryDeck() {
        return discoveryDeck;
    }

    public Deck<SpyMovementCard> getSpyMovementDeck() {
        return spyMovementDeck;
    }

    public List<DiscoveryCard> getDiscoveryCollection() {
        return discoveryCollection;
    }
}
