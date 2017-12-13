package Model;
import java.util.Vector;

public abstract class Pieces {
    private int color;
    protected String name;

    /**
     * Constructor
     */
    public Pieces(int color){
        this.color = color;
    }

    /**
     *
     * @return
     */
    public int getColor(){
        return this.color;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }

     public boolean notOffBoard(int height, int width, int coordinatesX, int coordinatesY){
        return (coordinatesX<width)&&(coordinatesX>=0)&&(coordinatesY<height)&&(coordinatesY>=0);
    }

    /** This method determine if the pieces can move from current position to destination without concerning putting
     * the king in check. (Board will check) This method assume that the coordinates of current position and destination
     * are not off bound.
     *
     * @param currentX
     * @param currentY
     * @param destX
     * @param destY
     * @return
     */

    public abstract boolean canMoveTo(int currentX,int currentY,int destX,int destY) ;
    


    /**
     * This function return the coo rdinates of every cube which is part of the path,except the current cube.
     * and destination cube. Also this method assume that the piece move from current position to destination is
     * valid and the coordinates of current position and destination are not off bound.
      * @param currentX
     * @param currentY
     * @param destX
     * @param destY
     * @return
     */
    public abstract Vector<Coordinates> generateValidPath(int currentX,int currentY,int destX,int destY);

    public abstract Vector<Coordinates> possibleMoves(int currentX, int currentY);
}