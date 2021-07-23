package harshshadows;

import harshshadows.game.Phase;
import harshshadows.game.PhaseStep;
import harshshadows.game.discovery.DiscoveryCard;
import harshshadows.game.discovery.Evidence;
import harshshadows.game.location.Location;
import harshshadows.game.spy.SpyMovement;
import harshshadows.game.spy.SpyMovementCard;
import harshshadows.view.View;

import java.awt.*;

public class Controller {
    private Model model;
    private View view;

    private boolean playerClickedDone = false;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    public void run(){
        while (model.getGame() != null && model.getGame().getPhase() != Phase.GAME_OVER){
            switch (model.getGame().getPhase()){
                case SETUP: {
                    switch (model.getGame().getPhaseStep()){
                        case START_PHASE:
                            model.getGame().setPhaseStep(PhaseStep.END_PHASE);
                            break;
                        case END_PHASE:
                            model.getGame().setup();
                            model.getGame().setPhase(Phase.PLAY);
                            break;
                    }
                    break;
                }
                case PLAY: {
                    switch (model.getGame().getPhaseStep()){
                        case START_PHASE:
                            model.getGame().setPhaseStep(PhaseStep.PLAY_AGENT_MOVE);
                            break;
                        case PLAY_AGENT_MOVE:
                            // Player must click on adjacent location
                            return;
                        case PLAY_AGENT_DRAW_DISCOVERY_CARD: {
                            Location location = model.getGame().getAgentLocation();
                            if (!location.getDiscoveryCards().isEmpty()) {
                                DiscoveryCard card = location.getDiscoveryCards().remove(0);
                                if (card.getType() == Evidence.BOMB){
                                    // TODO Agent must discard card or use diffuse bomb kit
                                    // TODO If used diffuse bomb kit, continue with turn
                                    // TODO If discarded a card, skip rest of Agent turn
                                    model.getGame().setPhaseStep(PhaseStep.PLAY_SPY_MOVE);
                                    break;
                                }
                                else {
                                    model.getGame().getDiscoveryCollection().add(card);
                                }
                            }
                            model.getGame().setPhaseStep(PhaseStep.PLAY_AGENT_ACTIONS);
                            break;
                        }
                        case PLAY_AGENT_ACTIONS:
                            if (playerClickedDone)
                                model.getGame().setPhaseStep(PhaseStep.PLAY_SPY_MOVE);
                            else
                                return;
                            break;
                        case PLAY_SPY_MOVE:
                            moveSpy();
                            model.getGame().setPhaseStep(PhaseStep.END_PHASE);
                            break;
                        case END_PHASE:
                            // TODO Check if spy moved onto same location as Agent.  If so, Agent must discard Discovery card to lose game.
                            model.getGame().setPhaseStep(PhaseStep.START_PHASE);
                            break;
                    }
                    break;
                }
            }
        }
    }

    public boolean checkMakeAccusation(){
        Location agentLocation = model.getGame().getAgentLocation();
        Location spyLocation = model.getGame().getSpyLocation();
        // If Agent at same location as Spy
        if (agentLocation != spyLocation)
            return false;
        // and Spy has been bugged
        // and Agent has all three Evidence
        // and does NOT have the Red Herring
        // then Agent wins

        // If Agent does have red herring, player loses
    }

    public void moveSpy(){
        SpyMovementCard card = model.getGame().getSpyMovementDeck().draw();
        SpyMovement movement = card.getPrimaryMovement();
        if (!canSpyMoveInDirection(movement)){
            movement = card.getSecondaryMovement();
            if (!canSpyMoveInDirection(movement)){
                movement = card.getPrimaryMovement().opposite();
                if (!canSpyMoveInDirection(movement)){
                    movement = card.getSecondaryMovement().opposite();
                }
            }
        }

        Point newLocation = getLocationInDirection(movement);
        model.getGame().getSpyLocationPoint().x = newLocation.x;
        model.getGame().getSpyLocationPoint().y = newLocation.y;
        if (!model.getGame().isOnTheRun()) {
            DiscoveryCard discoveryCard = model.getGame().getDiscoveryDeck().draw();
            if (discoveryCard == null) {
                model.getGame().setOnTheRun(true);
            }
            else
                model.getGame().getSpyLocation().getDiscoveryCards().add(discoveryCard);
        }

        if (model.getGame().isOnTheRun() && model.getGame().getSpyLocation().getDiscoveryCards().isEmpty()){
            model.getGame().adjOnTheRunCountdown(-1);
        }

        if (model.getGame().getOnTheRunCountdown() == 0){
            model.getGame().setPhase(Phase.GAME_OVER);
        }
    }

    public boolean canSpyMoveInDirection(SpyMovement spyMovement){
        return getLocationInDirection(spyMovement) != null;
    }

    public Point getLocationInDirection(SpyMovement spyMovement){
        int x = model.getGame().getSpyLocationPoint().x;
        int y = model.getGame().getSpyLocationPoint().y;
        switch (spyMovement){
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case UP_RIGHT:
                x += 1;
                y -= 1;
                break;
            case UP_LEFT:
                x -= 1;
                y -= 1;
                break;
            case DOWN_RIGHT:
                x += 1;
                y += 1;
                break;
            case DOWN_LEFT:
                x -= 1;
                y += 1;
                break;
        }
        return model.getGame().getLocation(y, x) != null? new Point(x, y): null;
    }
}
