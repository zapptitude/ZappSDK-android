package com.zapptitude.sampleapp.Activities.ZappActivity.Models;

/**
 * Created by andrew on 11.04.16.
 */
public class LogSolveIntTaskModel extends LogTaskInContextModel {

    String topics = "";
    int expected;
    int actual;

    public LogSolveIntTaskModel(String task, String context, String topics, int expected, int actual) {
        super(task, context);
        this.topics = topics;
        this.expected = expected;
        this.actual = actual;
        type = "Int";
    }
}
