package com.demo.sudoku.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.min;

public class Permutations {

    public static Map<Integer, List<int[]>> indexedPermutations(int[][] matrix) {
        Map<Integer, List<int[]>> indexedPermutations = new HashMap<>();
        List<int[]> P = allPermutations();
        for (int i = 1; i <= 9; i++) {
            List<int[]> temp = new ArrayList<>();
            indexedPermutations.put(i, temp);
            for (int j = 0; j < P.size(); j++) {
                int[] rows = toRows(P.get(j));
                int[] columns = toColumns(P.get(j));
                for (int k = 0; k < 9; k++) {
                    if ((matrix[rows[k]][columns[k]] == i) || ((matrix[rows[k]][columns[k]] == 0) && !blockContains(matrix, k, i))) {
                        if (k == 8) {
                            temp.add(P.get(j));
                            indexedPermutations.replace(i, temp);
                        }
                    } else break;
                }
            }

        }
        return indexedPermutations;
    }

    public static boolean blockContains(int[][] matrix, int numberOfBlock, int searchedNumber) {
        for (int i = 3 * (numberOfBlock / 3); i < 3 * (numberOfBlock / 3) + 3; i++) {
            for (int j = 3 * (numberOfBlock % 3); j < 3 * (numberOfBlock % 3) + 3; j++) {
                if (matrix[i][j] == searchedNumber) return true;
            }
        }
        return false;
    }

    public static List<int[]> allPermutations() {
        List<int[]> permutations = new ArrayList<int[]>();
        int[] permutation = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        boolean findAnotherPermutation = true;
        while (findAnotherPermutation) {
            if (mistakeInBlock(permutation) == -1) {
                int[] temp = new int[9];
                for (int i = 0; i < 9; i++) {
                    temp[i] = permutation[i];
                }
                permutations.add(temp);
                permutation[8]++;
            } else {
                permutation[mistakeInBlock(permutation)]++;
                if (mistakeInBlock(permutation) < 8 && mistakeInBlock(permutation) > 0) {
                    for (int i = mistakeInBlock(permutation) + 1; i < 9; i++) {
                        permutation[i] = 1;
                    }
                }
            }
            while (permutationOutOfBounds(permutation)) {
                for (int i = 0; i < 9; i++) {
                    if (permutation[i] > 9) {
                        if (i == 0) {
                            permutation[i] = 9;
                            findAnotherPermutation = false;
                            break;
                        } else {
                            permutation[i] = 1;
                            permutation[i - 1]++;
                        }
                    }
                }
            }
        }
        return permutations;
    }

    public static int mistakeInBlock(int[] permutation) {
        int[] k = toRows(permutation);
        int[] l = toColumns(permutation);
        if (mistakeInVector(k) == -1 && mistakeInVector(l) == -1) return -1;
        else if (mistakeInVector(k) == -1) return mistakeInVector(l);
        else if (mistakeInVector(l) == -1) return mistakeInVector(k);
        else return min(mistakeInVector(k), mistakeInVector(l));

    }

    public static int mistakeInVector(int[] vector) {
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < i; j++) {
                if (vector[i] == vector[j]) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int[] toRows(int[] permutation) {
        int[] k = new int[9];
        for (int i = 0; i < 9; i++) {
            if (permutation[i] == 1 || permutation[i] == 2 || permutation[i] == 3) {
                k[i] = (i / 3) * 3;
            } else if (permutation[i] == 4 || permutation[i] == 5 || permutation[i] == 6) {
                k[i] = 1 + (i / 3) * 3;
            } else k[i] = 2 + (i / 3) * 3;
        }
        return k;
    }

    public static int[] toColumns(int[] permutation) {
        int[] l = new int[9];
        for (int i = 0; i < 9; i++) {
            if (permutation[i] == 1 || permutation[i] == 4 || permutation[i] == 7) {
                l[i] = (i % 3) * 3;
            } else if (permutation[i] == 2 || permutation[i] == 5 || permutation[i] == 8) {
                l[i] = 1 + (i % 3) * 3;
            } else l[i] = 2 + (i % 3) * 3;
        }
        return l;
    }

    public static boolean permutationOutOfBounds(int[] permutation) {
        for (int i :
                permutation) {
            if (i > 9) return true;
        }
        return false;
    }
}
