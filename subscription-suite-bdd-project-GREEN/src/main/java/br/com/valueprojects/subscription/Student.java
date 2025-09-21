package br.com.valueprojects.subscription;
public class Student {
  private final String name;
  private Plan plan = Plan.BASIC;
  private int credits;
  private int coins;
  private int completedCourses;
  public Student(String name) { this.name = name; }
  public String getName() { return name; }
  public Plan getPlan() { return plan; }
  public void setPlan(Plan plan) { this.plan = plan; }
  public int getCredits() { return credits; }
  public void setCredits(int credits) { this.credits = credits; }
  public int getCoins() { return coins; }
  public void setCoins(int coins) { this.coins = coins; }
  public int getCompletedCourses() { return completedCourses; }
  public void setCompletedCourses(int completedCourses) { this.completedCourses = completedCourses; }
  public void addCredits(int a) { this.credits += a; }
  public void consumeCredit() { if (credits > 0) credits--; }
  public void addCoin() { this.coins++; }
  public void completeOneCourse() { this.completedCourses++; }
}
