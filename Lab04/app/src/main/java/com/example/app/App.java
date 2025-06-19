package com.example.app;

import com.example.annotations.LogRuntime;
import com.example.generated.GeneratedServiceFile;

import java.lang.reflect.Method;

public class App {
    public static void main(String[] args) {
        System.out.println("--- 1. COMPILE-TIME ANNOTATION DEMO ---");
        GeneratedServiceFile.sayHello();
        System.out.println();


        System.out.println("--- 2. RUNTIME ANNOTATION DEMO ---");
        MyService service = new MyService();
        processRuntimeAnnotations(service);
    }

    public static void processRuntimeAnnotations(Object object) {
        System.out.println("Checking methods in class: " + object.getClass().getSimpleName());
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(LogRuntime.class)) {
                System.out.println("LOG: Method '" + method.getName() + "' is annotated. Calling it now.");
                try {
                    method.invoke(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("INFO: Method '" + method.getName() + "' is not annotated.");
            }
        }
    }
}