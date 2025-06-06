package com.example.multimodule.app;

import com.example.multimodule.logic.Greeter;
import com.example.multimodule.utils.StringUtils;

public class Main {
    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        System.out.println(greeter.greet("World"));

        //FIXME
        System.out.println("Broken piece of code that should be caught by the plugin.");
    }
}