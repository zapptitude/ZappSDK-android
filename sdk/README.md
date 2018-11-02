# Zappitude Android SDK

Zapptitude Android SDK is distributed as Android Library in `aar` file format. 
It's a single drop-in solution to Zappify your App. 
Once your app is Zappified, it tracks events anonymously unless user request a `Zid`. 

A `Zid` can be obtained from [Zapptitude.com](https://zapptitude.com). User's progress and their inferred skills can
be viewd from Zapptitude's dashboard.

## Integration
**Android Studio**
There are two ways in which Zapptitude library can be integrated into your project:
1. Add following dependency in your `build.gradle`
```
compile 'com.zapptitude:zapptitude:0.0.1'
```
2. Add [AAR](https://github.com/zapptitude/android-sdk/blob/master/sampleapp/app/libs/zappSDK.aar) under your project's `lib` folder.

**AndroidManifest.xml**
```
<application
        android:name="mev.zappsdk.modules.ZApplication"
        ......
</application>        
```


## API

**To request Zid**
```
String zapptitude.requestZappId();
```

**To set Zid**
```
zapptitude.setZappId(String zid);
```

**To get user provider Zid**
``` 
String zapptitude.userProviderZappId();
```

## Tracking Events

**Log Event**
```
Zapptitude.logEvent(String event);
```

**Log beginning of task for a context**
``` 
Zapptitude.logBeginTask(String task, String context);
```

**Log solved binary task**
```
Zapptitude.logSolveBinaryTask(String task, String context, String topic, boolean expected, boolean actual);
```

**Log solved integer task**
```
Zapptitude.logSolveIntTask(String task, String context, String topic, int expected, int actual);
```

**Log solved float task**
```
Zapptitude.logSolveFloatTask(String task, String context, String topic, float expected, float actual);
```

**Log solved MC task**
```
Zapptitude.logSolveMCTask(String task, String context, String topic, char expected, char actual, int among);
```

**Log solved grad task**
```
Zapptitude.logSolveGradTask(String task, String context, String topic, int expected, int actual, int among);
```


## Activity Lifecycle

**Note: Please implement this methods to your every activity to provide stopping and resuming background process of log collection in your app.**

`Zapptitude.onResume()` in onResume() method of activity


```
@Override
    protected void onResume() {
        super.onResume();
        Zapptitude.onResume();
    }
```

`Zapptitude.onStop()` in onStop() method of activity
```
    @Override
    protected void onStop() {
        Zapptitude.onStop();
        super.onStop();
    }
```    

============
=====
***[Legacy]

## Environments

To switch environment on ZappSDK change **URL type** in ZappInternal() constructor.
Example: Logger.getInstance().initLogger(cleanedAppId, **URLType.PROD_ENVIRONMENT.getValue()**); for **Prod** or Logger.getInstance().initLogger(cleanedAppId, **URLType.DEV_ENVIRONMENT.getValue()**); for **Dev**


### Project Structure
One Android Activity (ZappId View);
Java classes (internal log implementation)
Bloom Filter
Tests (AndroidTestCase)
Implement this aar library to project to enable event logging. 
### images
Images used in app and icons for all devices
### lib
3rd party libraries

### Dependencies
All 3rd party libraries in lib directory
- [LoggerSDK] - Library provides functions to store logs and send them to server.
- [gson-2.2.jar] - Library provides functions to work with JSON format.
