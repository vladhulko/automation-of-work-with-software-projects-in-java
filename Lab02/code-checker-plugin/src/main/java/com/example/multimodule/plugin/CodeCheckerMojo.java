package com.example.multimodule.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Mojo to check for forbidden words in Java source files.
 */
@Mojo(name = "check-code", defaultPhase = LifecyclePhase.VALIDATE)
public class CodeCheckerMojo extends AbstractMojo {

    private static final String FORBIDDEN_WORD = "FIXME";

    /**
     * The Maven project this plugin is bound to.
     */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    /**
     * Flag to skip the execution of the code checker.
     */
    @Parameter(property = "code-checker.skip", defaultValue = "false")
    private boolean skip;

    @Override
    public void execute() throws MojoFailureException {
        if (skip) {
            getLog().info("Skipping code checker execution because 'code-checker.skip' is true.");
            return;
        }

        getLog().info("🚀 Starting Code Checker Plugin for " + project.getArtifactId());
        boolean failure = false;

        List<String> sourceRoots = project.getCompileSourceRoots();

        for (String sourceRoot : sourceRoots) {
            Path sourcePath = Paths.get(sourceRoot);

            if (Files.exists(sourcePath)) {
                try (Stream<Path> paths = Files.walk(sourcePath)) {
                    if (paths.filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".java"))
                            .anyMatch(this::fileContainsForbiddenWord)) {
                        failure = true;
                    }
                } catch (IOException e) {
                    getLog().error("Failed to analyze files in " + sourcePath, e);
                }
            } else {
                getLog().info("Source directory does not exist, skipping: " + sourceRoot);
            }
        }

        if (failure) {
            throw new MojoFailureException("Forbidden word '" + FORBIDDEN_WORD + "' found. Build failed.");
        }

        getLog().info("✅ Code check passed for " + project.getArtifactId());
    }

    /**
     * Additional method to check if a file contains the forbidden word.
     * @param file
     * @return
     */
    private boolean fileContainsForbiddenWord(Path file) {
        try (Stream<String> lines = Files.lines(file)) {
            if (lines.anyMatch(line -> line.contains(FORBIDDEN_WORD))) {
                getLog().error("Found '" + FORBIDDEN_WORD + "' in file: " + file);
                return true;
            }
            return false;
        } catch (IOException e) {
            getLog().warn("Cannot read file: " + file);
            return false;
        }
    }
}