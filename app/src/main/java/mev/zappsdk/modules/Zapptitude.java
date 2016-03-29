package mev.zappsdk.modules;

import java.util.HashMap;

/**
 * Created by andrew on 24.03.16.
 */
public class Zapptitude {

    public ZappInternal zappInternal;

    // TODO: Dispatch once?
    public Zapptitude() {
        zappInternal = new ZappInternal();
    }

    public void requestZappId() {
        zappInternal.requestZappId();
    }

    public void setZappId(String zappId) {
        zappInternal.setZappId(zappId);
    }

    public String userProviderZappId() {
        return zappInternal.userProviderZappId();
    }

    public void logEvent(String event) {
        ZappEventLogger.getInstance().logEvent(event, zappInternal.sessionInfo());
    }

    public void logBeginTask(String task, String context, HashMap<String, String> info) {
        zappInternal.setTask(task, context);
        ZappEventLogger.getInstance().logBeginTask(task, context, info);
    }

    public void logSolveBinaryTask(String task, String context, String topics, boolean expected, boolean actual) {
        ZappEventLogger.getInstance().logSolveBinaryTask(task, context, topics, expected, actual, zappInternal.sessionInfoForTask(task, context));
        zappInternal.resetTaskStartTime();
    }

    public void logSolveIntTask(String task, String context, String topics, int expected, int actual) {
        ZappEventLogger.getInstance().logSolveIntTask(task, context, topics, expected, actual, zappInternal.sessionInfoForTask(task, context));
        zappInternal.resetTaskStartTime();
    }

    public void logSolveFloatTask(String task, String context, String topics, float expected, float actual) {
        ZappEventLogger.getInstance().logSolveFloatTask(task, context, topics, expected, actual, zappInternal.sessionInfoForTask(task, context));
        zappInternal.resetTaskStartTime();
    }

    public void logSolveMCTTask(String task, String context, String topics, char expected, char actual, int among) {
        ZappEventLogger.getInstance().logSolveMCTTask(task, context, topics, expected, actual, among, zappInternal.sessionInfoForTask(task, context));
        zappInternal.resetTaskStartTime();
    }

    public void logSolveGradTask(String task, String context, String topics, int expected, int actual, int among) {
        ZappEventLogger.getInstance().logSolveGradTask(task, context, topics, expected, actual, among, zappInternal.sessionInfoForTask(task, context));
        zappInternal.resetTaskStartTime();
    }

}
