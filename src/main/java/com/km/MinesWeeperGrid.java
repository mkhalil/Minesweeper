package com.km;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class MinesWeeperGrid {
  public static final int MINES_CELL_VALUE = -1;
  private final int width;
  private final int height;
  private final int mines;
  private Map<Cell, Integer> grid;

  public MinesWeeperGrid(int width, int height, int mines) {
    this.width = width;
    this.height = height;
    this.mines = mines;
    this.grid = this.newGrid();
    this.fillGrid();
  }

  private Map<Cell, Integer> newGrid() {
    Map<Cell, Integer> map = new HashMap<>();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        map.put(new Cell(x, y), 0);
      }
    }
    return map;
  }

  private void fillGrid() {
    this.fillMines();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (this.grid.get(new Cell(x, y)) == 0) {
          final var countAdjacentMines = this.countAdjacentMines(x, y);
          this.grid.put(new Cell(x, y), countAdjacentMines);
        }
      }
    }
  }

  public void fillMines() {
    for (int i = 0; i < mines; i++) {
      int x = new Random().nextInt(width);
      int y = new Random().nextInt(height);
      this.grid.put(new Cell(x, y), MINES_CELL_VALUE);
    }
  }

  public int countAdjacentMines(int x, int y) {
    return Stream.of(
            this.getCellValue(x - 1, y),
            this.getCellValue(x - 1, y + 1),
            this.getCellValue(x - 1, y - 1),
            this.getCellValue(x, y + 1),
            this.getCellValue(x, y - 1),
            this.getCellValue(x + 1, y + 1),
            this.getCellValue(x + 1, y),
            this.getCellValue(x + 1, y - 1))
        .filter(Objects::nonNull)
        .map(operand -> operand == MINES_CELL_VALUE ? 1 : 0)
        .mapToInt(Integer::intValue)
        .sum();
  }

  public Integer getCellValue(int x, int y) {
    return this.grid.get(new Cell(x, y));
  }

  public Map<Cell, Integer> getGrid() {
    return grid;
  }

  public void setGrid(Map<Cell, Integer> grid) {
    this.grid = grid;
  }


  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getMines() {
    return mines;
  }
}
