import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;


public class Sell extends JPanel {
    private Portfolio portfolio;
    private JTable portfolioTable;

    public Sell(Portfolio portfolio, JTable portfolioTable) {
        this.portfolio = portfolio;
        this.portfolioTable = portfolioTable;

        JLabel symbolLabel = new JLabel("Symbol:");
        JTextField symbolTextField = new JTextField(10);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityTextField = new JTextField(10);
        JButton sellButton = new JButton("Sell");

        // Add action listener to sell button
        sellButton.addActionListener(e -> {
            String symbol = symbolTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());
            if (portfolio.contains(symbol)) {
                double price = getStockPrice(symbol);
                double totalSale = price * quantity;
                // Update portfolio
                portfolio.sell(symbol, quantity, price);
                // Update portfolio table
                DefaultTableModel model = (DefaultTableModel) portfolioTable.getModel();
                model.setDataVector(portfolio.getData(), new String[]{"Symbol", "Quantity", "Price"});
            }
         else {
            JOptionPane.showMessageDialog(null, "You do not own any shares of " + symbol + ".");
        }
        });

        add(symbolLabel);
        add(symbolTextField);
        add(quantityLabel);
        add(quantityTextField);
        add(sellButton);
    }

    private double getStockPrice(String symbol) {
        StockSearch stockSearch = new StockSearch("D45VQXUV5P1KUVP5");
        JSONObject stockInfo = stockSearch.getStockInfo(symbol);
        if (stockInfo != null) {
            return stockInfo.getDouble("price");
        } else {
            // Handle error
            return 0;
        }
    }
}