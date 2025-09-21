package br.com.valueprojects.subscription;

/** Serviço de domínio para progresso acadêmico e recompensas gamificadas. */
public class ProgressService {
    private static final double AVG_BONUS = 9.0;
    private static final double AVG_PASS  = 7.0;
    private static final int CREDITS_FOR_BONUS = 5;
    private static final int CREDITS_FOR_PASS  = 3;
    private static final int PREMIUM_MIN_EXCLUSIVE = 12; // promove somente quando > 12

    /** Conclui o curso, concede créditos pela média e verifica promoção. */
    public void finishCourse(Student student, double average) {
        student.completeOneCourse();
        int gained = creditsForAverage(average);
        if (gained > 0) student.addCredits(gained);
        promoteIfEligible(student);
    }

    /** Converte moedas em créditos pela política 2:1. */
    public void convertCoinsToCredits(Student student, int coins) {
        if (coins <= 0 || student.getCoins() < coins) return;
        int creditsToAdd = coins / 2; // divisão inteira -> 2:1
        if (creditsToAdd <= 0) return; // evita no-op
        student.setCoins(student.getCoins() - coins);
        student.addCredits(creditsToAdd);
    }

    /** Recompensa mensal de engajamento (+1 crédito para o top contributor). */
    public void grantMonthlyTopContributorCredit(Student student) {
        student.addCredits(1);
    }

    /** Função pura pequena, explícita e fácil de testar isoladamente. */
    int creditsForAverage(double average) {
        if (average >= AVG_BONUS) return CREDITS_FOR_BONUS;
        if (average >= AVG_PASS)  return CREDITS_FOR_PASS;
        return 0;
    }

    /** Promove para PREMIUM se passou do limiar exclusivo. */
    private void promoteIfEligible(Student student) {
        if (student.getPlan() == Plan.BASIC &&
            student.getCompletedCourses() > PREMIUM_MIN_EXCLUSIVE) {
            student.setPlan(Plan.PREMIUM);
        }
    }
}
