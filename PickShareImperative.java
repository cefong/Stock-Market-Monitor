import java.util.function.Predicate;

// i have no idea how imperative code works in java. I'm guessing this probably just goes in main method
public class PickShareImperative {
    public static void main(String[] args) {
        final Predicate<ShareInfo> isPriceLessThan500 = ShareUtil.isPriceLessThan(500);

        // i just defined this here so no undefined errors, no idea how we're supposed to actually initialize it yet
        ShareInfo highPriced = ShareUtil.getPrice("AAPL");

        // go through all the symbols listed in Shares.java and get highest under 500
        for(String symbol : Shares.symbols) {
            ShareInfo shareInfo = ShareUtil.getPrice(symbol);
    
            if(isPriceLessThan500.test(shareInfo)) {
                highPriced = ShareUtil.pickHigh(highPriced, shareInfo);
            }
        }    
        System.out.println("High priced under $500 is " + highPriced);
    }
}
