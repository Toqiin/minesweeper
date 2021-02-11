import java.util.*;

import javalib.impworld.*;

import javalib.worldimages.*;

// class to represent the grid of minesweeper, this is the world state
class Minesweeper {

  ArrayList<ArrayList<Cell>> grid;
  int rows;
  int cols;
  int mines;
  Random rand;

  //Constructor to test Random with a seed value
  Minesweeper(int r, int c, int m, int seed) {
    this.rows = r;
    this.cols = c;
    this.mines = m;
    this.grid = new ArrayList<ArrayList<Cell>>();
    this.rand = new Random(seed);

    ArrayList<Integer> mineList = new ArrayList<Integer>(this.mines);
    ArrayList<Integer> temp = new ArrayList<Integer>(this.rows * this.cols);

    temp = this.getNums(temp);
    mineList = this.genMineList(temp);

    for (int i = 0; i < this.rows; i++) {
      this.grid.add(new ArrayList<Cell>());
    }

    for (ArrayList<Cell> arr : this.grid) {
      for (int i = 0; i < this.cols; i++) {
        arr.add(new Cell(false));
      }
    }

    for (int i = 0; i < mineList.size(); i++) {
      int indx = mineList.get(i);
      int tempRow = (indx - (indx % this.cols)) / this.cols;
      int tempCol = indx % this.cols;

      this.grid.get(tempRow).get(tempCol).isMine = true;
    }

    this.genNeighbors();

  }

  // constructor that takes a custom grid of cells
  Minesweeper(int r, int c, int m, ArrayList<ArrayList<Cell>> grid) {
    this.rows = r;
    this.cols = c;
    this.mines = m;
    this.grid = grid;
    this.rand = new Random();

    ArrayList<Integer> mineList = new ArrayList<Integer>(this.mines);
    ArrayList<Integer> temp = new ArrayList<Integer>(this.rows * this.cols);

    temp = this.getNums(temp);
    mineList = this.genMineList(temp);

    for (int i = 0; i < this.rows; i++) {
      this.grid.add(new ArrayList<Cell>());
    }

    for (ArrayList<Cell> arr : this.grid) {
      for (int i = 0; i < this.cols; i++) {
        arr.add(new Cell(false));
      }
    }

    for (int i = 0; i < mineList.size(); i++) {
      int indx = mineList.get(i);
      int tempRow = (indx - (indx % this.cols)) / this.cols;
      int tempCol = indx % this.cols;

      this.grid.get(tempRow).get(tempCol).isMine = true;
    }

  }

  // constructor to testGenNeighbors
  Minesweeper(int r, int c, int m, boolean bool) {
    this.rows = r;
    this.cols = c;
    this.mines = m;
    this.grid = new ArrayList<ArrayList<Cell>>();
    this.rand = new Random();

    ArrayList<Integer> mineList = new ArrayList<Integer>(this.mines);
    ArrayList<Integer> temp = new ArrayList<Integer>(this.rows * this.cols);

    temp = this.getNums(temp);
    mineList = this.genMineList(temp);

    for (int i = 0; i < this.rows; i++) {
      this.grid.add(new ArrayList<Cell>());
    }

    for (ArrayList<Cell> arr : this.grid) {
      for (int i = 0; i < this.cols; i++) {
        arr.add(new Cell(false));
      }
    }

    for (int i = 0; i < mineList.size(); i++) {
      int indx = mineList.get(i);
      int tempRow = (indx - (indx % this.cols)) / this.cols;
      int tempCol = indx % this.cols;

      this.grid.get(tempRow).get(tempCol).isMine = true;
    }

  }

  // constructor to actually make minesweeper
  Minesweeper(int r, int c, int m) {
    if (r < 0) {
      this.rows = 0;
    } else {
      this.rows = r;
    }
    if (c < 0) {
      this.cols = 0;
    } else {
      this.cols = c;
    }
    if (m > this.cols * this.rows) {
      this.mines = this.cols * this.rows;
    } else {
      this.mines = m;
    }
    this.grid = new ArrayList<ArrayList<Cell>>();
    this.rand = new Random();

    ArrayList<Integer> mineList = new ArrayList<Integer>(this.mines);
    ArrayList<Integer> temp = new ArrayList<Integer>(this.rows * this.cols);

    temp = this.getNums(temp);
    mineList = this.genMineList(temp);

    for (int i = 0; i < this.rows; i++) {
      this.grid.add(new ArrayList<Cell>());
    }

    for (ArrayList<Cell> arr : this.grid) {
      for (int i = 0; i < this.cols; i++) {
        arr.add(new Cell(false));
      }
    }

    for (int i = 0; i < mineList.size(); i++) {
      int indx = mineList.get(i);
      int tempRow = (indx - (indx % this.cols)) / this.cols;
      int tempCol = indx % this.cols;

      this.grid.get(tempRow).get(tempCol).isMine = true;
    }

    this.genNeighbors();

  }

