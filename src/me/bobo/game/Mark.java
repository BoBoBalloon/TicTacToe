package me.bobo.game;

/**
 * Enum that represents a mark on the board
 */
public enum Mark {
    X('X'),
    O('O'),
    EMPTY('_');

    private final char icon;

    Mark(char icon) {
        this.icon = icon;
    }

    /**
     * Gets the char that represents the corresponding mark
     *
     * @return char that represents the corresponding mark
     */
    public char getIcon() {
        return this.icon;
    }
}
