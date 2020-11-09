package com.km;

import static java.lang.System.out;

import java.util.Map;
import java.util.Scanner;
import java.util.function.IntPredicate;

public class MinesWeeperConsole implements AutoCloseable {

  private final Scanner scanner;

  public MinesWeeperConsole() {
    this.scanner = new Scanner(System.in);
  }

  private int readVariable(String label, IntPredicate predicate) {
    int value;
    do {
      out.print(label);
      value = scanner.nextInt();
    } while (predicate.test(value));
    return value;
  }

  public int readWidth() {
    return this.readVariable("width = ", value -> value < 2);
  }

  public int readHeight() {
    return this.readVariable("height = ", value -> value < 2);
  }

  public int readMines(int width, int height) {
    return this.readVariable("mines = ", value -> value < 0 || value >= width * height);
  }

  public int readX(int width) {
    return this.readVariable("x = ", value -> value < 0 || value >= width);
  }

  public int readY(int height) {
    return this.readVariable("y = ", value -> value < 0 || value >= height);
  }

  public void printGrid(Map<Cell, Integer> uncoverdCells, int width, int height) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Integer uncoverCellValue = uncoverdCells.get(new Cell(x, y));
        if (uncoverCellValue == null) {
          out.print(" K | ");
        } else {
          out.print(" " + uncoverCellValue + " | ");
        }
      }
      out.println();
    }
  }

  public void printEndGame(GameStatus gameStatus) {
    if (gameStatus == GameStatus.FAILURE) {
      out.println("Game over");
    } else if (gameStatus == GameStatus.WIN) {
      out.println("You Win");
    }
  }

  public void printWelcome() {
    out.println("Start Game 'Minesweeper'");
  }

  @Override
  public void close() {
    this.scanner.close();
  }
}
