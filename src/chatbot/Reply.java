package chatbot;

import java.util.List;
import db.DataItem;

class Reply {
    String botAnswer;
    List<String> keyboardOptions;
    DataItem data; //TODO â battlesea

    Reply(String message, List<String> options) {
        this.botAnswer = message;
        keyboardOptions = options;
    }
}