package com.fortegrp.at.common.annotation

import com.fortegrp.at.extension.IgnoreFailureExtension
import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Created by yhraichonak on 2/12/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( [ElementType.METHOD,ElementType.TYPE])
@ExtensionAnnotation(IgnoreFailureExtension.class)
public @interface IgnoreFailure {
    String value()
}

