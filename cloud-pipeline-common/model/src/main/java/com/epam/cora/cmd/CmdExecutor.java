package com.epam.cora.cmd;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public interface CmdExecutor {

    /**
     * Executes the given command synchronously. Method <b>blocks</b> the current thread until the execution finishes!
     *
     * @param command Cmd command to be executed.
     * @return Execution std out.
     */
    default String executeCommand(String command) {
        return executeCommand(command, Collections.emptyMap());
    }

    default String executeCommand(String command, String username) {
        return executeCommand(command, Collections.emptyMap(), null, username);
    }

    /**
     * Executes the given command synchronously. Method <b>blocks</b> the current thread until the execution finishes!
     *
     * @param command              Cmd command to be executed.
     * @param environmentVariables Environment variables key-value map.
     * @return Execution std out.
     */
    default String executeCommand(String command, Map<String, String> environmentVariables) {
        return executeCommand(command, environmentVariables, null);
    }

    default String executeCommand(String command, Map<String, String> environmentVariables, File workDir) {
        return executeCommand(command, environmentVariables, workDir, null);
    }

    /**
     * Executes the given command synchronously. Method <b>blocks</b> the current thread until the execution finishes!
     *
     * @param command              Cmd command to be executed.
     * @param environmentVariables Environment variables key-value map.
     * @param workDir              Directory command should be executed in.
     * @param username             name of the user applied to execute a command
     * @return Execution std out.
     */
    String executeCommand(String command, Map<String, String> environmentVariables, File workDir, String username);

    /**
     * Start the given command execution. Method <b>doesn't wait</b> for the end of the command execution!
     *
     * @param command Cmd command to be executed.
     * @return Launched process.
     */
    default Process launchCommand(String command) {
        return launchCommand(command, Collections.emptyMap());
    }

    default Process launchCommand(String command, String username) {
        return launchCommand(command, Collections.emptyMap(), null, username);
    }

    /**
     * Start the given command execution. Method <b>doesn't wait</b> for the end of the command execution!
     *
     * @param command              Cmd command to be executed.
     * @param environmentVariables Environment variables key-value map.
     * @return Launched process.
     */
    default Process launchCommand(String command, Map<String, String> environmentVariables) {
        return launchCommand(command, environmentVariables, null);
    }

    default Process launchCommand(String command, Map<String, String> environmentVariables, File workDir) {
        return launchCommand(command, environmentVariables, workDir, null);
    }

    /**
     * Start the given command execution. Method <b>doesn't wait</b> for the end of the command execution!
     *
     * @param command              Cmd command to be executed.
     * @param environmentVariables Environment variables key-value map.
     * @param workDir              Directory command should be executed in.
     * @param username             name of the user applied to execute a command
     * @return Launched process.
     */
    Process launchCommand(String command, Map<String, String> environmentVariables, File workDir, String username);
}
