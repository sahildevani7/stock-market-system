import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.json.JSONObject;

public class Watchlist extends JPanel {
    private DefaultListModel<String> watchlistModel = new DefaultListModel<>();

    public Watchlist() {
        // Set layout for the panel
        setLayout(new BorderLayout());

        // Create watchlist label and scroll pane
        JLabel watchlistLabel = new JLabel("Watchlist");
        JScrollPane watchlistScrollPane = new JScrollPane();

        // Create watchlist JList and add mouse listener
        JList<String> watchlist = new JList<>(watchlistModel);
        watchlistScrollPane.setViewportView(watchlist);
        watchlist.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                // Check if item is double-clicked
                if (evt.getClickCount() == 2) { 
                    String selectedSymbol = watchlist.getSelectedValue();
                    StockSearch stockSearch = new StockSearch(selectedSymbol);
                    JSONObject stockInfo = stockSearch.getStockInfo(selectedSymbol);
                    if (stockInfo != null) {
                        double price = stockInfo.getDouble("price");
                        JOptionPane.showMessageDialog(Watchlist.this, selectedSymbol + " current price: $" + price);
                    } else {
                        JOptionPane.showMessageDialog(Watchlist.this, "Could not retrieve stock information for " + selectedSymbol + ".");
                    }
                }
            }
        });

        // Create buttons and action listeners
        JButton addToWatchlistButton = new JButton("Add to Watchlist");
        addToWatchlistButton.addActionListener(e1 -> {
            String symbolToAdd = JOptionPane.showInputDialog(Watchlist.this, "Enter a symbol to add to the watchlist:");
            // Check if symbol is already in watchlist
            if (!watchlistModel.contains(symbolToAdd)) {
                // Add symbol to watchlist
                watchlistModel.addElement(symbolToAdd);
                StockSearch stockSearch = new StockSearch(symbolToAdd);
                JSONObject stockInfo = stockSearch.getStockInfo(symbolToAdd);
                if (stockInfo != null) {
                    double price = stockInfo.getDouble("price");
                    JOptionPane.showMessageDialog(Watchlist.this, symbolToAdd + " added to watchlist at current price: $" + price);
                } else {
                    JOptionPane.showMessageDialog(Watchlist.this, "Could not retrieve stock information for " + symbolToAdd + ".");
                }
            } else {
                JOptionPane.showMessageDialog(Watchlist.this, "Symbol is already in watchlist.");
            }
        });

        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        removeFromWatchlistButton.addActionListener(e1 -> {
            int selectedIndex = watchlist.getSelectedIndex();
            if (selectedIndex != -1) { // Check if item is selected
                String selectedSymbol = watchlist.getSelectedValue();
                int confirmed = JOptionPane.showConfirmDialog(Watchlist.this, "Are you sure you want to remove " + selectedSymbol + " from the watchlist?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    watchlistModel.remove(selectedIndex);
                }
            } else {
                JOptionPane.showMessageDialog(Watchlist.this, "Please select an item to remove.");
            }
        });

        // Add components to the panel
        add(watchlistLabel, BorderLayout.NORTH);
        add(watchlistScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addToWatchlistButton);
        buttonPanel.add(removeFromWatchlistButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Watchlist());
        frame.setVisible(true);
    }
}