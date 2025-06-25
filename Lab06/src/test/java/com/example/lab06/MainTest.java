package com.example.lab06;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void testGetGreeting() {
        assertEquals("Hello from Lab06!", Main.getGreeting());
    }
}