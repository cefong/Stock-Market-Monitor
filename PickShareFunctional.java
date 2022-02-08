import java.util.stream.Stream;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PickShareFunctional {
    // findHighPriced function which determines which share has the highest price under 500$ in a functional style
    public static Optional<ShareInfo> findHighPriced(Stream<String> symbols){   
        // shares list
        List<ShareInfo> shares = new ArrayList<>();
        
        // populating shares list with ShareInfo objects
        symbols.forEach(x -> {
            shares.add(ShareUtil.getPrice(x));
            System.out.println("Fetching " + x + " prices from Alpha Vantage API...");
        });

        // printing the list of ShareInfo values: symbol and price
        // shares.stream().forEach(System.out::println);

        // First filtering the list to find the Shares with prices below 500 and then finding the max price in the list
        Optional<ShareInfo> highestPrice =  shares.stream().filter(x -> {
            try {
                return ShareUtil.isPriceLessThan(500).test(x);
            } catch (Exception e) {
                System.out.println("Exception Thrown: "+ e);
                return false;
            }
            }).max((x, y) -> x.price.compareTo(y.price));

        System.out.println("Highest price: " + highestPrice);
        return highestPrice;
        
    }
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        findHighPriced(Shares.symbols.stream()).toString();
        // findHighPriced(Shares.symbols.parallelStream()).toString();
        long endTime = System.nanoTime();


        // edit duration to remove effect of time delay
        long duration = (endTime - startTime) / 1000000;

        System.out.println("Total execution time is: " + duration + "ms");

        if (duration > 60000) {
            System.out.println("Execution time minus time used to wait out API limits is: " + (duration - 60000) + "ms");
        }
    }

}
