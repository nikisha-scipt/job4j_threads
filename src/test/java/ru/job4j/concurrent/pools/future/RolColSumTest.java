package ru.job4j.concurrent.pools.future;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RolColSumTest {

    protected RolColSum rolColSum;
    protected Sums[] synchronizedResult;
    protected Sums[] asyncResult;
    protected int[][] synchronizedMatrix;
    protected int[][] asyncMatrix;

    @Before
    public void init() throws ExecutionException, InterruptedException {
        rolColSum = new RolColSum();
        synchronizedMatrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        asyncMatrix = new int[][]{{10, 11, 12}, {13, 14, 15}, {16, 17, 18}};
        synchronizedResult = rolColSum.sum(synchronizedMatrix);
        asyncResult = rolColSum.asyncSum(asyncMatrix);
    }

    @Test
    public void whenSynchronizedSum() {
        List<Integer> result = List.of(
                synchronizedResult[0].getRowSum(),
                synchronizedResult[0].getColSum(),
                synchronizedResult[1].getRowSum(),
                synchronizedResult[1].getColSum(),
                synchronizedResult[2].getRowSum(),
                synchronizedResult[2].getColSum()
        );
        List<Integer> expected = List.of(
                6, 12, 15, 15, 24, 18
        );
        assertThat(expected, is(result));
    }

    @Test
    public void whenAsyncSum() {
        List<Integer> result = List.of(
                asyncResult[0].getRowSum(),
                asyncResult[0].getColSum(),
                asyncResult[1].getRowSum(),
                asyncResult[1].getColSum(),
                asyncResult[2].getRowSum(),
                asyncResult[2].getColSum()
        );
        List<Integer> expected = List.of(
                33, 39, 42, 42, 51, 45
        );
        assertThat(expected, is(result));

    }



}