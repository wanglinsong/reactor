/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tascape.qa.th;

import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author linsong wang
 */
public class TestHarness {
    private static final Logger LOG = LoggerFactory.getLogger(TestHarness.class);

    public static void main(String[] args) {
        int exitCode = 0;
        try {
            SystemConfiguration config = SystemConfiguration.getInstance();
            config.listAppProperties();

            Utils.cleanDirectory(config.getLogPath().toFile().getAbsolutePath(), 240,
                SystemConfiguration.CONSTANT_LOG_KEEP_ALIVE_PREFIX);

            String suiteClass = config.getTestSuite();
            Pattern testClassRegex = config.getTestClassRegex();
            Pattern testMethodRegex = config.getTestMethodRegex();
            int priority = config.getTestPriority();
            LOG.info("Running test suite class: {}", suiteClass);
            Object c = Class.forName(suiteClass).newInstance();
            AbstractTestSuite ts = null;
            if (c instanceof com.tascape.qa.junit4.AbstractSuite) {
                ts = new com.tascape.qa.junit4.TestSuite(suiteClass, testClassRegex, testMethodRegex, priority);
            } else if (c instanceof com.tascape.qa.testng.AbstractSuite) {
                ts = new com.tascape.qa.testng.TestSuite(suiteClass, testClassRegex, testMethodRegex, priority);
            }
            if (ts != null) {
                if (ts.getTests().isEmpty()) {
                    throw new RuntimeException("No test cases found based on system properties");
                }
                SuiteRunner sr = new SuiteRunner(ts);
                exitCode = sr.startExecution();
            } else {
                throw new RuntimeException("null test suite");
            }

        } catch (Throwable t) {
            LOG.error("TestHarness finishes with exception", t);
            exitCode = -1;
        } finally {
            if (exitCode != 0) {
                LOG.error("TestHarness finishes with exit code {}", exitCode);
            }
            System.exit(exitCode);
        }
    }
}
