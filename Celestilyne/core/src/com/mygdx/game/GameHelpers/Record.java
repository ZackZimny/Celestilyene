package com.mygdx.game.GameHelpers;

/**
 * Holds the name and score of a record
 */
public class Record {
    private final String name;
    private final int score;

    /**
     * Creates a record
     * @param name name associated with the record
     * @param score score associated with the record
     */
    public Record(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * gets the name associated with the record
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the score associated with the record
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Changes the reference string of the Record variable to a peek into the variable values
     * @return formatted string with record values
     */
    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
