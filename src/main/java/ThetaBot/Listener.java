package ThetaBot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {

    private final CommandManager cm;

    Listener(CommandManager cm) {
        this.cm = cm;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message m = event.getMessage();
        String content = m.getContentRaw();
        MessageChannel channel = event.getChannel();

        if(content.trim().equals("testing")) {
            channel.sendMessage("Working").queue();
        }

        //If the user who sent the message is not a bot and the beginning of the message begins with the correct prefix, then run the command
        if (!event.getAuthor().isBot() && content.startsWith("!")) {
            cm.runCommand(event);
        }
    }
}
