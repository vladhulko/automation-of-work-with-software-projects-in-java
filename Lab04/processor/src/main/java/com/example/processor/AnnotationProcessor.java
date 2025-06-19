package com.example.processor;

import com.google.auto.service.AutoService;
import com.example.annotations.GenerateFile;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("com.example.annotations.GenerateFile")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateFile.class)) {
            GenerateFile annotation = element.getAnnotation(GenerateFile.class);
            String fileName = annotation.fileName();
            String packageName = "com.example.generated";

            try {
                createGeneratedFile(packageName, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void createGeneratedFile(String packageName, String className) throws IOException {
        JavaFileObject file = processingEnv.getFiler().createSourceFile(packageName + "." + className);
        try (PrintWriter out = new PrintWriter(file.openWriter())) {
            out.println("package " + packageName + ";");
            out.println();
            out.println("public class " + className + " {");
            out.println("    public static void sayHello() {");
            out.println("        System.out.println(\"Hello from generated class '" + className + "'!\");");
            out.println("    }");
            out.println("}");
        }
    }
}