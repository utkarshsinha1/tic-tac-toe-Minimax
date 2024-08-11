                                    /** Contributed By -Utkarsh Sinha **/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


 class TicTacToe extends JPanel {
    private String[][] board = {
            {"", "", ""},
            {"", "", ""},//<------informat
            {"", "", ""}
    };

    final int SIZE = 3;
    final String AI = "X";
    final String HUMAN = "O";
     //------------------------------
    
    String currentPlayer = HUMAN;
     int w;
     int h;

    public TicTacToe() {
    this.setPreferredSize(new Dimension(400, 400));//usika hai
      this.w = 400 / SIZE;
      this.h = 400 / SIZE;
        this.addMouseListener(new MouseAdapter() 
        {//psodu class
           
            public void mousePressed(MouseEvent e) {
                if (currentPlayer.equals(HUMAN)) {
                    int i = e.getX()/w;
                    int j = e.getY() / h;
                    if (board[i][j].isEmpty()) {
                        board[i][j] = HUMAN;
                        currentPlayer = AI;
                        bestMove();
                        repaint();
                    }
                }
            }
        });
        
        bestMove();
    }
    //main fuc to see for wins
    public boolean equals3(String a, String b, String c) 
    {
        boolean bb=a.equals(b) && b.equals(c) && !a.isEmpty();
        return bb;}
/** ----------------------------------------------------------------------------------------- **/
    private String checkWinner() 
    {
        String winner = null;
        //            ---
        
        for (int i = 0; i < 3; i++) {
            if (equals3(board[i][0], board[i][1], board[i][2])) {
                winner = board[i][0];
            }
            if (equals3(board[0][i], board[1][i], board[2][i])) {
                winner = board[0][i];
            }
        }

                          //
                        //
                      //
        if (equals3(board[0][0], board[1][1], board[2][2])) {
            winner = board[0][0];
        }
        if (equals3(board[2][0], board[1][1], board[0][2])) {
            winner = board[2][0];
        }

        boolean openSpots = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].isEmpty()) {
                    openSpots = true;
                }
            }
        }

        if (winner == null && !openSpots) {
            return "tie";
        } else {
            return winner;
        }
    }

    private void bestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestI = 0;
        int bestJ = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].isEmpty()) {
                    board[i][j] = AI;
                    int score = minimax(board, 0, false);
                    board[i][j] = "";
                    if (score > bestScore) {
                        bestScore = score;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }
        board[bestI][bestJ] = AI;
        currentPlayer = HUMAN;
    }

    public int minimax(String[][] board, int depth, boolean isMaximizing) {
        String result = checkWinner();
        if (result != null) {
            if (result.equals(AI)) return 1;
            else if (result.equals(HUMAN)) return -1;
            else return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = AI;
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = "";
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = HUMAN;
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = "";
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

   
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 1; i < SIZE; i++) {
            g.drawLine(i * w, 0, i * w, 400);
            g.drawLine(0, i * h, 400, i * h);
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int x = i * w + w / 2;
                int y = j * h + h / 2;
                if (board[i][j].equals(HUMAN)) {
                    g.drawOval(x - w / 4, y - h / 4, w / 2, h / 2);
                } else if (board[i][j].equals(AI)) {
                    g.drawLine(x - w / 4, y - h / 4, x + w / 4, y + h / 4);
                    g.drawLine(x + w / 4, y - h / 4, x - w / 4, y + h / 4);
                }
            }
        }

        String result = checkWinner();
        if (result != null) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            if (result.equals("tie")) {
                g.drawString("Tie!", 150, 200);
            } else {
                g.drawString(result + " wins!", 100, 200);
            }
            this.removeMouseListener(this.getMouseListeners()[0]);
        }
    }

    public static void main() {
        JFrame frame = new JFrame("Tic Tac Toe game based on minimax");
        TicTacToe game = new TicTacToe();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
