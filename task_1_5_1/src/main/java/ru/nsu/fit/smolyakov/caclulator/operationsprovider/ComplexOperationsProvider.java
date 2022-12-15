package ru.nsu.fit.smolyakov.caclulator.operationsprovider;

import ru.nsu.fit.smolyakov.caclulator.complex.Complex;
import ru.nsu.fit.smolyakov.caclulator.operation.Operation;

import java.util.Map;
import java.util.Objects;

public class ComplexOperationsProvider extends AbstractOperationsProvider<Complex> {
    private static final Map<String, Operation<Complex>> operationsMap =
            Map.of(
                    "+", new Operation<>(Complex::add),
                    "-", new Operation<>(Complex::subtract),
                    "*", new Operation<>(Complex::multiply),
                    "/", new Operation<>(Complex::divide),
                    "sin", new Operation<>(Complex::sin),
                    "cos", new Operation<>(Complex::cos)
            );

    /**
     * Constructs an instance of {@code ComplexOperationProvider} with a minimum set
     * of operations. Namely, "+", "-", "*", "/", "sin" and "cos" operations with
     * obvious meanings are available.
     */
    public ComplexOperationsProvider() {
        super(operationsMap);
    }

    /**
     * Converts {@code operandString} into {@link Complex}.
     *
     * <p>Complex operands pattern is described in {@link Complex#valueOf(String)}.
     *
     * @param  operandString a string representation of operand
     * @return a {@link Complex} number corresponding to {@code operandString}
     * @throws NumberFormatException if {@code operandString} doesn't
     *                               match operand pattern
     */
    @Override
    protected Complex operandValue(String operandString) throws NumberFormatException {
        return Complex.valueOf(Objects.requireNonNull(operandString));
    }
}
