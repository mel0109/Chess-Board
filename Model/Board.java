package Model;
import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;


public class Board {
    
    private int height;
    private int width;
    private Pieces[][] storage;
    private Coordinates redHeartPosition;
    private Coordinates greenHeartPosition;
    
    //initialzes an empty two dimensional array of the width and height.
    public Board(){
        this.height=8;
        this.width=5;
        this.storage=new Pieces[height][width];
    }
    
    //initializes the pieces position on the board in a two dimensional array.
    private void initialize(){
        Arrow ArrowR= new Arrow(0);
        Arrow ArrowG= new Arrow(1);
        setPiece(1,6,ArrowG);
        setPiece(2,6,ArrowG);
        setPiece(3,6,ArrowG);
        setPiece(1,1,ArrowR);
        setPiece(2,1,ArrowR);
        setPiece(3,1,ArrowR);
        
        Star StarR= new Star(0);
        Star StarG= new Star(1);
        setPiece(0,7,StarG);
        setPiece(4,7,StarG);
        setPiece(0,0,StarR);
        setPiece(4,0,StarR);
        
        Cross CrossR= new Cross(0);
        Cross CrossG= new Cross(1);
        setPiece(1,7, CrossG);
        setPiece(3,7, CrossG);
        setPiece(1,0, CrossR);
        setPiece(3,0, CrossR);
        
        Heart HeartR= new Heart(0);
        Heart HeartG= new Heart(1);
        setPiece(2,7, HeartG);
        setPiece(2,0, HeartR);
    }
    
    //initializes the board with pieces set to its default positions(start of game position)
    public Board(int height, int width) {
        this.height=height;
        this.width=width;
        this.storage=new Pieces[height][width];
        //make sure the pos of heart is there
        this.redHeartPosition=new Coordinates(2,0);
        this.greenHeartPosition=new Coordinates(2,7);
        this.initialize();
    }
    
    public Board(String[] parsedLines,int height, int width){//loads a text file and reinitialized the board
        this.height=height;
        this.width=width;
        this.storage=new Pieces[height][width];
        
        for(int i = 0; i < parsedLines.length; i+=3)        //cycles through parsedLines array. i+=3 because 3 different pieces of data.(piece name, X, Y)
        {
                
                String name = parsedLines[i];               
                int xCord = Integer.valueOf( parsedLines[i +1] );
                int yCord = Integer.valueOf( parsedLines[i +2] );

                if ("GA".equals(name)){             //each if and else statement checks the pieces name and then initializes its X and Y position onto the new board.
                    Arrow ArrowG= new Arrow(1);
                    setPiece(xCord, yCord, ArrowG);
                }
                else if ("RA".equals(name)){
                    Arrow ArrowR= new Arrow(0);
                    setPiece(xCord, yCord, ArrowR);
                }
                else if ("GS".equals(name)){
                    Star StarG= new Star(1);
                    setPiece(xCord, yCord, StarG);
                }
                else if ("RS".equals(name)){
                    Star StarR= new Star(0);
                    setPiece(xCord, yCord, StarR);
                }
                else if ("GC".equals(name)){
                    Cross CrossG= new Cross(1);
                    setPiece(xCord, yCord, CrossG);
                }
                else if ("RC".equals(name)){
                    Cross CrossR = new Cross(0);
                    setPiece(xCord, yCord, CrossR);
                }
                
                else if ("GH".equals(name)){
                    Heart HeartG = new Heart(1);
                    setPiece(xCord, yCord, HeartG);
                    this.greenHeartPosition=new Coordinates(xCord,yCord);
                }
                else if ("RH".equals(name)){
                    Heart HeartR = new Heart(0);
                    setPiece(xCord, yCord, HeartR);
                    this.redHeartPosition=new Coordinates(xCord,yCord);
                }
        }
    }
    
    public void saveFile(String filename)throws IOException{  //saves the game into a text file.
        Pieces piece;
        String pieceName;
        int coordinateX, coordinateY;
        String inputData;
        int counter = 0;
        for(int i=7;i>=0;i--){                  //checks the entire board.
            for(int j=0;j<5;j++){
                piece = this.getPiece(j, i);
                if(piece instanceof Heart|| piece instanceof Cross || piece instanceof Star || piece instanceof Arrow)  // if the boards piece is not null, we get its piece name and coordinates.
                {
                    pieceName= piece.getName();
                    coordinateY = i;
                    coordinateX = j;
        
                    
                    if (counter == 0){
                        WriteFile data = new WriteFile(filename, false);  //overwrite  
                        data.writeToFile(pieceName);
                        WriteFile data1 = new WriteFile(filename, true); //append
                        data1.writeToFile(String.valueOf(coordinateX));
                        data1.writeToFile(String.valueOf(coordinateY));
                    }
                    else{
                        WriteFile data = new WriteFile(filename, true);
                        data.writeToFile(pieceName);
                        data.writeToFile(String.valueOf(coordinateX));
                        data.writeToFile(String.valueOf(coordinateY));
                    }
                    counter++;
                }
            }
        }
    }
   
