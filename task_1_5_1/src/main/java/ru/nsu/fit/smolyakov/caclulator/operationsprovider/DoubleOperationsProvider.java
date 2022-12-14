package ru.nsu.fit.smolyakov.caclulator.operationsprovider;

import java.util.Map;
import java.util.Objects;

import ru.nsu.fit.smolyakov.caclulator.complex.Complex;
import ru.nsu.fit.smolyakov.caclulator.operation.Operation;

/**
 * An implementation of {@link OperationsProvider} with {@link Double} as a
 * type of operand and a set of basic arithmetic operations, such as
 * "+", "-", "*", "/", "^" with obvious meanings, some trigonometrical
 * operations - "sin" and "cos" - and "to-deg" and "to-rad", providing conversion
 * from radians to degree and backwards.
 *
 * <p>This provider also contains {@link Math#PI} and {@link Math#E} constants,
 * also represented as suppliers.
 */
public class DoubleOperationsProvider extends AbstractOperationsProvider<Double> {
    private static final Map<String, Operation<Double>> operationsMap =
        Map.ofEntries(
            Map.entry("+", new Operation<>((a, b) -> a + b)),
            Map.entry("-", new Operation<>((a, b) -> a - b)),
            Map.entry("*", new Operation<>((a, b) -> a * b)),
            Map.entry("/", new Operation<>((a, b) -> a / b)),
            Map.entry("sin", new Operation<>(Math::sin)),
            Map.entry("cos", new Operation<>(Math::cos)),
            Map.entry("to-deg", new Operation<>(Math::toDegrees)),
            Map.entry("to-rad", new Operation<>(Math::toRadians)),
            Map.entry("PI", new Operation<>(() -> Math.PI)),
            Map.entry("E", new Operation<>(() -> Math.E)),
            Map.entry("^", new Operation<>(Math::pow)),
            Map.entry("ln", new Operation<>(Math::log)),
            Map.entry("lg", new Operation<>(Math::log10)),
            Map.entry("sqrt", new Operation<>(Math::sqrt))
        );

    /**
     * Constructs an instance of {@code ComplexOperationProvider}.
     */
    public DoubleOperationsProvider() {
        super(operationsMap);
    }

    /**
     * Converts {@code operandString} into {@link Double}.
     *
     * <p>Complex operands pattern is described in {@link Double#valueOf(String)}.
     *
     * @param operandString a string representation of operand
     * @return a {@link Double} number corresponding to {@code operandString}
     * @throws NumberFormatException if {@code operandString} doesn't
     *                               match operand pattern
     */
    @Override
    protected Double parseAsOperand(String operandString) throws NumberFormatException {
        return Double.valueOf(Objects.requireNonNull(operandString));
    }

    /**
     * Returns an adapter to use this {@code OperationsProvider} as {@link Complex} one.
     * Thus, operations are limited to use only complex numbers without an imaginary part.
     *
     * <p>Double operands are also parsed as complex without imaginary part.
     *
     * @return an adapter for this {@code OperationsProvider}
     */
    public OperationsProvider<Complex> adapterToComplex() {
        return new DoubleToComplexOperationsAdapter(this);
    }
}
