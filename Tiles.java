import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tiles extends JFrame implements ActionListener {

    final String imagePath = ".\\src\\images";
    Path imageFolder = Paths.get(imagePath);
    private List<String> imageFileNames = new ArrayList<>();
    private List<String> imageFileNames2 = new ArrayList<>();
    private JPanel[] buttonPanels = new JPanel[16];
    private JButton[] gameButtons = new JButton[16];
    private int[][] gameBoard = new int[4][4];
    private int[] imageArray = new int[16];
    private int input = 0;


    JPanel headerPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel headerLabel = new JLabel("Welcome to 4x4 game");
    JButton restart = new JButton("Restart");

    private static List<String> importImages(Path imageDir, List<String> imageFileNames){
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(imageDir)){
            for(Path file: stream){
                imageFileNames.add(file.toString());
            }
        }
        catch (IOException | DirectoryIteratorException x){
            x.printStackTrace();
        }
        return imageFileNames;
    }

    public void CreateAssets(){

        imageFileNames = importImages(imageFolder, imageFileNames);
        JPanel[] p = new JPanel[16];
        JButton[] b = new JButton[16];
        for (int i = 0; i < 16; i++) {

            String name = "gp" +(i);
            p[i] = new JPanel();
            p[i].setName(name);

        }
        for (int i = 0; i < 16; i++) {
            String name = "gb" + i;
            b[i] = new JButton();
            b[i].setName(name);
            b[i].setBorder(BorderFactory.createEmptyBorder());
            b[i].setSize(50,50);
            b[i].addActionListener(this);

        }
        System.arraycopy(p, 0, buttonPanels, 0, 16);
        System.arraycopy(b, 0, gameButtons, 0, 16);

    }



    public void CreateGame(){

        setLayout(new BorderLayout());
        add("North", headerPanel);
        add("Center", gamePanel);
        add("South", buttonPanel);

        buttonPanel.add(restart);
        restart.addActionListener (this);
        restart.setFont(new Font("Calibri", Font.PLAIN, 20));
        headerLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        headerPanel.add(headerLabel);

        gamePanel.setLayout(new GridLayout(4,4, 5, 5));
        gamePanel.setSize(100,100);


        for (int i = 0; i < 16; i++) {
            gamePanel.add(buttonPanels[i]);
        }
        for (int i = 0; i < 16; i++) {
            buttonPanels[i].add(gameButtons[i]);
        }
        imageFileNames2 = List.copyOf(imageFileNames);
        Collections.shuffle(imageFileNames, new Random());
        for (int i = 0; i < 16; i++) {
            gameButtons[i].setIcon(new ImageIcon(imageFileNames.get(i)));
        }
        for (int i = 0; i < 16; i++) {
            String tempString = imageFileNames.get(i);
            char tempChar = tempString.charAt(13);
            char tempChar2 = tempString.charAt(14);
            String tempString2 = "";
            if(tempChar == '0'){
                tempString2 = "" +tempChar2;
            }
            else{
                tempString2 = "" +tempChar + tempChar2;
            }
            int tempInt = Integer.parseInt(tempString2);
            if(tempInt == 0){
                tempInt = 16;
            }
            imageArray[i] = tempInt;
        }
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameBoard[i][j] = imageArray[k];
                k++;
            }
        }

        setSize(400, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void Game(){
        boolean check = false;
        int temp = -1;
        int temp2 = 0;
        int imageNumber = 0;
        int imageNumber2 = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(gameBoard[i][j] == input){
                    x = i;
                    y = j;
                }
            }
        }


        if(x+1 <= 3 && y <= 3){
            if(gameBoard[x+1][y] == 16 && x < 4){
                gameBoard[x+1][y] = input;
                gameBoard[x][y] = 16;
                check = true;
            }
        }
        if(x-1 >= 0 && y >= 0){
            if(gameBoard[x-1][y] == 16){
                gameBoard[x-1][y] = input;
                gameBoard[x][y] = 16;
                check = true;
            }
        }
        if(y+1 <= 3 && x <= 3){
            if(gameBoard[x][y+1] == 16 && y < 4){
                gameBoard[x][y+1] = input;
                gameBoard[x][y] = 16;
                check = true;
            }
        }
        if (y-1 >= 0 && x >= 0){
            if(gameBoard[x][y-1] == 16){
                gameBoard[x][y-1] = input;
                gameBoard[x][y] = 16;
                check = true;
            }
        }
        if(check = true) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    temp++;
                    if (gameBoard[i][j] == input) {
                        x = i;
                        y = j;
                        imageNumber = temp;
                        temp2 = gameBoard[i][j];
                        if(temp2 == 16){
                            temp2 = 0;
                        }
                    }
                }
            }
            temp = -1;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    temp++;
                    if (gameBoard[i][j] == 16) {
                        x = i;
                        y = j;
                        imageNumber2 = temp;
                    }
                }
            }
            check = false;
        }
        gameButtons[imageNumber].setIcon(new ImageIcon(imageFileNames2.get(temp2)));
        gameButtons[imageNumber2].setIcon(new ImageIcon(imageFileNames2.get(0)));
    }

    private void Win(){
        if(gameBoard[0][0] == 1 && gameBoard[0][1] == 2 && gameBoard[0][2] == 3 && gameBoard[0][3] == 4){
            if(gameBoard[1][0] == 5 && gameBoard[1][1] == 6 && gameBoard[1][2] == 7 && gameBoard[1][3] == 8) {
                if (gameBoard[2][0] == 9 && gameBoard[2][1] == 10 && gameBoard[2][2] == 11 && gameBoard[2][3] == 12) {
                    if (gameBoard[3][0] == 13 && gameBoard[3][1] == 14 && gameBoard[3][2] == 15 && gameBoard[3][3] == 16) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You won!");
                    }
                }
            }
        }
        if(gameBoard[0][0] == 16 && gameBoard[0][1] == 1 && gameBoard[0][2] == 2 && gameBoard[0][3] == 3){
            if(gameBoard[1][0] == 4 && gameBoard[1][1] == 5 && gameBoard[1][2] == 6 && gameBoard[1][3] == 7) {
                if (gameBoard[2][0] == 8 && gameBoard[2][1] == 9 && gameBoard[2][2] == 10 && gameBoard[2][3] == 11) {
                    if (gameBoard[3][0] == 12 && gameBoard[3][1] == 13 && gameBoard[3][2] == 14 && gameBoard[3][3] == 15) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You won!");
                    }
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 16; i++) {
            if(e.getSource() == gameButtons[i]){
                int l = 0;
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (i == l){
                            input = gameBoard[j][k];
                        }
                        l++;
                    }
                }
                Game();
                Win();
            }
        }
        if (e.getSource() == restart){
            imageFileNames.clear();
            imageFileNames = importImages(imageFolder, imageFileNames);
            CreateGame();
        }

    }

}
