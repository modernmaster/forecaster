package uk.co.jamesmcguigan.forecaster.stock.trends.point;

public class Point implements Comparable<Point> {

  private double x;
  private double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public int compareTo(Point o) {
    return Double.compare(this.getY(), o.getY());
  }
}
