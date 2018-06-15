package com.zapptitude.sampleapp.Activities.ZappActivity.Models;

/**
 * Created by andrew on 11.04.16.
 */
public class LogSolveGradTaskModel extends LogTaskInContextModel {

    String topics = "";
    int expected;
    int actual;
    int among;

    public LogSolveGradTaskModel(String task, String context, String topics, int expected, int actual, int among) {
        super(task, context);
        this.topics = topics;
        this.expected = expected;
        this.actual = actual;
        this.among = among;
        type = "Grad";
    }

}