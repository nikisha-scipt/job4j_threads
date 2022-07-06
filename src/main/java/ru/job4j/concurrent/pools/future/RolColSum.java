package ru.job4j.concurrent.pools.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            Sums tempSums = new Sums();
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < size; j++) {
                sumRow += matrix[i][j];
                sumCol += matrix[j][i];
            }
            tempSums.setRowSum(sumRow);
            tempSums.setColSum(sumCol);
            sums[i] = tempSums;
        }
        return sums;
    }

    public Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        List<CompletableFuture<Sums>> tempSums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CompletableFuture<Sums> completableFuture = supplyAsyncGetSums(matrix, i);
            tempSums.add(completableFuture);
            sums[i] = tempSums.get(i).get();
        }
        return sums;
    }

    private CompletableFuture<Sums> supplyAsyncGetSums(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < matrix.length; j++) {
                sumRow += matrix[i][j];
                sumCol += matrix[j][i];
            }
            sums.setRowSum(sumRow);
            sums.setColSum(sumCol);
            return sums;
        });
    }

}
