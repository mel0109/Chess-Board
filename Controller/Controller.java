package Controller;

import Model.*;
import View.ChessGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.SwingUtilities.isLeftMouseButton;



public class Controller{
    
    private Player playerRed;
    private Player playerGreen;
    private Player currentPlayer;
    private Board board;
    private ChessGUI view;
    private ChessGUI.tilePanel source;
    private ChessGUI.tilePanel dest;
    private Pieces preSource;
    private Pieces preDest;
    private int preSourceX;
    private int preSourceY;
    private int preDestX;
    private int preDestY;
    int turns;
    private int currentGreenScore;
    private int currentRedScore;
    private final int length;
    private final int width;
    //SAVEFILE PATH
    private final  String FILE_PATH = "D:/ " ;
    
    //START OF CONSTRUCTOR
    public Controller( final int length,final int width){
        this.length = length;
        this.width = width;
        //what happen?
        board = new Board(length,width);
        view = new ChessGUI(board);
        currentGreenScore = 0 ;
        currentRedScore = 0;

        //get name from user
        getUserName();
        
        //set current player to be Red
        currentPlayer = playerRed;
        
        if (currentPlayer.getColor() == 0){
            view.highlightPlayer(0);
        }
        else{
            view.highlightPlayer(1);
        }
        

        //update username on view
        view.updateGreenPlayerName(playerGreen.getName());
        view.updateRedPlayerName(playerRed.getName());


        //attach move listener to each tile. This is an implicit game loop as well
        attachActionToTile();

        //Start a new game
        view.newGameListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                //Game Reset
                JOptionPane.showMessageDialog(view.getMainFrame(),
                        "Game Reset");
                turns = 0;
                reset(8, 5);
                view.updateTurns(turns);
            }
        });

        view.forfeitRedListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                 turns = 0; 
                view.unHighlightPlayer(1);
                view.unHighlightPlayer(0);
                forfeitRed();
                view.updateTurns(turns);
                view.highlightPlayer(0);
               
            }
        });

        view.forfeitGreenListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                turns = 0;
                view.unHighlightPlayer(1);
                view.unHighlightPlayer(0);
                forfeitGreen();
                view.updateTurns(turns);
                view.highlightPlayer(0);
                
            }
        });
        
        view.undoListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                turns --;
                undo();
                view.updateTurns(turns);
            }
        });
        
         view.saveGameListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    saveGame();
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
         
          view.loadGameListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    loadGame();
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    } //END OF CONSTRUCTOR
    
    public final void getUserName(){
        // create pop up window
        JTextField green = new JTextField();
        JTextField red = new JTextField();
        Object[] message = {
                "Green Player Name:", green,
                "Red user name:", red
        };
        
        int option = JOptionPane.showConfirmDialog(view.getMainFrame(), message, "Player Names", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION){
            // get strings from Jtextfield and set 2 player
            String greenName = green.getText();
            String redName = red.getText();
            if(!greenName.equals("")) {
                playerGreen = new Player("    "  + greenName, 1);
            }else{
                playerGreen = new Player("    Green",1);
            }
            if(!redName.equals("")) {
                playerRed = new Player("    "  + redName, 0);
            }else{
                playerRed=new Player("    Red",0);
            }
        }else{
            playerGreen = new Player("    Green",1);
            playerRed = new Player("    Red",0);
        }
    }
    
    public void saveGame() throws IOException{
        // create pop up window
        JTextField fileName = new JTextField();
        Object[] message = {
                "File Name :", fileName,
        };
        
        int option = JOptionPane.showConfirmDialog(view.getMainFrame(), message, "Save Game", JOptionPane.OK_CANCEL_OPTION);
        String fullName = null;
        
        if (option == JOptionPane.OK_OPTION){
            // get strings from Jtextfield and set 2 player
            String name = fileName.getText();
            
            if(!name.equals("")) {
                fullName = FILE_PATH + name + ".txt";
                 board.saveFile(fullName); 
                 JOptionPane.showMessageDialog(view.getMainFrame(), "Save Succesful! ");
            }else{
                fullName = FILE_PATH + "DefaultSave" + ".txt";
                 board.saveFile(fullName); 
                 JOptionPane.showMessageDialog(view.getMainFrame(), "Save Succesful! ");
            }
        }
    }
   
    public void loadGame() throws IOException{
         // create pop up window
        JTextField fileName = new JTextField();
        Object[] message = {
                "File Name :", fileName,
        };
        boolean loop = true;
        String fullName = null;
        while (loop == true){
             int option = JOptionPane.showConfirmDialog(view.getMainFrame(), message, "Load Game", JOptionPane.OK_CANCEL_OPTION);
            

            if (option == JOptionPane.OK_OPTION){
                // get strings from Jtextfield and set 2 player
                String name = fileName.getText();

             if(!name.equals("")) {
                  fullName = FILE_PATH + name + ".txt";
                   loop = false;
              }else{
                 loop = true;
                  JOptionPane.showMessageDialog(view.getMainFrame(), "Please specify file name! ");
                }
            }
            else{
                loop = false;
            }
        }
        preSource=null;
        preDest=null;
        currentPlayer=playerRed;
        String[] previousGame = board.loadGame(fullName);
        JOptionPane.showMessageDialog(view.getMainFrame(), "Load  Succesful! ");
        board = new Board(previousGame, 8, 5);
        view.newGame(board);
        attachActionToTile();
    }

   public void forfeitRed(){
        JOptionPane.showMessageDialog(view.getMainFrame(), "Green wins! ");
        //update score and view
        view.updateGreenPlayerScore(currentGreenScore += 1);
        reset(length, width);
    }

   public void forfeitGreen(){
        JOptionPane.showMessageDialog(view.getMainFrame(), "Red wins! ");
        //update score and view
        view.updateRedPlayerScore(currentRedScore += 1);
        reset(length, width);
    }

   public void reset(int length,int width){
        view.unHighlightPlayer(1);
        view.highlightPlayer(0);
        preSource=null;
        preDest=null;
        currentPlayer=playerRed;
        board=new Board(length,width);
        view.newGame(board);
        //reattach the action listener to each tile
        attachActionToTile();
    }
    
   public void undo(){
        if(preSource!=null) {
            int curColor=preSource.getColor();
            // set current player
            if(curColor==1){
                view.unHighlightPlayer(0);
                view.highlightPlayer(1);
                currentPlayer=playerGreen;
            }else{
                view.unHighlightPlayer(1);
                view.highlightPlayer(0);
                currentPlayer=playerRed;
            }
            // set presource and predest
            board.setPiece(preSourceX, preSourceY, preSource);
            board.setPiece(preDestX, preDestY, preDest);
            //update view
            view.getBoardPanel().getTile(preSourceX, preSourceY).assignIcon(board, preSourceX, preSourceY);
            view.getBoardPanel().getTile(preDestX, preDestY).assignIcon(board, preDestX, preDestY);
            view.getMainFrame().setVisible(true);
            //reset presource and predest
            preSource=null;
            preDest=null;
        }else{
            JOptionPane.showMessageDialog(view.getMainFrame(),
                    "Can't undo! ");
        }
    }
        
   private Player getCurrentPlayer(){
        return this.currentPlayer;
    }
    
   private  void attachActionToTile(){
       
        for(int i = 0; i < view.BOARD_WIDTH; i ++){
            for (int j = 0; j < view.BOARD_LENGTH; j++){
                //each tile will attach its button action listener
                
                ChessGUI.tilePanel currentTile=view.getBoardPanel().getTile(i, j);
                //get the coordinates of current tile
                final int I = currentTile.getTileX();
                final int J = currentTile.getTileY();
                
                currentTile.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        
                        if (isLeftMouseButton(e)){
                            //if source is null set source first
                            if(source==null){
                                source=view.getBoardPanel().getTile(I,J);
                                Pieces current=board.getPiece(I,J);
                                
                                int sourceX=source.getTileX();
                                int sourceY=source.getTileY();
                                 //HIGHLIGHT TODO
                                Vector<Coordinates> possible;
                                possible = board.piecePossibleMoves(sourceX, sourceY);
                                for  ( int i = 0 ;  i < possible.size() ;  i++){
                                        int xVal = possible.get(i).getX();
                                        int yVal = possible.get(i).getY();
                                        
                                        view.getBoardPanel().getTile(xVal, yVal).highlightPath(xVal, yVal);
                                }
                                //if clicked on empty nothing happen source reset to null
                                if(current==null ){
                                    source=null;
                                }else{
                                    //if clicked piece is not empty check if its own piece.if not
                                    //give warning
                                    if(current.getColor()!=currentPlayer.getColor()){
                                        
                                        for  ( int i = 0 ;  i < possible.size() ;  i++){
                                        int xVal = possible.get(i).getX();
                                        int yVal = possible.get(i).getY();
                                        
                                        view.getBoardPanel().getTile(xVal, yVal).unhighlightPath(xVal, yVal);
                                        }
                                        System.out.println("its "+currentPlayer.getColor()+" 's turn");
                                        source=null;
                                        //todo popup warning
                                        String warning;
                                        if(currentPlayer.getColor()==0){
                                            warning="It's Red's turn";
                                        }else{
                                            warning="It's Green's turn";
                                        }

                                        JOptionPane.showMessageDialog(view.getMainFrame(), warning);
                                    }
                                }

                            }else{
                                //after made sure we have a source,set dest
                                
                                int sourceX=source.getTileX();
                                int sourceY=source.getTileY();
                                
                              
                                 //HIGHLIGHT TODO
                                Vector<Coordinates> possible;
                                possible = board.piecePossibleMoves(sourceX, sourceY);
                                for  ( int i = 0 ;  i < possible.size() ;  i++){
                                        int xVal = possible.get(i).getX();
                                        int yVal = possible.get(i).getY();
                                        
                                        view.getBoardPanel().getTile(xVal, yVal).unhighlightPath(xVal, yVal);
                                }
                                dest=view.getBoardPanel().getTile(I,J);
                                
                                int destX=dest.getTileX();
                                int destY=dest.getTileY();
                                
                                

                                //memorize move set presource and predest and their coordinates
                                preSource = board.getPiece(sourceX,sourceY);
                                preDest = board.getPiece(destX,destY);
                                preSourceX=sourceX;
                                preSourceY=sourceY;
                                preDestX=destX;
                                preDestY=destY;


                                //update board model
                                boolean success=board.move(sourceX,sourceY,destX,destY);
                                
                               
                                //check if move success
                                if(success) {
                                    
                                    //if success update view
                                    view.getBoardPanel().getTile(destX,destY).assignIcon(board,destX,destY);
                                    view.getBoardPanel().getTile(sourceX,sourceY).assignIcon(board,sourceX,sourceY);
                                    
                                    board.printBoard();
                                    //reset source and dest
                                    source=null;
                                    dest=null;
                                    //display updated view
                                    view.getMainFrame().setVisible(true);

                                    //check condition
                                    int curColor=1-currentPlayer.getColor();
                                    boolean isCheck=board.isChecked(curColor);
                                    boolean isCheckMate=board.isCheckmate(curColor);
                                    boolean isStalemate=board.isStalemate(curColor);

                                    //change player
                                    if(currentPlayer.getColor()==1){
                                        currentPlayer = playerRed;
                                        
                                        view.highlightPlayer(0);
                                        view.unHighlightPlayer(1);
                                    }else{
                                        currentPlayer=playerGreen;
                                        view.highlightPlayer(1);
                                        view.unHighlightPlayer(0);
                                    }
                                    //check is in check
                                    if(isCheck && (!isCheckMate) ){
                                        JOptionPane.showMessageDialog(view.getMainFrame(),
                                                "Check! ");
                                    }else if(isCheckMate){
                                        //check if checkmate
                                        JOptionPane.showMessageDialog(view.getMainFrame(),
                                                "Checkmate! ");
                                        //end game
                                        if(currentPlayer.getColor()==1){
                                            currentGreenScore+=1;
                                            view.updateGreenPlayerScore(currentGreenScore);
                                        }else{
                                            currentRedScore+=1;
                                            view.updateRedPlayerScore(currentRedScore);
                                        }
                                        reset(length,width);


                                    }else if(isStalemate){
                                        //check if stalemate
                                        JOptionPane.showMessageDialog(view.getMainFrame(),
                                                "Stalemate! ");
                                        //end game
                                        view.updateGreenPlayerScore(currentGreenScore + 1);
                                        view.updateRedPlayerScore(currentRedScore + 1);
                                        
                                        reset(length,width);
                                    }
                                    
                                    turns++;
                                    view.updateTurns(turns);
                                   System.out.println(turns);
                                     if (turns % 4 == 0){
                                         
                                    System.out.println("transform");
                                    ArrayList transform = board.transform();
                                    for (int i = 0 ; i < transform.size() - 1  ; i = i + 2){
                                        view.getBoardPanel().getTile((int) transform.get(i),(int) transform.get(i+1) ).assignIcon(board,(int) transform.get(i),(int) transform.get(i+1));
                                    }
                                    
                                    }

                                }else{
                                    System.out.println("failed");
                                    //warn it's a illgle move
                                    JOptionPane.showMessageDialog(view.getMainFrame(),
                                            "Illegal Move! ");
                                    //reset
                                    source=null;
                                    dest=null;
                                    preSource=null;
                                    preDest=null;
                                }

                                
                            }
                        }
                        
                    }

                    @Override
                    public void mousePressed(final MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(final MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(final MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(final MouseEvent e) {

                    }


                });
            }
        }
    }
    
    public static void main(String[] args)
    {
        Controller ctrl = new Controller(8,5);
    }
    
}//END OF CLASS Controller
