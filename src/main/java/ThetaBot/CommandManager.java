package ThetaBot;

import Commands.*;
import TickerInfo.ParseTickerCsv;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<String, Command>();
    private static ParseTickerCsv parsedTickers;

    CommandManager() {
        parsedTickers = new ParseTickerCsv();

        addCommand(new Fiesta());
        addCommand(new Ping());
        addCommand(new Echo());
        addCommand(new TickerSearch(parsedTickers));
        addCommand(new TickerPrice(parsedTickers));
    }

    private void addCommand(Command command) {
        if(!commands.containsKey(command.getKeyword().toLowerCase())) {
            commands.put(command.getKeyword().toLowerCase(), command);
        }
    }

    void runCommand(MessageReceivedEvent event) throws IOException {
        String prefix = "!";

        String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(prefix), "").split("\\s+");

        String keyword = split[0].toLowerCase();

        if (commands.containsKey(keyword)) {
            List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();

            commands.get(keyword).execute(args, event);
        }
    }

    public ParseTickerCsv getParsedTickers() {
        return parsedTickers;
    }
}
