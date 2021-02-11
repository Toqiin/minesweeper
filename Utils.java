

import java.awt.Color;

// class for util functions working with integers
class Utils {
  // converts an integer to its corresponding column
  public int convertCol(int i, int cols) {
    return i % cols;
  }
  
  // converts an integer to its corresponding row
  public int convertRow(int i, int cols) {
    return (i - (i % cols)) / cols;
  }

  // converts an integer to a color
  public Color numToColor(int num) {
    if (num == 1) {
      return Color.BLUE;
    } else if (num == 2) {
      return Color.GREEN;
    } else if (num == 3) {
      return Color.RED;
    } else if (num == 4) {
      return new Color(75, 0, 130);
    } else if (num == 5) {
      return new Color(139, 0, 0);
    } else if (num == 6) {
      return new Color(64, 224, 208);
    } else if (num == 7) {
      return Color.BLACK;
    } else if (num == 8) {
      return Color.DARK_GRAY;
    } else {
      return Color.WHITE;
    }
  }
}
