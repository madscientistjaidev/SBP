import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Board
{
    /**Stores board as an integer array.*/
    private int state[][];
    
    //Width and height of board.
    private int w,h;
    
    /**Stores list of pieces.*/
    private ArrayList <Integer> PieceList;
    
    /**Initializes board from integer array.*/
    Board(int state[][])
    {
        this.state = state;
        h = state.length;
        w = state[0].length;
        PieceList = getPieceList();
    }
    
    /**Initializes board from Board object*/
    Board(Board b)
    {
        this.state = b.state;
        h = b.getHeight();
        w = b.getWidth();
        PieceList = getPieceList();
    }
    
    /**Initializes board from file.*/
    Board(String path)
    {
        state = loadGameState(path);
        
        if(state!=null)
        {
            h = state.length;
            w = state[0].length;
        }
        
        PieceList = getPieceList();
    }
    
    /**Initializes blank board.*/
    Board(){}
    
    ArrayList <Integer> getPieceList()
    {
        
        
        for(int x[]: state)
            for(int y : x)
                if(y>2 && !PieceList.contains(y))
                {
                    
                }
    }
    
    /**Prints game state*/
    void outputGameState()
    {
        System.out.println(w+","+h+",");
        
        for(int x[] : state)
        {
            for(int y : x) System.out.print(y + ",");
            System.out.println();
        }
    }
    
    /**Returns clone of current state.*/
    Board cloneState() {return new Board(state);}

    /**Checks if a state is equal to current state.*/
    boolean stateEqual(Board b)
    {
        int a[][] = b.state;
        
        if(a.length!=h) return false;
        if(a[0].length!=w) return false;
        
        for(int i=0; i<a.length; i++)
            for(int j=0; j<a[0].length; j++)
                if(a[i][j]!=state[i][j]) return false;
        
        return true;
    }
    
    boolean stateEqual(int a[][])
    {
        if(a.length!=h) return false;
        if(a[0].length!=w) return false;
        
        for(int i=0; i<a.length; i++)
            for(int j=0; j<a[0].length; j++)
                if(a[i][j]!=state[i][j]) return false;
        
        return true;
    }
    
    /**Checks if state is solved.*/
    boolean gameStateSolved()
    {       
        for(int x[]: state)
            for(int y : x)
                if(y==-1) return false;
        
        return true;
    }
    
    /**Loads games state from file.*/
    private int[][] loadGameState(String path)
    {
        //Objects to load and parse file.
        File f;
        Scanner ParseFile;
        Scanner ParseLine;
        int w,h;
        
        try
        {
            f = new File(path);
            ParseFile = new Scanner(f);
        }
        
        catch(FileNotFoundException e) {return null;}
        
        String line = ParseFile.nextLine();
        ParseLine = new Scanner(line).useDelimiter(",");
        
        //Width and height of board.
        try
        {
            w = ParseLine.nextInt();
            h = ParseLine.nextInt();
        }
        
        catch(NumberFormatException | InputMismatchException e) {return null;}
        
        //Stores values read from game file.
        int NewBoard[][] = new int[h][w];
        
        for(int i=0; i<h; i++)
        {
            if(!ParseFile.hasNextLine()) return null;
            
            line = ParseFile.nextLine();
            ParseLine = new Scanner(line).useDelimiter(",");
            
            for(int j=0; j<w; j++)
            {
                if(!ParseLine.hasNextInt()) return null;
                NewBoard[i][j] = ParseLine.nextInt();
            }
        }
        
        return NewBoard;
    }
    
    void swapIdx(int idx1,int idx2)
    {
        for(int i = 0;i < h;i++)
        {
            for(int j = 0;j < w;j++)
            {
                if (state[i][j]==idx1) state[i][j]=idx2;
                
                else if (state[i][j]==idx2) state[i][j]=idx1;
            }
        }
    } 
    
    void normalizeState()
    {
        int nextIdx = 3;
        
        for(int i = 0;i < h;i++)
            for(int j = 0;j < w;j++)
            {
                if (state[i][j]==nextIdx) nextIdx++;

                else if (state[i][j] > nextIdx)
                {
                    swapIdx(nextIdx,state[i][j]); 
                    nextIdx++;
                }  
            }
        
        PieceList = getPieceList();
    }
    
    int getHeight() {return h;}
    
    int getWidth() {return w;}
    
    boolean isValid() {return state!=null;}
    
    ArrayList allMovesHelp(int a)
    {
        ArrayList <Move> MoveList = new ArrayList<>();
        
        boolean up = true;
        boolean down = true;
        boolean left = true;
        boolean right = true;
        
        for(int i = 0;i < h;i++)
        {
            for(int j = 0;j < w;j++)
            {
                if(state[i][j]==a)
                {
                    if(i!=0 && (state[i-1][j]!=a || state[i-1][j]!=0)) up = false;
                    if(i!=h && (state[i+1][j]!=a || state[i+1][j]!=0)) down = false;
                    if(j!=0 && (state[i][j-1]!=a || state[i][j-1]!=0)) left = false;
                    if(j!=0 && (state[i][j+1]!=a || state[i][j+1]!=0)) right = false;
                }
            }
        }
        
        if(up==true) MoveList.add(new Move(a,Direction.up));
        if(down==true) MoveList.add(new Move(a,Direction.down));
        if(left==true) MoveList.add(new Move(a,Direction.left));
        if(right==true) MoveList.add(new Move(a,Direction.right));
        
        return MoveList;
    }
}