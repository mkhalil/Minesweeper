package com.km;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MinesWeeperGameTest {

  @Mock MinesWeeperGrid minesWeeperGrid;

  @InjectMocks MinesWeeperGame minesWeeperGame;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void uncoverAllAdjacentMineCellShouldCountTwo() {
    int[][] grid = {
      {1, -1},
      {1, 1}
    };
    when(minesWeeperGrid.getGrid()).thenReturn(HelperTest.convertToMap(grid));
    minesWeeperGame.uncoverAllAdjacentMineCell(0, 0);
    Assert.assertEquals(2, minesWeeperGame.getUncoverdCells().size());
  }

  @Test
  public void uncoverAllAdjacentMineCellShouldCountZero() {
    int[][] grid = {
      {0, 0, 0},
      {0, 0, 0},
      {0, 0, 0}
    };

    when(minesWeeperGrid.getGrid()).thenReturn(HelperTest.convertToMap(grid));
    // uncover mine cell
    minesWeeperGame.uncoverAllAdjacentMineCell(0, 0);
    Assert.assertEquals(0, minesWeeperGame.getUncoverdCells().size());
  }

  @Test
  public void uncoverAllAdjacentMineCellShouldCountOne() {
    int[][] grid = {
      {0, 0, 0},
      {0, 1, 1},
      {0, 1, -1}
    };

    when(minesWeeperGrid.getGrid()).thenReturn(HelperTest.convertToMap(grid));
    // uncover mine cell
    minesWeeperGame.uncoverAllAdjacentMineCell(0, 0);
    Assert.assertEquals(1, minesWeeperGame.getUncoverdCells().size());
  }
}
