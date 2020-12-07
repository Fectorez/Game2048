package comparators;

import game2048.Cell;

import java.util.Comparator;

public class ComparatorCellRight implements Comparator<Cell> {
    @Override
    public int compare(Cell a, Cell b) {
        return b.getX() - a.getX();
    }
}
