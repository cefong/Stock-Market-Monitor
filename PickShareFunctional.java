import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PickShareFunctional {
    // findHighPriced function which determines which share has the highest price under 500$ in a functional style
    public static Optional<ShareInfo> findHighPriced(Stream<String> symbols){
        // shares list
        List<ShareInfo> shares = new ArrayList<>();

        //populating shares list with ShareInfo objects
        symbols.forEach(x -> shares.add(ShareUtil.getPrice(x)));

        // printing the list of ShareInfo values: symbol and price
        shares.stream().forEach(System.out::println);

        // First filtering the list to find the Shares with prices below 500 and then finding the max price in the list
        Optional<ShareInfo> highestPrice =  shares.stream().filter(x -> ShareUtil.isPriceLessThan(500).test(x)).max((x, y) -> x.price.compareTo(y.price));

        System.out.println("Highest price: " + highestPrice);
        return highestPrice;
        
    }
    public static void main(String[] args) {
        System.out.println(findHighPriced(Shares.symbols.stream()).toString());
    }

}
