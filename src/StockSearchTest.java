import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class StockSearchTest {
    private final String apiKey = "D45VQXUV5P1KUVP5";
    private final StockSearch stockSearch = new StockSearch(apiKey);

    @Test
    public void testGetStockInfoValidSymbol() {
        JSONObject result = stockSearch.getStockInfo("AAPL");
        assertNotNull(result);
    }

    @Test
    public void testGetStockInfoInvalidSymbol() {
        JSONObject result = stockSearch.getStockInfo("Invalid_Symbol");
        assertNull(result);
    }

    @Test
    public void testGetStockInfoAllFields() {
        JSONObject result = stockSearch.getStockInfo("AAPL");
        assertNotNull(result.optString("symbol"));
        assertNotNull(result.optString("open"));
        assertNotNull(result.optString("high"));
        assertNotNull(result.optString("low"));
        assertNotNull(result.optString("price"));
        assertNotNull(result.optString("volume"));
        assertNotNull(result.optString("latest_trading_day"));
        assertNotNull(result.optString("previous_close"));
        assertNotNull(result.optString("change"));
        assertNotNull(result.optString("change_percent"));
    }
}