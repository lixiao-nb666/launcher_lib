//package com.newbee.launcher_lib.util;
//
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.util.List;
//
///**
// * ShellUtils
// * <ul>
// * <strong>Check root</strong>
// * <li>{@link ShellUtils#checkRootPermission()}</li>
// * </ul>
// * <ul>
// * <strong>Execte command</strong>
// * <li>{@link ShellUtils#execCommand(String, boolean)}</li>
// * <li>{@link ShellUtils#execCommand(String, boolean, boolean)}</li>
// * <li>{@link ShellUtils#execCommand(List, boolean)}</li>
// * <li>{@link ShellUtils#execCommand(List, boolean, boolean)}</li>
// * <li>{@link ShellUtils#execCommand(String[], boolean)}</li>
// * <li>{@link ShellUtils#execCommand(String[], boolean, boolean)}</li>
// * </ul>
// */
//public class ShellUtils {
//
//    public static final String COMMAND_SU       = "su";
//    public static final String COMMAND_SH       = "sh";
//    public static final String COMMAND_EXIT     = "exit\n";
//    public static final String COMMAND_LINE_END = "\n";
//
//    private ShellUtils() {
//        throw new AssertionError();
//    }
//
//    /**
//     * check whether has root permission
//     *
//     * @return
//     */
//    public static boolean checkRootPermission() {
//        return execCommand("echo root", true, false).result == 0;
//    }
//
//    /**
//     * execute shell command, default return result msg
//     *
//     * @param command command
//     * @param isRoot whether need to run with root
//     * @return
//     * @see ShellUtils#execCommand(String[], boolean, boolean)
//     */
//    public static CommandResult execCommand(String command, boolean isRoot) {
//        return execCommand(new String[] {command}, isRoot, true);
//    }
//
//    /**
//     * execute shell commands, default return result msg
//     *
//     * @param commands command list
//     * @param isRoot whether need to run with root
//     * @return
//     * @see ShellUtils#execCommand(String[], boolean, boolean)
//     */
//    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
//        return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot, true);
//    }
//
//    /**
//     * execute shell commands, default return result msg
//     *
//     * @param commands command array
//     * @param isRoot whether need to run with root
//     * @return
//     * @see ShellUtils#execCommand(String[], boolean, boolean)
//     */
//    public static CommandResult execCommand(String[] commands, boolean isRoot) {
//        return execCommand(commands, isRoot, true);
//    }
//
//    /**
//     * execute shell command
//     *
//     * @param command command
//     * @param isRoot whether need to run with root
//     * @param isNeedResultMsg whether need result msg
//     * @return
//     * @see ShellUtils#execCommand(String[], boolean, boolean)
//     */
//    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
//        return execCommand(new String[] {command}, isRoot, isNeedResultMsg);
//    }
//
//    /**
//     * execute shell commands
//     *
//     * @param commands command list
//     * @param isRoot whether need to run with root
//     * @param isNeedResultMsg whether need result msg
//     * @return
//     * @see ShellUtils#execCommand(String[], boolean, boolean)
//     */
//    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
//        return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot, isNeedResultMsg);
//    }
//
//    /**
//     * execute shell commands
//     *
//     * @param commands command array
//     * @param isRoot whether need to run with root
//     * @param isNeedResultMsg whether need result msg
//     * @return <ul>
//     *         <li>if isNeedResultMsg is false, {@link CommandResult#successMsg} is null and
//     *         {@link CommandResult#errorMsg} is null.</li>
//     *         <li>if {@link CommandResult#result} is -1, there maybe some excepiton.</li>
//     *         </ul>
//     */
//    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
//        int result = -1;
//        Log.i("kankan cmd","kankan cmd:0");
//        if (commands == null || commands.length == 0) {
//            return new CommandResult(result, null, null);
//        }
//
//        Process process = null;
//        BufferedReader successResult = null;
//        BufferedReader errorResult = null;
//        StringBuilder successMsg = null;
//        StringBuilder errorMsg = null;
//        Log.i("kankan cmd","kankan cmd:1");
//        DataOutputStream os = null;
//        try {
//            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
//            os = new DataOutputStream(process.getOutputStream());
//            for (String command : commands) {
//                if (command == null) {
//                    continue;
//                }
//
//                // donnot use os.writeBytes(commmand), avoid chinese charset error
//                os.write(command.getBytes());
//                os.writeBytes(COMMAND_LINE_END);
//                os.flush();
//            }
//            os.writeBytes(COMMAND_EXIT);
//            os.flush();
//
//            result = process.waitFor();
//            // get command result
//            Log.i("kankan cmd","kankan cmd:2");
//            if (isNeedResultMsg) {
//                successMsg = new StringBuilder();
//                errorMsg = new StringBuilder();
//                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                String s;
//                while ((s = successResult.readLine()) != null) {
//                    successMsg.append(s);
//                }
//                while ((s = errorResult.readLine()) != null) {
//                    errorMsg.append(s);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i("kankan cmd","kankan cmd:err-"+e.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("kankan cmd","kankan cmd:err1-"+e.toString());
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (successResult != null) {
//                    successResult.close();
//                }
//                if (errorResult != null) {
//                    errorResult.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (process != null) {
//                process.destroy();
//            }
//        }
//        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
//                : errorMsg.toString());
//    }
//
//    /**
//     * result of command
//     * <ul>
//     * <li>{@link CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
//     * linux shell</li>
//     * <li>{@link CommandResult#successMsg} means success message of command result</li>
//     * <li>{@link CommandResult#errorMsg} means error message of command result</li>
//     * </ul>
//     *
//     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
//     */
//    public static class CommandResult {
//
//        /** result of command **/
//        public int    result;
//        /** success message of command result **/
//        public String successMsg;
//        /** error message of command result **/
//        public String errorMsg;
//
//        public CommandResult(int result) {
//            this.result = result;
//        }
//
//        public CommandResult(int result, String successMsg, String errorMsg) {
//            this.result = result;
//            this.successMsg = successMsg;
//            this.errorMsg = errorMsg;
//        }
//
//        public int getResult() {
//            return result;
//        }
//
//        public void setResult(int result) {
//            this.result = result;
//        }
//
//        public String getSuccessMsg() {
//            return successMsg;
//        }
//
//        public void setSuccessMsg(String successMsg) {
//            this.successMsg = successMsg;
//        }
//
//        public String getErrorMsg() {
//            return errorMsg;
//        }
//
//        public void setErrorMsg(String errorMsg) {
//            this.errorMsg = errorMsg;
//        }
//
//        @Override
//        public String toString() {
//            return "CommandResult{" +
//                    "result=" + result +
//                    ", successMsg='" + successMsg + '\'' +
//                    ", errorMsg='" + errorMsg + '\'' +
//                    '}';
//        }
//    }
//
//
//    public static String execShellCommand(String cmd) {
//        String returnString = "";
//        Process pro = null;
//        Runtime runTime = Runtime.getRuntime();
//        String TAG="kankanexecShellCommand";
//        Log.d(TAG, "execShellCommand : " + cmd);
//        if (runTime == null) {
//            Log.e(TAG, "Create runtime false!");
//            return null;
//        }
//        Log.d(TAG, "execShellCommand 111: " + cmd);
//        try {
//            pro = runTime.exec(cmd);
//            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
//            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
//            String line;
//            Log.d(TAG, "execShellCommand 222: " + cmd);
//            while ((line = input.readLine()) != null) {
//                returnString = returnString + line + "\n";
//                Log.d(TAG, "execShellCommand result 111: " + returnString);
//            }
//            Log.d(TAG, "execShellCommand 333: " + cmd);
//            input.close();
//            output.close();
//            pro.destroy();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d(TAG, "execShellCommand err: " + e.toString());
//        }
//
//        Log.d(TAG, "execShellCommand result : " + returnString);
//        return returnString;
//    }
//}