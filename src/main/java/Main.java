import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = "";
        JDABuilder.createLight(token)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new Main())
                .setActivity(Activity.playing("-_-"))
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        System.out.println(event.getAuthor().getAsTag());
        if ((msg.getContentDisplay().equals("!ping")))
        {
            System.out.println("YES");
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }
    }
//        JDABuilder builder = JDABuilder.createDefault(token);
//        builder.addEventListeners(new Main());
//        builder.build();
//    }
//
//    @Override
//    public void onMessageReceived(MessageReceivedEvent event) {
//        System.out.println("We received a message from " +
//                event.getAuthor().getName() + ": " +
//                event.getMessage().getContentDisplay());
//        if(event.getMessage().getContentRaw().equals("!ping")) {
//            event.getChannel().sendMessage("Pong!").queue();
//        }
//    }
}
