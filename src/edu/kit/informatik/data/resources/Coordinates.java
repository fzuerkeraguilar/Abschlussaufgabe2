package edu.kit.informatik.data.resources;

import edu.kit.informatik.data.resources.exceptions.GameBoardSizeNotOddExceptions;
import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;

import java.util.Objects;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Coordinates {
    /**
     *
     */
    public int x;
    /**
     *
     */
    public int y;

    /**
     *
     * @param y
     * @param x
     */
    public Coordinates(final int y, final int x) {
        this.y = y;
        this.x = x;
    }

    /**
     *
     * @param y
     * @param x
     * @throws ValueOutOfRangeException
     * @throws GameBoardSizeNotOddExceptions
     */
    public Coordinates(final String y, final String x) throws ValueOutOfRangeException, GameBoardSizeNotOddExceptions {
        try {
            int temp = Integer.parseInt(y);
            if (temp % 2 != 1) {
                throw new GameBoardSizeNotOddExceptions(x);
            }
            this.y = temp;
        } catch (NumberFormatException | GameBoardSizeNotOddExceptions e) {
            throw new ValueOutOfRangeException(e.getMessage());
        }
        try {
            int temp = Integer.parseInt(y);
            if (temp % 2 != 1) {
                throw new GameBoardSizeNotOddExceptions(y);
            }
            this.x = temp;
        } catch (NumberFormatException e) {
            throw new ValueOutOfRangeException(e.getMessage());
        }

    }

    /**
     *
     * @param northBound
     * @return
     */
    public Coordinates north(int northBound) {
        if (this.y <= northBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x);
    }

    /**
     *
     * @param northBound
     * @param eastBound
     * @return
     */
    public Coordinates northEast(int northBound, int eastBound) {
        if (this.y <= northBound || this.x <= eastBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x + 1);
    }

    /**
     *
     * @param eastBound
     * @return
     */
    public Coordinates east(int eastBound) {
        if (this.x >= eastBound) {
            return this;
        }
        return new Coordinates(this.y, this.x + 1);
    }

    /**
     *
     * @param southBound
     * @param eastBound
     * @return
     */
    public Coordinates southEast(int southBound, int eastBound) {
        if (this.y >= southBound  || this.x >= eastBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x + 1);
    }

    /**
     *
     * @param southBound
     * @return
     */
    public Coordinates south(int southBound) {
        if (this.y >= southBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x);
    }

    /**
     *
     * @param southBound
     * @param westBound
     * @return
     */
    public Coordinates southWest(int southBound, int westBound) {
        if (this.y >= southBound || this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x - 1);
    }

    /**
     *
     * @param westBound
     * @return
     */
    public Coordinates west(int westBound) {
        if (this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y, this.x - 1);
    }

    /**
     *
     * @param northBound
     * @param westBound
     * @return
     */
    public Coordinates northWest(int northBound, int westBound) {
        if (this.y <= northBound || this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x - 1);
    }
//TODO

    /**
     *
     * @param destination
     * @return
     */
    public double distance(Coordinates destination) {
        return Math.hypot(Math.abs(this.x - destination.x), Math.abs(this.y - destination.y));
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
}