    public String[] loadGame(String gameFile) throws IOException{       //creates a ReadFile object. passing in file path. reads the file and passes it into the string array.
        ReadFile file = new ReadFile(gameFile);
        
        String[] aryLines = file.OpenFile();
        
        return aryLines;
    }
    //returns green heart's current position
    public Coordinates getGreenHeartPosition(){     //returns the position of the green heart.(x,y)
        return greenHeartPosition;
    }
    
    //returns red heart's current position
    public Coordinates getRedHeartPosition(){       //returns the position of the red heart.(x,Y0
        return redHeartPosition;
    }
    
    //returns height of the board (verticle)
    public int getHeight() {
        return height;
    }
    
    //returns width of the board (horizontal)
    public int getWidth() {
        return width;
    }
    
    public Pieces getPiece(int coordinatesX,int coordinatesY){      //returns the piece at position x,y.
        if(isNotOffBoard(getHeight(),getWidth(),coordinatesX,coordinatesY)) {
            return this.storage[coordinatesY][coordinatesX];
        }else{
            System.out.println("Can't get piece invalid coordinates");
            return null;
        }
    }
    
    public void setPiece(int coordinatesX,int coordinatesY,Pieces piece){       //sets a piece on the board with position x,y.
        //check if off bound
        if(isNotOffBoard(height,width,coordinatesX,coordinatesY)) {
            storage[coordinatesY][coordinatesX] = piece;
            if (piece != null && piece instanceof Heart) {
                //update king's position
                Coordinates heartPosition = new Coordinates(coordinatesX, coordinatesY);
                if (piece.getColor() == 1) {
                    greenHeartPosition = heartPosition;
                } else {
                    redHeartPosition = heartPosition;
                }
            }
        }
    }
    
    //Removes anything in coordinate (x,y) and sets it to null.
    //if coordinate fails to be on the board, warning given.
    public void remove(int coordinatesX, int coordinatesY){
        if(isNotOffBoard(getHeight(),getWidth(),coordinatesX,coordinatesY)) {
            this.storage[coordinatesY][coordinatesX] = null;

        } else{
            //gui cant see the effect since u cant click out of boundary
            System.out.println("Can't remove invalid coordinates");
        }
    }
    
    //Check whether coordinate (x,y) is null.
    public boolean isEmpty(int coordinatesX,int coordinatesY){
        return this.storage[coordinatesY][coordinatesX]==null;
    }
    
    //Check whether the coordinates (x,y) fall within the range of the board ([height][width])
    private boolean isNotOffBoard(int height, int width, int coordinatesX, int coordinatesY){
        return (coordinatesX<width)&&(coordinatesX>=0)&&(coordinatesY<height)&&(coordinatesY>=0);
    }
    
    //Checks whether the piece at currentX and currentY is obstructed by any other pieces from current(x,y) to destination(x,y)
    //if the list is empty, it means that there is nothing obstructing the path
    //if the list has values, check the values inside the list. if values are only null values, there is no obstruction
    private boolean noBlock(int currentX, int currentY, int destX, int destY){
        Pieces piece = getPiece(currentX,currentY);
        Vector<Coordinates> list = piece.generateValidPath(currentX,currentY,destX,destY);
        //when it is empty, means it's block
        if(!list.isEmpty()){
            int yValue;
            int xValue;
            for(int iterator=0; iterator< list.size(); iterator++){
                xValue=list.get(iterator).getX();
                yValue=list.get(iterator).getY();
                if(storage[yValue][xValue] != null){
                    //System.out.println(xValue+""+yValue+"is not null current peice is "+piece.getName());
                    return false;
                }
            }
        }
        return true;
    }
    //important
    //takes a piece. deletes the piece's current location, reassign the piece to it's new location.
    public boolean move(int currentX, int currentY,int destX,int destY){
        if(canMoveWithoutCheckOwnHeart(currentX,currentY,destX,destY)){
            Pieces piece = getPiece(currentX,currentY);
            remove(currentX,currentY); //remove old
            setPiece(destX,destY,piece); //set new co
             if(getPiece(destX,destY) instanceof Arrow && (destY==7 || destY==0)){//check when reach end of board
                                int colour = getPiece(destX,destY).getColor();
                                remove(destX,destY);
                                ReverseArrow RA = new ReverseArrow(colour);
                                setPiece(destX,destY, RA);
                            }
                            else if(getPiece(destX,destY) instanceof ReverseArrow && (destY==7 ||destY==0)){
                                int colour = getPiece(destX,destY).getColor();
                                remove(destX,destY);
                                Arrow A= new Arrow(colour);
                                 setPiece(destX,destY, A);
                            }
            return true;
        }
        return false;
    }
    
