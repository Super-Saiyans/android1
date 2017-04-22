package minor.hackathon123;

/**
 * Created by Gaurang on 22-04-2017.
 */

class LifeStyle{
    private int eating;
    private int drinking;
    private	int toileting;
    private int showering;
    private int leaving;
    private int sleeping;
    private double weight;

    LifeStyle(int eating, int drinking, int toileting, int showering, int leaving, int sleeping, double weight){
        this.eating = eating;
        this.drinking = drinking;
        this.toileting = toileting;
        this.showering = showering;
        this.leaving = leaving;
        this.sleeping = sleeping;
        this.weight = weight;
    }

    public void setEating(int eating){
        this.eating = eating;
    }

    public void setDrinking(int drinking){
        this.drinking = drinking;
    }

    public void setToileting(int toileting){
        this.toileting = toileting;
    }

    public void setShowering(int showering){
        this.showering = showering;
    }

    public void setLeaving(int leaving){
        this.leaving = leaving;
    }

    public void setSleeping(int sleeping){
        this.sleeping = sleeping;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public int getEating(){
        return this.eating;
    }

    public int getDrinking(){
        return this.drinking;
    }

    public int getToileting(){
        return this.toileting;
    }

    public int getShowering(){
        return this.showering;
    }

    public int getLeaving(){
        return this.leaving;
    }

    public int getSleeping(){
        return this.sleeping;
    }

    public double getWeight(){
        return weight;
    }
}