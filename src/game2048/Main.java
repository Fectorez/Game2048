package game2048;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {
    private boolean isColored = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        /*Pane root = new Pane();
        root.setPrefSize(700, 500);
        primaryStage.setTitle("Hello World");

        Rectangle rect = new Rectangle(40, 40);
        rect.setFill(Paint.valueOf("#000"));
        rect.setTranslateX(150);
        rect.setTranslateY(150);

        Button btn = new Button("FadeTransition");
        btn.setTranslateY(300);
        btn.setTranslateX(50);
        btn.setOnAction(event -> {
            if (rect.isVisible()) {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), rect);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(evt -> rect.setVisible(false));
                ft.play();
            }
            else {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), rect);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setOnFinished(evt -> rect.setVisible(true));
                ft.play();
            }
        });

        Button btn2 = new Button("Translate transition");
        btn2.setTranslateY(300);
        btn2.setTranslateX(200);

        btn2.setOnAction(event -> {
            if (rect.getTranslateX() == 300) {
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), rect);
                tt.setToX(150);
                tt.setOnFinished(evt -> rect.setTranslateX(150));
                tt.play();
            }
            else {
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), rect);
                tt.setToX(300);
                tt.setOnFinished(evt -> rect.setTranslateX(300));
                tt.play();
            }
        });

        Button btn3 = new Button("Color transition");
        btn3.setTranslateY(300);
        btn3.setTranslateX(350);

        btn3.setOnAction(event -> {
            FillTransition ft = new FillTransition(Duration.seconds(0.5), rect);
            if (!isColored) {
                ft.setFromValue(Color.BLACK);
                ft.setToValue(Color.GREEN);
                ft.play();
                isColored = true;
            }
            else {
                ft.setFromValue(Color.GREEN);
                ft.setToValue(Color.BLACK);
                ft.play();
                isColored = false;
            }
        });

        TextField textField = new TextField();

        root.getChildren().addAll(rect, btn, btn2, btn3);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/



        /*Pane root = new Pane();

        Rectangle[][] matrix = new Rectangle[4][4];

        for ( int i = 0 ; i < 4 ; i++ ) {
            for ( int j = 0 ; j < 4 ; j++ ) {
                matrix[i][j] = new Rectangle(i*55, j*55, 50, 50);
                matrix[i][j].setFill(Paint.valueOf("#ccc"));
                root.getChildren().add(matrix[i][j]);
            }
        }

        matrix[1][3] = new Rectangle(1*55, 3*55, 50, 50);
        matrix[1][3].setFill(Paint.valueOf("#aaa"));
        Label label = new Label("2");
        label.setLayoutX(1*55 + (50/2 - label.getWidth()));
        label.setLayoutY(3*55 + (50/2 - label.getHeight()));
        root.getChildren().add(matrix[1][3]);
        root.getChildren().add(label);

        root.setPrefSize(700, 500);

        primaryStage.setTitle("2048");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/

        Game game = new Game();

        primaryStage.setTitle("2048");
        primaryStage.setScene(game.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
