package com.zapptitude.sampleapp.Activities.ZappActivity.Models;

/**
 * Created by andrew on 11.04.16.
 */
public class LogSolveFloatTaskModel extends LogTaskInContextModel {

    String topics = "";
    float expected;
    float actual;

    public LogSolveFloatTaskModel(String task, String context, String topics, float expected, float actual) {
        super(task, context);
        this.topics = topics;
        this.expected = expected;
        this.actual = actual;
        type = "Float";
    }
}
