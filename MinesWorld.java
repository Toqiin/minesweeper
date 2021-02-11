import java.util.*;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// world class, ms is the world state
class MinesWorld extends World {
  int gameWidth = 500;
  int gameHeight = 500;
  Minesweeper ms;

  MinesWorld(int rows, int cols, int mines) {
    this.ms = new Minesweeper(rows, cols, mines);
  }


  MinesWorld(int rows, int cols, int mines, Minesweeper ms) {
    this.ms = ms;
  }

  // draws the world
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    return this.ms.drawGrid(this.gameHeight, this.gameWidth, scene);
  }

  // draws the world with a game over message
  public WorldScene lastScene(String msg) {
    WorldScene scene = this.makeScene();
    scene.placeImageXY(new TextImage(msg, this.gameWidth / 17, FontStyle.BOLD, Color.WHITE),
        this.gameWidth / 2, this.gameHeight / 2);
    return scene;
  }

  // handler for clicking the mouse on the game
  // EFFECT: reveals all mines if click on mine and end game
  // checks if all non mines are revealed and says you won if they are
  public void onMouseClicked(Posn pos, String buttonName) {
    int size = this.ms.cellSize(this.gameHeight, this.gameWidth);
    boolean endQ = this.ms.handleClick(pos, buttonName, size);
    if (endQ) {
      this.ms.revealMines();
      this.endOfWorld("GAME OVER: YOU LOST");
    } else {
      if (this.ms.wonQ()) {
        this.endOfWorld("GAME OVER: YOU WON");
      }
    }
  }
}

// Class to run and test minesweeper
// RUN THIS
class ExamplesMinesWorld {

  void testBigBang(Tester t) {
    MinesWorld w = new MinesWorld(20, 20, 55);

    int worldWidth = w.gameWidth;
    int worldHeight = w.gameHeight;

    double tickRate = 1.0 / 60.0;

    w.bigBang(worldWidth, worldHeight, tickRate);   
  }

  ArrayList<Integer> i1 = new ArrayList<Integer>();
  Minesweeper ms1; 
  Minesweeper ms2;
  Cell cell1;
  Minesweeper ms3;
  Minesweeper ms4;
  Minesweeper ms5;
  MinesWorld w1;

  void initData() {
    this.ms1 = new Minesweeper(10, 10, 10);
    this.ms2 = new Minesweeper(10, 10, 10);
    this.ms1.grid.get(1).get(1).isMine = true;
    cell1 = new Cell(false);
    this.ms3 = new Minesweeper(2, 1, 1, 2);
    this.ms4 = new Minesweeper(10, 10, 10, 10);
    this.w1 = new MinesWorld(2, 2, 0);
    this.ms5 = new Minesweeper(2, 2, 0);

  }

  void testRandomMines(Tester t) {
    this.initData();
    t.checkExpect(this.ms3.grid.get(1).get(0).isMine, true);
    t.checkExpect(this.ms3.grid.get(0).get(0).isMine, false);
    t.checkExpect(this.ms4.grid.get(5).get(5).isMine, false);
    t.checkExpect(this.ms4.grid.get(2).get(3).isMine, true);

  }

  void testStuff(Tester t) {
    this.initData();
    t.checkExpect(this.ms1.grid.get(1).get(1).isMine, true);


  }

  void testNeighborsExist(Tester t) {
    this.initData();
    t.checkExpect(this.ms1.grid.get(3).get(3).neighbors.get(1).exists, true);
    t.checkExpect(this.ms2.grid.get(5).get(3).neighbors.get(1).exists, true);
    t.checkExpect(this.ms2.grid.get(9).get(9).neighbors.get(2).exists, true);
    t.checkExpect(this.ms2.grid.get(0).get(0).neighbors.get(2).exists, true);

  }

  void testGetNums(Tester t) {
    Minesweeper smallGrid = new Minesweeper(2, 2, 0);
    Minesweeper smallerGrid = new Minesweeper(2, 1, 0);

    t.checkExpect(smallGrid.getNums(new ArrayList<Integer>()), new ArrayList<Integer>(Arrays.asList(
        0, 1, 2, 3)));
    t.checkExpect(smallerGrid.getNums(new ArrayList<Integer>()),
        new ArrayList<Integer>(Arrays.asList(
        0, 1)));
  }

