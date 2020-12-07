package game2048;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell {
    public final static int CELL_SIZE = 50;
    public final static int CELL_SPACE = CELL_SIZE + 5;

    private final Number number;
    private final Rectangle square;
    private final Label label;
    private int x;
    private int y;

    public Cell(Number number, int x, int y) {
        this.number = number;
        this.square = new Rectangle(CELL_SIZE, CELL_SIZE);
        this.square.setFill(Paint.valueOf(number.getColor()));
        this.label = new Label(number.getStrValue());
        this.setX(x);
        this.setY(y);
    }

    public void addToPane(Pane pane) {
        pane.getChildren().addAll(square, label);
    }

    public void removeFromPane(Pane pane) {
        pane.getChildren().removeAll(square, label);
    }

    public void setX(int x) {
        this.x = x;
        square.setLayoutX(x * CELL_SPACE);
        label.setLayoutX(x * CELL_SPACE + (double)CELL_SIZE / 2 - number.getNbDigits() * (double)CELL_SIZE / 12);
    }

    public void setY(int y) {
        this.y = y;
        square.setLayoutY(y * CELL_SPACE);
        label.setLayoutY(y * CELL_SPACE + (double)CELL_SIZE / 2 - (double)CELL_SIZE / 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Number getNumber() {
        return number;
    }
}
