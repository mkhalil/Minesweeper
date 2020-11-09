package com.km;

import java.util.HashMap;
import java.util.Map;

public class HelperTest {

  private HelperTest() {
  }

  public static Map<Cell, Integer> convertToMap(int[][] grid) {
    Map<Cell, Integer> map = new HashMap<>();
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[0].length; x++) {
        map.put(new Cell(x, y), grid[x][y]);
      }
    }
    return map;
  }
}
