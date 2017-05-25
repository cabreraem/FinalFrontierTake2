import java.util.ArrayList;

/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Universe {

    private double radius; //radius of studied universe to determine scaling
    private ArrayList<Body> bodies; //bodies within Universe

    public Universe(double r) {
        radius = r;
        bodies = new ArrayList<>();
    }

    public Universe(double r, ArrayList b){
        radius = r;
        bodies = b;
    }

    //Getters
    public double getRadius() {
        return radius;
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }

    //Methods
    public void addBody(Body b){
        bodies.add(b);
    }

    public void timeLapse(int t){

        //iterate through every body in system and calculate the forces on it based on every other body
        for(Body b: bodies){
            for(Body other:bodies){
                if(!b.equals(other)){
                    b.updateAccelFrom(other);
                }
            }
        }

        //make every body move
        for(Body b: bodies)
            b.move(t);
    }
}
