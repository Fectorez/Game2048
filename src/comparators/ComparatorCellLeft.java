package comparators;

import game2048.Cell;

import java.util.Comparator;

public class ComparatorCellLeft implements Comparator<Cell> {
    @Override
    public int compare(Cell a, Cell b) {
        return a.getX() - b.getX();
    }
}
