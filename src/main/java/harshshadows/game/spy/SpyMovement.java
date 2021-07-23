package harshshadows.game.spy;

public enum SpyMovement {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_RIGHT,
    UP_LEFT,
    DOWN_RIGHT,
    DOWN_LEFT;

    public SpyMovement opposite(){
        switch (this){
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP_RIGHT:
                return DOWN_LEFT;
            case UP_LEFT:
                return DOWN_RIGHT;
            case DOWN_RIGHT:
                return UP_LEFT;
            case DOWN_LEFT:
                return UP_RIGHT;
        }
        return null;
    }
}
