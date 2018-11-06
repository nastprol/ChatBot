package chatbot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import db.DataBase;

import java.util.ArrayList;
import java.util.List;

public class TelegramCommunicator extends TelegramLongPollingBot {
    private static IBot chatbot;
    private static IBotConfig config;

    TelegramCommunicator(DefaultBotOptions botOptions, IBot chatbot, IBotConfig cf) {
    	
        super(botOptions);
  
		this.chatbot = chatbot;
        this.config = cf;
    }
    
    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Reply reply = chatbot.ProcessRequest(update.getMessage().getText(), update.getMessage().getFrom().getId().intValue());

            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId(), 
            		reply.botAnswer);
            if (reply.keyboardOptions != null) {
                sendMessage.setReplyMarkup(setKeyboard(reply.keyboardOptions));
            }
            else {
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove());
            }
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup setKeyboard(List<String> options) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (String row : options) {
            KeyboardRow kRow = new KeyboardRow();
            kRow.add(row);
            keyboardRows.add(kRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup; 
    }
}
