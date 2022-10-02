package TickerInfo;

import com.opencsv.bean.CsvToBeanBuilder;
import org.jetbrains.annotations.NotNull;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ParseTickerCsv {
    private List<Ticker> tickers = new ArrayList<Ticker>();
    public Tree tickerTree = new Tree();

    public ParseTickerCsv() {
        String filename = "./src/main/java/TickerInfo/nasdaq_tickers.csv";

        try {
            this.tickers = new CsvToBeanBuilder(new FileReader(filename))
                    .withType(Ticker.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Ticker ticker : tickers) {
            tickerTree.insert(ticker.getTicker(), ticker.getMarketCap());
        }
    }

//    public Tree getTickerTree() {
//        return tickerTree;
//    }

    class Tuple {
        String ticker;
        long mcap;
        Tuple(String ticker, long mcap) {
            this.ticker = ticker;
            this.mcap = mcap;
        }
    }

    class Comparer implements Comparator<Tuple> {
        public int compare(Tuple t1, Tuple t2) {
            if (t1.mcap < t2.mcap)
                return -1;
            else if (t1.mcap > t2.mcap)
                return 1;
            return 0;
        }
    }

    public class TreeNode {
        public boolean isTicker;
        public long marketCap;
        public Map<String, Long> freq;
        public HashMap<Character, TreeNode> children;

        public TreeNode() {
            isTicker = false;
            marketCap = 0L;
            freq = new HashMap<String, Long>();
            children = new HashMap<Character, TreeNode>();
        }
    }

    public class Tree {
        private TreeNode root;

        public Tree() {
            root = new TreeNode();
        }

        public void insert(@NotNull String ticker, long mCap) {
            TreeNode node = root;
            for (int i = 0; i < ticker.length(); ++i) {
                HashMap<Character, TreeNode> children = node.children;
                char ch = ticker.charAt(i);
                children.putIfAbsent(ch, new TreeNode());

                if (i == ticker.length() - 1) {
                    children.get(ch).isTicker = true;
                    children.get(ch).marketCap = mCap;
                }
                node.freq.put(ticker, mCap);
                node = node.children.get(ch);
            }
        }

        public TreeNode searchNode(@NotNull String input) {
            TreeNode curr = root;
            int i = 0;

            // Search through tree for input String
            while (i < input.length()) {
                try {
                    curr = curr.children.get(input.charAt(i));
                }
                catch (Exception NullPointerException){
                    return new TreeNode();
                }
                ++i;
            }
            return curr;
        }

        // Returns a maximum of N (0 <= N <= 10) tickers using market cap based autocomplete
        public List<String> findTopN(String input, int n) {
            List<String> result = new ArrayList<>();

            TreeNode node = searchNode(input);

            PriorityQueue<Tuple> pqueue = new PriorityQueue<Tuple>(new Comparer());

            for (Map.Entry<String, Long> entry : node.freq.entrySet()) {
                pqueue.offer(new Tuple(entry.getKey(), entry.getValue()));
                if (pqueue.size() > n) {
                    pqueue.poll();
                }
            }

            while (!pqueue.isEmpty()) {
                result.add(0, pqueue.poll().ticker);
            }

            if (node.isTicker) {
                result.add(0, "Matching ticker: " + input);
            }

            return result;
        }
    }
}
