package expressivo;

/**
 * Represents a variable expression
 * 
 * A valid variable satisfies the regular expression [A-Za-z]+
 * 
 * @author jdouma
 *
 */
public class VariableExpression implements Expression {
    private final String variable;

    /**
     * A variable must be of the form [A-Za-z]+
     * 
     * @param variable input variable
     */
    public VariableExpression(String variable) {
        this.variable = variable;
        // TODO check that variable matches [A-Za-z]+, throw IllegalArgumentException otherwise
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((variable == null) ? 0 : variable.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VariableExpression other = (VariableExpression) obj;
        if (variable == null) {
            if (other.variable != null)
                return false;
        } else if (!variable.equals(other.variable))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public Expression differentiateWithRespectTo(String var) {
        if (variable.equals(var)) {
            return new NumberExpression(1);
        }
        
        return new NumberExpression(0);
    }
}
