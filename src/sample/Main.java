package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private double WORLD_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private double WORLD_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

    Scientist scientist;
    Missile missile;
    Barry barry;
    Zapper zapper;
    CoinGroup group;
    Label scoreLabel;
    int score = 0;
//    int tempDy = 0;

    @Override
    public void start(Stage stage) throws Exception {

        World world = new World() {
            @Override
            public void act(long now) {

            }
        };

        stage.setTitle("P1 Group 8 Game Engine");

        Scene scene = new Scene(world, WORLD_WIDTH, WORLD_HEIGHT);

        scoreLabel = new Label("Score: " + score);
        barry = new Barry();
        barry.setDy(-4);
        zapper = new Zapper();
        missile = new Missile();
        group = new CoinGroup();

        for (int i = 0; i < 60; i++) {
            group.add(new Coin());
        }

        barry.setImage(new Image("file:img/barry.png"));
        zapper.setImage(new Image("file:img/zapper.png"));
        missile.setImage(new Image("file:img/missile.png"));

        barry.setLocation(80, WORLD_HEIGHT - barry.getHeight());
        zapper.setLocation(WORLD_WIDTH + 50, WORLD_HEIGHT + 50);
        missile.setLocation(WORLD_WIDTH + 50, WORLD_HEIGHT + 50);

        barry.setOnKeyPressed(new EventHandler<KeyEvent>() {

            double dy = barry.getDy();

            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.W) {
                    if (barry.getFalling()) {
                        barry.setDy(barry.getDy() - 4);
                    }
                    barry.setFalling(false);
                    if (barry.getY() < 0){
                        //dy = 0;
                    } else if (barry.getY() > WORLD_HEIGHT - barry.getHeight() * 2){
                        //dy = 0;
                    } else {
////                        barry.setDy(-(5 + tempDy));
////                        tempDy += 2;
//                        dy = -7;
                    }

                    if (barry.getDy() != dy && barry.getDy() != 0) {
                        dy = barry.getDy();
                    }

                    System.out.println(dy);

                    barry.setDy(dy);
                }
            }
        });


        barry.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                barry.setFalling(true);

                //barry.setDy(barry.getDy() * -1);

                //tempDy = 0;
            }
        });

        world.add(barry);
        world.add(zapper);
        world.add(missile);

        for (int i = 0; i < 60; i++) {
            world.add(group.get(i));
        }

        Timeline boundaryCheckerTimeline = new Timeline(new KeyFrame(
                Duration.millis(10),
                ae -> boundarycheck()));
        boundaryCheckerTimeline.setCycleCount(Animation.INDEFINITE);
        boundaryCheckerTimeline.play();

        Timeline zapperTimeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> shootZapper()));
        zapperTimeline.setCycleCount(Animation.INDEFINITE);
        zapperTimeline.play();

        Timeline missileTimeline = new Timeline(new KeyFrame(
                Duration.millis(3000),
                ae -> shootMissile()));
        missileTimeline.setCycleCount(Animation.INDEFINITE);
        missileTimeline.play();

        Timeline coinTimeline = new Timeline(new KeyFrame(
                Duration.millis(4000),
                ae -> addCoins()));
        coinTimeline.setCycleCount(Animation.INDEFINITE);
        coinTimeline.play();

        world.start();

        stage.setScene(scene);

        stage.show();
    }

    public void addCoins(){
        int randomNum = (int)(Math.random() * 60);
        int randomY = (int) (Math.random() * WORLD_HEIGHT);
        for (int i = 0; i < randomNum; i++) {
            group.get(i).setLocation(WORLD_WIDTH + (30 * i), randomY);
            group.get(i).setDx(-6);
        }
    }

    public void boundarycheck(){
        if (barry.getY() > WORLD_HEIGHT - barry.getHeight() * 2) {
            barry.setDy(0);
            barry.setLocation(barry.getX(), WORLD_HEIGHT - barry.getHeight() * 2);
        }
        if (barry.getY() < 0) {
            //barry.setDy(7);
            barry.setLocation(barry.getX(), 1);
        }
    }

    public void shootMissile(){
        int randomY = (int) (Math.random() * WORLD_HEIGHT);
        missile.setLocation(WORLD_WIDTH, randomY);
        missile.setDx(-10);
    }

    public void shootZapper(){
        int randomY = (int) (Math.random() * WORLD_HEIGHT);
        if (zapper.getX() < 0) {
            zapper.setLocation(WORLD_WIDTH, randomY);
        }
        zapper.setDx(-10);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
