package expressivo;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Represents a numerical expression
 * 
 * @author jdouma
 *
 */
public class NumberExpression implements Expression {
    
    private final double number;
    
    public NumberExpression(double number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(number);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        NumberExpression other = (NumberExpression) obj;
        if (Double.doubleToLongBits(number) != Double.doubleToLongBits(other.number))
            return false;
        return true;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(100);
        return df.format(number);
    }

    @Override
    public Expression differentiateWithRespectTo(String var) {
        return new NumberExpression(0);
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        return this;
    }
   
}
