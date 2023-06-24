import static org.junit.Assert.*;
import org.junit.Test;
import javax.swing.JTable;

public class PortfolioTest {
    
    @Test
    public void testAdd() {
    
        JTable portfolioTable = new JTable();
        Portfolio portfolio = new Portfolio(portfolioTable);
        portfolio.add("AAPL", 10, 130.0);    
        Object[][] data = portfolio.getData();
        assertEquals(1, data.length);
        assertEquals("AAPL", data[0][0]);
        assertEquals(10, data[0][1]);
        assertEquals(130.0, data[0][2]);
    }

    @Test
    public void testMultipleStocks() {
        Portfolio portfolio = new Portfolio(new JTable());
        portfolio.add("AAPL", 10, 100.0);
        portfolio.add("GOOGL", 7, 250.0);
        Object[][] data = portfolio.getData();
        assertEquals(2, data.length); 
        assertEquals("GOOGL", data[0][0]);  
        assertEquals(7, data[0][1]);  
        assertEquals(250.0, data[0][2]); 
        assertEquals("AAPL", data[1][0]);  
        assertEquals(10, data[1][1]);  
        assertEquals(100.0, data[1][2]); 
    }    
    @Test
    public void testContains() {
        Portfolio portfolio = new Portfolio(new JTable());
        portfolio.add("TSLA", 100, 220.0);
        portfolio.add("GOOGL", 5, 150.0);
        assertTrue(portfolio.contains("TSLA"));
        assertTrue(portfolio.contains("GOOGL"));
        assertFalse(portfolio.contains("MSFT"));
        assertFalse(portfolio.contains("AMD"));
    }

}