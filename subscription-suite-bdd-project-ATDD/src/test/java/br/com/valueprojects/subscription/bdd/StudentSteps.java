package br.com.valueprojects.subscription.bdd;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.en.Given;
import br.com.valueprojects.subscription.Plan;
import br.com.valueprojects.subscription.Student;

/** TODOS os Givens ficam aqui para evitar duplicação. */
public class StudentSteps {
    private final StepContext ctx;
    public StudentSteps(StepContext ctx) { this.ctx = ctx; } // injeção

    @Dado("um aluno BASIC chamado {string} com {int} cursos concluídos e {int} créditos")
    @Given("a BASIC student named {string} with {int} completed courses and {int} credits")
    public void basicStudentWithCredits(String name, int completed, int credits) {
        ctx.student = new Student(name);
        ctx.student.setPlan(Plan.BASIC);
        ctx.student.setCompletedCourses(completed);
        ctx.student.setCredits(credits);
    }

    @Dado("um aluno BASIC chamado {string} com {int} cursos concluídos, {int} créditos e {int} moedas")
    @Given("a BASIC student named {string} with {int} completed courses, {int} credits and {int} coins")
    public void basicStudentWithCreditsAndCoins(String name, int completed, int credits, int coins) {
        basicStudentWithCredits(name, completed, credits);
        ctx.student.setCoins(coins);
    }
}
