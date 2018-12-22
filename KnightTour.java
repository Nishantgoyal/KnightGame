
public class KnightTour {
    
    private int[][] chessBoard = new int[8][8];
    private int[] iniPos = new int[2];

    public KnightTour(int x, int y) {
        iniPos[0] = x;
        iniPos[1] = y;        
    }

    void initialize() {
        int i,j;
        for(i = 0;i < 8;i++) {
            for(j = 0; j < 8; j++) {
                if(i == 0 || j == 0 || i == 7 || j == 7)  {
                    if((i + j) % 7 == 0) chessBoard[i][j] = 2;
                    else if(i == 1 || j == 1 || i == 6 || j == 6) chessBoard[i][j] = 3;
                    else chessBoard[i][j] = 4;
                }
                else if(i == 1 || j == 1 || i == 6 || j == 6) {
                    if((i == j) || ((i + j) % 7 == 0)) chessBoard[i][j] = 4;
                    else chessBoard[i][j] = 6;
                }
                else chessBoard[i][j] = 8;
                System.out.print(chessBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    int[] move(int x, int y) {
        int[] pos = new int[2];
        int min = 1000;
        decrease(x,y);
        chessBoard[x][y] = 1000;
        if(check(x+1,y+2) && min > chessBoard[x+1][y+2]) {
            pos[0] = x + 1;
            pos[1] = y + 2;
            min = chessBoard[pos[0]][pos[1]];
        }
        if(check(x+1,y-2) && min > chessBoard[x+1][y-2]) {
            pos[0] = x + 1;
            pos[1] = y - 2;
            min = chessBoard[pos[0]][pos[1]];
        }
        if(check(x-1,y+2) && min > chessBoard[x-1][y+2]) {
            pos[0] = x - 1;
            pos[1] = y + 2;
            min = chessBoard[pos[0]][pos[1]];
        }
        if(check(x-1,y-2) && min > chessBoard[x-1][y-2]) {
            pos[0] = x - 1;
            pos[1] = y - 2;
            min = chessBoard[pos[0]][pos[1]];
        }
        if(check(x+2,y+1) && min > chessBoard[x+2][y+1]) {
            pos[0] = x + 2;
            pos[1] = y + 1;
            min = chessBoard[pos[0]][pos[1]];
        }
        if(check(x+2,y-1) && min > chessBoard[x+2][y-1]) {
            pos[0] = x + 2;
            pos[1] = y - 1;
            min = chessBoard[pos[0]][pos[1]];
        }
        if(check(x-2,y+1) && min > chessBoard[x-2][y+1]) {
            pos[0] = x - 2;
            pos[1] = y + 1;
            min = chessBoard[pos[0]][pos[1]];
        }
        if(check(x-2,y-1) && min > chessBoard[x-2][y-1]) {
            pos[0] = x - 2;
            pos[1] = y - 1;
            min = chessBoard[pos[0]][pos[1]];
        }
        return pos;
    }

    boolean check(int x, int y) {
        if(x >= 0 && x <=7 && y >=0 && y <= 7) return true;
        else return false;
    }

    void decrease(int x, int y) {
        if(check(x+1,y+2)) chessBoard[x+1][y+2]--;
        if(check(x+1,y-2)) chessBoard[x+1][y-2]--;
        if(check(x-1,y+2)) chessBoard[x-1][y+2]--;
        if(check(x-1,y-2)) chessBoard[x-1][y-2]--;
        if(check(x+2,y+1)) chessBoard[x+2][y+1]--;
        if(check(x+2,y-1)) chessBoard[x+2][y-1]--;
        if(check(x-2,y+1)) chessBoard[x-2][y+1]--;
        if(check(x-2,y-1)) chessBoard[x-2][y-1]--;
    }

}