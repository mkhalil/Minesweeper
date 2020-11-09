package com.km;

import static com.km.MinesWeeperGrid.MINES_CELL_VALUE;

import java.util.HashMap;
import java.util.Map;

public class MinesWeeperGame {

  private MinesWeeperGrid minesWeeperGrid;

  private final Map<Cell, Integer> uncoverdCells;

  private GameStatus gameStatus;


  public MinesWeeperGame() {
    this(4,4,2);
  }

  public MinesWeeperGame(int width, int height, int mines) {
    this.uncoverdCells = new HashMap<>();
    this.minesWeeperGrid = new MinesWeeperGrid(width, height, mines);
    this.gameStatus = GameStatus.READY;
  }

  public static void main(String[] args) {
    try (MinesWeeperConsole minesWeeperConsole = new MinesWeeperConsole()) {
      minesWeeperConsole.printWelcome();
      final var width = minesWeeperConsole.readWidth();
      final var height = minesWeeperConsole.readHeight();
      final var mines = minesWeeperConsole.readMines(width, height);
      MinesWeeperGame minesWeeperGame = new MinesWeeperGame(width, height, mines);
      minesWeeperGame.play(minesWeeperConsole);
    }
  }

  private void play(MinesWeeperConsole minesWeeperConsole) {
    this.gameStatus = GameStatus.START;
    while (this.gameStatus == GameStatus.START) {
      final var x = minesWeeperConsole.readX(this.minesWeeperGrid.getWidth());
      final var y = minesWeeperConsole.readY(this.minesWeeperGrid.getHeight());
      this.uncoverCell(x, y);
      this.uncoverAllAdjacentMineCell(x, y);
      this.updateGameStatus(x, y);
      minesWeeperConsole.printGrid(
          this.uncoverdCells, this.minesWeeperGrid.getWidth(), this.minesWeeperGrid.getHeight());
    }
    minesWeeperConsole.printEndGame(this.gameStatus);
  }

  private void updateGameStatus(int x, int y) {
    final var maxCellToUnover = this.countMaxCellToUncover();
    if (this.uncoverdCells.get(new Cell(x, y)) == MINES_CELL_VALUE) {
      this.gameStatus = GameStatus.FAILURE;
    }
    if (maxCellToUnover == this.uncoverdCells.size()) {
      this.gameStatus = GameStatus.WIN;
    }
  }

  public int countMaxCellToUncover() {
    return this.minesWeeperGrid.getWidth() * this.minesWeeperGrid.getHeight()
        - this.minesWeeperGrid.getMines();
  }

  public void uncoverCell(int x, int y) {
    final var cellValue = this.minesWeeperGrid.getGrid().get(new Cell(x, y));
    if (cellValue != null) {
      this.uncoverdCells.put(new Cell(x, y), cellValue);
    }
  }

  public void uncoverAdjacentMineCell(int x, int y) {
    final var cellValue = this.minesWeeperGrid.getGrid().get(new Cell(x, y));
    if (cellValue != null && cellValue > 0) {
      this.uncoverdCells.putIfAbsent(new Cell(x, y), cellValue);
    }
  }

  public void uncoverAllAdjacentMineCell(int x, int y) {
    this.uncoverAdjacentMineCell(x - 1, y);
    this.uncoverAdjacentMineCell(x - 1, y + 1);
    this.uncoverAdjacentMineCell(x - 1, y - 1);
    this.uncoverAdjacentMineCell(x, y + 1);
    this.uncoverAdjacentMineCell(x, y - 1);
    this.uncoverAdjacentMineCell(x + 1, y + 1);
    this.uncoverAdjacentMineCell(x + 1, y);
    this.uncoverAdjacentMineCell(x + 1, y - 1);
  }

  public Map<Cell, Integer> getUncoverdCells() {
    return uncoverdCells;
  }
}
