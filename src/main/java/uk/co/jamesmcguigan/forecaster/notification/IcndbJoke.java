package uk.co.jamesmcguigan.forecaster.notification;

import java.util.Arrays;

public class IcndbJoke {
  private String type;
  private Joke value;

  public String getJoke() {
    return this.value.getJoke();
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Joke getValue() {
    return this.value;
  }

  public void setValue(Joke value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "IcndbJoke [type=" + this.type + ", value=" + this.value + "]";
  }

  public static class Joke {
    private int id;
    private String joke;
    private String[] categories;

    public int getId() {
      return this.id;
    }

    public String getJoke() {
      return this.joke;
    }

    public String[] getCategories() {
      return this.categories;
    }

    @Override
    public String toString() {
      return "Joke [id=" + this.id + ", joke=" + this.joke + ", categories="
          + Arrays.toString(this.categories) + "]";
    }

  }
}