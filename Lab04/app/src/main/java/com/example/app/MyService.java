package com.example.app;

import com.example.annotations.GenerateFile;
import com.example.annotations.LogRuntime;

@GenerateFile(fileName = "GeneratedServiceFile")
public class MyService {

    @LogRuntime
    public void performAction() {
        System.out.println("MyService is performing its action.");
    }

    public void doSomethingElse() {
        System.out.println("MyService is doing something else.");
    }
}