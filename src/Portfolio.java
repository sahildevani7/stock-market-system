import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class Portfolio {
    private Map<String, Integer> quantities;
    private Map<String, Double> prices;
    private JTable portfolioTable;

    // Add a stock to the portfolio
    public Portfolio(JTable portfolioTable) {
        quantities = new HashMap<>();
        prices = new HashMap<>();
        this.portfolioTable = portfolioTable;
    }

    // Get the data for the table
    public void add(String symbol, int quantity, double price) {
        if (quantities.containsKey(symbol)) {
            int currentQuantity = quantities.get(symbol);
            double currentPrice = prices.get(symbol);
            quantities.put(symbol, currentQuantity + quantity);
            prices.put(symbol, (currentPrice * currentQuantity + price * quantity) / (currentQuantity + quantity));
        } else {
            quantities.put(symbol, quantity);
            prices.put(symbol, price);
        }
    }

    public Object[][] getData() {
        Object[][] data = new Object[quantities.size()][3];
        int i = 0;
        for (String symbol : quantities.keySet()) {
            data[i][0] = symbol;
            data[i][1] = quantities.get(symbol);
            data[i][2] = prices.get(symbol);
            i++;
        }
        return data;
    }

    public void sell(String symbol, int quantity, double price) {
        if (quantities.containsKey(symbol)) {
            int currentQuantity = quantities.get(symbol);
            if (quantity > currentQuantity) {
                JOptionPane.showMessageDialog(null, "You do not have enough shares to sell.");
            } else {
                int newQuantity = currentQuantity - quantity;
                if (newQuantity == 0) {
                    quantities.remove(symbol);
                    prices.remove(symbol);
                } else {
                    quantities.put(symbol, newQuantity);
                }
                Object[][] data = getData();
                JTable portfolioTable = new JTable();
                Portfolio portfolio = new Portfolio(portfolioTable);
                // Update portfolio table
                DefaultTableModel model = (DefaultTableModel) portfolioTable.getModel();
                model.setDataVector(data, new String[]{"Symbol", "Quantity", "Price"});
                double totalSale = price * quantity;
                JOptionPane.showMessageDialog(null, String.format("%s shares of %s sold", quantity, symbol));
            }
        } else {
            JOptionPane.showMessageDialog(null, "You do not own any shares of " + symbol + ".");
        }
    }

    public boolean contains(String symbol) {
        return quantities.containsKey(symbol);
    }

    public Portfolio getPortfolio() {
        return this;
    }

    public int getSize() {
        return quantities.size();
    }
}


    