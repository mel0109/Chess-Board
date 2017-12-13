package Model;

public class Coordinates {
    private int x;
    private int y;

    /**
     *
     * @param xValue
     * @param yValue
     */
    public Coordinates(int xValue, int yValue){
        this.x = xValue;
        this.y = yValue;
    }

    /**
     * Return left value of pairs
     * @return
     */
    public int getX(){
        return this.x;
    }

    /**
     * Return right value of pairs
     * @return
     */
    public int getY(){
        return this.y;               
    }

    public boolean compair(Coordinates cord){
        return this.getX() == cord.getX() && this.getY() == cord.getY();
    }

    public void printPairs(){
        System.out.print(getX());
        System.out.print(" ");
        System.out.println(getY());
    }
}
