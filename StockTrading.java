import java.util.*; 
import java.text.DecimalFormat;
class Stock { 
	String symbol; 
	double price;
	public Stock(String symbol, double price) {
    	this.symbol = symbol;
   	 this.price = price;
	}
}
class Portfolio { 
	Map<String, Integer> holdings; double cash;
	public Portfolio(double initialCash)
	{
	    this.holdings = new HashMap<>();
            this.cash = initialCash;
	}
	public void buyStock(Stock stock, int quantity) {
    	double cost = stock.price * quantity;
    	if (cost > cash) {
        	System.out.println("Insufficient funds to complete purchase.");
        	return;
    	}
    	cash -= cost;
    	holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + quantity);
   	 System.out.println("Bought " + quantity + " shares of " + stock.symbol);
	}
	public void sellStock(Stock stock, int quantity) {
    	if (!holdings.containsKey(stock.symbol) || holdings.get(stock.symbol) < quantity) {
        System.out.println("Insufficient shares to complete sale.");
        return;
    	}
    	cash += stock.price * quantity;
    	holdings.put(stock.symbol, holdings.get(stock.symbol) - quantity);
    	if (holdings.get(stock.symbol) == 0) {
        holdings.remove(stock.symbol);
    	}
    	System.out.println("Sold " + quantity + " shares of " + stock.symbol);
	}
	public double getTotalValue(Map<String, Stock> market) {
    	double totalValue = cash;
    	for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
        	totalValue += market.get(entry.getKey()).price * entry.getValue();
    	}
    	return totalValue;
	}
	public void displayPortfolio(Map<String, Stock> market) { 
	System.out.println("\nPortfolio:"); 
	System.out.println("Cash: ₹" + new DecimalFormat("#,##0.00").format(cash)); 
	for (Map.Entry<String, Integer> entry : holdings.entrySet()) { 
	String symbol = entry.getKey(); 
	int quantity = entry.getValue(); 
	double currentPrice = market.get(symbol).price; 
	double value = quantity * currentPrice; 
	System.out.println(symbol + ": " + quantity + " shares, Current Price: ₹" 
	+ new DecimalFormat("#,##0.00").format(currentPrice) + ", 
	Total Value: ₹" + new DecimalFormat("#,##0.00").format(value)); 
	} 
	System.out.println("Total Portfolio Value: ₹" + new DecimalFormat("#,##0.00").format(getTotalValue(market))); 
	} 
	}
	class StockMarket { Map<String, Stock> stocks; Random random;
	public StockMarket() {
    	this.stocks = new HashMap<>();
    	this.random = new Random();
   	 initializeStocks();
	}
	private void initializeStocks() {
   	stocks.put("RELIANCE", new Stock("RELIANCE", 2500.0));
   	 stocks.put("TCS", new Stock("TCS", 3500.0));
   	 stocks.put("HDFC", new Stock("HDFC", 1500.0));
    	stocks.put("INFY", new Stock("INFY", 1700.0));
	}
	public void updatePrices() {
    	for (Stock stock : stocks.values()) {
        double change = (random.nextDouble() - 0.5) * 0.1; 
        stock.price *= (1 + change);
    	}
	}
	public void displayMarketData() {
   	System.out.println("\nCurrent Market Data:");
    	for (Stock stock : stocks.values()) {
        System.out.println(stock.symbol + ": ₹" + new DecimalFormat("#,##0.00").format(stock.price));
   	 }
	}
}
public class StockTrading { 
	public static void main(String[] args) { 
	StockMarket market = new StockMarket(); 
	Portfolio portfolio = new Portfolio(100000.0); 
	Scanner scanner = new Scanner(System.in);
	while (true) {
        market.updatePrices();
        market.displayMarketData();
        portfolio.displayPortfolio(market.stocks);

        System.out.println("\nEnter command (buy/sell/quit):");
        String command = scanner.nextLine().toLowerCase();

        if (command.equals("quit")) {
            break;
        } 
	else if (command.equals("buy") || command.equals("sell")) {
            System.out.println("Enter stock symbol:");
            String symbol = scanner.nextLine().toUpperCase();
            if (!market.stocks.containsKey(symbol)) {
                System.out.println("Invalid stock symbol.");
                continue;
            }
		System.out.println("Enter quantity:");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (command.equals("buy")) {
                portfolio.buyStock(market.stocks.get(symbol), quantity);
            } 
		else {
                portfolio.sellStock(market.stocks.get(symbol), quantity);
            }
        } 
		else {
            System.out.println("Invalid command.");
        }
    }

    scanner.close();
    System.out.println("Thank you for using the Stock Trading Simulation!");
}
}