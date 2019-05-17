package com.company.course_work;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelSolution {

    private int tPointsNumber;
    private int hPointsNumber;
    private double x0;
    private double h;
    private double t0;
    private double tau;
    private Dyffur dyffur;

    public ParallelSolution(Dyffur dyffur) {
        this.dyffur = dyffur;
        this.tPointsNumber = dyffur.getTpointsNumber();
        this.hPointsNumber = dyffur.getHpointsNumber();
        this.x0 = dyffur.getX0();
        this.h = dyffur.getH();
        this.t0 = dyffur.getT0();
        this.tau = dyffur.getTau();
    }

    public double[][] solve() {
        double t = t0 + tau;
        double x = x0;
        double[][] w = new double[tPointsNumber][hPointsNumber];
        for (int j = 0; j < hPointsNumber; j++, x += h) {
            w[0][j] = dyffur.calculateBottomBorder(x);
        }

        for (int i = 1; i < tPointsNumber; ++i, t += tau) {
            w[i][0] = dyffur.calculateLeftBorder(t);
            int k=i;

            IntStream.range(1, dyffur.getHpointsNumber() - 1).parallel().forEach(j -> {

                w[k][j] = dyffur.calculateApproximateSolve(w[k - 1][j - 1], w[k - 1][j], w[k - 1][j + 1]);

            });

            w[i][hPointsNumber - 1] = dyffur.calculateRightBorder(t);
        }
        return w;
    }

}
