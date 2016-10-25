package com.fortegrp.at.extension

import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

public class MethodExecutionRule implements MethodRule {

    public static def failedTests = []

    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate()
                } catch (Throwable t) {
                    failedTests.add(frameworkMethod.getName())
                    throw t
                }
            }
        }
    }
}