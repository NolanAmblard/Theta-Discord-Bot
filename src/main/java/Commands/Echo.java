package Commands;

import ThetaBot.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public class Echo implements Command {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String echo = "";
        try {
            echo = message.substring(message.indexOf(' '));
        }
        catch (Exception StringIndexOutOfBoundsException) {
            echo = "The silence is deafening...";
        }
        event.getChannel().sendMessage(echo).queue();
    }

    @Override
    public String getKeyword() {
        return "Echo";
    }
}