  // makes an array list of numbers from 0 to rows times columns 
  ArrayList<Integer> getNums(ArrayList<Integer> temp) {
    for (int z = 0; z < this.rows * this.cols; z++) {
      temp.add(z);
    } 
    return temp;
  }

  // randomly generates an arraylist of integers that represents the spots on the grid
  // that become mines
  ArrayList<Integer> genMineList(ArrayList<Integer> temp) {
    ArrayList<Integer> mineList = new ArrayList<Integer>();
    for (int i = 0; i < this.mines; i++) {
      int indx = rand.nextInt(temp.size());
      mineList.add(temp.get(indx));
      temp.remove(indx);
    }
    return mineList;
  }

  // links all the cells in the grid to their neighbors
  void genNeighbors() {

    /* i is rows, i + 1 = go down a row, i - 1 = go up a row
     * j is columns, j + 1 = go right a column, j - 1 = go left a column
     */

    for (int i = 0; i < this.rows; i++) {
      // sets neighbors for the first row
      if (i == 0) {
        for (int j = 0; j < this.cols; j++) {
          // sets neighbors for the first cell in the row
          if (j == 0) {
            if (this.rows == 1 && this.cols == 1) {
              this.grid.get(i).get(j).neighbors = this.grid.get(i).get(j).neighbors;
            } else if (this.rows == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
            } else if (this.cols == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            } else {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j + 1));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            }
            // sets neighbors for the last cell in the row
          } else if (j == this.cols - 1) {
            if (this.rows == 1 && this.cols == 1) {
              this.grid.get(i).get(j).neighbors = this.grid.get(i).get(j).neighbors;
            } else if (this.rows == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
            } else if (this.cols == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            } else {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j - 1));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
            }
            // sets neighbors for every other cell in the row
          } else {
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
          }
        }
        // sets neighbors for the last row
      } else if (i == this.rows - 1) {
        for (int j = 0; j < this.cols; j++) {
          // sets the neighbors for the first cell
          if (j == 0) {
            if (this.rows == 1 && this.cols == 1) {
              this.grid.get(i).get(j).neighbors = this.grid.get(i).get(j).neighbors;
            } else if (this.rows == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
            } else if (this.cols == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j));
            } else {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j + 1));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
            }
            // sets the neighbors for the last cell
          } else if (j == this.cols - 1) {
            if (this.rows == 1 && this.cols == 1) {
              this.grid.get(i).get(j).neighbors = this.grid.get(i).get(j).neighbors;
            } else if (this.rows == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
            } else if (this.cols == 1) {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            } else {
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
              this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j - 1));
            }
            // sets the neighbors for every other cell
          } else {
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
          }
        }
        // sets the neighbors for every other row
      } else {
        for (int j = 0; j < this.cols; j++) {
          if (j == 0) {
            // sets the neighbors for the first cell
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            // sets the neighbors for the last cell
          } else if (j == this.cols - 1) {
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            // sets the neighbors for every other cell
          } else {
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i - 1).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j - 1));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
            this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j + 1));
          }
        }
      }
    }
  }

  // draws the grid
  public WorldScene drawGrid(int h, int w, WorldScene scene) {
    int size = Math.min(h / this.rows, w / this.cols);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        scene.placeImageXY(this.grid.get(i).get(j).cellImage(h, w,
            this.rows, this.cols), (j * size) + (size / 2), (i * size) + (size / 2));
      }
    }
    return scene;
  }

  // handles clicks on this grid
  public boolean handleClick(Posn pos, String buttonName, int size) {
    int i = pos.y / size;
    int j = pos.x / size;
    boolean mineQ = false;
    if (buttonName.equals("RightButton")) {
      this.grid.get(i).get(j).handleRight();
    } else if (buttonName.equals("LeftButton")) {
      mineQ = this.grid.get(i).get(j).handleLeft();
    } 
    return mineQ;
  }

  // calculates the size of a cell on this minesweeper
  public int cellSize(int gameHeight, int gameWidth) {
    return Math.min(gameHeight / this.rows, gameWidth / this.cols);
  }

  // reveals all the mines on the grid
  public void revealMines() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (this.grid.get(i).get(j).isMine) {
          this.grid.get(i).get(j).revealed = true;
        }
      }
    }
  }

  // determines if the game is won or not
  public boolean wonQ() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (!this.grid.get(i).get(j).isMine && !this.grid.get(i).get(j).revealed) {
          return false;
        }
      }
    }
    return true;
  }

}

