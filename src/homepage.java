import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import org.json.JSONObject;
import java.awt.*;


public class homepage {
    private JFrame mainWindow;
    private JTabbedPane tabbedPane;
    private StockSearch stocksearch;
    private double total = 0.0;
    private Portfolio portfolio;
  
   
    public homepage(String apiKey) {

        stocksearch = new StockSearch(apiKey);
        Portfolio portfolio = new Portfolio(null);
        

        // Create the login panel
        JPanel loginPanel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        // Create the main window
        mainWindow = new JFrame("Login Tabbed Window");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800, 800);

        // Create the tabbed pane
        tabbedPane = new JTabbedPane();

        // Add the login panel to the tabbed pane as the first tab
        tabbedPane.addTab("Login", null, loginPanel, "Login");

        // Add action listener to login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
        
    
        if (username.equals("admin") && password.equals("password")) {

                JPanel searchPanel = new JPanel();
                JLabel searchSymbolLabel = new JLabel("Symbol:");
                JTextField searchSymbolTextField = new JTextField(10);
                JButton searchButton = new JButton("Search");

                // Add action listener to search button
                searchButton.addActionListener(e2 -> {
                    String symbol = searchSymbolTextField.getText();
                    JSONObject stockInfo = stocksearch.getStockInfo(symbol);
                
                    if (stockInfo == null) {
                        JOptionPane.showMessageDialog(mainWindow, "Invalid ticker symbol.");
                    } else {
                        // Display the stock info in a pop-up message
                        String output = String.format("Symbol: %s\nPrice: %s \nOpen: %s \nHigh: %s \nLow: %s \nVolume: %s \nLatest trading day: %s \nPrevious close: %s \nChange: %s \nChange Percent: %s", 
                        stockInfo.getString("symbol"), stockInfo.getString("price"), 
                        stockInfo.getString("open"), stockInfo.getString("high"),
                        stockInfo.getString("low"), stockInfo.getString("volume"),
                        stockInfo.getString("latest_trading_day"), stockInfo.getString("previous_close"),
                        stockInfo.getString("change"), stockInfo.getString("change_percent"));
                        JOptionPane.showMessageDialog(mainWindow, output);
                    }
                });
                
                // Add the components to the Search panel
                searchPanel.add(searchSymbolLabel);
                searchPanel.add(searchSymbolTextField);
                searchPanel.add(searchButton);

                // Create the Portfolio panel
                JPanel portfolioPanel = new JPanel();
                Object[][] data = portfolio.getData();
                DefaultTableModel model = new DefaultTableModel(data, new String[]{"Symbol", "Quantity", "Price"});
                JTable portfolioTable = new JTable(model);
                JScrollPane portfolioScrollPane = new JScrollPane(portfolioTable);
                portfolioScrollPane.setPreferredSize(new Dimension(400, 250));
                portfolioPanel.add(portfolioScrollPane);
            
                // Create the Buy panel
                JPanel buyPanel = new JPanel();
                JLabel symbolLabel = new JLabel("Symbol:");
                JTextField symbolTextField = new JTextField(10);
                JLabel quantityLabel = new JLabel("Quantity:");
                JTextField quantityTextField = new JTextField(10);
                JButton buyButton = new JButton("Buy");

                // Add action listener to buy button
                buyButton.addActionListener(e1 -> {
                String symbol = symbolTextField.getText();
                int quantity = Integer.parseInt(quantityTextField.getText());
                StockSearch stockSearch = new StockSearch(symbol);
                JSONObject stockInfo = stockSearch.getStockInfo(symbol);
                if (stockInfo != null) {
                    double price = stockInfo.getDouble("price");
                    double totalCost = price * quantity;
                    // Update portfolio
                    portfolio.add(symbol, quantity, totalCost);
                    JOptionPane.showMessageDialog(mainWindow, String.format("%s shares of %s bought for $%.2f", quantity, symbol, totalCost));
                    // Refresh portfolio tab
                    Object[][] newdata = portfolio.getData();
                    DefaultTableModel newmodel = (DefaultTableModel) portfolioTable.getModel();
                    model.setDataVector(newdata, new String[]{"Symbol", "Quantity", "Price"});
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Could not retrieve stock information for " + symbol + ".");
                }
                });

                buyPanel.add(symbolLabel);
                buyPanel.add(symbolTextField);
                buyPanel.add(quantityLabel);
                buyPanel.add(quantityTextField);
                buyPanel.add(buyButton);

                

                
                // Create the Sell panel
                JPanel sellPanel = new Sell(portfolio, portfolioTable);
                


                // Create the Account Information panel
                JPanel accountInfoPanel = new Transaction();        
                
                

                // Create the Watchlist panel
                JPanel watchlistPanel = new Watchlist();
                

                // Add the panels to the tabbed pane and remove the login panel
                tabbedPane.addTab("Account Information", null, accountInfoPanel, "View Account Information");
                tabbedPane.addTab("Portfolio", null, portfolioPanel, "Portfolio");
                tabbedPane.addTab("Search", null, searchPanel, "Search for Stock");
                tabbedPane.addTab("Watchlist", null, watchlistPanel, "Watchlist");
                tabbedPane.addTab("Buy", null, buyPanel, "Buy");
                tabbedPane.addTab("Sell", null, sellPanel, "Sell");
                
                
                tabbedPane.remove(loginPanel);
                              
            } else {
                JOptionPane.showMessageDialog(mainWindow, "Invalid username or password.");
            }
        });

        // Add the tabbed pane to the main window and display it as a pop-up window
        mainWindow.getContentPane().add(tabbedPane);
        // Center the window on the screen
        mainWindow.setLocationRelativeTo(null); 
        mainWindow.setVisible(true);
    }

    

    public static void main(String[] args) {
        String apiKey = "D45VQXUV5P1KUVP5";
        new homepage(apiKey);
    }

    

    
}