package com.demo.sudoku.model;

import java.util.*;
import java.util.stream.Collectors;

public class Solver {
    List<Map<Integer, List<int[]>>> solutions = new ArrayList<>();

    public List<Map<Integer, List<int[]>>> solve(int[][] matrix) {
        Map<Integer, List<int[]>> IP = Permutations.indexedPermutations(matrix);
        List sortedKeys = IP
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getValue().size()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        findAllSolutions(-1, IP, sortedKeys);
        return solutions;
    }

    public int[][] toMatrix(Map<Integer, List<int[]>> IP) {
        int[][] solvedMatrix = new int[9][9];
        for (int i = 1; i <= 9; i++) {
            int[] k = Permutations.toRows(IP.get(i).get(0));
            int[] l = Permutations.toColumns(IP.get(i).get(0));
            for (int j = 0; j < 9; j++) {
                solvedMatrix[k[j]][l[j]] = i;
            }
        }
        return solvedMatrix;
    }

    public void findAllSolutions(int level, Map<Integer, List<int[]>> P, List sortedKeys) {
        level++;
        if (level == 8) {
            solutions.add(P);
            return;
        } else {
            for (int i = 0; i < P.get(sortedKeys.get(level)).size(); i++) {
                Map<Integer, List<int[]>> tempP = copy(P);
                int[] tempVector = tempP.get(sortedKeys.get(level)).get(i).clone();
                tempP.get(sortedKeys.get(level)).clear();
                tempP.get(sortedKeys.get(level)).add(tempVector);
                for (int j = level + 1; j < 9; j++) {
                    if (tempP.get(sortedKeys.get(j)).size() == 0) break;
                    for (int k = 0; k < tempP.get(sortedKeys.get(j)).size(); k++) {
                        for (int l = 0; l < 9; l++) {
                            if (tempP.get(sortedKeys.get(level)).get(0)[l] == (tempP.get(sortedKeys.get(j)).get(k)[l])) {
                                tempP.get(sortedKeys.get(j)).remove(k);
                                k--;
                                break;
                            }
                        }
                    }
                }
                boolean solvable = true;
                for (int j = level + 1; j < 9; j++) {
                    if (tempP.get(sortedKeys.get(j)).size() == 0) {
                        solvable = false;
                    }
                }
                if (solvable) {
                    findAllSolutions(level, tempP, sortedKeys);
                }
                if (solutions.size() > 1) break;
            }
        }
    }

    public static Map<Integer, List<int[]>> copy(
            Map<Integer, List<int[]>> original) {
        Map<Integer, List<int[]>> copy = new HashMap<Integer, List<int[]>>();
        for (Map.Entry<Integer, List<int[]>> entry : original.entrySet()) {
            copy.put(entry.getKey(),
                    new ArrayList<int[]>(entry.getValue()));
        }
        return copy;
    }

    public void printSolution(List<Map<Integer, List<int[]>>> solution) {
        if (solution.size() == 1) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(toMatrix(solution.get(0))[i][j] + "  ");
                }
                System.out.println();
            }
        } else if (solutions.size() > 1) {
            System.out.println("This is not a Sudoku, because there are more than one solution!");
        } else System.out.println("This Sudoku has no solution.");
    }
}
