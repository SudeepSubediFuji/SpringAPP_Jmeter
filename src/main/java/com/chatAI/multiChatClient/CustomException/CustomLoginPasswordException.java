package com.chatAI.multiChatClient.CustomException;

import javax.security.auth.login.LoginException;

public class CustomLoginPasswordException extends LoginException {
    public CustomLoginPasswordException(String password){
        super("Invalid Password: "+password);
    }
}
