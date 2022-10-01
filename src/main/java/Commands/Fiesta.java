package Commands;

import ThetaBot.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public class Fiesta implements Command {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            for (int i = 0; i < 10; ++i) {
                event.getChannel().sendMessage("IT'S A FIESTA").queue();
            }
        }
        if (message.length != 1) {
            event.getChannel().sendMessage("too many arguments :(").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Fiesta";
    }
}
