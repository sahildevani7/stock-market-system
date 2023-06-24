import static org.junit.Assert.*;
import org.junit.Test;
import javax.swing.JTable;

public class SellTest {

@Test
    public void testSell() {
  
        JTable portfolioTable = new JTable();
        Portfolio portfolio = new Portfolio(portfolioTable);
        portfolio.add("AAPL", 10, 130.0);        
        portfolio.sell("AAPL", 6, 130.0);                
        Object[][] data = portfolio.getData();
        assertEquals(1, data.length);
        assertEquals("AAPL", data[0][0]);
        assertEquals(4, data[0][1]);
        assertEquals(130.0, data[0][2]);
    }
@Test
public void testMultipleSell() {

    JTable portfolioTable = new JTable();
    Portfolio portfolio = new Portfolio(portfolioTable);
    portfolio.add("AAPL", 10, 130.0);   
    portfolio.add("META", 15, 180.0);  
    portfolio.add("TSLA", 17, 230.0);  
    portfolio.sell("AAPL", 5, 130.0);  
    portfolio.sell("TSLA", 2, 230.0);
    portfolio.sell("TSLA", 2, 230.0);
    Object[][] data = portfolio.getData();
    assertEquals(3, data.length);
    assertEquals("META", data[0][0]);
    assertEquals(15, data[0][1]);
    assertEquals(180.0, data[0][2]);
    assertEquals("AAPL", data[1][0]);
    assertEquals(5, data[1][1]);
    assertEquals(130.0, data[1][2]);
    assertEquals("TSLA", data[2][0]);
    assertEquals(13, data[2][1]);
    assertEquals(230.0, data[2][2]);
}
}