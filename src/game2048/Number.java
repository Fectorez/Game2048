package game2048;

public enum Number {
    None(0, "#cdc1b4"),
    _2(2, "#eee4da"),
    _4(4, "#ede0c8"),
    _8(8, "#f2b179"),
    _16(16, "#f59563"),
    _32(32, "#f67c5f"),
    _64(64, "#f65e3b"),
    _128(128, "#edcf72"),
    _256(256, "#edcc61"),
    _512(512, "#edc850"),
    _1024(1024, "#edc53f"),
    _2048(2048, "#edc22e");

    private final int value;
    private final String color;

    Number(int value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getStrValue() {
        return value == 0 ? "" : String.valueOf(value);
    }

    /**
     * Obtenir le nombre de chiffres du nombre
     * @return le nombre de chiffres du nombre
     */
    public int getNbDigits() {
        if ( value / 1000 > 0 )
            return 4;
        if ( value / 100 > 0 )
            return 3;
        if ( value / 10 > 0 )
            return 2;
        return 1;
    }

    /**
     * Obtenir le nombre suivant (4 => 8)
     * @return le nombre suivant
     */
    public Number next() {
        return values()[this.ordinal()+1];
    }
}
