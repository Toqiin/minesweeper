import java.util.ArrayList;

import java.awt.Color;
import javalib.worldimages.*;

// class to represent a Cell in minesweeper
class Cell {
  boolean isMine;
  ArrayList<Cell> neighbors;
  boolean exists;
  boolean flagged;
  boolean revealed;

  // constructor for testing with custom neighbor list
  Cell(boolean m, ArrayList<Cell> n) {
    this.isMine = m;
    this.neighbors = n;
    this.exists = true;
    this.flagged = false;
    this.revealed = false;

  }

  Cell(boolean m) {
    this.isMine = m;
    this.neighbors = new ArrayList<Cell>();
    this.exists = true;
    this.flagged = false;
    this.revealed = false;
  }

  // returns the number of mines that are in the neigboring cells
  int numMines() {
    int count = 0;
    for (Cell c : this.neighbors) {
      if (c.isMine) {
        count++;
      }
    }
    return count;
  }

  // returns the number of neighbors, used for testing other methods
  int countNeighbors() {
    int count = 0;
    for (int i = 0; i < this.neighbors.size(); i++) {
      count++;
    }
    return count;
  }

  // draws an image of a cell
  WorldImage cellImage(int h, int w, int rows, int cols) {

    int size;
    size = Math.min(h / rows, w / cols);

    Color underColor;
    if (this.isMine) {
      underColor = new Color(219, 112, 147);
    } else {
      underColor = Color.GRAY;
    }

    WorldImage underImg;
    if (this.isMine) {
      underImg = new CircleImage((int) Math.round(size * 0.4), OutlineMode.SOLID, Color.BLACK);
    } else if (this.numMines() == 0) {
      underImg = new EmptyImage();
    } else {
      underImg = new TextImage(Integer.toString(this.numMines()), size * 0.8,
          new Utils().numToColor(this.numMines()));
    }

    Color overColor;
    if (!this.revealed) {
      overColor = new Color(135, 206, 250);
    } else {
      overColor = new Color(0, 0, 0, 1);
    }

    WorldImage flag;
    flag = new EquilateralTriangleImage(size * 0.2, OutlineMode.SOLID, Color.yellow);

    WorldImage top;
    top = new OverlayImage(new RectangleImage(size, size, OutlineMode.OUTLINE, Color.BLACK),
        new RectangleImage(size, size, OutlineMode.SOLID, overColor));
    WorldImage bot;
    bot = new OverlayImage(underImg, (new OverlayImage(new RectangleImage(size, size,
        OutlineMode.OUTLINE, Color.BLACK),
        new RectangleImage(size, size, OutlineMode.SOLID, underColor))));

    if (this.flagged) {
      return new OverlayImage(flag, new OverlayImage(top, bot));
    } else {
      return new OverlayImage(top, bot);
    }

  }

  // handles right clicks on this cell
  // EFFECT: toggles flagged on this cell
  public void handleRight() {
    if (!this.revealed) {
      this.flagged = !this.flagged;
    }
  }

  // handles left clicks on this cell
  // EFFECT: reveals the cell if not already, 
  public boolean handleLeft() {
    this.flagged = false;
    if (!this.revealed) {
      this.revealed = true;
      if (this.isMine) {
        return true;
      } else if (this.numMines() == 0) {
        for (int i = 0; i < this.neighbors.size(); i++) {
          this.neighbors.get(i).handleLeft();
        }
        return false;
      } else {
        return false;
      }
    } else {
      return false;
    }

  }

}

