package chatbot;

import java.util.List;

class Reply {
    String botAnswer;
    List<String> keyboardOptions;

    Reply(String message, List<String> options) {
        this.botAnswer = message;
        keyboardOptions = options;
    }
}