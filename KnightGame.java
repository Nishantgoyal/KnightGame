import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KnightGame {

    Calendar start = Calendar.getInstance();
    
    private JFrame frame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private MyPanel panel = new MyPanel();

    private MyButton[][] button = new MyButton[8][8];
    private JButton backButton = new JButton("Back");
    private JButton solutionButton = new JButton("Solution");

    private JTextField textField = new JTextField();
    private JTextField timeField = new JTextField("    00:00:00");

    private int[][] chessBoard = new int[8][8];
    private int[] iniPos = new int[2];
    private int NoOfMoves = 0;

    private boolean iniPosSet = false;
    private boolean correctMove = true;
    private boolean solutioning = false;

    Image image = new ImageIcon("Knight.png").getImage();

    javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent ae) {
            updateTime();
        }
    });
    private Deque<MyButton> stack = new ArrayDeque<MyButton>(64);
    
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
            }
        }
        mainPanel.setLayout(new GridLayout(8,8));
        mainPanel.setVisible(true);
        mainPanel.setSize(600,600);
        mainPanel.setLocation(0,0);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setSize(1000,700);
        frame.setLocation(0,0);
        frame.getContentPane().add(mainPanel);
        frame.setTitle("KnightTour");

        panel.setLayout(null);
        panel.setVisible(true);
        panel.setSize(200,600);
        panel.setLocation(700,0);

        frame.getContentPane().add(panel);
        panel.add(backButton);
        backButton.addActionListener(new backButton());
        backButton.setBackground(Color.pink);
        backButton.setBounds(0, 0, 200, 50);
        panel.add(textField);
        textField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        textField.setBounds(0, 50, 200, 50);
        panel.add(solutionButton);
        panel.add(timeField);
        solutionButton.setBounds(0, 100, 200, 50);
        timeField.setBounds(0, 150, 200, 50);
        timeField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        solutionButton.setBackground(Color.pink);
        solutionButton.addActionListener(new Solution());

        for(i=0;i<8;i++) {
            for(j=0;j<8;j++) {
                button[i][j] = new MyButton(i,j);
                button[i][j].addActionListener(new buttonListen(i,j));
                button[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
                mainPanel.add(button[i][j]);
                setButtonColor(i, j);
            }
        }
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE) ;
        timer.start();
    }
    boolean checkmove(int iniX, int iniY, int finX, int finY){
        if((finX == iniX + 1) && (finY == iniY + 2)) return true;
        else if((finX == iniX + 1) && (finY == iniY - 2)) return true;
        else if((finX == iniX - 1) && (finY == iniY + 2)) return true;
        else if((finX == iniX - 1) && (finY == iniY - 2)) return true;
        else if((finX == iniX + 2) && (finY == iniY + 1)) return true;
        else if((finX == iniX + 2) && (finY == iniY - 1)) return true;
        else if((finX == iniX - 2) && (finY == iniY + 1)) return true;
        else if((finX == iniX - 2) && (finY == iniY - 1)) return true;
        else return false;
    }
    void updateTime() {
        Calendar curTime = Calendar.getInstance();
        long time = curTime.getTimeInMillis() - start.getTimeInMillis();
        timeField.setText(convertToString(time));
    }
    
    String convertToString(long A) {
        A = A / 1000;
        String sec = Integer.toString((int) A % 60);
        if(Integer.parseInt(sec) < 10) sec = "0" + sec;
        A = A / 60;
        String min = Integer.toString((int) A % 60);
        if(Integer.parseInt(min) < 10) min = "0" + min;
        A = A / 60;
        String hr = Integer.toString((int) A % 24);
        if(Integer.parseInt(hr) < 10) hr = "0" + hr;
        String time ="    " + hr + ":" + min + ":" + sec;
        return time;
    }
    public void setButtonColor(int x, int y) {
        if(!button[x][y].checkClick()) {
            if((x+y)%2==0){
                button[x][y].setBackground(Color.yellow);
            }else {
                button[x][y].setBackground(Color.white);
            }
        }
    }
    public void highLight(int x, int y) {
        if(check(x+1,y+2) && button[x+1][y+2].checkClick() == false) button[x+1][y+2].setBackground(Color.red);
        if(check(x+1,y-2) && button[x+1][y-2].checkClick() == false) button[x+1][y-2].setBackground(Color.red);
        if(check(x-1,y+2) && button[x-1][y+2].checkClick() == false) button[x-1][y+2].setBackground(Color.red);
        if(check(x-1,y-2) && button[x-1][y-2].checkClick() == false) button[x-1][y-2].setBackground(Color.red);
        if(check(x+2,y+1) && button[x+2][y+1].checkClick() == false) button[x+2][y+1].setBackground(Color.red);
        if(check(x+2,y-1) && button[x+2][y-1].checkClick() == false) button[x+2][y-1].setBackground(Color.red);
        if(check(x-2,y+1) && button[x-2][y+1].checkClick() == false) button[x-2][y+1].setBackground(Color.red);
        if(check(x-2,y-1) && button[x-2][y-1].checkClick() == false) button[x-2][y-1].setBackground(Color.red);
    }
    public void deHighLight(int x, int y) {
        if(check(x+1,y+2))setButtonColor(x+1, y+2);
        if(check(x+1,y-2))setButtonColor(x+1, y-2);
        if(check(x-1,y+2))setButtonColor(x-1, y+2);
        if(check(x-1,y-2))setButtonColor(x-1, y-2);
        if(check(x+2,y+1))setButtonColor(x+2, y+1);
        if(check(x+2,y-1))setButtonColor(x+2, y-1);
        if(check(x-2,y+1))setButtonColor(x-2, y+1);
        if(check(x-2,y-1))setButtonColor(x-2, y-1);

    }
    boolean check(int x, int y) {
        if(x >= 0 && x <=7 && y >=0 && y <= 7) return true;
        else return false;
    }
    public class buttonListen implements ActionListener {


        int i,j;
        String string;

        public buttonListen(int a, int b) {
            i = a;
            j = b;
        }

        public void actionPerformed(ActionEvent e) {
            if(!solutioning) {
                frame.setTitle("KnightTour");
                if(iniPosSet) {
                    correctMove = checkmove(iniPos[0], iniPos[1], i, j);
                    if(correctMove) {
                        stack.push(button[i][j]);
                        deHighLight(iniPos[0], iniPos[1]);
                        highLight(i,j);
                        button[i][j].Click();
                        NoOfMoves++;
                        button[i][j].setBackground(Color.black);
                        iniPos[0] = i;
                        iniPos[1] = j;
                    } else {
                        frame.setTitle("Invalid Knight Move");
                    }
                    if(NoOfMoves == 64) {
                        frame.setTitle("You Win");
                        timer.stop();
                    }
                }  else {
                    iniPos[0] = i;
                    iniPos[1] = j;
                    button[i][j].Click();
                    button[i][j].setBackground(Color.black);
                    stack.push(button[i][j]);
                    highLight(i,j);
                    NoOfMoves++;
                }
                iniPosSet = true;
            }
            else {
                int k = 0;
                KnightTour knightMove = new KnightTour(i,j);
                knightMove.initialize();
                int[] pos = new int[2];
                pos[0] = i;
                pos[1] = j;
                string = Integer.toString(k);
                button[pos[0]][pos[1]].setText(string);
                try {
                    Thread.sleep(1000);
                } catch(Exception ex) {}
                while(k < 63) {
                    pos = knightMove.move(pos[0],pos[1]);
                    System.out.print(pos[0] + "" + pos[1] + " ");
                    if((k + 1) % 8 == 0) System.out.println();
                    k++;
                    string = Integer.toString(k);
                    button[pos[0]][pos[1]].setText(string);
                }
            }
        }
    }
    public class backButton implements ActionListener {
        
        String data;
        int backMoves;
        MyButton tempbutton;
        int pos[] = new int[2];

        public void actionPerformed(ActionEvent ae) {
            backMoves = 0;
            data = textField.getText();
            try {
                backMoves = Integer.parseInt(data);
            } catch(Exception ex) {
                frame.setTitle("Invalid data");
            }
            if(backMoves != 0) {
                frame.setTitle("Knight Tour");
                for(int i = 1;i <= backMoves;i++) {
                   if(stack.peek() != null) {
                       tempbutton = stack.pop();
                       pos = tempbutton.getCoords();
                       button[pos[0]][pos[1]].unClick();
                       deHighLight(pos[0], pos[1]);
                       NoOfMoves--;
                   }
                }
                if(stack.peek() != null) {
                    tempbutton = stack.peek();
                    pos = tempbutton.getCoords();
                    highLight(pos[0], pos[1]);
                    iniPos = pos;
                    button[pos[0]][pos[1]].setBackground(Color.black);
                }
                else {
                    setButtonColor(pos[0], pos[1]);
                    iniPosSet = false;
                }
            }
        }
    }

    class Solution implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            solutioning = true;
            for(int i = 0;i < 8;i++) {
                for(int j = 0;j < 8;j++) {
                    button[i][j].unClick();
                    setButtonColor(i, j);
                }
            }
            frame.setTitle("Select Initial Position");
        }
    }

    class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(image, 0, 200, this);
        }
    }
}
