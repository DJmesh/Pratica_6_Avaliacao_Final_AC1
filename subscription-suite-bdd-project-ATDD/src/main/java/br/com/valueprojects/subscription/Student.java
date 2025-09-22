package br.com.valueprojects.subscription;

public class Student {
    private String name;
    private Plan plan = Plan.BASIC;
    private int completedCourses;
    private int credits;
    private int coins;

    public Student(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public int getCompletedCourses() { return completedCourses; }
    public void setCompletedCourses(int completedCourses) { this.completedCourses = completedCourses; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }

    public void addCredits(int delta) { this.credits += delta; }
    public void consumeCredit() { if (this.credits > 0) this.credits -= 1; }

    public void addCompletedCourses(int count) { this.completedCourses += count; }
}
