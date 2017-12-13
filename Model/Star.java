package Model;
import java.util.Vector;

public class Star extends Pieces{
    public Star(int color) {
            /**
         * Constructor
         *
         * @param color
         */
        super(color);
        if(color == 1){
            //Green Star
            this.name="GS";
        }else{
            //Red Star
            this.name="RS";
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
        boolean forward1;
        boolean forward2;
        boolean backward1;
        boolean backward2;
        boolean horizontal1;
        boolean horizontal2;
        boolean diagonal1;
        boolean diagonal2;

        if(this.getColor() == 1 ){
           // System.out.println("Green Star");
           //Move 1 tile forward
           forward1 = ((currentX == destX) && (currentY - 1 == destY)); 
           //Move 2 tile forward
           forward2 = ((currentX == destX) && (currentY - 2 == destY)); 
           //Move 1 tile backward
           backward1 = ((currentX == destX) && (currentY + 1 == destY));
           //Move 2 tile backward
           backward2 = ((currentX == destX) && (currentY + 2 == destY));
           //Move 1 tile horizontally
           horizontal1 = ((currentY == destY) && (currentX + 1 == destX || currentX - 1 == destX ));
           //Move 2 tile horizontally
           horizontal2 = ((currentY == destY) && (currentX + 2 == destX || currentX - 2 == destX ));
           
           //Move 1 tile diagonally
           diagonal1 = (currentX - 1 == destX && currentY - 1 == destY) || (currentX + 1 == destX && currentY - 1 == destY || 
                       currentX - 1 == destX && currentY + 1 == destY) || (currentX + 1 == destX && currentY + 1 == destY);
           //Move 2 tiles diagonally
           diagonal2 = (currentX - 2 == destX && currentY - 2 == destY) || (currentX + 2 == destX && currentY - 2 == destY || 
                       currentX - 2 == destX && currentY + 2 == destY) || (currentX + 2 == destX && currentY + 2 == destY);
        }else{
          //  System.out.println("Red Star");
          //Move 1 tile forward
           forward1 = ((currentX == destX) && (currentY + 1 == destY)); 
           //Move 2 tile forward
           forward2 = ((currentX == destX) && (currentY + 2 == destY)); 
           //Move 1 tile backward
           backward1 = ((currentX == destX) && (currentY - 1 == destY));
           //Move 2 tile backward
           backward2 = ((currentX == destX) && (currentY - 2 == destY));
           //Move 1 tile horizontally
           horizontal1 = ((currentY == destY) && (currentX + 1 == destX || currentX - 1 == destX ));
           //Move 2 tile horizontally
           horizontal2 = ((currentY == destY) && (currentX + 2 == destX || currentX - 2 == destX ));
           //Move 1 tile diagonally
           diagonal1 = (currentX - 1 == destX && currentY - 1 == destY) || (currentX + 1 == destX && currentY - 1 == destY || 
                       currentX - 1 == destX && currentY + 1 == destY) || (currentX + 1 == destX && currentY + 1 == destY);
           //Move 2 tiles diagonally
           diagonal2 = (currentX - 2 == destX && currentY - 2 == destY) || (currentX + 2 == destX && currentY - 2 == destY || 
                       currentX - 2 == destX && currentY + 2 == destY) || (currentX + 2 == destX && currentY + 2 == destY);
        }
        //Check if it's the same position
        boolean notSamePosition = !((currentX==destX) && (currentY==destY));
        
        return notSamePosition && (forward1 || forward2 || backward1 || backward2 || diagonal1 || diagonal2 || horizontal1 || horizontal2 );
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
        Vector<Coordinates> list = new Vector(0);
        int destX;
        int destY;
        Coordinates possible;
        //move 1 step fowards
        destX=currentX;
        destY=currentY+1;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //move 2 step fowards
        destX=currentX;
        destY=currentY+2;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //move 1 step backwards
        destX=currentX;
        destY=currentY-1;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //move 2 steps backwards
        destX=currentX;
        destY=currentY-2;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //move 1 step towards the left
        destX=currentX-1;
        destY=currentY;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //move 2 steps towards the left
        destX=currentX-2;
        destY=currentY;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //move 1 step towards the right
        destX=currentX+1;
        destY=currentY;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //move 2 step towards the right
        destX=currentX+2;
        destY=currentY;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //1 top left
        destX=currentX-1;
        destY=currentY+1;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //2 top left
        destX=currentX-2;
        destY=currentY+2;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //1 top right
        destX=currentX+1;
        destY=currentY+1;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //2 top right
        destX=currentX+2;
        destY=currentY+2;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //1 bottom left
        destX=currentX-1;
        destY=currentY-1;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //2 bottom left
        destX=currentX-2;
        destY=currentY-2;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //1 bottom right
        destX=currentX+1;
        destY=currentY-1;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        //2 bottom right
        destX=currentX+2;
        destY=currentY-2;
        possible = new Coordinates(destX,destY);
        if(notOffBoard(8,5,destX,destY)){
            list.add(possible);
        }
        return list;
    }
}
