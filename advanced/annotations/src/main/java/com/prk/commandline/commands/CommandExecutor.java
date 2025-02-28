package com.prk.commandline.commands;

import com.prk.commandline.SessionState;
import com.prk.commandline.UserInput;

public interface CommandExecutor {
    void execute(SessionState sessionState, UserInput userInput);
}
