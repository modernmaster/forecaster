package uk.co.jamesmcguigan.forecaster.chart;

public interface EquitiesChart {

    Chart get(String symbol);
    void update();

    void update(Chart chart);

    void put(String symbol);
}
