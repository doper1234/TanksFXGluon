package com.tanksfxgluon;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TanksFXGluon extends Application {

    Font NESFont;
    Button twoPlayerButton, onePlayerButton, constructionButton, instructionsButton, exitButton;
    Label twoPlayerScoreLabel, onePlayerScoreLabel, companyLogoLabel, disclaimerLabel, battleLabel, cityLabel, stageLabel, pauseLabel;
    Background backgroundBack, stageBackground;
    private AudioClip playerShootSound, selectedSound;
    AudioClip tanksTheme;
    private URL playerShootFile, selectedSoundFile; 
    URL tanksThemeFile;
    ImageView stageScreenTop, stageScreenBottom;
    Paint backColor, logoColor, labelFrontColor, buttonOverColor, stageBackColor;
    VBox tanksMainContainer;
    VBox instructionsContainer;
    HBox scoresContainer;
    VBox exitContainer;
    Label allRightsReservedLabel;
    ImageView gameLogoImage;
    Image tankCursor, tankCursor2, stageImage;
    final int cursorImageWidth = 10;
    final int FONT_SIZE = 16;
    final int TITLE_FONT_SIZE = 72;
    final int SMALLER_FONT_SIZE = 12;
    final int WIDTH = 512;
    final int HEIGHT = 480;
    
//    final int WIDTH = 768;
//    final int HEIGHT = 720;
    static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      //  static final double WIDTH = screenSize.getWidth();
   // static final double HEIGHT = screenSize.getHeight();
//    final int X_ALIGNMENT = -10;
//    final int FONT_SIZE = HEIGHT / 30;
//    final int TITLE_FONT_SIZE = HEIGHT / 5;
//    final int SMALLER_FONT_SIZE = HEIGHT/40;
    int onePlayerHiScore;
    int twoPlayerHiScore;
    int cursorCount = 0;
    Scene scene;
    StackPane root;
    GameTimer gameTimer;
    ArrayList<ImageView> bulletViews;
    //boolean dialogOpen = false;
    boolean setStage = false;
    private Image treeImage, brickImage, waterImage1, waterImage2, waterImage3, steelImage, flagImage;
    private Image p1A, p1B, p1C, p1D, p2A, p2B, p2C, p2D;
    private ArrayList<MapTerrain> mapTerrains;
    private final int TREE_TERRAIN = 3;
    private final int STEEL_TERRAIN = 2;
    private final int BRICK_TERRAIN = 1;
    private final int FLAG = 4;
    private final int PLAYER_1_STARTING_LOCATION = 8;
    private final int PLAYER_2_STARTING_LOCATION = 9;
    Player player1, player2;
    private boolean p1Up, p1Down, p1Left, p1Right, p2Up, p2Down, p2Left, p2Right;
    private boolean twoPlayerGame = false;
    private boolean paused = false;
    private final String url = "";
    private final String fontURL = "/emulogic.ttf";
    @Override
    public void start(Stage primaryStage) {
       // Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
       // Scene sceney = new Scene(rooty, visualBounds.getWidth(), visualBounds.getHeight());

        primaryStage.getIcons().add(new Image(TanksFXGluon.class.getResourceAsStream("/icon.png")));

        loadImageAssetsAndColor();
        loadAudioAssets();
        loadPreviousHighScores();
        createNodes();
        createSceneEvents();
        primaryStage.setTitle("Battle City");
        primaryStage.setScene(scene);
        primaryStage.show();
        tanksTheme.play();
    }
    
    private void setupButtons(Button... bs) {
        for (int i = 0; i < bs.length; i++) {
            final Button b = bs[i];
            b.setFont(NESFont);
            b.setBackground(backgroundBack);
            b.setTextFill(labelFrontColor);
            b.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    b.setTextFill(buttonOverColor);
                    selectedSound.play();
                }
            });
            b.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    b.setTextFill(labelFrontColor);
                }
            });

        }

    }
    
    private void setupLabels(int fontSize, Label... bs){
        for (int i = 0; i < bs.length; i++) {
            Label b = bs[i];
            b.setFont(NESFont);
            b.setBackground(backgroundBack);
            b.setTextFill(labelFrontColor);
            

        }
    }
    
    private void createNodes(){
       
        NESFont = new Font(FONT_SIZE);//Font.loadFont(fontURL, FONT_SIZE);
        
        tanksMainContainer = new VBox();
        scoresContainer = new HBox();
        
        tanksMainContainer.setAlignment(Pos.CENTER);
        
        onePlayerScoreLabel = new Label ("I-     00");
        twoPlayerScoreLabel = new Label("HI-     00");
        scoresContainer.getChildren().addAll(onePlayerScoreLabel, twoPlayerScoreLabel);
        
        gameLogoImage = new ImageView();
        battleLabel = new Label("battle");
        cityLabel = new Label("City");
        
        
        onePlayerButton = new Button();
        onePlayerButton.setText("1 Player");
        onePlayerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                newStageScreen();
                twoPlayerGame = false;
            }
        });
        
        twoPlayerButton = new Button();
        twoPlayerButton.setText("2 Players");
        twoPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                newStageScreen();
                twoPlayerGame = true;
            }
        });
        
        
        constructionButton = new Button();
        constructionButton.setText("Construction");
        constructionButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starting construction!"); 
            }
        });
        
        instructionsButton = new Button();
        instructionsButton.setText("Instructions");
        instructionsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(tanksMainContainer);
                createInstructionsScreen();
            }
        });
        
        exitButton = new Button();
        exitButton.setText("Exit to desktop");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                exitScreen();
                  //TanksOptionPane top = new TanksOptionPane("Exit to windows?");
                  
                  
