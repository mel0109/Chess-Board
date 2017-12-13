package Model;

import java.util.Vector;

public class Arrow extends Pieces {
    
    /**
     * Constructor
     *
     * @param color
     */
    public Arrow(int color) {
        super(color);
        if(color == 1){
            //Green Arrow
            this.name="GA";
        }else{
            //Red Arrow
            this.name="RA";
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
        boolean move1 = false;
        boolean move2 = false;
        

        if (this.getColor() == 1) {
           // System.out.println("Green Arrow");
           //Move 1 tile downwards
           move1 =((currentX == destX) && (currentY - 1 == destY));
           //Move 2 tile downwards
           move2 = ((currentX == destX) && (currentY - 2 == destY));
           
        }else if  (this.getColor() == 0){
          //  System.out.println("Red Arrow");
          //Move 1 tile upwards
           move1 = ((currentX == destX) && (currentY + 1 == destY));
           //Move 2 tile upwards
           move2 = ((currentX == destX) && (currentY + 2 == destY));
        }
        
        //Check if it's the same position
        boolean notSamePosition = !((currentX==destX) && (currentY==destY));
       // System.out.println("pawn can move to"+(notSamePosition && (move1 || move2 || diagonal)));
        return notSamePosition && (move1 || move2);
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
        for (int i = 1; i < Math.abs(currentY-destY); i++) {
            Coordinates pair;
            if(this.getColor() ==1) {
                pair = new Coordinates(currentX, currentY - i);
            }else{
                pair = new Coordinates(currentX, currentY + i);
            }
            list.add(pair);
        }
        return list;
    }
    
    @Override
    public Vector<Coordinates> possibleMoves(int currentX, int currentY)
    {
        Vector<Coordinates> list = new Vector(0);
        int destX;
        int destY;
        Coordinates possible;
        // moving backwards 1 or 2 steps
        if (this.getColor() == 1){
            destX = currentX;
            destY = currentY - 1;
            if (notOffBoard(8,5,destX,destY)){
                possible = new Coordinates(destX,destY);
                list.add(possible);
            }
            destX = currentX;
            destY = currentY - 2;
            if(notOffBoard(8,5,destX,destY)){
                possible = new Coordinates(destX,destY);
                list.add(possible);
            }
        }
        //moving foward 1 or 2 steps.
        else if (this.getColor() ==  0){
            destX = currentX;
            destY = currentY+1;
            if(notOffBoard(8,5,destX,destY))
            {
                possible = new Coordinates(destX,destY);
                list.add(possible);
            }
            destX = currentX;
            destY = currentY+2;
            if (notOffBoard(8,5,destX,destY))
            {
                possible = new Coordinates(destX,destY);
                list.add(possible);
            }
        }
        
        return list;
    }
}
