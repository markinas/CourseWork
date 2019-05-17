package com.company.course_work;


public class Main {
    public static void main(String[] args) {

        System.out.println("Последовательное апроксимирующие решение:");
        Dyffur dyffur = new Dyffur();

        long beginTimeSerial = System.nanoTime();
        double[][] approximateResult = new SerialSolution(dyffur).solve();
        long endTimeSerial = System.nanoTime();
        long serialTime = endTimeSerial - beginTimeSerial;
        printMatrix(approximateResult, dyffur);
        System.out.println("Время последовательного решения: " + serialTime);
        System.out.println();


        System.out.println("Параллельное решение");
        long beginTimeParallel = System.nanoTime();
        double[][] parallelSolution = new ParallelSolution(dyffur).solve();
        long endTimeParallel = System.nanoTime();
        long parallelTime = endTimeParallel - beginTimeParallel;
        printMatrix(parallelSolution, dyffur);
        System.out.println("Время параллельного решения: " + parallelTime + " ns");
        System.out.println("Точное решение:");
        printErrors(approximateResult, calculateExactResult(dyffur), dyffur);
    }

    private static double[][] calculateExactResult(Dyffur dyffur) {
        double[][] exactMatrix = new double[dyffur.getTpointsNumber()][dyffur.getHpointsNumber()];
        double t = dyffur.getT0();
        for (int i = 0; i < dyffur.getTpointsNumber(); i++) {
            double x = dyffur.getX0();
            for (int j = 0; j < dyffur.getHpointsNumber(); j++) {
                exactMatrix[i][j] = dyffur.calculatePreciseSolve(x, t);
                x += dyffur.getH();
            }
            t += dyffur.getTau();
        }

        System.out.println();

        System.out.print("{");
        for (int i = 0; i < dyffur.getTpointsNumber(); ++i) {
            System.out.print("{");
            for (int j = 0; j < dyffur.getHpointsNumber(); ++j) {
                System.out.print(exactMatrix[i][j]);
                if (j != dyffur.getHpointsNumber()-1) System.out.print(",");
            }
            if (i != dyffur.getTpointsNumber()) {System.out.print("},");}
            else System.out.print("}");
            System.out.println();
        }
        System.out.println("}");

        return exactMatrix;
    }




    private static void printMatrix(double[][] matrix, Dyffur dyffur) {
        System.out.print("{");
        for (int i = 0; i < dyffur.getTpointsNumber(); ++i) {
            System.out.print("{");
            for (int j = 0; j < dyffur.getHpointsNumber(); ++j) {
                System.out.print(matrix[i][j]);
                if (j != dyffur.getHpointsNumber()-1) System.out.print(",");
            }
            if (i != dyffur.getTpointsNumber()) {System.out.print("},");}
            else System.out.print("}");
            System.out.println();
            }
        System.out.println("}");

        }



    private static void printErrors(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        System.out.println("Средняя абсолютная погрешность: " + calculateError(dyffur,approximateResult,exactMatrix,1));
        System.out.println("Максимальная абсолютная погрешность: " + calculateError(dyffur,approximateResult,exactMatrix,2));
        System.out.println("Средняя относительная погрешность: " + calculateError(dyffur,approximateResult,exactMatrix,3));
        System.out.println("Максимальная относительная погрешность: " + calculateError(dyffur,approximateResult,exactMatrix,4));
    }



    private static double calculateError(Dyffur Dyffur, double[][] serialSolve, double[][] trueMatrix, int check) {
        double[][] errorMatrix = new double[Dyffur.getTpointsNumber()][Dyffur.getHpointsNumber()];
        double error = 0.0;

        if (check == 1 || check == 2) {
            for (int i = 0; i < Dyffur.getTpointsNumber(); i++) {
                for (int j = 0; j < Dyffur.getHpointsNumber(); j++) {
                    errorMatrix[i][j] = Math.abs(serialSolve[i][j] - trueMatrix[i][j]);
                }
            }

            if (check == 1) {
                for (int i = 0; i < Dyffur.getTpointsNumber(); i++) {
                    for (int j = 0; j < Dyffur.getHpointsNumber(); j++) {
                        error += errorMatrix[i][j];
                    }
                }

                error = error / (Dyffur.getTpointsNumber() * Dyffur.getHpointsNumber());
            } else if (check == 2) {
                error = errorMatrix[0][0];
                for (int i = 0; i < Dyffur.getTpointsNumber(); i++) {
                    for (int j = 0; j < Dyffur.getHpointsNumber(); j++) {
                        if (error < errorMatrix[i][j]) {
                            error = errorMatrix[i][j];
                        }
                    }
                }
            }
        } else if (check == 3 || check == 4) {
            for (int i = 0; i < Dyffur.getTpointsNumber(); i++) {
                for (int j = 0; j < Dyffur.getHpointsNumber(); j++) {
                    errorMatrix[i][j] = 100 * (Math.abs(serialSolve[i][j] - trueMatrix[i][j])) / trueMatrix[i][j];
                }
            }

            if (check == 3) {
                for (int i = 0; i < Dyffur.getTpointsNumber(); i++) {
                    for (int j = 0; j < Dyffur.getHpointsNumber(); j++) {
                        error += errorMatrix[i][j];
                    }
                }

                error = error / (Dyffur.getTpointsNumber() * Dyffur.getHpointsNumber());
            } else if (check==4) {
                error = errorMatrix[0][0];
                for (int i = 0; i < Dyffur.getTpointsNumber(); i++) {
                    for (int j = 0; j < Dyffur.getHpointsNumber(); j++) {
                        if (error < errorMatrix[i][j]) {
                            error = errorMatrix[i][j];
                        }
                    }
                }
            }
        }
        return error;
    }



}
