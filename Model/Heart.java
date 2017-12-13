package Model;
import java.util.Vector;

public class Heart extends Pieces {
    public Heart(int color) {
            /**
         * Constructor
         *
         * @param color
         */
        super(color);
        if(color == 1){
            //Green Heart
            this.name="GH";
        }else{
            //Red Heart
            this.name="RH";
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
        //System.out.println("Inside pawn");
        boolean forward;
        boolean backward;
        boolean horizontal;
        boolean diagonal;

        if(this.getColor() == 1 ){
           // System.out.println("Green Star");
           //Move 1 tile forward
           forward = ((currentX == destX) && (currentY - 1 == destY)); 
           //Move 1 tile backward
           backward= ((currentX == destX) && (currentY + 1 == destY));
           //Move 1 tile horizontally
           horizontal= ((currentY == destY) && (currentX + 1 == destX || currentX - 1 == destX ));
           //Move 1 tile diagonally
           diagonal = (currentX - 1 == destX && currentY - 1 == destY) || (currentX + 1 == destX && currentY - 1 == destY || 
                       currentX - 1 == destX && currentY + 1 == destY) || (currentX + 1 == destX && currentY + 1 == destY);
        }else{
          //  System.out.println("Red Star");
          //Move 1 tile forward
           forward = ((currentX == destX) && (currentY + 1 == destY)); 
           //Move 1 tile backward
           backward = ((currentX == destX) && (currentY - 1 == destY));
           //Move 1 tile horizontally
           horizontal = ((currentY == destY) && (currentX + 1 == destX || currentX - 1 == destX ));
           //Move 1 tile diagonally
           diagonal = (currentX - 1 == destX && currentY - 1 == destY) || (currentX + 1 == destX && currentY - 1 == destY || 
                       currentX - 1 == destX && currentY + 1 == destY) || (currentX + 1 == destX && currentY + 1 == destY);
        }
        //Check if it's the same position
        boolean notSamePosition = !((currentX==destX) && (currentY==destY));
        
        return notSamePosition && (forward || backward ||  diagonal || horizontal);
}

    /**
     *  This function return the coordinates of every cube which is part of the path of Pawn. This method
     * assume that there is a valid move of Pawn from current to destination.If the vector returned is empty,
     * then it means that there is no other piece get in the way of a valid path.
     * @param currentX
     * @param currentY
     * @param destX
     * @param destY
     * @return
     */
    @Override
    public Vector<Coordinates> generateValidPath(int currentX, int currentY, int destX, int destY){
        Vector<Coordinates> list = new Vector(0);
        int numberOfY=Math.abs(destY - currentY);
        int numberOfX=Math.abs(destX - currentX);

        if(currentX == destX){
            for(int iY = 1; iY < numberOfY;iY++){
                Coordinates pair;
                if(currentY < destY) {
                    pair = new Coordinates(currentX, currentY + iY);
                }else {
                    pair = new Coordinates(currentX, currentY - iY);
                }
                list.add(pair);
            }
        } else if(currentY==destY){
            for(int iX=1; iX< numberOfX;iX++){
                Coordinates pair;
                if(currentX < destX) {
                    pair = new Coordinates(currentX + iX, currentY);
                }else{
                    pair = new Coordinates(currentX - iX, currentY);
                }
                list.add(pair);
            }
        } else if(currentX < destX && currentY < destY) {
            for (int i = 1; i < numberOfX; i++) {
                Coordinates pair = new Coordinates(currentX + i, currentY + i);
                list.add(pair);
            }
        }else if(currentX < destX && currentY > destY) {
            for (int i = 1; i < numberOfX; i++) {
                Coordinates pair = new Coordinates(currentX + i, currentY - i);
                list.add(pair);
            }
        }else if(currentX > destX && currentY < destY) {
            for (int i = 1; i < numberOfX; i++) {
                Coordinates pair = new Coordinates(currentX - i, currentY + i);
                list.add(pair);
            }
        }else{
            for (int i = 1; i < numberOfX; i++) {
                Coordinates pair = new Coordinates(currentX - i, currentY - i);
                list.add(pair);
            }
        }

        return list;
    }
    
    @Override
    public Vector<Coordinates> possibleMoves(int currentX, int currentY)
    {
        
        
        Vector<Coordinates> list =new Vector(0);
        int testX;
        int testY;
        Coordinates possible;
        //move 1 step foward
        testX = currentX;
        testY = currentY+1;
        if(notOffBoard(8,5,testX,testY))
        {
            
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        // move 1 step backward
        testX = currentX;
        testY = currentY-1;
        if(notOffBoard(8,5,testX,testY))
        {
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        //move 1 step towards the left
        testX = currentX-1;
        testY = currentY;
        if(notOffBoard(8,5,testX,testY))
        {
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        //move 1 step towards the right
        testX = currentX+1;
        testY = currentY;
        if(notOffBoard(8,5,testX,testY))
        {
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        //top right
        testX = currentX+1;
        testY = currentY+1;
        if(notOffBoard(8,5,testX,testY))
        {
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        
        //top left
        testX = currentX-1;
        testY = currentY+1;
        if(notOffBoard(8,5,testX,testY))
        {
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        //bottom right
        testX = currentX+1;
        testY = currentY-1;
        if(notOffBoard(8,5,testX,testY))
        {
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        //bottom left
        testX = currentX-1;
        testY = currentY-1;
        if(notOffBoard(8,5,testX,testY))
        {
            possible = new Coordinates(testX,testY);
            list.add(possible);
        }
        return list;
    }
    

}







   