package com.fortegrp.at.extension

import com.fortegrp.at.env.CommonEnvironment
import groovy.util.logging.Slf4j
import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

@Slf4j
class RetryInterceptor implements IMethodInterceptor {

    Integer retryMax

    RetryInterceptor(int retryMax) {
        this.retryMax = retryMax
    }

    void intercept(IMethodInvocation invocation) throws Throwable {
        Integer attempts = 0
        while (attempts <= retryMax) {
            try {
                log.info("[Fork #${CommonEnvironment.forkNumber}] Test execution attempt #" + attempts + " of method '" + invocation.feature.name + "." + invocation.method.name + "'")
                if ((attempts > 0)) {
                }
                invocation.proceed()
                attempts = retryMax + 1
            } catch (Throwable t) {
                attempts++
                if (attempts > retryMax) {
                    throw t
                }
                invocation.target."cleanupTest"()
                invocation.target."setupTest"()
            }
        }
    }
}
