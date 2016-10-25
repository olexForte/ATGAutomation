package com.fortegrp.at.extension

import com.fortegrp.at.common.annotation.IgnoreFailure
import com.fortegrp.at.env.CommonEnvironment
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

/**
 * Created by yhraichonak
 **/
class IgnoreFailureExtension extends AbstractAnnotationDrivenExtension<IgnoreFailure>{

    public void visitFeatureAnnotation(IgnoreFailure annotation, FeatureInfo feature) {
        setFeatureIgnored(feature, annotation.value())
    }

    public void visitSpecAnnotation( IgnoreFailure annotation, SpecInfo spec) {
        spec.features.each {
            setFeatureIgnored(it, annotation.value())
        }
    }

    def setFeatureIgnored(FeatureInfo feature, String annotationValue){
        if (!feature.isSkipped() && !feature.isExcluded()&& CommonEnvironment.ignoreKnownFailures()) {
            feature.setName(feature.getName() + " [IGNORED: '" + annotationValue + "']")
            feature.setSkipped(true)
        }
    }
}