//                int exit = JOptionPane.showConfirmDialog(null, "Exit to windows?", "Exit", JOptionPane.YES_NO_OPTION);
//                if(exit == JOptionPane.YES_OPTION){
//                    try {
//                        stop();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    finally{
//                    
//                        System.exit(0);
//                    }
//                    
//                }
//                JFrame blackFrame = new JFrame();
//                JPanel blackPanel = new JPanel();
//                blackFrame.setUndecorated(true);
//                blackFrame.setVisible(true);
//                blackFrame.setSize(300, 150);
//                blackFrame.setLocationRelativeTo(null);
//                blackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                blackFrame.add(BorderLayout.CENTER,blackPanel);
//                blackPanel.setBackground(java.awt.Color.BLACK);
//                Graphics g = blackPanel.getGraphics();
//                g.setColor(java.awt.Color.ORANGE);
//                g.drawRect(0, 0, blackPanel.getWidth(), blackPanel.getHeight());
//                blackFrame.add(new JButton("Exit"));
                
                
            }
        });
        
        
        companyLogoLabel = new Label("Namecot");
        disclaimerLabel = new Label("© 1980 1985 Namco LTD.");
        allRightsReservedLabel = new Label("All rights reserved");
        
        setupButtons(onePlayerButton, twoPlayerButton, constructionButton, instructionsButton, exitButton);
        setupLabels(FONT_SIZE, onePlayerScoreLabel, twoPlayerScoreLabel, battleLabel, cityLabel, companyLogoLabel, 
                    disclaimerLabel, allRightsReservedLabel);
        companyLogoLabel.setTextFill(logoColor);
        battleLabel.setFont(NESFont);
        battleLabel.setTextFill(logoColor);
        cityLabel.setFont(NESFont);
        cityLabel.setTextFill(logoColor);
        
        pauseLabel = new Label("paused");
        pauseLabel.setVisible(false);
        setupLabels(FONT_SIZE, pauseLabel);
        pauseLabel.setTextFill(logoColor);
        pauseLabel.setAlignment(Pos.CENTER);
        
        tanksMainContainer.getChildren().addAll(scoresContainer,gameLogoImage, battleLabel, cityLabel, 
                  onePlayerButton, twoPlayerButton, constructionButton, instructionsButton, exitButton, 
                  companyLogoLabel, disclaimerLabel, allRightsReservedLabel);
        
      gameTimer = new GameTimer(this);
        bulletViews = new ArrayList<>();
        root = new StackPane();
        root.setBackground(backgroundBack);
        root.getChildren().add(tanksMainContainer);
 
        
    }
    
