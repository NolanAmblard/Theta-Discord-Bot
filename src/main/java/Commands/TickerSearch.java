package Commands;

import ThetaBot.Command;
import TickerInfo.ParseTickerCsv;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public class TickerSearch implements Command {
    private ParseTickerCsv parsedTickers;

    public TickerSearch(ParseTickerCsv parsedTickers) {
        this.parsedTickers = parsedTickers;
    }

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length != 2 && message.length != 3) {
            event.getChannel().sendMessage("Incorrect format. Use !tickersearch [ticker/partial ticker] [OPTIONAL: num results]").queue();
        } else if (message.length == 3) {
            int numResults = -1;
            try {
                numResults = Integer.parseInt(message[2]);
                if (numResults < 0 || numResults > 10) {
                    numResults = -1;
                }
            }
            catch (Exception NumberFormatException) { }
            finally {
                if (numResults != -1) {
                    List<String> resultingTickers = parsedTickers.tickerTree.findTopN(message[1], numResults);
                    sendTickerMessage(resultingTickers, event);
                } else {
                    event.getChannel().sendMessage("Please enter a valid number of results (0 <= N <= 10)").queue();
                }
            }
        } else {
            // Get the 5 top tickers for input
            List<String> resultingTickers = parsedTickers.tickerTree.findTopN(message[1], 5);
            sendTickerMessage(resultingTickers, event);
        }
    }

    private void sendTickerMessage(List<String> tickers, MessageReceivedEvent event) {
        String output = "No tickers found";
        for (String ticker : tickers) {
            if (output.equals("No tickers found")) output = ticker;
            else output += ", " + ticker;
        }
        event.getChannel().sendMessage(output).queue();
    }

    @Override
    public String getKeyword() {
        return "tickersearch";
    }
}
