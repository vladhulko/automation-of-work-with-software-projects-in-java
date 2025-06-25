package com.example.testing;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Тести для класу Calculator")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @Tag("fast")
    @DisplayName("Перевірка коректності додавання двох чисел")
    void testAddition() {
        assertEquals(5, calculator.add(2, 3), "2 + 3 має дорівнювати 5");
        assertEquals(-1, calculator.add(-2, 1), "-2 + 1 має дорівнювати -1");
    }

    @ParameterizedTest(name = "{0} / {1} = {2}")
    @CsvSource({
            "10, 2, 5.0",
            "15, 3, 5.0",
            "5, 2, 2.5",
            "-10, 4, -2.5"
    })
    @Tag("fast")
    @DisplayName("Перевірка ділення з різними параметрами")
    void testDivisionWithParameters(double dividend, double divisor, double expectedResult) {
        assertEquals(expectedResult, calculator.divide(dividend, divisor), "Ділення має бути коректним");
    }
    @Test
    @Tag("exceptions")
    @DisplayName("Перевірка винятку при діленні на нуль")
    void testDivisionByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(1, 0);
        });

        assertEquals("Divisor cannot be zero", exception.getMessage());
    }

    @Test
    @Tag("slow")
    @DisplayName("Тест, що виконується тільки на сервері розробки")
    void testOnDevEnvironment() {
        assumeTrue("DEV".equals(System.getProperty("ENV")),
                "Тест ігнорується, бо це не DEV-середовище");

        assertEquals(100, calculator.add(50, 50));
    }
}