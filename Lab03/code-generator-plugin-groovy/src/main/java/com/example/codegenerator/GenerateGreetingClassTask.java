package com.example.codegenerator;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class GenerateGreetingClassTask extends DefaultTask {

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @Input
    public abstract Property<String> getPackageName();

    @Input
    public abstract Property<String> getClassName();

    @TaskAction
    public void generate() {
        File outputDir = getOutputDir().get().getAsFile();
        String packageName = getPackageName().get();
        String className = getClassName().get();

        File packageDir = new File(outputDir, packageName.replace('.', File.separator));
        if (!packageDir.exists()) {
            packageDir.mkdirs();
        }

        File outputFile = new File(packageDir, className + ".java");

        String content = String.format(
                "package %s;\n\n" +
                        "public class %s {\n" +
                        "    public String getGreeting() {\n" +
                        "        return \"Hello from generated Java code!\";\n" +
                        "    }\n" +
                        "}\n",
                packageName, className
        );

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println(content);
            System.out.println("Generated Java class: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generating class: " + e.getMessage());
        }
    }
}