package ThetaBot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public interface Command {
    void execute(List<String> args, MessageReceivedEvent event);

    String getKeyword();
}
