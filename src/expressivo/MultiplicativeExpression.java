package expressivo;

import java.util.Map;

/**
 * Represents the product of two Expression objects
 * 
 * @author jdouma
 *
 */
public class MultiplicativeExpression implements Expression {
    private final Expression leftExpression;
    private final Expression rightExpression;

    public MultiplicativeExpression(Expression leftExpression, Expression rightExpression) {
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
        MultiplicativeExpression other = (MultiplicativeExpression) obj;
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
        return leftExpression.toString() + " * " + rightExpression.toString();
    }

    @Override
    public Expression differentiateWithRespectTo(String var) {
        return new AdditiveExpression(
                new MultiplicativeExpression(leftExpression, rightExpression.differentiateWithRespectTo(var)),
                new MultiplicativeExpression(leftExpression.differentiateWithRespectTo(var), rightExpression));
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        Expression simplifiedLeftExpression = leftExpression.simplify(environment);
        Expression simplifiedRightExpression = rightExpression.simplify(environment);

        if (simplifiedLeftExpression instanceof NumberExpression
                && simplifiedRightExpression instanceof NumberExpression) {
            return new NumberExpression(Double.parseDouble(simplifiedLeftExpression.toString())
                    * Double.parseDouble(simplifiedRightExpression.toString()));
        }
        
        return new MultiplicativeExpression(simplifiedLeftExpression, simplifiedRightExpression);
    }
}
