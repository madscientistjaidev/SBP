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
    
    /**Initializes board from integer array.*/
    Board(int state[][])
    {
        this.state = state;
        h = state.length;
        w = state[0].length;
    }
    
    /**Initializes board from Board object*/
    Board(Board b)
    {
        this.state = b.state;
        h = b.getHeight();
        w = b.getWidth();
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
    }
    
    /**Initializes blank board.*/
    Board(){}
    
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
    }
    
    int getHeight() {return h;}
    
    int getWidth() {return w;}
    
    boolean isValid() {return state!=null;}
    
    ArrayList allMovesHelp(int a)
    {
        ArrayList MoveList = new ArrayList();
        
        for(int x[] : state)
        {
            for(int y : x)
            {
                if(y==a)
                {
                    
                }
            }
        }
        
        return null;
    }
}