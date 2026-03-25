package com.chatAI.multiChatClient.controller;

import com.chatAI.multiChatClient.CustomException.CustomLoginPasswordException;
import com.chatAI.multiChatClient.CustomException.CustomLoginUsernameException;
import com.chatAI.multiChatClient.advisor.tokenUsageAuditAdvisor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

//import static com.sun.tools.jdeprscan.Main.call;

@RestController
@RequestMapping("/advisor")
public class advisorController {

    private static final Logger log = LogManager.getLogger(advisorController.class);
    private final ChatClient ollamaOfficeChatClient;
//    private final ChatClient openAISimpleChatClient;
//    private final ChatClient openAiChatClient;

    //@Qualifier("openAISimpleChatClient") ChatClient openAISimpleChatClient
    public advisorController(@Qualifier("ollamaOfficeChatClient") ChatClient ollamaOfficeChatClient
    ) {
        this.ollamaOfficeChatClient = ollamaOfficeChatClient;
//        this.openAISimpleChatClient = openAISimpleChatClient;
    }

    @GetMapping("/office")
    public String ollamaofficeAdvisorChat(@RequestParam("message") String message) {
        return ollamaOfficeChatClient.prompt(message).advisors(new SimpleLoggerAdvisor()).call().content();
    }

    @GetMapping("/login")
    public Flux<String> ollamaofficeAdvisorChat(@RequestParam("username") String username,
                                                @RequestParam("password") String password,
                                                @RequestParam("message") String message
    ) throws CustomLoginUsernameException, CustomLoginPasswordException {
        log.info("Username: " + username);
        usernameErrorChecker(username);
        passwordErrorChecker(password);

        return ollamaOfficeChatClient.prompt(message).stream().content();

    }

    public void usernameErrorChecker(String username) throws CustomLoginUsernameException {
        String defaultUser = "sudh";
        log.info("Username: " + username);
        if (username.equals(defaultUser)) {
            log.info("Username is matched");
        }else {
            throw new CustomLoginUsernameException(username);
        }
    }

    public void passwordErrorChecker(String password) throws CustomLoginPasswordException {
        String defaultPassword = "password";
        if (password.equals(defaultPassword)) {
            log.info("Password is matched.");
        } else {
            throw new CustomLoginPasswordException(password);
        }
    }

    @GetMapping("/fulloffice")
    public String ollamaofficeAdvisorChat2(@RequestParam("message") String message) {
        return ollamaOfficeChatClient.prompt(message).advisors(new SimpleLoggerAdvisor()).call().content();
    }

//    @GetMapping("/fulloff")
//    public String ollamaofficeAdvisorChat3(@RequestParam ("message") String message) {
//        return ollamaOfficeChatClient.prompt(message)
//                .advisors(AdvisorSpec->
//                        AdvisorSpec.param("",new SimpleLoggerAdvisor()).call().content());
//    }

//    @GetMapping("/openai")
//    public String openAiAdvisorChat(@RequestParam ("message") String message) {
//        return openAISimpleChatClient.prompt(message).call().content();
//    }

    @GetMapping("/open")
    public String ollamaAdvisorChat2(@RequestParam("message") String message) {
        return ollamaOfficeChatClient.prompt(message).advisors(new tokenUsageAuditAdvisor()).call().content();
    }
}