    //checks whether the pieces are able to move without having it's heart get into check.
    private boolean canMoveWithoutCheckOwnHeart(int currentX, int currentY,int destX,int destY){
        boolean retVal = true;

        if(canMoveTo(currentX, currentY, destX, destY)){
            Pieces currentPiece=getPiece(currentX,currentY);
            Pieces removedPiece=getPiece(destX,destY);
            int color = currentPiece.getColor();    //sets the color of heart
            remove(currentX,currentY);
            setPiece(destX,destY,currentPiece);
            //check if this move will put friendly heart in check
            if(isChecked(color)){
                //System.out.println(color+"put king in check");
                retVal = false;
            }
            /*
            else{
                System.out.println("move from"+currentX+currentY+" to "+destX+destY+" didn't out king in check");
            }
*/
            // put it back to original spot , 2 times 
            setPiece(currentX,currentY,currentPiece);
            //put removed piece back
            setPiece(destX,destY,removedPiece);
            return retVal;

        }


        return false;
    }
    
    //checks whether a piece is able to move to it's destination without any interference
    //board
    private boolean canMoveTo(int currentX, int currentY,int destX,int destY){
        if (this.isNotOffBoard(getHeight(), getWidth(), destX, destY)) {        //gets height and width of the board and checks whether dest X and Y fall on the board.
            Pieces piece = getPiece(currentX, currentY);    //get piece at current position
            if (piece != null){     
                 //if destination is empty or the color of the current piece is different from the piece at the destination.
                if (isEmpty(destX, destY) || (getPiece(currentX, currentY).getColor() != getPiece(destX, destY).getColor())) { 
                    //piece
                    if (piece.canMoveTo(currentX, currentY, destX, destY)) {    //if the piece is able to move to that position
                        
                        if (this.noBlock(currentX, currentY, destX, destY)) { //if current piece can move to destination without any piece in the middle
                            return true;
                        }
                        
                    }
                   
                }
            }
        }
        return false;
    }
    //get x position of any chosen color of heart
    public int getHeartX(int color){
        if(color == 1){
            return greenHeartPosition.getX();
        }else{
            return redHeartPosition.getX();
        }
    }
    //get y position of any chosen color of heart
    public int getHeartY(int color){
        if(color == 1){
            return greenHeartPosition.getY();
        }else{
            return redHeartPosition.getY();
        }
    }
    
