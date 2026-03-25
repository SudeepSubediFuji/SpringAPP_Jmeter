package com.chatAI.multiChatClient.CustomException;

import javax.security.auth.login.LoginException;

public class CustomLoginUsernameException extends LoginException {
    public CustomLoginUsernameException(String username){
        super("Invalid Username."+username);
    }
}
