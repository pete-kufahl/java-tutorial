package com.prk.commandline.commands;

import com.prk.commandline.Command;
import com.prk.commandline.SessionState;
import com.prk.commandline.UserInput;

@Command(value = "logout", order = 90, description = "logout and exit")
public class LogoutCommand implements CommandExecutor {

    @Override
    public void execute(SessionState sessionState, UserInput userInput) {
        System.out.println("thank you and goodbye");
        sessionState.setFinished(true);
    }
}
