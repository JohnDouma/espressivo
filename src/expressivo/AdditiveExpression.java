package expressivo;

/**
 * Represents the sum of two Expression objects
 * 
 * @author jdouma
 *
 */
public class AdditiveExpression implements Expression {
    private final Expression leftExpression;
    private final Expression rightExpression;

    public AdditiveExpression(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((leftExpression == null) ? 0 : leftExpression.hashCode());
        result = prime * result + ((rightExpression == null) ? 0 : rightExpression.hashCode());
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
        AdditiveExpression other = (AdditiveExpression) obj;
        if (leftExpression == null) {
            if (other.leftExpression != null)
                return false;
        } else if (!leftExpression.equals(other.leftExpression))
            return false;
        if (rightExpression == null) {
            if (other.rightExpression != null)
                return false;
        } else if (!rightExpression.equals(other.rightExpression))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + leftExpression.toString() + " + " + rightExpression.toString() + ")";
    }
}
