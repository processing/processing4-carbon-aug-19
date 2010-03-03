package processing.app.tools.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import processing.app.debug.RunnerListener;
import processing.app.tools.android.LogEntry.Severity;

class AndroidDevice implements AndroidDeviceProperties {
  private final AndroidEnvironment env;
  private final String id;
  private final Set<Integer> activeProcesses = new HashSet<Integer>();
  private final Set<DeviceListener> listeners = Collections
      .synchronizedSet(new HashSet<DeviceListener>());

  // mutable state
  private Process logcat;

  public AndroidDevice(final AndroidEnvironment env, final String id) {
    this.env = env;
    this.id = id;
  }

  public void bringLauncherToFront() {
    try {
      adb("shell", "am", "start", "-a", "android.intent.action.MAIN", "-c",
        "android.intent.category.HOME");
    } catch (final Exception e) {
      e.printStackTrace(System.err);
    }
  }

  // adb -s emulator-5556 install helloWorld.apk

  // : adb -s HT91MLC00031 install bin/Brightness-debug.apk
  // 532 KB/s (190588 bytes in 0.349s)
  // pkg: /data/local/tmp/Brightness-debug.apk
  // Failure [INSTALL_FAILED_ALREADY_EXISTS]

  // : adb -s HT91MLC00031 install -r bin/Brightness-debug.apk
  // 1151 KB/s (190588 bytes in 0.161s)
  // pkg: /data/local/tmp/Brightness-debug.apk
  // Success

  // safe to just always include the -r (reinstall) flag
  public boolean installApp(final String apkPath, final RunnerListener status) {
    bringLauncherToFront();
    try {
      final ProcessResult installResult = adb("install", "-r", apkPath);
      if (!installResult.succeeded()) {
        status.statusError("Could not install the sketch.");
        System.err.println(installResult);
        return false;
      }
      String errorMsg = null;
      for (final String line : installResult) {
        if (line.startsWith("Failure")) {
          errorMsg = line.substring(8);
          System.err.println(line);
        }
      }
      if (errorMsg == null) {
        status.statusNotice("Done installing.");
        return true;
      }
      status.statusError("Error while installing " + errorMsg);
    } catch (final IOException e) {
      status.statusError(e);
    } catch (final InterruptedException e) {
    }
    return false;
  }

  // different version that actually runs through JDI:
  // http://asantoso.wordpress.com/2009/09/26/using-jdb-with-adb-to-debugging-of-android-app-on-a-real-device/
  public boolean launchApp(final String packageName, final String className)
      throws IOException, InterruptedException {
    return adb("shell", "am", "start", "-e", "debug", "true", "-a",
      "android.intent.action.MAIN", "-c", "android.intent.category.LAUNCHER",
      "-n", packageName + "/." + className).succeeded();
  }

  public boolean isEmulator() {
    return id.startsWith("emulator");
  }

  // I/Process ( 9213): Sending signal. PID: 9213 SIG: 9
  private static final Pattern SIG = Pattern
      .compile("PID:\\s+(\\d+)\\s+SIG:\\s+(\\d+)");

  private final List<String> stackTrace = new ArrayList<String>();

  private class LogLineProcessor implements LineProcessor {
    public void processLine(final String line) {
      final LogEntry entry = new LogEntry(line);
      if (entry.message.startsWith("PROCESSING")) {
        if (entry.message.contains("onStart")) {
          startProc(entry.source, entry.pid);
        } else if (entry.message.contains("onStop")) {
          endProc(entry.pid);
        }
      } else if (entry.source.equals("Process")) {
        final Matcher m = SIG.matcher(entry.message);
        if (m.find()) {
          final int pid = Integer.parseInt(m.group(1));
          final int signal = Integer.parseInt(m.group(2));
          if (signal == 9) {
            endProc(pid);
          } else if (signal == 3) {
            reportStackTrace(entry);
          }
        }
      } else if (activeProcesses.contains(entry.pid)) {
        handleConsole(entry);
      }
    }
  }

  private void handleConsole(final LogEntry entry) {
    final boolean isStackTrace = entry.source.equals("AndroidRuntime")
        && entry.severity == Severity.Error;
    if (isStackTrace) {
      if (!entry.message.startsWith("Uncaught handler")) {
        stackTrace.add(entry.message);
        System.err.println(entry.message);
      }
    } else if (entry.source.equals("System.out")
        || entry.source.equals("System.err")) {
      if (entry.severity.useErrorStream) {
        System.err.println(entry.message);
      } else {
        System.out.println(entry.message);
      }
    }
  }

  private void reportStackTrace(final LogEntry entry) {
    if (stackTrace.isEmpty()) {
      System.err.println("That's weird. Proc " + entry.pid
          + " got signal 3, but there's no stack trace.");
    }
    final List<String> stackCopy = Collections
        .unmodifiableList(new ArrayList<String>(stackTrace));
    for (final DeviceListener listener : listeners) {
      listener.stacktrace(stackCopy);
    }
    stackTrace.clear();
  }

  void initialize() throws IOException, InterruptedException {
    adb("logcat", "-c");
    logcat = Runtime.getRuntime().exec(generateAdbCommand("logcat"));
    new StreamPump(logcat.getInputStream()).addTarget(new LogLineProcessor())
        .start();
    new StreamPump(logcat.getErrorStream()).addTarget(System.err).start();
  }

  void shutdown() {
    if (logcat != null) {
      logcat.destroy();
    }
    env.deviceRemoved(this);
    listeners.clear();
  }

  public String getId() {
    return id;
  }

  public AndroidEnvironment getEnv() {
    return env;
  }

  private void startProc(final String name, final int pid) {
    //    System.err.println("Process " + name + " started at pid " + pid);
    activeProcesses.add(pid);
  }

  private void endProc(final int pid) {
    //    System.err.println("Process " + pid + " stopped.");
    activeProcesses.remove(pid);
  }

  public void addListener(final DeviceListener listener) {
    listeners.add(listener);
  }

  public void removeListener(final DeviceListener listener) {
    listeners.remove(listener);
  }

  private ProcessResult adb(final String... cmd) throws InterruptedException,
      IOException {
    final String[] adbCmd = generateAdbCommand(cmd);
    return new ProcessHelper(adbCmd).execute();
  }

  private String[] generateAdbCommand(final String... cmd) {
    final String[] adbCmd = new String[3 + cmd.length];
    adbCmd[0] = "adb";
    adbCmd[1] = "-s";
    adbCmd[2] = getId();
    System.arraycopy(cmd, 0, adbCmd, 3, cmd.length);
    return adbCmd;
  }

  @Override
  public String toString() {
    return "[AndroidDevice " + getId() + "]";
  }

}
