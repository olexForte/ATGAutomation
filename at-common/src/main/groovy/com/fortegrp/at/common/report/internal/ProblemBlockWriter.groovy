package com.fortegrp.at.common.report.internal

import com.fortegrp.at.env.CommonEnvironment
import groovy.xml.MarkupBuilder
import groovy.xml.XmlUtil
import org.apache.commons.lang3.exception.ExceptionUtils
import org.codehaus.groovy.runtime.StackTraceUtils
import org.spockframework.runtime.model.IterationInfo

class ProblemBlockWriter {

    DateStringFormatHelper stringFormatter

    void writeProblems(MarkupBuilder builder, FeatureRun run) {
        failures(run.failuresByIteration).each { Map problem ->
            if (problem.dataValues) {
                builder.ul {
                    li {
                        div problem.dataValues.toString()
                        writeProblemMsgs(builder, problem.messages, problem.stacktraces)
                    }
                }
            } else {
                writeProblemMsgs(builder, problem.messages, problem.stacktraces)
            }

            builder.div('class': 'problem-list') {
                div('Screenshot:')
                String filename = ""
                try {
                    filename=HtmlReportCreator.getScreenshots(run.getFeature().getParent().getReflection().getName(),
                            "(" + problem.name.replaceAll("\\[.*\\]","") + ").*" + CommonEnvironment.reportGlobalSuffix())
                    a('href': filename) {
                        builder.mkp.yield(new File(filename).getName())
                        br()
                    }
                } catch (Exception e) {
                    System.out.print("Unable to attach screenshot " + it + " to the reports:" + e.getMessage())

                }

            }
        }
    }

    def indexOfByReference(list, object) {
        def result = -1
        for (int i in 0..(list.size() - 1)) {
            if (list[i].is(object)) {
                result = i;
                break
            }
        }
        result
    }


    private void writeProblemMsgs(MarkupBuilder builder, List msgs, List stackTraces) {
        builder.ul {
            for (int i = 0; i < msgs.size(); i++) {

                b {
                    mkp.yieldUnescaped(
                            stringFormatter.formatToHtml(
                                    XmlUtil.escapeXml(msgs.get(i).toString())))
                }

                pre {
                    mkp.yieldUnescaped(
                            stringFormatter.formatToHtml(
                                    XmlUtil.escapeXml(stackTraces.get(i).toString())))
                }
            }

        }
    }

    private List<Map> failures(Map<IterationInfo, List<ExtendedErrorInfo>> failures) {
        failures.inject([]) { List<Map> acc, iteration, List<ExtendedErrorInfo> failureList ->
            def errorMessages = failureList.collect { "[STEP #"+(it.stepNumber+1)+"]:"+ it.exception.toString() }
            def stackTraces = failureList.collect {
                ExceptionUtils.getStackTrace(StackTraceUtils.sanitize(it.exception)).replace("\r\n", "")
            }
            if (errorMessages) {
                acc << [dataValues: iteration.dataValues, messages: errorMessages, stacktraces: stackTraces, name: iteration.name]
            }
            acc
        }
    }


}
