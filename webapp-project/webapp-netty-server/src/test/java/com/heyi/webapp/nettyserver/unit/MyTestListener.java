package com.heyi.webapp.nettyserver.unit;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class MyTestListener implements TestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {

    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {

    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        System.out.println("mytestlistener-------start");
    }

    @Override
    public void beforeTestExecution(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestExecution(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        System.out.println("mytestlistener-------end");
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }
}
