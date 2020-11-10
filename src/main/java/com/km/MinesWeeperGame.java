package com.km;

import static com.km.MinesWeeperGrid.MINES_CELL_VALUE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MinesWeeperGame {

  private final Map<Cell, Integer> uncoverdCells;
  private MinesWeeperGrid minesWeeperGrid;
  private GameStatus gameStatus;


  public MinesWeeperGame() {
    this.uncoverdCells = new HashMap<>();
    this.gameStatus = GameStatus.READY;
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
      final var cellValue = this.uncoverCell(x, y);
      cellValue.ifPresent(value -> {
        if (value != MINES_CELL_VALUE) {
          this.uncoverAllAdjacentMineCell(x, y);
        }
        this.updateGameStatus(value);
      });
      minesWeeperConsole.printGrid(
          this.uncoverdCells, this.minesWeeperGrid.getWidth(), this.minesWeeperGrid.getHeight());
    }
    minesWeeperConsole.printEndGame(this.gameStatus);
  }

  private void updateGameStatus(int unCovredCellValue) {
    final var maxCellToUnover = this.countMaxCellToUncover();
    if (unCovredCellValue == MINES_CELL_VALUE) {
      this.gameStatus = GameStatus.FAILURE;
    } else if (maxCellToUnover == this.uncoverdCells.size()) {
      this.gameStatus = GameStatus.WIN;
    }
  }

  public int countMaxCellToUncover() {
    return this.minesWeeperGrid.getWidth() * this.minesWeeperGrid.getHeight()
        - this.minesWeeperGrid.getMines();
  }


  public Optional<Integer> uncoverCell(int x, int y) {
    final var cellValue = this.minesWeeperGrid.getGrid().get(new Cell(x, y));
    this.uncoverdCells.put(new Cell(x, y), cellValue);
    return Optional.ofNullable(cellValue);
  }

  public void uncoverAdjacentMineCell(int x, int y) {
    final var cellValue = this.minesWeeperGrid.getGrid().get(new Cell(x, y));
    if (cellValue != null && cellValue != MINES_CELL_VALUE) {
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