  void testGenNeighbors(Tester t) {
    Minesweeper by3 = new Minesweeper(3, 3, 0, false);
    t.checkExpect(by3.grid.get(1).get(1).neighbors, new ArrayList<Cell>());
    t.checkExpect(by3.grid.get(1).get(1).countNeighbors(), 0);
    by3.genNeighbors();
    t.checkExpect(by3.grid.get(1).get(1).countNeighbors(), 8);
    t.checkExpect(by3.grid.get(0).get(0).countNeighbors(), 3);
    t.checkExpect(by3.grid.get(0).get(1).countNeighbors(), 5);
  }

  void testCountNeighbors(Tester t) {
    Cell single = new Cell(false, new ArrayList<Cell>());
    Cell next = new Cell(false, new ArrayList<Cell>(Arrays.asList(new Cell(true),
        new Cell(false), new Cell(false))));
    t.checkExpect(single.countNeighbors(), 0);
    t.checkExpect(next.countNeighbors(), 3);
  }

  void testNumToColor(Tester t) {
    t.checkExpect(new Utils().numToColor(34), Color.WHITE);
    t.checkExpect(new Utils().numToColor(3), Color.RED);
  }

  void testConvertColsAndRows(Tester t) {
    t.checkExpect(new Utils().convertCol(63, 10), 3);
    t.checkExpect(new Utils().convertRow(63, 10), 6);
  }

  void testCellImage(Tester t) {
    Cell tested = new Cell(true);
    t.checkExpect(tested.cellImage(500, 500, 1, 1),
        new OverlayImage(new OverlayImage(new RectangleImage(500, 500,
        OutlineMode.OUTLINE, Color.BLACK),
        new RectangleImage(500, 500, OutlineMode.SOLID, new Color(135, 206, 250))),
        new OverlayImage(new CircleImage(200, OutlineMode.SOLID, Color.BLACK),
            new OverlayImage(new RectangleImage(500, 500, OutlineMode.OUTLINE, Color.BLACK),
                new RectangleImage(500, 500, OutlineMode.SOLID, new Color(219, 112, 147))))));
  }

