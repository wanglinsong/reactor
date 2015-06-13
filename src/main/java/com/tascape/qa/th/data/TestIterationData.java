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
package com.tascape.qa.th.data;

import com.tascape.qa.th.SystemConfiguration;

/**
 *
 * @author linsong wang
 */
public class TestIterationData extends AbstractTestData {

    private static final SystemConfiguration config = SystemConfiguration.getInstance();

    private int iteration = 1;

    private int iterations = 1;

    public TestIterationData() {
    }

    private TestIterationData(int iteration, int iterations) {
        this.iteration = iteration;
        this.iterations = iterations;
    }

    public TestIterationData[] getData(String sysPropIterations) {
        String n = config.getProperty(sysPropIterations);
        return useIterations(n);
    }

    public TestIterationData[] useIterations(String n) {
        int iters = 1;
        try {
            iters = Integer.parseInt(n);
        } catch (Exception ex) {
        }
        TestIterationData[] data = new TestIterationData[iters];
        for (int i = 0; i < iters; i++) {
            data[i] = new TestIterationData(i, iters);
        }
        return data;
    }

    @Override
    public String getValue() {
        return (this.iteration + 1) + "/" + this.iterations;
    }

    public int getIteration() {
        return iteration;
    }

    public int getIterations() {
        return iterations;
    }
}
