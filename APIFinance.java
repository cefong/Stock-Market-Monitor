import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

//"7NN5OJFNCBGUU3DE", "EP2AI9I74IB43WMG", "JJ5RJ5TQVK225JA2", "4XPMZ1EC4FBSIQ8Q"

// for using Alpha Vantage API
public class APIFinance {
    private static final String BASE_URL = "https://www.alphavantage.co/query?"; // 5 API requests per minute, 500 requests per day
    private final static String[] apiKey = {"7NN5OJFNCBGUU3DE", "EP2AI9I74IB43WMG", "JJ5RJ5TQVK225JA2", "4XPMZ1EC4FBSIQ8Q"}; // switch API key that we are using so we don't max out requests
    private static int requests = 0;
    
    // returns the price of given stock
    public static BigDecimal getPrice(final String symbol) {
        BigDecimal price = new BigDecimal(0);
        while(true) {
            try {
                String key = apiKey[requests%4];
                // connect to the API
                URL url = new URL(BASE_URL + "function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + key);
                URLConnection connection = url.openConnection();
    
                // read in stock information using a stream
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream(), "UTF-8");
                
                // read the stream with a buffered reader
                BufferedReader bufferedReader = new BufferedReader(inputStream);
    
                // gets the price from the stock information given
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("price")) {
                        price = new BigDecimal(line.split("\"")[3].trim());
                    }
                }
                // close out the reader
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("failure sending request");
            }
    
            // note that we have sent one request
            requests++;
    
            if (price.doubleValue() == 0.0) {
                // we need to time out and try again
                // timeout if we have reached 5 requests in one minute
                if (requests%5 == 0 && requests != 0) {
                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (InterruptedException ex) {
                        System.out.println("problem with sleep");
                    }
                }
            } else {
                break;
            }
        }
        return price;
    }
}
