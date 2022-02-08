import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

// for using Alpha Vantage API
public class APIFinance {
    private static final String BASE_URL = "https://www.alphavantage.co/query?"; // 5 API requests per minute, 500 requests per day
    private final static String[] apiKey = {"7NN5OJFNCBGUU3DE", "EP2AI9I74IB43WMG", "JJ5RJ5TQVK225JA2"}; // switch API key that we are using so we don't max out requests
    private static int requests = 0;
    
    // returns the price of given stock
    public static BigDecimal getPrice(final String symbol) {
        BigDecimal price = new BigDecimal(0);
        try {
            String key = apiKey[requests%3];
            // connect to the API
            URL url = new URL(BASE_URL + "function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + key);
            URLConnection connection = url.openConnection();
            System.out.println("Request #" + requests + " API key used: " + key + " Symbol requested: " + symbol);

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
                System.out.println(line);
            }
            // close out the reader
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("failure sending request");
        }

        // note that we have sent one request
        requests++;
        return price;
    }
}
