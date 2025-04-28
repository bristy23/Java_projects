import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe {
    JFrame frame;
    JButton[] buttons = new JButton[9];
    boolean isXTurn;

    //Constructor
    public TicTacToe(){
        frame = new JFrame("Tic Tac Toe🌻");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(3, 3));

        // randomly decide that who will be playing first
        isXTurn = Math.random() < 0.5;// 50-50

        for(int i=0; i<9; i++){
            buttons[i] =new JButton();//new button
            buttons[i].setFont(new Font("Century Gothic",Font.BOLD,60));
            buttons[i].setBackground(new Color(0,150,136));
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(245,245,245),3));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(e-> {
                        JButton buttonClicked = (JButton) e.getSource();
                        if (buttonClicked.getText().equals("")) {
                            if (isXTurn) {
                                buttonClicked.setText("X");
                            } else {
                                buttonClicked.setText("O");
                            }
                            isXTurn = !isXTurn;
                            checkWinner();
                        }
                    });
            frame.add(buttons[i]);
        }
        frame.setVisible(true);
    }


    private void checkWinner(){
        String winner = "";

        //rows
        for(int i =0; i<9; i +=3){
            if (!buttons[i].getText().equals("") &&
                    buttons[i].getText().equals(buttons[i + 1].getText()) &&
                    buttons[i].getText().equals(buttons[i + 2].getText())) {
                winner = buttons[i].getText();
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[i].getText().equals("") &&
                    buttons[i].getText().equals(buttons[i + 3].getText()) &&
                    buttons[i].getText().equals(buttons[i + 6].getText())) {
                winner = buttons[i].getText();
            }
        }

        // Check diagonals
        if (!buttons[0].getText().equals("") &&
                buttons[0].getText().equals(buttons[4].getText()) &&
                buttons[0].getText().equals(buttons[8].getText())) {
            winner = buttons[0].getText();
        }
        if (!buttons[2].getText().equals("") &&
                buttons[2].getText().equals(buttons[4].getText()) &&
                buttons[2].getText().equals(buttons[6].getText())) {
            winner = buttons[2].getText();
        }

        // If winner found
        if (!winner.equals("")) {
            String message = "";
            if(winner.equals("X"))
            {
               message = "❌ Winner 😎";
            }
            else if(winner.equals("O"))
            {
                message = "⭕ Winner 😎";
            }
            JOptionPane.showMessageDialog(frame, message);
            resetBoard();
        }
        else {
            // Check if board is full (draw)
            boolean draw = true;
            for (JButton button : buttons) {
                if (button.getText().equals("")) {
                    draw = false;
                    break;
                }
            }

            if (draw) {
                JOptionPane.showMessageDialog(frame, "It's a Draw! 🤝");
                resetBoard();
            }
        }

    }

    private void resetBoard(){
        for(JButton button : buttons){
            button.setText("");
        }
    }
}
