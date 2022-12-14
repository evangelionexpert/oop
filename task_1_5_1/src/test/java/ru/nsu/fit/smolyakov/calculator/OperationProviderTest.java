package ru.nsu.fit.smolyakov.calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.smolyakov.caclulator.Calculator;
import ru.nsu.fit.smolyakov.caclulator.exceptions.UnknownOperationException;
import ru.nsu.fit.smolyakov.caclulator.operation.Operation;
import ru.nsu.fit.smolyakov.caclulator.operationsprovider.DoubleOperationsProvider;

class OperationProviderTest {
    private DoubleOperationsProvider provider;
    private Calculator<Double> calc;

    @BeforeEach
    void init() {
        provider = new DoubleOperationsProvider();
        calc = new Calculator<>(provider);
    }

    double compute(String what) {
        return calc.compute(new Scanner(what));
    }

    @Test
    void addOperationTestAllOk() {
        assertThatThrownBy(() -> compute("anime"))
            .isInstanceOf(UnknownOperationException.class);

        provider.insertOperation("anime", new Operation<Double>(() -> 666.666));
        assertThat(compute("anime")).isEqualTo(666.666);
    }

    @Test
    void addOperationTestNameExists() {
        assertThat(provider.insertOperation("-", new Operation<Double>(() -> 0d))).isFalse();
    }

    @Test
    void addOperationTestNameIsDouble() {
        assertThatThrownBy(() -> {
            provider.insertOperation("0.67", new Operation<Double>(() -> 0.66));
        })
            .isInstanceOf(IllegalArgumentException.class);
    }
}
