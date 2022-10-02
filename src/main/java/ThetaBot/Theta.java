package ThetaBot;

import TickerInfo.ParseTickerCsv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Theta extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = "MTAyNTUyMzk2OTQ1MDg1MjM5Mg.GuWbW-.8YIRywLEtE59J4gByqwchCs_2MnTt81vgyhNTw";

        CommandManager commandManager = new CommandManager();

        JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new Listener(commandManager))
                .setActivity(Activity.playing("-_-"))
                .build();
    }
}
