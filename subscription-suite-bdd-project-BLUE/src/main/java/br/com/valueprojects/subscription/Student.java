package br.com.valueprojects.subscription;

import java.util.Objects;

/** Agregado que representa um estudante no sistema de assinaturas. */
public class Student {
    private final String name;
    private Plan plan = Plan.BASIC;
    private int credits;
    private int coins;
    private int completedCourses;

    public Student(String name) { this.name = Objects.requireNonNull(name); }

    public String getName() { return name; }
    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = Objects.requireNonNull(plan); }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }

    public int getCompletedCourses() { return completedCourses; }
    public void setCompletedCourses(int completedCourses) { this.completedCourses = completedCourses; }

    /** Soma N créditos (pode ser 0). */
    public void addCredits(int amount) { this.credits += amount; }

    /** Consome 1 crédito apenas se houver saldo (> 0). */
    public void consumeCredit() { if (credits > 0) credits--; }

    /** Soma 1 moeda. */
    public void addCoin() { this.coins++; }

    /** Registra a conclusão de um curso. */
    public void completeOneCourse() { this.completedCourses++; }
}
