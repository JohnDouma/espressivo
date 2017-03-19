package expressivo;

import java.io.File;
import java.io.IOException;

import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

/**
 * An immutable data type representing a polynomial expression of: + and *
 * nonnegative integers and floating-point numbers variables (case-sensitive
 * nonempty strings of letters)
 * 
 * <p>
 * PS1 instructions: this is a required ADT interface. You MUST NOT change its
 * name or package or the names or type signatures of existing methods. You may,
 * however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Datatype definition
    // Expression = Number + Variable + Plus(a:Expression, b:Expression) +
    // Times(a:Expression, b:Expression)

    enum ExpressionGrammar {
        ROOT, EXPR, SUM, PRODUCT, PRIMITIVE, NUMBER, VARIABLE, WHITESPACE
    };

    /**
     * Parse an expression.
     * 
     * @param input
     *            expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException
     *             if the expression is invalid
     */
    public static Expression parse(String input) {
        // throw new RuntimeException("unimplemented");
        try {
            Parser<ExpressionGrammar> parser = GrammarCompiler.compile(new File("src/expressivo/Expression.g"),
                    ExpressionGrammar.ROOT);
            ParseTree<ExpressionGrammar> tree = parser.parse(input);

            return buildAST(tree);
        } catch (

        IOException e) {
            e.printStackTrace();
        } catch (UnableToParseException ue) {
            ue.printStackTrace();
        }

        return null;
    }

    /**
     * @return a parsable representation of this expression, such that for all
     *         e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    public String toString();

    /**
     * @param thatObject
     *            any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of
     *         structural equality, such that for all e1,e2:Expression,
     *         e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();

    static Expression buildAST(ParseTree<ExpressionGrammar> tree) {
        switch (tree.getName()) {
        case ROOT:
            return buildAST(tree.childrenByName(ExpressionGrammar.EXPR).get(0));
        case EXPR:
            if (!tree.childrenByName(ExpressionGrammar.PRIMITIVE).isEmpty()) {
                return buildAST(tree.childrenByName(ExpressionGrammar.PRIMITIVE).get(0));
            } else if (!tree.childrenByName(ExpressionGrammar.PRODUCT).isEmpty()) {
                return buildAST(tree.childrenByName(ExpressionGrammar.PRODUCT).get(0));
            } else {
                return buildAST(tree.childrenByName(ExpressionGrammar.SUM).get(0));
            }
        case SUM:
            return new AdditiveExpression(buildAST(tree.childrenByName(ExpressionGrammar.PRIMITIVE).get(0)),
                    buildAST(tree.childrenByName(ExpressionGrammar.EXPR).get(0)));
        case PRODUCT:
            return new MultiplicativeExpression(buildAST(tree.childrenByName(ExpressionGrammar.PRIMITIVE).get(0)),
                    buildAST(tree.childrenByName(ExpressionGrammar.EXPR).get(0)));
        case PRIMITIVE:
            if (!tree.childrenByName(ExpressionGrammar.NUMBER).isEmpty()) {
                return buildAST(tree.childrenByName(ExpressionGrammar.NUMBER).get(0));
            } else if (!tree.childrenByName(ExpressionGrammar.VARIABLE).isEmpty()) {
                return buildAST(tree.childrenByName(ExpressionGrammar.VARIABLE).get(0));
            } else {
                return buildAST(tree.childrenByName(ExpressionGrammar.EXPR).get(0));
            }
        case NUMBER:
            return new NumberExpression(Double.parseDouble(tree.getContents()));
        case VARIABLE:
            return new VariableExpression(tree.getContents());
        case WHITESPACE:
        default:
            throw new RuntimeException("This should not happen");

        }
    }

    /*
     * Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course
     * staff.
     */
}
