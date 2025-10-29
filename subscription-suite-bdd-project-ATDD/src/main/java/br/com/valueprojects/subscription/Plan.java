package br.com.valueprojects.subscription;

/**
 * Representa o plano de assinatura do estudante.
 * Contém a regra de negócio para determinar se o aluno deve estar no plano Premium ou Básico.
 */
public class Plan {
    
    public static final Plan BASIC = new Plan("BASIC");
    public static final Plan PREMIUM = new Plan("PREMIUM");
    
    private final String type;
    private static final int MINIMUM_COURSES_FOR_PREMIUM = 12;
    
    private Plan(String type) {
        this.type = type;
    }
    
    /**
     * Determina qual plano o estudante deve ter baseado na quantidade de cursos concluídos.
     * Regra: Premium quando completedCourses > 12, caso contrário Basic.
     * 
     * @param completedCourses quantidade de cursos concluídos pelo estudante
     * @return Plan.PREMIUM se completedCourses > 12, caso contrário Plan.BASIC
     */
    public static Plan determinePlan(int completedCourses) {
        if (completedCourses > MINIMUM_COURSES_FOR_PREMIUM) {
            return PREMIUM;
        }
        return BASIC;
    }
    
    /**
     * Verifica se o plano atual deve ser atualizado baseado na quantidade de cursos concluídos.
     * 
     * @param completedCourses quantidade de cursos concluídos pelo estudante
     * @return true se o plano deve ser alterado, false caso contrário
     */
    public boolean shouldUpdate(int completedCourses) {
        Plan expectedPlan = determinePlan(completedCourses);
        return !this.equals(expectedPlan);
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isBasic() {
        return this == BASIC;
    }
    
    public boolean isPremium() {
        return this == PREMIUM;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Plan plan = (Plan) obj;
        return type != null ? type.equals(plan.type) : plan.type == null;
    }
    
    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return type;
    }
}
