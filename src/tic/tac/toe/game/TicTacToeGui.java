package tic.tac.toe.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicTacToeGui extends JFrame implements ActionListener {
    
    JLabel turnLabel, scoreLabel, barLabel, resultLabel;
    private JButton [][] board;
    private boolean isPlayerOne;
    private int xScore, oScore, moveCounter;
    private JDialog resultDialog;
    
    TicTacToeGui(){
    super("Tic Tac Toe");
    setSize(CommonConstants.FRAME_SIZE);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(null);
    getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
    
    createResultDialog();
    
    board = new JButton[3][3];
    
    isPlayerOne = true;
    
    addGuiComponent();    
    }
    
    private void addGuiComponent(){
     barLabel = new JLabel ();
     //Quando um componente é definido como opaco (opaque = true), isso significa que o componente desenhará sua própria área e não permitirá que a área abaixo dele seja visível. Em outras palavras, o componente será completamente sólido, e qualquer cor de fundo aplicada ao componente será visível. Isso é útil para garantir que o componente tenha uma aparência consistente, sem mostrar o que está por trás dele.
     barLabel.setOpaque(true);     
     barLabel.setBackground(Color.red);
     barLabel.setBounds(0,0, CommonConstants.FRAME_SIZE.width, 25);           
   
     turnLabel = new JLabel(CommonConstants.X_LABEL);
     turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
     turnLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
     turnLabel.setPreferredSize(new Dimension(100, turnLabel.getPreferredSize().height));
     turnLabel.setOpaque(true);
     turnLabel.setBackground(CommonConstants.X_COLOR);
     turnLabel.setForeground(CommonConstants.BOARD_COLOR);
     turnLabel.setBounds(
                (CommonConstants.FRAME_SIZE.width - turnLabel.getPreferredSize().width)/2,
                0,
                turnLabel.getPreferredSize().width,
                turnLabel.getPreferredSize().height
     );
     
     scoreLabel = new JLabel(CommonConstants.SCORE_LABEL);
     scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
     scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
     scoreLabel.setForeground(CommonConstants.BOARD_COLOR);
     scoreLabel.setBounds(0,
                turnLabel.getY() + turnLabel.getPreferredSize().height + 25,
                CommonConstants.FRAME_SIZE.width,
                scoreLabel.getPreferredSize().height
     );
     
     //game grid
     GridLayout gridLayout = new GridLayout(3,3);
     JPanel boardPanel = new JPanel(gridLayout);
     boardPanel.setBounds(0,
             scoreLabel.getY()+ scoreLabel.getPreferredSize().height +35,
             CommonConstants.BOARD_SIZE.width,
             CommonConstants.BOARD_SIZE.height
             );
     
     //board
     for(int i = 0; i < board.length;i++){
         for(int j = 0; j < board[1].length;j++){
         JButton button = new JButton();
         button.setFont(new Font("Dialog", Font.PLAIN, 180));
         button.setPreferredSize(CommonConstants.BUTTON_SIZE);
         button.addActionListener(this);
         button.setBackground(CommonConstants.BACKGROUND_COLOR);
         
         //add button to board
                 board[i][j]=button;
                 boardPanel.add(board[i][j]);

         }
     }
     
     JButton resetButton = new JButton ("Reset");
     resetButton.setFont(new Font("Dialog", Font.PLAIN, 24));
     resetButton.addActionListener(this);
     resetButton.setBackground(CommonConstants.BOARD_COLOR);
     resetButton.setBounds(
                (CommonConstants.FRAME_SIZE.width - resetButton.getPreferredSize().width)/2,
                CommonConstants.FRAME_SIZE.height - 100,
                resetButton.getPreferredSize().width,
                resetButton.getPreferredSize().height
        );


     getContentPane().add(turnLabel);
     getContentPane().add(barLabel);
     getContentPane().add(scoreLabel);
     getContentPane().add(boardPanel);
     getContentPane().add(resetButton);
    }
    
    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(2,1));
        resultDialog.addWindowListener(new WindowAdapter(){
        
            @Override
            public void windowClosing(WindowEvent e ){
                resetGame();
            }
        
        });
        
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        resultLabel.setForeground(CommonConstants.BOARD_COLOR);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        //restart button
        JButton restartButton = new JButton("Play Again");
        restartButton.setForeground(Color.BLACK);
        restartButton.addActionListener(this);
        
        resultDialog.add(resultLabel);
        resultDialog.add(restartButton);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e ){
        String command = e.getActionCommand();
        if(command.equals("Reset")|| command.equals("Play Again")){
            resetGame();       
            
            if(command.equals("Reset"))
                xScore = oScore = 0;
            
            if(command.equals("Play Again"))
                resultDialog.setVisible(false);
                
                
                
        }else{
        JButton button =(JButton)e.getSource();
            if(button.getText().equals("")){
                moveCounter++;
                
                if(isPlayerOne){
                    button.setText(CommonConstants.X_LABEL);
                    button.setForeground(CommonConstants.X_COLOR);
                    
                    turnLabel.setText(CommonConstants.O_LABEL);
                    turnLabel.setBackground(CommonConstants.O_COLOR);
                                        
                    isPlayerOne = false;         
                    
                }else{
                    //PLAYER TWO
                    button.setText(CommonConstants.O_LABEL);
                    button.setForeground(CommonConstants.O_COLOR);
                    
                    //update turn label
                    turnLabel.setText(CommonConstants.X_LABEL);
                    turnLabel.setBackground(CommonConstants.X_COLOR);
                    
                    isPlayerOne = true;                
                }                
                //win condtions
                 if(isPlayerOne){
                     checkOWin();
                 }{
                     checkXWin();   
                }
                 
                 // update score label
                scoreLabel.setText("X: " + xScore + " | O: " + oScore);
            }
            
            repaint();
            revalidate();
        }
    }    
    
  private void checkXWin(){
        String result = "X wins!";

        // check rows
        for(int row = 0; row < board.length; row++){
            if(board[row][0].getText().equals("x") && board[row][1].getText().equals("X") && board[row][2].getText().equals("x")){
                resultLabel.setText(result);

                // display result dialog
                resultDialog.setVisible(true);

                // update score
                xScore++;
            }
        }

        // check columns
        for(int col = 0; col < board.length; col++){
            if(board[0][col].getText().equals("x") && board[1][col].getText().equals("x") && board[2][col].getText().equals("x")){
                resultLabel.setText(result);

                // display result dialog
                resultDialog.setVisible(true);

                // update score
                xScore++;
            }
        }

        // check diagonals
        if(board[0][0].getText().equals("x") && board[1][1].getText().equals("x") && board[2][2].getText().equals("x")){
            resultLabel.setText(result);

            // display result dialog
            resultDialog.setVisible(true);

            // update score
            xScore++;
        }

        if(board[2][0].getText().equals("x") && board[1][1].getText().equals("x") && board[0][2].getText().equals("x")) {
            resultLabel.setText(result);

            // display result dialog
            resultDialog.setVisible(true);

            // update score
            xScore++;
        }
    }

    private void checkOWin(){
        String result = "O wins!";

        // check rows
        for(int row = 0; row < board.length; row++){
            if(board[row][0].getText().equals("o") && board[row][1].getText().equals("o") && board[row][2].getText().equals("o")){
                resultLabel.setText(result);

                // display result dialog
                resultDialog.setVisible(true);

                // update score
                oScore++;
            }
        }

        // check columns
        for(int col = 0; col < board.length; col++){
            if(board[0][col].getText().equals("o") && board[1][col].getText().equals("o") && board[2][col].getText().equals("o")){
                resultLabel.setText(result);

                // display result dialog
                resultDialog.setVisible(true);

                // update score
                oScore++;
            }
        }

        // check diagonals
        if(board[0][0].getText().equals("o") && board[1][1].getText().equals("o") && board[2][2].getText().equals("o")){
            resultLabel.setText(result);

            // display result dialog
            resultDialog.setVisible(true);

            // update score
            oScore++;
        }

        if(board[2][0].getText().equals("o") && board[1][1].getText().equals("o") && board[0][2].getText().equals("o")) {
            resultLabel.setText(result);

            // display result dialog
            resultDialog.setVisible(true);

            // update score
            oScore++;
        }
    }
    
    private void checkDraw(){
        // if there a total of 9 moves and no player has won yet then it means there is a draw
        if(moveCounter >= 9){
            resultLabel.setText("Draw!");
            resultDialog.setVisible(true);
        }
    }
    
    private void resetGame(){
        //reset player
        isPlayerOne = true;
        turnLabel.setText(CommonConstants.X_LABEL);
        turnLabel.setBackground(CommonConstants.X_COLOR);
        
        
        //reset score
        scoreLabel.setText(CommonConstants.SCORE_LABEL);
        
        //reset moove count
        
        moveCounter = 0;
        
        //reset board
        for(int i = 0;i<board.length;i++){
            for(int j = 0;j<board[i].length;j++){
            board[i][j].setText("");
           }
        } 
    } 
    
}