    //returns a list of coordinates that are of the same team and is able to reach the pieces current position
    //check all ur own team
    private Vector<Coordinates> reachedBy(int currentX, int currentY,int color){
        Vector<Coordinates> list = new Vector(0);
        for(int y=0; y<getHeight();y++){//checks all pieces on the board.
            for(int x=0;x<getWidth();x++){
                //if the position is not empty.
                if(!isEmpty(x,y)) {
                    if ((getPiece(x, y).getColor() == color) && canMoveTo(x, y, currentX, currentY)) {//if the position at x,y same color as piece being checked and piece at x,y is ablet to move to the position of the piece being checked
                        list.add(new Coordinates(x, y));                                              //then add it to the list.
                    }
                }
            }
        }
        return list;
    }
    //checks whether the piece is in danger of opposing enemy's pieces.
    private boolean canReached(int currentX, int currentY){
        Pieces piece = getPiece(currentX,currentY);
        for(int y=0; y<getHeight();y++){//checks all positions on the chess board.
            for(int x=0;x<getWidth();x++){
                if(!isEmpty(x,y)) {//if the position is not empty.
                    if (getPiece(x, y).getColor() != piece.getColor() && canMoveTo(x, y, currentX, currentY)) { //checks all pieces color on the board.if the piece is of opposite color and is able to move to the current position. 
                        //System.out.println("");                                                               //Means it can be reached
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //checks all possible movement of the heart where it doesnt place itself in check.
    private boolean canEscape(int color){
        int heartX = getHeartX(color);
        int heartY = getHeartY(color);
        return canMoveWithoutCheckOwnHeart(heartX,heartY,heartX+1,heartY) || canMoveWithoutCheckOwnHeart(heartX,heartY,heartX-1,heartY) ||
                canMoveWithoutCheckOwnHeart(heartX,heartY,heartX,heartY+1) || canMoveWithoutCheckOwnHeart(heartX,heartY,heartX,heartY-1) ||
                canMoveWithoutCheckOwnHeart(heartX,heartY,heartX+1,heartY+1) || canMoveWithoutCheckOwnHeart(heartX,heartY,heartX+1,heartY-1) ||
                canMoveWithoutCheckOwnHeart(heartX,heartY,heartX-1,heartY+1) || canMoveWithoutCheckOwnHeart(heartX,heartY,heartX-1,heartY-1);
    }
    
    //checks whether a piece is able to save it's heart.
    private boolean canCapture(int color){
        int heartX = getHeartX(color);
        int heartY = getHeartY(color);
        Vector<Coordinates> listOfThreatens = reachedBy(heartX, heartY,1-color);    //gets a list of coordinates that threatens the heart.
        int size = listOfThreatens.size();  //number of pieces that threatens the heart
        for(int i=0; i<size;i++){
            int threatenX = listOfThreatens.get(i).getX();
            int threatenY = listOfThreatens.get(i).getY();
            //System.out.println("x is " +threatenX+" y is "+ threatenY);
            Vector<Coordinates> capturer = reachedBy(threatenX,threatenY,color);    //gets a list of coordinates that are able to kill opposing threats to the heart.
            if(capturer.size()!=0){ //if there is a piece that is able to capture the threat
                //System.out.println("has capturer");
                for (int j=0;j<capturer.size();j++) {   //loops through all the pieces that are able to capture the threat to the heart.
                    int x = capturer.get(j).getX();
                    int y = capturer.get(j).getY();
                    if(canMoveWithoutCheckOwnHeart(x,y,threatenX,threatenY)){    //checks whether the piece at x,y is able to move without having place its own heartin check. If can move, it can eliminate the threat.
                                                                                //if there are multiple threats to the heart. return value will be false.
                       // System.out.println("capturer x is " +x+" y is "+ y);
                        return true;
                    }

                }
            }

        }
        return false;
    }
    
    private boolean canBlock(int color){
        int heartX=getHeartX(color);
        int heartY=getHeartY(color);
        Vector<Coordinates> listOfThreatens = reachedBy(heartX, heartY,1-color);    //gets a list of coordinates that is able to threaten the heart.
       // printList(listOfThreatens);
        if(listOfThreatens.size()==1){  //if there is only one threat
            int threatenX = listOfThreatens.get(0).getX();
            int threatenY = listOfThreatens.get(0).getY();
            Vector<Coordinates> list = getPiece(threatenX,threatenY).generateValidPath(threatenX,threatenY,heartX,heartY);  //gets a list of coordinates that the piece travels through.
            //printList(list);
            int length=list.size(); //the number of tiles the threat passes through
            if(length>0){
                for(int i=0;i<length;i++){  //loops through the list of tiles.
                    int possibleX = list.get(i).getX();
                    int possibleY = list.get(i).getY();
                    Vector<Coordinates> capturer=reachedBy(possibleX,possibleY,color);  //returns a list of coordinates(pieces) that is able to move to obstruct the path of the threat.
                    int numOfCapturer = capturer.size();    //number of pieces that are able to block the threat.
                    for(int j=0;j<numOfCapturer;j++) {      
                        int capturerX = capturer.get(j).getX();
                        int capturerY = capturer.get(j).getY();
                        if (canMoveWithoutCheckOwnHeart(capturerX, capturerY, possibleX, possibleY)) {   //if the piece is able to obstruct the path of the threat without having the heart be put in danger.
                            return true;                                                                //then return true.
                        }
                    }
                }
            }
        }
        return false;
    }
    
        //returns whether a piece is able to reach the heart.
    public boolean isChecked(int color){
        int heartX = getHeartX(color);
        int heartY = getHeartY(color);
        //System.out.println("king position is "+kingX+" "+kingY+"and is in check"+canReached(kingX,kingY));
        return canReached(heartX,heartY);
    }
    
    
    public boolean isCheckmate(int color){
        if(isChecked(color)){
           // System.out.println("canEscape "+canEscape(color)+" canCapture "+ canCapture(color)  +
           //         "canBlock" + canBlock(color));
            return !(canEscape(color) || canCapture(color) || canBlock(color)); //heart is in checkmate if it cant escap, threats cant be captured, and pieces are unable to obstruct the path of the threat.
        }
        return false;
    }
    
    //checks whether a piece is able to move without having it check its own king. If it unable to move, then return true.
    public boolean isStalemate(int color){
        //check if it's checkmate
        if(!isCheckmate(color)) {   
            //check if existing peice can move legally
            for (int y = 0; y < getHeight(); y++) {     //go through the chessboard
                for (int x = 0; x < getWidth(); x++) {
                    Pieces piece = getPiece(x, y);
                    if (piece != null && (piece.getColor()==color)) {   //if theres a piece in position x,y and of the same team.
                        for (int i = 0; i < getHeight(); i++) {     //loops through chessboard once again.
                            for (int j = 0; j < getWidth(); j++) {
                                if (canMoveWithoutCheckOwnHeart(x,y,i,j)){   //if the piece at x, y is able to move to destination(i,j)
                                                                            //means it is not stalemate.
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    //transforms all Cross to Hearts and Hearts to Cross on the chessboard.
    public ArrayList  transform(){
        int color;
       ArrayList cords = new ArrayList();
       
        for(int i=7;i>=0;i--){
            for(int j=0;j<5;j++){
                if(this.storage[i][j] instanceof Cross || this.storage[i][j] instanceof Star){
                    if(this.storage[i][j] instanceof Cross ){
                        
                        color = this.storage[i][j].getColor();
                        
                        if(color == 1){
                            remove(j,i);
                            Star StarG= new Star(1);
                            setPiece(j,i,StarG);
                            cords.add(j);
                            cords.add(i);
                        }
                        else{
                            remove(j,i);
                            Star StarR= new Star(0);
                            setPiece(j,i,StarR);
                            cords.add(j);
                            cords.add(i);
                        }
                    }
                    else if(this.storage[i][j] instanceof Star){
                        color=this.storage[i][j].getColor();
                        if(color == 1){
                            remove(j,i);
                            Cross CrossG= new Cross(1);
                            setPiece(j,i,CrossG);
                            cords.add(j);
                            cords.add(i);
                        }
                        else{
                            remove(j,i);
                            Cross CrossR= new Cross(0);
                            setPiece(j,i,CrossR);
                            cords.add(j);
                            cords.add(i);
                        }
                    }
                }
            }  
        }
        return cords;
    }
    
    //calculating possible moves for any given piece.
    public Vector<Coordinates> piecePossibleMoves(int currentX, int currentY)
    {
        Vector<Coordinates> list = new Vector(0);
        Vector<Coordinates> hold;
        Pieces currentPiece = getPiece(currentX,currentY);
        int destX;
        int destY;
        Coordinates possible;
        if(currentPiece!= null){
            if(currentPiece instanceof Arrow || currentPiece instanceof Cross || currentPiece instanceof Star || currentPiece instanceof Heart || currentPiece instanceof ReverseArrow){
                hold = currentPiece.possibleMoves(currentX, currentY);
                for(int i = 0 ; i < hold.size(); i++)
                {
                    destX= hold.get(i).getX();
                    destY= hold.get(i).getY();
                    possible = new Coordinates(destX,destY);
                    if(canMoveTo(currentX,currentY,destX,destY))
                    {
                        list.add(possible);
                    }
                }
            }/*
            else if(currentPiece instanceof Cross){
                hold= currentPiece.possibleMoves(currentX, currentY);
                for(int i = 0; i < hold.size(); i++){
                    destX= hold.get(i).getX();
                    destY= hold.get(i).getY();
                    possible =new Coordinates(destX,destY);
                    if(canMoveTo(currentX,currentY,destX,destY))
                    {
                        list.add(possible);
                    }
                }
            }
            else if(currentPiece instanceof Star){
                hold= currentPiece.possibleMoves(currentX, currentY);
                for(int i = 0; i < hold.size(); i++){
                    destX= hold.get(i).getX();
                    destY= hold.get(i).getY();
                    possible =new Coordinates(destX,destY);
                    if(canMoveTo(currentX,currentY,destX,destY))
                    {
                        list.add(possible);
                    }
                }
            }*/
        }
        return list;
    }
    
    //prints the board
    public void printBoard(){
        System.out.println("Printing board");
        for(int i=7;i>=0;i--){
            for(int j=0;j<5;j++){
                if(this.storage[i][j] == null){
                    System.out.print("NL");
                }else{
                    System.out.print(this.storage[i][j].getName());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }  
}