//    private void setTitleFont(Label l, int fontSize){
//        battleLabel.setFont(Font.loadFont(fontURL, TITLE_FONT_SIZE));
//    }
    
    private void loadAudioAssets(){
        playerShootFile = getClass().getResource(url+"/sounds/PlayerShoot.wav");
        playerShootSound = new AudioClip(playerShootFile.toString());
        selectedSoundFile = getClass().getResource(url+"/sounds/Selected.wav");
        selectedSound = new AudioClip(selectedSoundFile.toString());
        tanksThemeFile = getClass().getResource(url+"/sounds/TanksTheme.wav");
        tanksTheme = new AudioClip(tanksThemeFile.toString());
    }
    
    private void loadImageAssetsAndColor(){
        
        backColor = Color.BLACK;
        logoColor = Color.DARKORANGE;
        labelFrontColor = Color.WHITE;
        buttonOverColor = Color.GOLD;
        stageBackColor = Paint.valueOf("808080");
        treeImage = new Image(url+"/images/trees.png");
        brickImage = new Image(url+"/images/bricks.png");
        steelImage = new Image(url+"/images/steel.png");
        flagImage = new Image(url+"/images/flag.png");
        p1A = new Image(url+"/images/player1a.png", 32, 32, false, true, false);
        p1B = new Image(url+"/images/player1b.png", 32, 32, false, true, false);
        p1C = new Image(url+"/images/player1c.png", 32, 32, false, true, false);
        p1D = new Image(url+"/images/player1d.png", 32, 32, false, true, false);
        p2A = new Image(url+"/images/player2a.png");
        p2B = new Image(url+"/images/player2b.png");
        p2C = new Image(url+"/images/player2c.png");
        p2D = new Image(url+"/images/player2d.png");
        tankCursor = new Image(url+"/images/TankCursor.png"); 
        tankCursor2 = new Image(url+"/images/TankCursor2.png");
        stageImage = new Image(url+"/images/stageScreen.png");
        backgroundBack = new Background(new BackgroundFill(backColor, null, null));
        stageBackground = new Background(new BackgroundFill(stageBackColor, null, null));
        
        
    }
    
    private void loadPreviousHighScores(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("scores.txt")));
        } catch (FileNotFoundException ex) {
            
        }
    }
    
    private void createInstructionsScreen(){
        instructionsContainer = new VBox();
        instructionsContainer.setAlignment(Pos.CENTER);
        HBox instructionsHolder = new HBox();
        HBox instructionsHolder2 = new HBox();
        
        VBox onePlayerControls = new VBox();
        Label playerOneLabel = new Label("Player One Controls");
        Label playerUp = new Label("Up");
        Label playerDown = new Label("Down");
        Label playerLeft = new Label("Left");
        Label playerRight = new Label("Right");
        Label playerShoot = new Label("Shoot");
        
        VBox onePlayerControlsKeys = new VBox();
        Label upKey = new Label("up arrow");
        Label downKey = new Label("down arrow");
        Label leftKey = new Label("left arrow");
        Label rightKey = new Label("right arrow");
        Label shootKey = new Label("space bar");
        
        VBox twoPlayerControls = new VBox();
        Label playerTwoLabel = new Label("Two Player Controls");
        Label player2Up = new Label("Up");
        Label player2Down = new Label("Down");
        Label player2Left = new Label("Left");
        Label player2Right = new Label("Right");
        Label player2Shoot = new Label("Shoot");
        
        VBox twoPlayerControlsKeys = new VBox();
        Label upKey2 = new Label("w key");
        Label downKey2 = new Label("s key");
        Label leftKey2 = new Label("a key");
        Label rightKey2 = new Label("d key");
        Label shootKey2 = new Label("enter key");
        
        onePlayerControls.getChildren().addAll(playerUp, playerDown, playerLeft, playerRight, playerShoot);
        onePlayerControlsKeys.getChildren().addAll(upKey, downKey, leftKey, rightKey);
        twoPlayerControls.getChildren().addAll(player2Up, player2Down, player2Left, player2Right, player2Shoot);
        twoPlayerControlsKeys.getChildren().addAll(upKey2, downKey2, leftKey2, rightKey2);
        
        instructionsHolder.getChildren().addAll(onePlayerControls, onePlayerControlsKeys);
        instructionsHolder2.getChildren().addAll(twoPlayerControls, twoPlayerControlsKeys);
        
        
        
        
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(instructionsContainer);
                root.getChildren().add(tanksMainContainer);
            }
        });
        
        setupLabels(FONT_SIZE, playerOneLabel, playerTwoLabel);
        setupLabels(SMALLER_FONT_SIZE, playerUp, playerDown, playerLeft, playerRight, playerShoot,
                       player2Up, player2Down, player2Left, player2Right, player2Shoot,
                       upKey, downKey, leftKey, rightKey, upKey2, downKey2, leftKey2, rightKey2);
        setupButtons(back);
        
        instructionsContainer.getChildren().addAll(playerOneLabel, instructionsHolder, playerTwoLabel, instructionsHolder2,back);
        root.getChildren().add(instructionsContainer);
    }
    
    private void createSceneEvents(){
        scene = new Scene(root, WIDTH, HEIGHT);
        
        if(1 == 0){
            scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (!setStage) {
                    ImageView bulletView = new ImageView("/images/bullet.png");
                    root.getChildren().add(bulletView);
                    bulletViews.add(bulletView);
                    bulletView.setTranslateX((event.getX() - WIDTH / 2) + tankCursor.getWidth() / 2 + 4);
                    bulletView.setTranslateY((event.getY() - HEIGHT / 2) + tankCursor.getHeight() / 4 + 4);

                    playerShootSound.play();
                } else{
                    root.getChildren().removeAll(bulletViews);
                }
            }
        });
            scene.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(!setStage){
                    scene.setCursor(new ImageCursor(tankCursor));
                }
                
                gameTimer.start();
            }
        });
        }
    }
    
    private void exitScreen() {
//        dialogOpen = true;
        exitContainer = new VBox();
        HBox buttonsContainer = new HBox();
        buttonsContainer.setAlignment(Pos.CENTER);
        exitContainer.setVisible(true);
        exitContainer.setAlignment(Pos.CENTER);
//        exitContainer.getStyleClass().add("-fx-border-color: white;");
//        exitContainer.setStyle("-fx-border-color: white;");
        
        Label exitLabel = new Label("Exit to windows?");
        exitLabel.setStyle("-fx-border-color: orange;");
        Button yesButton = new Button("yes");
        yesButton.setStyle("-fx-border-color: orange;");
        yesButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        Button noButton = new Button("no");
        noButton.setStyle("-fx-border-color: orange;");
        noButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                exitContainer.setVisible(false);
            }
        });
        buttonsContainer.getChildren().addAll(yesButton, noButton);
        exitContainer.getChildren().addAll(exitLabel, buttonsContainer);
        setupButtons(yesButton, noButton);
        setupLabels(30, exitLabel);
        root.getChildren().add(exitContainer);

    }
    
    private void pauseScreen() {
        if (paused = true) {
            pauseLabel.setVisible(true);
            paused = false;
            gameTimer.stop();
            root.getChildren().add(pauseLabel);
        } else {
            pauseLabel.setVisible(false);
            gameTimer.start();
            root.getChildren().remove(pauseLabel);
        }
        
    }
    
    private void newStageScreen(){
        scene.setCursor(Cursor.NONE);
        stageScreenTop = new ImageView();
        stageScreenTop.setImage(stageImage);
        stageScreenTop.setTranslateY(-360 - (HEIGHT/2));
        stageScreenBottom = new ImageView();
        stageScreenBottom.setImage(stageImage);
        stageScreenBottom.setTranslateY(360 + (HEIGHT/2));
        VBox stageBox = new VBox();
        stageBox.setAlignment(Pos.CENTER);
        stageLabel = new Label("Stage   1");
        stageLabel.setBackground(stageBackground);
        stageLabel.setTextFill(backColor);
        stageLabel.setFont(NESFont);
        stageLabel.setVisible(false);
        stageBox.getChildren().add(stageLabel);
        root.getChildren().remove(tanksMainContainer);
        root.getChildren().addAll(stageScreenTop, stageScreenBottom);
        root.getChildren().add(stageBox);
        //root.setBackground(stageBackground);
        //cursorTimer.start();
        setStage = true;
    }
    
    public void setNewStage(){
        Image stageFrame = new Image(url+"/images/stageframe.png");
        ImageView stageImageView = new ImageView(stageFrame);
        loadMap();
        root.getChildren().add(stageImageView);
        stageImageView.toBack();
    }
    
    private void loadMap() {
        double grid = 16;
        double x, y;
        x = (-WIDTH / 2) + grid * 2.5;
        y = (-WIDTH / 2) + grid * 3.5;

        Map map = new Map(1);
        int[][] mapGrid = map.getMap();
        mapTerrains = new ArrayList<>();
        for (int i = 0; i < mapGrid.length; i++) {
            //y = y + i;
            for (int j = 0; j < mapGrid.length; j++) {
                //x = x + j;
                if (mapGrid[i][j] == BRICK_TERRAIN) {
                    mapTerrains.add(new MapTerrain("", x + j * grid, y + i * grid, brickImage));
                } else if (mapGrid[i][j] == STEEL_TERRAIN) {
                    mapTerrains.add(new MapTerrain("", x + j * grid, y + i * grid, steelImage));
                }else if (mapGrid[i][j] == TREE_TERRAIN) {
                    mapTerrains.add(new MapTerrain("", x + j * grid, y + i * grid, treeImage));
                } else if (mapGrid[i][j] == FLAG){
                    mapTerrains.add(new MapTerrain("", (x + grid/2) + j * grid, (y + grid/2) + i * grid, flagImage));
                } else if(mapGrid[i][j] == PLAYER_1_STARTING_LOCATION){
                    player1 = new Player(this,"", (x + grid/2) + j * grid, (y + grid/2) + i * grid, p1A, p1B, p1C, p1D);
                    root.getChildren().add(player1.spriteFrame);
                } else if(mapGrid[i][j] == PLAYER_2_STARTING_LOCATION && twoPlayerGame){
                    player2 = new Player(this,"", (x + grid/2) + j * grid, (y + grid/2) + i * grid, p2A, p2B, p2C, p2D);
                    root.getChildren().add(player2.spriteFrame);
                }
            }
        }
        for(MapTerrain mt: mapTerrains) {
            root.getChildren().add(mt.spriteFrame);
        };

    }
    
    public void animateBullets(){
        for (ImageView b : bulletViews) {
            double x = b.getTranslateX() + 2;
            b.setTranslateX(x);

        }
    }
    
    public void loadTanksLivesAndOtherShitThatTheUserShouldSee(){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                    case UP: 
                        p1Up = true;
                        break;
                    case DOWN:
                        p1Down = true;
                        break;
                    case LEFT:
                        p1Left = true;
                        break;
                    case RIGHT:
                        p1Right = true;
                        break;
                    case P:
                        pauseScreen();
                        break;
                }
            }
        });
    }

}
