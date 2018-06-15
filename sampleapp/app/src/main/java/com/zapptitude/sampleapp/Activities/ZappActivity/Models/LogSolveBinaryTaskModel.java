package com.zapptitude.sampleapp.Activities.ZappActivity.Models;

/**
 * Created by andrew on 11.04.16.
 */
public class LogSolveBinaryTaskModel extends LogTaskInContextModel {

    String topics = "";
    boolean expected;
    boolean actual;

    public LogSolveBinaryTaskModel(String task, String context, String topics, boolean expected, boolean actual) {
        super(task, context);
        this.topics = topics;
        this.expected = expected;
        this.actual = actual;
        type = "Binary";
    }
}
