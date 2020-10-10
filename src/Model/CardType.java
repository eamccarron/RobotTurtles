package Model;

//An enum to hold card types
public enum CardType {
    STEP_FORWARD,
    TURN_LEFT,
    TURN_RIGHT,
    BUG,
    LASER;

    //Return a nicely formatted string, mostly for debugging and testing purposes
    @Override
    public String toString() {
        switch(this){
            case STEP_FORWARD:
                return "Step Forward";
            case TURN_LEFT:
                return "Turn Left";
            case TURN_RIGHT:
                return "Turn Right";
            case BUG:
                return "Bug";
            case LASER:
                return "Laser";
            default:
                //return standard Object toString() by default, but this default can never actually be reached
                return super.toString();
        }
    }
}
