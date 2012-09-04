package ru.lexx.acsystem.backend.task.stat;

public class UserStatistic {
    private CheckResult[] resolves;

    public int getResolvesCount() {
        return resolvesCount;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public double getAvgPoints() {
        return avgPoints;
    }

    private int resolvesCount;
    private int maxPoints;
    private int minPoints = 6;
    private double avgPoints;

    public UserStatistic(CheckResult[] data) {
        resolves = data;
        resolvesCount = resolves.length;
        int psum = 0;
        for (CheckResult res : resolves) {
            int p = res.getMark();
            if (p > maxPoints)
                maxPoints = p;
            if (p < minPoints)
                minPoints = p;
            psum += p;
        }
        avgPoints = (double) psum / resolvesCount;
        if (minPoints == 6)
            minPoints = 0;
        if (new Double(avgPoints).isNaN())
            avgPoints = 0.0d;
    }

    public CheckResult[] getResolves() {
        return resolves;
    }
}
