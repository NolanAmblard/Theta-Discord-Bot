package Commands;

import ThetaBot.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public class Ping implements Command {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            long time = System.currentTimeMillis();
            event.getChannel().sendMessage("wake up \n WAKE UP").queue(response -> {
                response.editMessageFormat("Ping %d ms", System.currentTimeMillis() - time).queue();
            });
        }
        if (message.length != 1) {
            event.getChannel().sendMessage("too many arguments :(").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Ping";
    }
}

