import java.util.*;
import java.util.Map.Entry;

public class Main {

    public static void main(String[] args) {
        // Fetch data through API

        // Parameters + menu
        double Under = 9.0;
        double Strike = 10.0;
        double Rfr = 0.06;
        double Vol = 0.3;
        double Time = 3.0;
        double Intervals = 4.0; // intervals
        int trials = 100000; //  MC
        int ex_typ = 0; // Call: 1 / Put: else

        // Storage map
        Map<Integer, Algos> ptf = new HashMap<>();
        ptf.put(1, new BlackScholes(Under, Strike, Rfr, Vol, Time, ex_typ));
        ptf.put(2, new Monte_carlo(Under, Strike, Rfr, Vol, Time, trials, ex_typ));
        ptf.put(3, new Binomial(Under, Strike, Rfr, Vol, Time, Intervals, ex_typ));

        // Iterator to display content
        Set<Entry<Integer, Algos>> setPtf = ptf.entrySet();
        Iterator<Entry<Integer, Algos>> it = setPtf.iterator();
        while(it.hasNext()) {
            it.next().getValue().ToString();
        }
    }
}
