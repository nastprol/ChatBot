package chatbot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramCommunicator extends TelegramLongPollingBot {
    private static Chatbot chatbot = new Chatbot(new GameFactory());
    private static String BOT_USERNAME;
    private static String BOT_TOKEN;

    TelegramCommunicator(DefaultBotOptions botOptions) {
        super(botOptions);
        try {
            BOT_USERNAME = System.getenv("BOT_USERNAME");
            BOT_TOKEN = System.getenv("BOT_TOKEN");
        }
        catch (NumberFormatException e) {
            System.out.println("Set correct data");
            System.exit(0);
        }
    }
    
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Reply reply = chatbot.ProcessRequest(update.getMessage().getText(), update.getMessage().getFrom().getId());

            var sendMessage = new SendMessage(update.getMessage().getChatId(), 
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
