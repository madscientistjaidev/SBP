import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class State
{
    /**Stores board as an integer array.*/
    int[][] board;
    
    /**Width of board.*/
    int w;
    
    /**Height of board.*/
    int h;
    
    /**Depth of recursion.*/
    int d;
    
    /**Initializes board from integer array.*/
    State(int state[][])
    {
        this.board = state;
        h = state.length;
        w = state[0].length;
    }
    
    /**Initializes board from Board object*/
    State(State s)
    {
        this.board = s.board;
        h = s.h;
        w = s.w;
    }
    
    /**Initializes board from file.*/
    State(String path)
    {
        board = loadGameState(path);
        
        if(board!=null)
        {
            h = board.length;
            w = board[0].length;
        }
    }
    
    /**Initializes blank board.*/
    State(){}
    
    ArrayList <Integer> getPieceList()
    {
        ArrayList <Integer> PL = new ArrayList<>();
        
        for(int x[]: board)
            for(int y : x)
                if(y>=2 && !PL.contains(y)) PL.add(y);
                        
        Collections.sort(PL);
        
        return PL;
    }
    
    /**Prints game board*/
    void outputGameState()
    {
        System.out.println(w+","+h+",");
        
        for(int x[] : board)
        {
            for(int y : x) System.out.print(y + ",");
            System.out.println();
        }
    }
    
    /**Returns clone of current board.*/
    State cloneState() {return new State(board);}

    /**Checks if a board is equal to current board.*/
    boolean stateEqual(State b)
    {
        int a[][] = b.board;
        
        if(a.length!=h) return false;
        if(a[0].length!=w) return false;
        
        for(int i=0; i<a.length; i++)
            for(int j=0; j<a[0].length; j++)
                if(a[i][j]!=board[i][j]) return false;
        
        return true;
    }
    
    boolean stateEqual(int a[][])
    {
        if(a.length!=h) return false;
        if(a[0].length!=w) return false;
        
        for(int i=0; i<a.length; i++)
            for(int j=0; j<a[0].length; j++)
                if(a[i][j]!=board[i][j]) return false;
        
        return true;
    }
    
    /**Checks if board is solved.*/
    boolean gameStateSolved()
    {       
        for(int x[]: board)
            for(int y : x)
                if(y==-1) return false;
        
        return true;
    }
    
    /**Loads games board from file.*/
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
                if (board[i][j]==idx1) board[i][j]=idx2;
                
                else if (board[i][j]==idx2) board[i][j]=idx1;
            }
        }
    } 
    
    void normalizeState()
    {
        int nextIdx = 3;
        
        for(int i = 0;i < h;i++)
            for(int j = 0;j < w;j++)
            {
                if (board[i][j]==nextIdx) nextIdx++;

                else if (board[i][j] > nextIdx)
                {
                    swapIdx(nextIdx,board[i][j]); 
                    nextIdx++;
                }  
            }
    }
    
    int getHeight() {return h;}
    
    int getWidth() {return w;}
    
    boolean isValid() {return board!=null;}
    
    /**Returns all moves possible for a given piece.*/
    ArrayList <Move> allMovesHelp(int a)
    {
        ArrayList <Move> MoveList = new ArrayList<>();
        
        boolean up = true;
        boolean down = true;
        boolean left = true;
        boolean right = true;
        
        for(int i=0; i<h; i++)
        {
            for(int j=0; j<w; j++)
            {
                if(board[i][j]==a)
                {
                    System.out.println("Found " + a + " at " + i + "," + j);
                    
                    if(i==0) up = false;
                    if(i==h) down = false;
                    if(j==0) left = false;
                    if(j==w) right = false;
                    
                    if (board[i-1][j]!=a || board[i-1][j]!=0) up = false;
                    if (board[i+1][j]!=a || board[i+1][j]!=0) down = false;
                    if (board[i][j-1]!=a || board[i][j-1]!=0) left = false;
                    if (board[i][j+1]!=a || board[i][j+1]!=0) right = false;
                }
            }
        }
        
        if(up==true) MoveList.add(new Move(a,Direction.up));
        if(down==true) MoveList.add(new Move(a,Direction.down));
        if(left==true) MoveList.add(new Move(a,Direction.left));
        if(right==true) MoveList.add(new Move(a,Direction.right));
        
        return MoveList;
    }
    
    /**Returns all moves possible for all pieces.*/
    ArrayList <Move> allMoves()
    {
        ArrayList <Move> MoveList = new ArrayList<>();

        ArrayList <Integer> PieceList = getPieceList();
        PieceList.stream().forEach((PieceNo) -> {
            MoveList.addAll(allMovesHelp(PieceNo));
        });
                
        return MoveList;
    }
    
    /**Applies move to current state.*/
    void applyMove(Move m)
    {
        int PieceNo = m.piece;
        Direction d = m.direction;
        
        switch(d)
        {
            case up:
                for(int i=0; i<h; i++)
                {
                    for(int j=0; j<w; j++)
                    {
                        if(board[i][j]==PieceNo)
                        {
                            board[i-1][j] = PieceNo;
                            board[i][j] = 0;
                        }
                    }
                }
                
                break;
            
            
            case down:
                for(int i=h; i>=0; i--)
                {
                    for(int j=0; j<w; j++)
                    {
                        if(board[i][j]==PieceNo)
                        {
                            board[i+1][j] = PieceNo;
                            board[i][j] = 0;
                        }
                    }
                }
                break;
            
            
            case left:
                for(int i=0; i<h; i++)
                {
                    for(int j=0; j<w; j++)
                    {
                        if(board[i][j]==PieceNo)
                        {
                            board[i][j-1] = PieceNo;
                            board[i][j] = 0;
                        }
                    }
                }
                break;
            
            
            case right:
                for(int i=0; i<h; i++)
                {
                    for(int j=w; j>=0; j--)
                    {
                        if(board[i][j]==PieceNo)
                        {
                            board[i][j+1] = PieceNo;
                            board[i][j] = 0;
                        }
                    }
                }
                break;
            
            default: break;
        }
    }
    
    /**Applies move to and returns a copy of current state.*/
    State applyMoveCloning(Move m)
    {
        int PieceNo = m.piece;
        Direction d = m.direction;
        
        int NewBoard[][] = board;
        
        switch(d)
        {
            case up:
                for(int i=0; i<h; i++)
                {
                    for(int j=0; j<w; j++)
                    {
                        if(NewBoard[i][j]==PieceNo)
                        {
                            NewBoard[i-1][j] = PieceNo;
                            NewBoard[i][j] = 0;
                        }
                    }
                }
                
                break;
            
            
            case down:
                for(int i=h-1; i>=0; i--)
                {
                    for(int j=0; j<w; j++)
                    {
                        if(NewBoard[i][j]==PieceNo)
                        {
                            NewBoard[i+1][j] = PieceNo;
                            NewBoard[i][j] = 0;
                        }
                    }
                }
                break;
            
            
            case left:
                for(int i=0; i<h; i++)
                {
                    for(int j=0; j<w; j++)
                    {
                        if(NewBoard[i][j]==PieceNo)
                        {
                            NewBoard[i][j-1] = PieceNo;
                            NewBoard[i][j] = 0;
                        }
                    }
                }
                break;
            
            
            case right:
                for(int i=0; i<h; i++)
                {
                    for(int j=w-1; j>=0; j--)
                    {
                        if(NewBoard[i][j]==PieceNo)
                        {
                            NewBoard[i][j+1] = PieceNo;
                            NewBoard[i][j] = 0;
                        }
                    }
                }
                break;
            
            default: break;
        }
        
        State NewState = new State(NewBoard);
                
        return NewState;
    }
    
    /**Returns Manhattan distance between Master brick and goal.*/
    int ManhattanDistance()
    {
        int MasterMaxX=-1, MasterMinX=w, MasterMaxY=-1, MasterMinY=w;
        int GoalMaxX=-1, GoalMinX=w, GoalMaxY=-1, GoalMinY=h;
        
        for(int i=0; i<h; i++)
            {
                for(int j=0; j<w; j++)
                {
                    if(board[i][j]==2)
                    {
                        if(j>MasterMaxX) MasterMaxX = j;
                        if(j<MasterMinX) MasterMinX = j;
                        if(i>MasterMaxY) MasterMaxY = i;
                        if(i<MasterMinY) MasterMinY = i;
                    }
                    
                    if(board[i][j]==-1)
                    {
                        if(j>GoalMaxX) GoalMaxX = j;
                        if(j<GoalMinX) GoalMinX = j;
                        if(i>GoalMaxY) GoalMaxY = i;
                        if(i<GoalMinY) GoalMinY = i;
                    }
                }
            }
        
        int xDist = Math.abs(MasterMaxX - GoalMaxX) - (MasterMaxX - MasterMinX - 1);
        int yDist = Math.abs(MasterMaxY - GoalMaxY) - (MasterMaxY - MasterMinY - 1);
        
        System.out.println(xDist + "," + yDist);
        
        return xDist + yDist;
    }
}