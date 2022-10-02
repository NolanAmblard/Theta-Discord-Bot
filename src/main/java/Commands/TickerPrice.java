package Commands;

import ThetaBot.Command;

import TickerInfo.ParseTickerCsv;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class TickerPrice implements Command {
    private ParseTickerCsv parsedTickers;

    public TickerPrice(ParseTickerCsv parsedTickers) {this.parsedTickers = parsedTickers; }

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) throws IOException {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 2 && validTicker(message[1], event)) {
            Stock stock = YahooFinance.get(message[1]);
            BigDecimal price = stock.getQuote().getPrice();
            String priceS = price.toString();
            event.getChannel().sendMessage("Price: " + priceS).queue();
        }
        if (message.length != 2 || !(validTicker(message[1], event))) {
            event.getChannel().sendMessage("Incorrect format. Please only enter a valid ticker.").queue();
        }
    }

    boolean validTicker(String ticker, MessageReceivedEvent event) {
        return parsedTickers.tickerTree.searchNode(ticker).isTicker;
    }

    @Override
    public String getKeyword() {return "tickerprice";}
}
