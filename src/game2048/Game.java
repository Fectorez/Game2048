package game2048;

import comparators.ComparatorCellDown;
import comparators.ComparatorCellLeft;
import comparators.ComparatorCellRight;
import comparators.ComparatorCellUp;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
    public static final int GAME_SIZE = 8;
    public static final int INV_PROBA_GENERATE_4 = 4;
    public static final String LABEL_VICTORY = "YOU WIN";

    private final Scene scene;
    private final Pane pane;
    private final Cell[][] background;
    private final List<Cell> blocks;

    public Game() {
        pane = new Pane();
        scene = new Scene(pane, Cell.CELL_SPACE * GAME_SIZE, Cell.CELL_SPACE * GAME_SIZE);
        background = new Cell[GAME_SIZE][GAME_SIZE];
        blocks = new LinkedList<>();
        initBackground();
        initKeysPressed();
        generateNewBlock();
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * La grille de fond est un ensemble de Cell
     */
    private void initBackground() {
        for ( int i = 0 ; i < GAME_SIZE ; i++ ) {
            for ( int j = 0 ; j < GAME_SIZE ; j++ ) {
                background[i][j] = new Cell(Number.None, i, j);
                background[i][j].addToPane(pane);
            }
        }
    }

    /**
     * Initialiser les actions à la pression des flèches
     */
    private void initKeysPressed() {
        scene.setOnKeyPressed(event -> {
            if ( event.getCode() == KeyCode.LEFT )
                moveLeft();
            else if ( event.getCode() == KeyCode.RIGHT )
                moveRight();
            else if ( event.getCode() == KeyCode.UP )
                moveUp();
            else if ( event.getCode() == KeyCode.DOWN )
                moveDown();
            if ( wun() )
                displayVictory();
        });
    }

    /**
     * Savoir si le joueur a gagné
     * @return true si le joueur a gagné, false sinon
     */
    private boolean wun() {
        return blocks.stream().anyMatch(block -> block.getNumber() == Number._2048);
    }

    /**
     * Affiche un message à l'écran et bloque le jeu
     */
    private void displayVictory() {
        Label labelVictory = new Label(LABEL_VICTORY);
        labelVictory.setLayoutX((double)Cell.CELL_SPACE * GAME_SIZE / 2 - (double)Cell.CELL_SPACE * GAME_SIZE / 7);
        labelVictory.setLayoutY((double)Cell.CELL_SPACE * GAME_SIZE / 2 - (double)Cell.CELL_SPACE * GAME_SIZE / 20);
        pane.getChildren().add(labelVictory);
        scene.setOnKeyPressed(event -> {});
    }

    /**
     * Savoir si une case du jeu est vide
     * @param x indice x
     * @param y indice y
     * @return true si case vide, false sinon
     */
    private boolean cellEmpty(int x, int y) {
        return blocks.stream().noneMatch(block -> block.getX() == x && block.getY() == y);
    }

    /**
     * Obtenir les indices d'une case libre aléatoire
     * @return une paire contenant le x et le y de la case libre aléatoire trouvée
     */
    private Pair<Integer, Integer> getRandomAvailableCell() {
        Random rand;
        int x, y;
        for ( int cpt = 0 ; cpt < 10_000 ; cpt++ ) {
            rand = new Random();
            x = rand.nextInt(GAME_SIZE);
            y = rand.nextInt(GAME_SIZE);
            if ( cellEmpty(x, y) )
                return new Pair<>(x, y);
        }
        throw new RuntimeException("Empty cell not found.");
    }

    /**
     * Génère un nouveau bloc (un 2 ou un 4) dans une case libre du jeu
     */
    public void generateNewBlock() {
        Pair<Integer, Integer> indexes = getRandomAvailableCell();
        Random rand = new Random();
        Number number = rand.nextInt(INV_PROBA_GENERATE_4) == 0 ? Number._4 : Number._2;
        Cell newCell = new Cell(number, indexes.getKey(), indexes.getValue());
        blocks.add(newCell);
        newCell.addToPane(pane);
    }

    /**
     * Le joueur a appuyé sur "gauche" alors bouger le jeu à gauche
     */
    private void moveLeft() {
        boolean moved = false;
        boolean fusionHappened;
        do {
            // Traiter les blocs les plus à gauche d'abord
            blocks.sort(new ComparatorCellLeft());
            for (Cell block : blocks) {
                // Bouger à gauche tant qu'on peut
                while (block.getX() > 0 && cellEmpty(block.getX() - 1, block.getY())) {
                    block.setX(block.getX() - 1);
                    moved = true;
                }
            }
            // Effectuer une fusion si possible, et si fusion a eu lieu, refaire le décalage gauche
            fusionHappened = manageLeftFusionAndReturnTrueIfFusionHappened();
        } while ( fusionHappened );
        if ( moved )
            generateNewBlock();
    }

    /**
     * Faire une fusion gauche, si c'est possible.
     * @return true si une fusion a eu lieu
     */
    private boolean manageLeftFusionAndReturnTrueIfFusionHappened() {
        for (Cell rightBlock : blocks) {
            if (rightBlock.getX() == 0)
                continue;
            Cell leftBlock = blocks.stream()
                    .filter(b -> b.getX() == rightBlock.getX() - 1 && b.getY() == rightBlock.getY())
                    .findAny()
                    .orElse(null);
            if (leftBlock == null)
                throw new RuntimeException("Après avoir bougé le jeu à gauche, une case à gauche est libre !");
            if (leftBlock.getNumber() == rightBlock.getNumber()) {
                // Supprimer bloc de droite
                blocks.remove(rightBlock);
                rightBlock.removeFromPane(pane);
                // Créer et ajouter nouveau super bloc
                Cell newSuperCell = new Cell(leftBlock.getNumber().next(), leftBlock.getX(), leftBlock.getY());
                blocks.add(newSuperCell);
                newSuperCell.addToPane(pane);
                // Supprimer bloc de gauche
                blocks.remove(leftBlock);
                leftBlock.removeFromPane(pane);
                return true;
            }
        }
        return false;
    }

    /**
     * Le joueur a appuyé sur "droite" alors bouger le jeu à droite
     */
    private void moveRight() {
        boolean moved = false;
        boolean fusionHappened;
        do {
            // Traiter les blocs les plus à droite d'abord
            blocks.sort(new ComparatorCellRight());
            for ( Cell block : blocks ) {
                // Bouger à droite tant qu'on peut
                while ( block.getX() < GAME_SIZE - 1 && cellEmpty(block.getX() + 1, block.getY()) ) {
                    block.setX(block.getX() + 1);
                    moved = true;
                }
            }
            // Effectuer une fusion si possible, et si fusion a eu lieu, refaire le décalage droite
            fusionHappened = manageRightFusionAndReturnTrueIfFusionHappened();
        } while ( fusionHappened );
        if ( moved )
            generateNewBlock();
    }

    /**
     * Faire une fusion droite, si c'est possible.
     * @return true si une fusion a eu lieu
     */
    private boolean manageRightFusionAndReturnTrueIfFusionHappened() {
        for (Cell leftBlock : blocks) {
            if (leftBlock.getX() == GAME_SIZE - 1)
                continue;
            Cell rightBlock = blocks.stream()
                    .filter(b -> b.getX() - 1 == leftBlock.getX() && b.getY() == leftBlock.getY())
                    .findAny()
                    .orElse(null);
            if (rightBlock == null)
                throw new RuntimeException("Après avoir bougé le jeu à droite, une case à droite est libre !");
            if (leftBlock.getNumber() == rightBlock.getNumber()) {
                // Supprimer bloc de gauche
                blocks.remove(leftBlock);
                leftBlock.removeFromPane(pane);
                // Créer et ajouter nouveau super bloc
                Cell newSuperCell = new Cell(rightBlock.getNumber().next(), rightBlock.getX(), rightBlock.getY());
                blocks.add(newSuperCell);
                newSuperCell.addToPane(pane);
                // Supprimer bloc de droite
                blocks.remove(rightBlock);
                rightBlock.removeFromPane(pane);
                return true;
            }
        }
        return false;
    }

    /**
     * Le joueur a appuyé sur "haut" alors bouger le jeu en haut
     */
    private void moveUp() {
        boolean moved = false;
        boolean fusionHappened;
        do {
            // Traiter les blocs les plus en haut d'abord
            blocks.sort(new ComparatorCellUp());
            for (Cell block : blocks) {
                // Bouger en haut tant qu'on peut
                while (block.getY() > 0 && cellEmpty(block.getX(), block.getY() - 1)) {
                    block.setY(block.getY() - 1);
                    moved = true;
                }
            }
            // Effectuer une fusion si possible, et si fusion a eu lieu, refaire le décalage haut
            fusionHappened = manageUpFusionAndReturnTrueIfFusionHappened();
        } while ( fusionHappened );
        if ( moved )
            generateNewBlock();
    }

    /**
     * Faire une fusion haut, si c'est possible.
     * @return true si une fusion a eu lieu
     */
    private boolean manageUpFusionAndReturnTrueIfFusionHappened() {
        for (Cell bottomBlock : blocks) {
            if (bottomBlock.getY() == 0)
                continue;
            Cell topBlock = blocks.stream()
                    .filter(b -> b.getX() == bottomBlock.getX() && b.getY() == bottomBlock.getY() - 1)
                    .findAny()
                    .orElse(null);
            if (topBlock == null)
                throw new RuntimeException("Après avoir bougé le jeu en haut, une case en haut est libre !");
            if (topBlock.getNumber() == bottomBlock.getNumber()) {
                // Supprimer bloc du bas
                blocks.remove(bottomBlock);
                bottomBlock.removeFromPane(pane);
                // Créer et ajouter nouveau super bloc
                Cell newSuperCell = new Cell(topBlock.getNumber().next(), topBlock.getX(), topBlock.getY());
                blocks.add(newSuperCell);
                newSuperCell.addToPane(pane);
                // Supprimer bloc de gauche
                blocks.remove(topBlock);
                topBlock.removeFromPane(pane);
                return true;
            }
        }
        return false;
    }

    /**
     * Le joueur a appuyé sur "bas" alors bouger le jeu en bas
     */
    private void moveDown() {
        boolean moved = false;
        boolean fusionHappened;
        do {
            // Traiter les blocs les plus en bas d'abord
            blocks.sort(new ComparatorCellDown());
            for (Cell block : blocks) {
                // Bouger en bas tant qu'on peut
                while (block.getY() < GAME_SIZE - 1 && cellEmpty(block.getX(), block.getY() + 1)) {
                    block.setY(block.getY() + 1);
                    moved = true;
                }
            }
            // Effectuer une fusion si possible, et si fusion a eu lieu, refaire le décalage bas
            fusionHappened = manageDownFusionAndReturnTrueIfFusionHappened();
        } while ( fusionHappened );
        if ( moved )
            generateNewBlock();
    }

    /**
     * Faire une fusion bas, si c'est possible.
     * @return true si une fusion a eu lieu
     */
    private boolean manageDownFusionAndReturnTrueIfFusionHappened() {
        for (Cell topBlock : blocks) {
            if (topBlock.getY() == GAME_SIZE - 1)
                continue;
            Cell bottomBlock = blocks.stream()
                    .filter(b -> b.getX() == topBlock.getX() && b.getY() == topBlock.getY() + 1)
                    .findAny()
                    .orElse(null);
            if (bottomBlock == null)
                throw new RuntimeException("Après avoir bougé le jeu en bas, une case en bas est libre !");
            if (bottomBlock.getNumber() == topBlock.getNumber()) {
                // Supprimer bloc du haut
                blocks.remove(topBlock);
                topBlock.removeFromPane(pane);
                // Créer et ajouter nouveau super bloc
                Cell newSuperCell = new Cell(bottomBlock.getNumber().next(), bottomBlock.getX(), bottomBlock.getY());
                blocks.add(newSuperCell);
                newSuperCell.addToPane(pane);
                // Supprimer bloc du bas
                blocks.remove(bottomBlock);
                bottomBlock.removeFromPane(pane);
                return true;
            }
        }
        return false;
    }
}
