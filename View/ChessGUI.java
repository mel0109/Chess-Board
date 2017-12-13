package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.border.*;
import java.util.concurrent.TimeUnit;

import Model.Board;

public class ChessGUI{
    private final JFrame mainFrame;
    private  boardPanel board;
    private  Board  chessBoard;
    private JPanel menu;
    private JPanel scoreBoard;
    
    private JButton newGame;
    private JButton forfeitG;
    private JButton forfeitR;
    private JButton undo;
    private JButton saveGame;
    private JButton loadGame;
    
    private JLabel greenPlayerName;
    private JLabel redPlayerName;
    private JLabel greenPlayerScore;
    private JLabel redPlayerScore;
    private JLabel turnCounter;
    private JLabel turnWord;
    

    private ImageIcon greenStar;
    private ImageIcon greenHeart;
    private ImageIcon greenCross;
    private ImageIcon greenArrowBox;
    private ImageIcon redCross;
    private ImageIcon redStar;
    private ImageIcon redHeart;
    private ImageIcon redArrowBox;
    
    //ICONPATH
    private static String ICONPATH="src/Icon/";
    private static String ROW_LABEL = "ABCDE";
    
    public final int BOARD_LENGTH = 8;
    public final int BOARD_WIDTH = 5;
    private static Dimension OUTER_FRAME=new Dimension(600,700);
    private static Dimension BOARD_DIMENSION=new Dimension(400,350);
    private static Dimension TILE_DIMENSION=new Dimension(10,10); 