  void testDrawGrid(Tester t) {
    Cell gridTest = new Cell(true);
    ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        new ArrayList<Cell>(Arrays.asList(gridTest, gridTest)),
            new ArrayList<Cell>(Arrays.asList(gridTest, gridTest))));
    Minesweeper msTest = new Minesweeper(2, 2, 0, cells);
    WorldScene scene = new MinesWorld(2, 2, 0).getEmptyScene();
    WorldScene draw = scene;
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 100, 100);
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 300, 100);
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 100, 300);
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 300, 300);
    t.checkExpect(msTest.drawGrid(400, 400, scene), draw);
  }

  void testDrawScene(Tester t) {
    Cell gridTest = new Cell(true);
    ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        new ArrayList<Cell>(Arrays.asList(gridTest, gridTest)),
            new ArrayList<Cell>(Arrays.asList(gridTest, gridTest))));
    Minesweeper msTest = new Minesweeper(2, 2, 0, cells);
    MinesWorld mw = new MinesWorld(2, 2, 0, msTest);
    WorldScene scene = mw.getEmptyScene();
    WorldScene draw = scene;
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 100, 100);
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 300, 100);
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 100, 300);
    draw.placeImageXY(gridTest.cellImage(400, 400, 2, 2), 300, 300);
    t.checkExpect(mw.makeScene(), draw);
  }

  void testCellHandleLeftClick(Tester t) {
    this.initData();
    t.checkExpect(this.cell1.revealed, false);
    t.checkExpect(this.cell1.handleLeft(), false);
    t.checkExpect(this.cell1.revealed, true);
    this.initData();
    this.cell1.isMine = true;
    t.checkExpect(this.cell1.revealed, false);
    t.checkExpect(this.cell1.handleLeft(), true);
    t.checkExpect(this.cell1.revealed, true);
    this.initData();
    Cell cell2 = new Cell(false);
    this.cell1.neighbors = new ArrayList<Cell>(Arrays.asList(cell2, cell2, cell2));
    t.checkExpect(this.cell1.neighbors.get(0).revealed, false);
    t.checkExpect(this.cell1.neighbors.get(1).revealed, false);
    t.checkExpect(this.cell1.neighbors.get(2).revealed, false);
    this.cell1.handleLeft();
    t.checkExpect(this.cell1.neighbors.get(0).revealed, true);
    t.checkExpect(this.cell1.neighbors.get(1).revealed, true);
    t.checkExpect(this.cell1.neighbors.get(2).revealed, true);
    this.initData();
    this.cell1.flagged = true;
    this.cell1.handleLeft();
    this.cell1.flagged = false;
  }

  void testCellHandleRight(Tester t) {
    this.initData();
    this.cell1.revealed = true;
    t.checkExpect(this.cell1.flagged, false);
    this.cell1.handleRight();
    t.checkExpect(this.cell1.flagged, false);
    this.initData();
    t.checkExpect(this.cell1.flagged, false);
    this.cell1.handleRight();
    t.checkExpect(this.cell1.flagged, true);
  }


  void testMakeScene(Tester t) {
    MinesWorld mw = new MinesWorld(2, 2, 0);
    Cell gridTest = new Cell(true);
    ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        new ArrayList<Cell>(Arrays.asList(gridTest, gridTest)),
            new ArrayList<Cell>(Arrays.asList(gridTest, gridTest))));
    mw.ms.grid = cells;
    t.checkExpect(mw.makeScene(), mw.ms.drawGrid(mw.gameHeight, mw.gameWidth, mw.getEmptyScene()));
  }

  void testLastScene(Tester t) {
    MinesWorld mw = new MinesWorld(2, 2, 0);
    Cell gridTest = new Cell(true);
    ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        new ArrayList<Cell>(Arrays.asList(gridTest, gridTest)),
            new ArrayList<Cell>(Arrays.asList(gridTest, gridTest))));
    mw.ms.grid = cells;
    WorldScene scene = mw.makeScene();
    scene.placeImageXY(new TextImage("test",
        29, FontStyle.BOLD, Color.WHITE), 250, 250);
    t.checkExpect(mw.lastScene("test"), scene);
  }

  void testOnMouseClicked(Tester t) {
    MinesWorld mw = new MinesWorld(2, 2, 4);
    t.checkExpect(mw.ms.grid.get(0).get(0).flagged, false);
    mw.onMouseClicked(new Posn(100, 100), "RightButton");
    t.checkExpect(mw.ms.grid.get(0).get(0).flagged, true);
    mw = new MinesWorld(2, 2, 4);
    t.checkExpect(mw.ms.grid.get(0).get(0).revealed, false);
    mw.onMouseClicked(new Posn(100, 100), "LeftButton");
    t.checkExpect(mw.ms.grid.get(0).get(0).revealed, true);
    mw = new MinesWorld(2, 2, 4);
    WorldScene scene = mw.ms.drawGrid(mw.gameHeight, mw.gameWidth, mw.getEmptyScene());
    t.checkExpect(mw.makeScene(), scene);
    scene = mw.lastScene("GAME OVER: YOU LOST");
    mw.onMouseClicked(new Posn(100, 100), "LeftButton");
    t.checkExpect(mw.makeScene(), scene);

  }

  void testHandleClick(Tester t) {
    this.initData();
    this.ms5.handleClick(new Posn(100,100), "RightButton", 250);
    t.checkExpect(this.ms5.grid.get(0).get(0).flagged, true);
    this.ms5.handleClick(new Posn(300,100), "LeftButton", 250);
    t.checkExpect(this.ms5.grid.get(0).get(1).revealed, true);
    this.ms5.grid.get(1).get(1).isMine = true;
    t.checkExpect(this.ms1.handleClick(new Posn(400, 400), "LeftButton", 250), true);
    this.ms5.grid.get(1).get(1).isMine = false;
    t.checkExpect(this.ms1.handleClick(new Posn(400, 400), "LeftButton", 250), false);
  }

  void testRevealMines(Tester t) {
    this.initData();
    this.ms5.grid.get(1).get(1).isMine = true;
    this.ms5.grid.get(0).get(1).isMine = true;
    this.ms5.revealMines();
    t.checkExpect(this.ms5.grid.get(1).get(1).revealed, true);
    t.checkExpect(this.ms5.grid.get(0).get(1).revealed, true);

  }

  void testWonQ(Tester t) {
    this.initData();
    this.ms5.grid.get(0).get(0).isMine = true;
    this.ms5.grid.get(0).get(1).revealed = true;
    this.ms5.grid.get(1).get(0).revealed = true;
    this.ms5.grid.get(1).get(1).revealed = true;
    t.checkExpect(this.ms5.wonQ(), true);

  }

  void testCellSize(Tester t) {
    this.initData();
    t.checkExpect(this.ms1.cellSize(500, 500), 50);
    t.checkExpect(this.ms2.cellSize(500, 500), 50);
    t.checkExpect(this.ms5.cellSize(500, 500), 250);

  }
  
  void testConstructorLimits(Tester t) {
    Minesweeper msConstruct = new Minesweeper(-1, -1, 100);
    t.checkExpect(msConstruct.rows, 0);
    t.checkExpect(msConstruct.cols, 0);
    t.checkExpect(msConstruct.mines, 0);
  }

}