package com.km;

import org.junit.Assert;
import org.junit.Test;

public class MinesWeeperGridTest {

    @Test
    public void testCountAdjacentsShouldReturnThree() {
        MinesWeeperGrid minesWeeperGrid = new MinesWeeperGrid(2, 2, 3);
        // when grid
        int[][] a = {{0, -1}, {-1, -1}};
        minesWeeperGrid.setGrid(HelperTest.convertToMap(a));
        Assert.assertEquals(3, minesWeeperGrid.countAdjacentMines(0, 0));
        int[][] b = {{-1, -1}, {-1, 0}};
        minesWeeperGrid.setGrid(HelperTest.convertToMap(b));
        Assert.assertEquals(3, minesWeeperGrid.countAdjacentMines(1, 1));
    }

    @Test
    public void testCountAdjacentsShouldReturnFour() {
        MinesWeeperGrid minesWeeperGrid = new MinesWeeperGrid(3, 3, 4);
        // when grid
        int[][] a = {
                {0, -1, 0},
                {-1, 0, -1},
                {0, -1, 0},
        };
        minesWeeperGrid.setGrid(HelperTest.convertToMap(a));
        Assert.assertEquals(4, minesWeeperGrid.countAdjacentMines(1, 1));
    }
}