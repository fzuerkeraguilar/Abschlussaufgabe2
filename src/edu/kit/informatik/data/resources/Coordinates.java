package edu.kit.informatik.data.resources;

import edu.kit.informatik.data.resources.exceptions.GameBoardSIzeNotOddExceptions;
import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;

import java.awt.geom.Point2D;

public class Coordinates {
    public int x;
    public int y;

    public Coordinates(final int Y, final int X) {
        this.y = Y;
        this.x = X;
    }

    public Coordinates(final String Y, final String X) throws ValueOutOfRangeException, GameBoardSIzeNotOddExceptions {
        try {
            int temp = Integer.parseInt(Y);
            if (temp % 2 != 1) {
                throw new GameBoardSIzeNotOddExceptions(X);
            }
            this.y = temp;
        } catch (NumberFormatException | GameBoardSIzeNotOddExceptions e) {
            throw new ValueOutOfRangeException(e.getMessage());
        }
        try {
            int temp = Integer.parseInt(Y);
            if (temp % 2 != 1) {
                throw new GameBoardSIzeNotOddExceptions(Y);
            }
            this.x = temp;
        } catch (NumberFormatException e) {
            throw new ValueOutOfRangeException(e.getMessage());
        }

    }

    public Coordinates north(int northBound) {
        if(this.y <= northBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x);
    }

    public Coordinates northEast(int northBound, int eastBound) {
        if(this.y <= northBound || this.x <= eastBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x + 1);
    }

    public Coordinates east(int eastBound) {
        if(this.x >= eastBound) {
            return this;
        }
        return new Coordinates(this.y, this.x + 1);
    }

    public Coordinates southEast(int southBound, int eastBound) {
        if (this.y >= southBound  || this.x >= eastBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x + 1);
    }

    public Coordinates south(int southBound) {
        if(this.y >= southBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x);
    }

    public Coordinates southWest(int southBound, int westBound) {
        if(this.y >= southBound || this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y + 1, this.x - 1);
    }

    public Coordinates west(int westbound) {
        if(this.x <= westbound) {
            return this;
        }
        return new Coordinates(this.y, this.x - 1);
    }

    public Coordinates northWest(int northBound, int westBound) {
        if(this.y <= northBound || this.x <= westBound) {
            return this;
        }
        return new Coordinates(this.y - 1, this.x - 1);
    }

    public double distance(Coordinates destination) {
        return Math.hypot(Math.abs(this.x - destination.x), Math.abs(this.y - destination.y));
    }

    @Override
    public boolean equals(Object o)  {
        if(!o.getClass().equals(this.getClass())) {
            return false;
        }
        Coordinates c = (Coordinates) o;
        return this.y == c.y && this.x == c.x;
    }
}
