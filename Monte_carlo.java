import java.text.DecimalFormat;
import java.util.Random;

public class Monte_carlo extends Algos {

    // Attributes
    private double S;
    private double K;
    private double R;
    private double V;
    private double T;
    private int trials;
    private String type;

    // Constructors
    public Monte_carlo(){
        super();
        S = 0;
        K = 0;
        R = 0;
        V = 0;
        T = 0;
        trials = 100000;
        type = "European";
        name = "MonteCarlo";
    }

    public Monte_carlo(double _S, double _K, double _R, double _V, double _T, int _trials, double _type){
        super();
        S = _S;
        K = _K;
        R = _R;
        V = _V;
        T = _T;
        trials = _trials;
        name = "MonteCarlo";
        if(_type == 1){
            type = "European";
        }else{
            type = "American";
        }
    }

    // Accessors
    public double getK() {
        return K;
    }

    public double getS() {
        return S;
    }

    public double getR() {
        return R;
    }

    public double getT() {
        return T;
    }

    public double getV() {
        return V;
    }

    //Mutators
    public void setK(double k) {
        K = k;
    }

    public void setR(double r) {
        R = r;
    }

    public void setS(double s) {
        S = s;
    }

    public void setT(double t) {
        T = t;
    }

    public void setV(double v) {
        V = v;
    }

    // Methods
    public String Call_Pricing(){
        DecimalFormat df = new DecimalFormat("#.0000");
        Random rand = new Random();
        int n = this.trials;
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double eps = rand.nextDouble();
            double price = S * Math.exp(R * T - 0.5 * Math.pow(V, 2) * T + V * eps * Math.sqrt(T));
            double value = Math.max(price - K, 0);
            sum += value;
        }
        double mean = sum / n;

        return df.format(Math.exp(-R*T) * mean);
    }

    public String Put_Pricing(){
        DecimalFormat df = new DecimalFormat("#.0000");
        Random rand = new Random();
        int n = this.trials;
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double eps = rand.nextDouble();
            double price = S * Math.exp(R * T - 0.5 * Math.pow(V, 2) * T + V * eps * Math.sqrt(T));
            double value = Math.max(K - price, 0);
            sum += value;
        }
        double mean = sum / n;

        return df.format(Math.exp(-R*T) * mean);
    }

    public String Antithetic_Call_Pricing(){
        DecimalFormat df = new DecimalFormat("#.0000");
        Random rand = new Random();
        int n = this.trials;
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double eps = rand.nextDouble();
            double price1 = S * Math.exp(R * T - 0.5 * Math.pow(V, 2) * T + V * eps * Math.sqrt(T));
            double value1 = Math.max(price1 - K, 0);
            eps = -eps;
            double price2 = S * Math.exp(R * T - 0.5 * Math.pow(V, 2) * T + V * eps * Math.sqrt(T));
            double value2 = Math.max(price2 - K, 0);
            double value = (value1+value2)/2;
            sum += value;
        }
        double mean = sum / n;

        return df.format(Math.exp(-R*T) * mean);
    }

    public String Antithetic_Put_Pricing(){
        DecimalFormat df = new DecimalFormat("#.0000");
        Random rand = new Random();
        int n = this.trials;
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double eps = rand.nextDouble();
            double price1 = S * Math.exp(R * T - 0.5 * Math.pow(V, 2) * T + V * eps * Math.sqrt(T));
            double value1 = Math.max(K - price1, 0);
            eps = -eps;
            double price2 = S * Math.exp(R * T - 0.5 * Math.pow(V, 2) * T + V * eps * Math.sqrt(T));
            double value2 = Math.max(K - price2, 0);
            double value = (value1+value2)/2;
            sum += value;
        }
        double mean = sum / n;

        return df.format(Math.exp(-R*T) * mean);
    }

    public void ToString(){
        System.out.println(this.name + " call price is: " + this.Call_Pricing());
        //System.out.println(this.name + " Antithetic call price is: " + this.Antithetic_Call_Pricing());
        System.out.println(this.name + " put price is: " + this.Put_Pricing());
        //System.out.println(this.name + " Antithetic put price is: " + this.Antithetic_Put_Pricing());
    }

    public void Error(){
        //X = std sample payoffs / Math.sqrt
    }

}
