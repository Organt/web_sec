package com.demo.sudoku.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Generator {

    public static int[][] solvedSudoku() {
        Map<Integer, int[]> solved = new HashMap<>();
        List<int[]> allP = Permutations.allPermutations();

        while (solved.size() < 9) {
            List<int[]> restP = allP.stream().collect(Collectors.toList());
            ;
            for (int i = 1; i < 10; i++) {
                if (restP.size() > 0) {
                    int[] temp = restP.get((int) (Math.random() * (restP.size())));
                    solved.put(i, temp);
                    if (i < 9) {
                        for (int j = 0; j < restP.size(); j++) {
                            for (int k = 0; k < 9; k++) {
                                if (restP.get(j)[k] == temp[k]) {
                                    restP.remove(j);
                                    j--;
                                    break;
                                }
                            }
                        }
                    }
                } else break;
            }
        }
        int[][] solvedMatrix = new int[9][9];
        for (int i = 1; i <= 9; i++) {
            int[] k = Permutations.toRows(solved.get(i));
            int[] l = Permutations.toColumns(solved.get(i));
            for (int j = 0; j < 9; j++) {
                solvedMatrix[k[j]][l[j]] = i;
            }
        }
        return solvedMatrix;
    }

    public static int[][] generate() {
        boolean notProperLevel = true;
        int[][] matrix = solvedSudoku();
        List<Integer> fixedX = new ArrayList<>();
        List<Integer> fixedY = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fixedX.add(i);
                fixedY.add(j);
            }
        }
            notProperLevel = false;
            while (fixedX.size() > 0 ) {
                int random = (int) (Math.random() * fixedX.size());
                int save = matrix[fixedX.get(random)][fixedY.get(random)];
                matrix[fixedX.get(random)][fixedY.get(random)] = 0;
                Solver solver = new Solver();
                List<Map<Integer, List<int[]>>> solutions = solver.solve(matrix);
                if (solutions.size() > 1) {
                    matrix[fixedX.get(random)][fixedY.get(random)] = save;
                }
                fixedX.remove(random);
                fixedY.remove(random);
            }
        return matrix;
    }

}
