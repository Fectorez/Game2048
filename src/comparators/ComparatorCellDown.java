package comparators;

import game2048.Cell;

import java.util.Comparator;

public class ComparatorCellDown implements Comparator<Cell> {
    @Override
    public int compare(Cell a, Cell b) {
        return b.getY() - a.getY();
    }
}
