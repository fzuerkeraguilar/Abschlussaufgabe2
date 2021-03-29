package edu.kit.informatik.data.resources;

import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;

import java.util.Objects;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Coordinates {
    /**
     * x coordinate of coordinate pair
     */
    public final int x;
    /**
     * y coordinate of coordinate pair
     */
    public final int y;

    /**
     * Constructor of new 2D-coordinate
     * @param y y value of this coordinate
     * @param x x value of this coordinate
     */
    public Coordinates(final int y, final int x) {
        this.y = y;
        this.x = x;
    }

    /**
     *
     * @param y y value of this coordinate
     * @param x x value of this coordinate
     * @throws ValueOutOfRangeException - if one value is < 0
     */
    public Coordinates(final String y, final String x) throws ValueOutOfRangeException {
        try {
            this.y = Integer.parseInt(y);
        } catch (NumberFormatException e) {
            throw new ValueOutOfRangeException(e.getMessage());
        }
        try {
            this.x = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            throw new ValueOutOfRangeException(e.getMessage());
        }
        if (this.x < 0 || this.y < 0) throw new ValueOutOfRangeException(0, Integer.MAX_VALUE);
    }

    /**
     * Returns coordinate that is one unit north from this coordinate
     * @param northBound northern limit of grid system
     * @return coordinate that is one unit up from this coordinate, if boundary is not crossed, else returns this
     */
    public Coordinates north(int northBound) {
        if (this.y <= northBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x);
    }

    /**
     * Returns coordinate that is one north and one unit east from this coordinate
     * @param northBound northern limit of grid system
     * @param eastBound eastern limit of grid system
     * @return coordinate that is one north and one unit east from this coordinate, if boundary is not crossed,
     * else returns this
     */
    public Coordinates northEast(int northBound, int eastBound) {
        if (this.y <= northBound || this.x <= eastBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x + 1);
    }

    /**
     * Returns coordinate that is one unit east from this coordinate
     * @param eastBound eastern limit of grid system
     * @return coordinate that is one unit east from this coordinate if boundary is not crossed, else returns this
     */
    public Coordinates east(int eastBound) {
        if (this.x >= eastBound) {
            return this;
        }
        return new Coordinates(this.y, this.x + 1);
    }

    /**
     * Returns coordinate that is one unit south and one unit east from this coordinate
     * @param southBound southern limit of grid system
     * @param eastBound eastern limit of grid system
     * @return coordinate that is one unit south and one unit east from this coordinate if boundary is not crossed,
     * else returns this
     */
    public Coordinates southEast(int southBound, int eastBound) {
        if (this.y >= southBound  || this.x >= eastBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x + 1);
    }

    /**
     * Returns coordinate that is one unit south from this coordinate
     * @param southBound southern limit of grid system
     * @return coordinate that is one unit south from this coordinate if boundary is not crossed, else returns this
     */
    public Coordinates south(int southBound) {
        if (this.y >= southBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x);
    }

    /**
     * Returns coordinate that is one unit south and one unit west from this coordinate
     * @param southBound southern limit of grid system
     * @param westBound western limit of grid system
     * @return coordinate that is one unit south and one unit west from this coordinate if boundary is not crossed,
     * else returns this
     */
    public Coordinates southWest(int southBound, int westBound) {
        if (this.y >= southBound || this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x - 1);
    }

    /**
     * Returns coordinate that is one unit west from this coordinate
     * @param westBound western limit of grid system
     * @return coordinate that is one unit west from this coordinate if boundary is not crossed, else returns this
     */
    public Coordinates west(int westBound) {
        if (this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y, this.x - 1);
    }

    /**
     * Returns coordinate that is one unit north and one unit west from this coordinate
     * @param northBound northern limit of grid system
     * @param westBound western limit of grid system
     * @return coordinate that is one unit north and one unit west from this coordinate if boundary is not crossed,
     * else returns this
     */
    public Coordinates northWest(int northBound, int westBound) {
        if (this.y <= northBound || this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x - 1);
    }

    /**
     * Method to get distance to other coordinate
     * @param destination other 2D-coordinate
     * @return min field crossings needed to get to this coordinate
     */
    public int distance(Coordinates destination) {
        return Math.abs(this.x - destination.x) +  Math.abs(this.y - destination.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return this.y + "," + this.x;
    }

    @Override
    public Coordinates clone() {
        return new Coordinates(this.y, this.x);
    }
}
