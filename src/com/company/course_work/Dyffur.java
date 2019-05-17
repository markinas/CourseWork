package com.company.course_work;

public class Dyffur {

    private double x0 = 0;
    private double x1 = 1;
    private double h = 1.0 / 10;
    private double t0 = 0;
    private double t1 = 1;
    private double tau = 1.0 / 300;
    private double a = -1;
    private double C = 1;

    public double getX0() {
        return x0;
    }

    public double getH() {
        return h;
    }

    public double getT0() {
        return t0;
    }

    public double getTau() {
        return tau;
    }

    public int getTpointsNumber() {
        return (int) Math.ceil((t1 - t0) / tau) + 1;
    }

    public int getHpointsNumber() {
        return (int) Math.ceil((x1 - x0) / h) + 1;
    }

    public double calculatePreciseSolve(double x, double t) {
        return C*Math.exp(-a*t-x*Math.sqrt(-a));
    }

    public double calculateBottomBorder(double x) {
        double t = 0;
        return C*Math.exp(-a*t-x*Math.sqrt(-a));
    }

    public double calculateLeftBorder(double t) {
        double x = 0;
        return C*Math.exp(-a*t-x*Math.sqrt(-a));
    }

    public double calculateRightBorder(double t) {
        double x = 1;
        return C*Math.exp(-a*t-x*Math.sqrt(-a));
    }

    public double calculateApproximateSolve(double wiLeft, double wiCurrent, double wiRight) {
        double sigma = tau / (h * h);
        return (wiCurrent + (sigma/4)*(4*wiLeft-8*wiCurrent+4*wiRight+Math.pow(wiRight-wiLeft,2)+4*a*h*h*wiCurrent*wiCurrent));


    }
}
