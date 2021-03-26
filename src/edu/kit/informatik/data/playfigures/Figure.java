package edu.kit.informatik.data.playfigures;

import edu.kit.informatik.data.resources.Coordinates;

public abstract class Figure {
    public final Coordinates position;
    public final String identifier;
    public int index;

    public Figure(int y, int x, String identifier) {
        position = new Coordinates(y, x);
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return this.identifier;
    }


    public void setIndex(final int index) {
        this.index = index;
    }
}
