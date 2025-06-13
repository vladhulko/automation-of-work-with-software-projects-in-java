package com.example.codegenerator;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CodeGeneratorPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getTasks().register("generateGreetingClass", GenerateGreetingClassTask.class, task -> {
            task.getOutputDir().set(project.file(project.getBuildDir() + "/generated-src"));
            task.getPackageName().set("com.example.generated");
            task.getClassName().set("GeneratedGreeting");
        });
    }
}