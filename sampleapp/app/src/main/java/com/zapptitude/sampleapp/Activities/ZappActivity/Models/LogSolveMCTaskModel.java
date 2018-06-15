package com.zapptitude.sampleapp.Activities.ZappActivity.Models;

/**
 * Created by andrew on 11.04.16.
 */
public class LogSolveMCTaskModel extends LogTaskInContextModel {

    String topics = "";
    char expected;
    char actual;
    int among;

    public LogSolveMCTaskModel(String task, String context, String topics, char expected, char actual, int among) {
        super(task, context);
        this.topics = topics;
        this.expected = expected;
        this.actual = actual;
        this.among = among;
        type = "MC";
    }
}
