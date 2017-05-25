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
        posX = a / Math.sqrt(2);
        posY = a / Math.sqrt(2);
        double vel = Math.sqrt(G*1.99e30/a);
        velocityX = vel / Math.sqrt(2);
        velocityY = - vel /Math.sqrt(2);
        label = s;
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

    public void updateAccelFrom(Body b){
        double dX = b.getPosX() - posX;
        double dY = b.getPosY() - posY;
        double dist = Math.sqrt(dX*dX + dY*dY);
        //double dist = this.axis;

        double totalAccel = G * b.getMass() / Math.pow(dist, 2);

        this.accelX = (dX / dist) * totalAccel;
        this.accelY = (dY / dist) * totalAccel;
        if(this.label.equals("Mercury")) {
            System.out.println("xaccel: " + this.accelX);
            System.out.println("yaccel: " + this.accelY);
        }
    }

    public void move(int t){
        //permit arbitrarily large intervals
        int interval = Math.min(t, 100);
        for (int i = 0; i < t/interval; i++) {
            moveHelper(interval);
        }
        moveHelper(t%interval);
    }

    void moveHelper(int t){
        this.velocityX += this.accelX * t;
        this.velocityY += this.accelY * t;

        this.posX += this.velocityX * t;
        this.posY += this.velocityY * t;

        //TODO: update acceleration values
    }
}
