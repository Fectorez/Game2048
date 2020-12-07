package comparators;

import game2048.Cell;

import java.util.Comparator;

public class ComparatorCellUp implements Comparator<Cell> {
    @Override
    public int compare(Cell a, Cell b) {
        return a.getY() - b.getY();
    }
}