    //CONSTRUCTOR
    public ChessGUI(Board chessBoard){
         //Initialize to standard chessboard
        this.chessBoard = chessBoard; 
        //Initialize frame
        this.mainFrame = new JFrame("Barsoomian Chess");
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.setSize(900,800);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setLocationRelativeTo(null);


        //add menu which consist of new game, resize, forfeitG, forfeitR and undo buttons to frame
        menu = new JPanel(new GridLayout(0,7,3,3));
        menu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));//(x,y,width,height)
        newGame = new JButton("New");
        newGame.setFont(new Font("Arial",Font.PLAIN, 15));

        forfeitG = new JButton("Forfeit Green");
        forfeitG.setFont(new Font("Arial",Font.PLAIN, 15));
        forfeitG.setBackground(Color.GREEN);
        forfeitR = new JButton("Forfeit Red");
        forfeitR.setFont(new Font("Arial",Font.PLAIN, 15));
        forfeitR.setBackground(Color.RED);
        undo = new JButton("Undo");
        undo.setFont(new Font("Arial",Font.PLAIN, 15));
        saveGame = new JButton("Save");
        saveGame.setFont(new Font("Arial",Font.PLAIN, 15));
        loadGame = new JButton("Load");
        loadGame.setFont(new Font("Arial",Font.PLAIN, 15));
        
        menu.add(newGame);
        menu.add(saveGame);
        menu.add(loadGame);
        menu.add(forfeitG);
        menu.add(forfeitR);
        menu.add(undo);
        
        this. mainFrame.add(menu, BorderLayout.NORTH);

        //add score board to frame
        scoreBoard = new JPanel(new GridLayout(3,2));
        scoreBoard.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        //scoreBoard.setBackground(Color.WHITE);
        turnWord = new JLabel("    Turn :");
        turnCounter = new JLabel("    0", SwingConstants.CENTER);
        turnWord.setFont(new Font("Arial", Font.BOLD, 20));
        turnWord.setForeground(Color.BLUE);
        turnCounter.setFont(new Font("Arial", Font.BOLD, 20));
        turnCounter.setForeground(Color.BLUE);
        greenPlayerName = new JLabel("    Green Player");
        greenPlayerName.setFont(new Font("Arial", Font.PLAIN, 20));
       // greenPlayerName.setBackground(Color.cyan);
        greenPlayerName.setOpaque(true);
        //greenPlayerName.setForeground(Color.CYAN);
        greenPlayerScore = new JLabel("0", SwingConstants.CENTER);
        greenPlayerScore.setFont(new Font("Arial", Font.PLAIN, 20));
        redPlayerName = new JLabel("    Red Player");
        redPlayerName.setFont(new Font("Arial", Font.PLAIN, 20));
        redPlayerScore = new JLabel("0", SwingConstants.CENTER);
        redPlayerScore.setFont(new Font("Arial", Font.PLAIN, 20));

        scoreBoard.add(greenPlayerName); 
        scoreBoard.add(greenPlayerScore);
        scoreBoard.add(turnWord);
        scoreBoard.add(turnCounter);
        scoreBoard.add(redPlayerName);
        scoreBoard.add(redPlayerScore);
        scoreBoard.setVisible(true);
        this.mainFrame.add(scoreBoard, BorderLayout.WEST);

        //initialize the chess  board
        this.board=new boardPanel();
        this.mainFrame.add(this.board,BorderLayout.CENTER);
        this.board.setBounds(0,50,600,600);
        this.mainFrame.setVisible(true);		
}
    
    //SET AND GET
    public void updateTurns(int turns){
        turnCounter.setText(Integer.toString(turns));
    }
   
    public void updateGreenPlayerName(String name){
        greenPlayerName.setText(name);
    }

    public void updateRedPlayerName(String name){
    redPlayerName.setText(name);
    }
	
    public void updateGreenPlayerScore(int score){
        greenPlayerScore.setText(Integer.toString(score));
    }

    public void updateRedPlayerScore(int score){
        redPlayerScore.setText(Integer.toString(score));
    }
   
   public JFrame getMainFrame(){
        return this.mainFrame;
    }
    
    public void newGame(Board chessBoard){
        this.chessBoard=chessBoard;
        //remove original board panal
        this.mainFrame.remove(board);
        this.board=new boardPanel();
        //add a new board panal
        this.mainFrame.add(board);
        this.board.setBounds(0,50,600,600);
        this.mainFrame.setVisible(true);
    }
    
    public boardPanel getBoardPanel(){
        return this.board;
    }
    
    public void highlightPlayer(int player)
    {
        if (player == 0)
        {
            redPlayerName.setOpaque(true);
            redPlayerScore.setOpaque(true);
            redPlayerName.setBackground(Color.RED);
            redPlayerScore.setBackground(Color.RED);
            
        }
        else if (player == 1)
        {
               greenPlayerName.setOpaque(true);
               greenPlayerScore.setOpaque(true);
               greenPlayerName.setBackground(Color.GREEN);
               greenPlayerScore.setBackground(Color.GREEN);
        }
    }
        
        public void unHighlightPlayer(int player)
    {
        if (player == 0)
        {
            redPlayerName.setOpaque(true);
            redPlayerName.setBackground(null);
            redPlayerScore.setBackground(null);
            
        }
        else if (player == 1){
               greenPlayerName.setOpaque(true);
                greenPlayerName.setBackground(null);
                greenPlayerScore.setBackground(null);
        }
    }
       
        
    // LISTENERS 
    public void newGameListener(ActionListener e) {
        newGame.addActionListener(e);
    }

    public void forfeitGreenListener(ActionListener e) {
        forfeitG.addActionListener(e);
    }

    public void forfeitRedListener(ActionListener e) {
         forfeitR.addActionListener(e);
    }

    public void undoListener(ActionListener e) {
        undo.addActionListener(e);
    }
    
    public void saveGameListener(ActionListener e) {
        saveGame.addActionListener(e);
    }
    
    public void loadGameListener(ActionListener e) {
        loadGame.addActionListener(e);
    }

    //CREATES Chessboard Panel
    public class boardPanel extends JPanel{
        
            tilePanel[][] tilesList=new tilePanel[BOARD_LENGTH][BOARD_WIDTH];
            
            //CONSTRUCTOR
            boardPanel(){
                
                super(new GridLayout(BOARD_LENGTH+1,BOARD_WIDTH+1));
                
                for(int i = 0 ; i < BOARD_LENGTH + 1; i++ ){
                    for(int j = 0 ; j < BOARD_WIDTH + 1 ; j++ ) {
                        if(j == 0 ){
                            JPanel numPanel=new JPanel();
                            if(i == 8 ){
                                numPanel.add(new JLabel(""));
                                
                            }else {
                                numPanel.add(new JLabel("" + (9 - i-1),SwingConstants.CENTER));
                            }
                            this.add(numPanel);
                        }
                        else if(i==8){
                            JPanel letterPanel=new JPanel();
                            letterPanel.add(new JLabel(ROW_LABEL.substring(j-1,j),SwingConstants.CENTER));
                            this.add(letterPanel);
                        }
                        else {
                            tilePanel tile = new tilePanel(j-1, 7-i);
                            //add to list
                            this.tilesList[i][j-1] = tile;
                             
                            //add to boardPanel
                            this.add(tile);
                        }
                    }
                }

                printTileList();
                setPreferredSize(BOARD_DIMENSION);
                validate();
            }

            public void printTileList(){
                System.out.println("PRINGTING GUI**************");
                for(int i=0;i<BOARD_LENGTH;i++){
                    for(int j=0;j<BOARD_WIDTH;j++){
                        System.out.print(this.tilesList[i][j].getPieceName());
                        System.out.print(" ");
                    }
                    System.out.println();
                }
            }

            public tilePanel getTile(int x,int y){
                return tilesList[7-y][x];
            }


        }

    //BUTTONS on chessboard
    public class tilePanel extends JButton{
            private  int tileX;
            private  int tileY;
            private String pieceName ="NL";

            tilePanel(int x, int y){
                super();
                this.tileX=x;
                this.tileY=y;
                setPreferredSize(TILE_DIMENSION);
                Insets buttonMargin = new Insets(0, 0, 0, 0);
                this.setMargin(buttonMargin);
                this.assignColor(x, y);
                assignIcon(chessBoard, x, y);
                validate();
            }

            public int getTileX(){
                return tileX;
            }

            public int getTileY(){
                return tileY;
            }


            private void assignColor(int x,int y) {

                this.setOpaque(true);
                this.setBorderPainted(true);
                setBackground(Color.WHITE);
                
            }
            
            public void highlightPath(int x, int y){
                
                this.setOpaque(true);
                setBackground(Color.YELLOW);
            }
            
            public void unhighlightPath(int x, int y){
                
                this.setOpaque(true);
                setBackground(Color.WHITE);
            }

            /**
             *
              * @param board
             * @param x
             * @param y
             */
            public void assignIcon(Board board,int x,int y){
                    this.removeAll();
                    // board panel is vertically flipped chessboard
                    if(!chessBoard.isEmpty(x,y)){
                        String name=chessBoard.getPiece(x,y).getName();
                        try{
                            final BufferedImage image=ImageIO.read(new File(ICONPATH+name+".png"));
                            ImageIcon ic=new ImageIcon(image);
                            this.setIcon(ic);
                            this.pieceName=name;

                        }catch(IOException exception){
                            exception.printStackTrace();
                        }
                    }else{
                        setIcon(null);
                        this.pieceName="NL";
                    }
            }


            public String getPieceName(){
                return this.pieceName;
            }

            /**
             *
             * @param name
             */
            public void setPieceName(String name){
                System.out.print("In setPieceName: ");
                this.pieceName=name;
                System.out.println(this.pieceName);
            }


        }

    public void update(){
            this.mainFrame.setVisible(true);
        }
    
}





