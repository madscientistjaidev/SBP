public class Piece
{
    int Q1X, Q1Y, Q2X, Q2Y, Q3X, Q3Y, Q4X, Q4Y;

    int [][] board;

    int pieceNumber;

    Piece(int [] coord, int[][] board, int PieceNo)
    {
            this.Q2X = coord[0];
            this.Q2Y = coord[1];
            this.Q3X = coord[2];
            this.Q3Y = coord[3];
            this.Q1X = coord[4];
            this.Q1Y = coord[5];
            this.Q4X = coord[6];
            this.Q4Y = coord[7];
            this.board = board;
            this.pieceNumber = PieceNo;
    }

    public boolean MoveUp()
    {
        for (int j = Q2Y; j <= Q1Y; j++)
            if (board[Q2X - 1][j] != 0) return false;
        
        return true;
    }

    public boolean MoveDown()
    {
        for (int j = Q3Y; j <= Q4Y; j++)
            if (board[Q4X + 1][j] != 0) return false;
            
        return true;
    }

    public boolean MoveLeft()
    {
        for (int i = Q2X; i <= Q3X; i++)
            if (board[i][Q3Y - 1] != 0) return false;
        
        return true;
    }

    public boolean MoveRight()
    {
        for (int i = Q2X; i <= Q3X; i++)
            if (board[i][Q4Y + 1] != 0) return false;
                            
        return true;
    }
}