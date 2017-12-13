package Model;
import java.util.Vector;

public class Cross extends Pieces {
    /**
     * Constructor
     *
     * @param color
     */
    public Cross(int color) {
        super(color);
        if(color == 1){
            //Green Cross
            this.name="GC";
        }else{
            //Red Cross
            this.name="RC";
        }
    }

    /**
     *This method determine if the pieces can move from current position to destination without concerning putting
     * the king in check. (Board will check) This method assume that the coordinates of current position and destination
     * are not off bound.
     * @param currentX
     * @param currentY
     * @param destX
     * @param destY
     * @return
     */
    @Override
    public boolean canMoveTo(int currentX,int currentY,int destX,int destY){
        // y=x+b
        double b1=currentY-currentX;
        // y=-x+b
        double b2=currentY+currentX;

        boolean validPositive= (destY==destX+b1);

        boolean validNegative= (destY == -destX+b2);

        boolean notCurrentEqualsDestination =!((currentX==destX) && (currentY==destY));

        return notCurrentEqualsDestination && (validPositive || validNegative);
    }

    /**
     *  This function return the coordinates of every cube which is part of the path of Bishop. This method
     * assume that there is a valid move of Bishop from current to destination.If the vector returned is empty,
     * then it means that there is no other piece get in the way of a valid path
     * @param currentX
     * @param currentY
     * @param destX
     * @param destY
     * @return
     */
    @Override
    public Vector<Coordinates> generateValidPath(int currentX, int currentY, int destX, int destY){
        Vector<Coordinates> list = new Vector(0);
        int numberOfStep=Math.abs(destY-currentY);
        if(currentX < destX && currentY < destY) {
            for (int i = 1; i < numberOfStep; i++) {
                Coordinates pair = new Coordinates(currentX + i, currentY + i);
                list.add(pair);
            }
        }else if(currentX < destX && currentY > destY) {
            for (int i = 1; i < numberOfStep; i++) {
                Coordinates pair = new Coordinates(currentX + i, currentY - i);
                list.add(pair);
            }
        }else if(currentX > destX && currentY < destY) {
            for (int i = 1; i < numberOfStep; i++) {
                Coordinates pair = new Coordinates(currentX - i, currentY + i);
                list.add(pair);
            }
        }else{
            for (int i = 1; i < numberOfStep; i++) {
                Coordinates pair = new Coordinates(currentX - i, currentY - i);
                list.add(pair);
            }
        }
        return list;
    }
    
@Override
    public Vector<Coordinates> possibleMoves(int currentX, int currentY)
    {
        Vector<Coordinates> list = new Vector(0);
        int testX=currentX;
        int testY=currentY;
        while(notOffBoard(8,5,testX,testY))
        {
            testX = testX+1;
            testY = testY+1;
            Coordinates possible = new Coordinates(testX,testY);
            if(notOffBoard(8,5,testX,testY))
            {
                list.add(possible);
            }
            else{}
        }
        testX=currentX;
        testY=currentY;
        while(notOffBoard(8,5,testX,testY))
        {
            testX = testX-1;
            testY = testY+1;
            Coordinates possible = new Coordinates(testX,testY);
            if(notOffBoard(8,5,testX,testY))
            {
                list.add(possible);
            }
            else{}
        }
        testX=currentX;
        testY=currentY;
        while(notOffBoard(8,5,testX,testY))
        {
            testX = testX-1;
            testY = testY-1;
            Coordinates possible = new Coordinates(testX,testY);
            if(notOffBoard(8,5,testX,testY))
            {
                list.add(possible);
            }
            else{}
        }
        testX=currentX;
        testY=currentY;
        while(notOffBoard(8,5,testX,testY))
        {
            testX = testX+1;
            testY = testY-1;
            Coordinates possible = new Coordinates(testX,testY);
            if(notOffBoard(8,5,testX,testY))
            {
                list.add(possible);
            }
            else{}
        }
        return list;
    }
}
