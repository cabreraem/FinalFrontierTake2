/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Body {

    private final static double G = 6.67e-11;

    private Body orbit;

    private double mass;
    private double radius;
    private double posX;
    private double posY;
    private double velocityX;
    private double velocityY;
    private double accelX;
    private double accelY;
    private double period;
    private double axis;
    private String label;

    //Constructors
    public Body(String s, double m, double r, double a, double v){
        mass = m;
        radius = r;
        axis = a;
        velocityX = 0;
        velocityY = v;
        label = s;

        posX = a;
        posY = 0;
    }

    public Body(double m, double r, double x, double y, double vX, double vY, double p, double a) {
        mass = m;
        radius = r;
        posX = x;
        posY = y;
        velocityX = vX;
        velocityY = vY;
        period = p;
        axis = a;
    }

    public Body(double m, double r, double x, double y, double vX, double vY, Body o, double a) {
        mass = m;
        radius = r;
        posX = x;
        posY = y;
        velocityX = vX;
        velocityY = vY;
        orbit = o;
        axis = a;
        period = 2 * Math.PI * Math.sqrt(Math.pow(a,3)/(G*o.getMass()));

    }

    public Body(double m, double r, double vx, double vy, double a, Body o){
        mass = m;
        radius = r;
        posY = o.getPosY();
        posX = o.getPosX() + a;
        velocityX = vx;
        velocityY = vy;

    }

    //Getters
    public double getMass() {
        return mass;
    }

    public String getName(){return label;}

    public double getRadius() {
        return radius;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getAxis() {
        return axis;
    }

    //Setters
    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    //Methods
    public double getGravity(double d){

        double g;

        if(d==0)
            return 0.0;

        if(d<0)
            g = -1;
        else
            g = 1;

        g *= G*mass / Math.pow(d,2);

        return g;
    }

    public void force(Body b){
        double dX = b.getPosX() - posX;
        double dY = b.getPosY() - posY;

        accelX+= b.getGravity(dX);
        accelY+= b.getGravity(dY);
    }

    public void move(int t){
        velocityX += accelX * t;
        velocityY += accelY *t;

        posX += velocityX * t;
        posY += velocityY * t;

        accelX = 0;
        accelY = 0;
    }

}
