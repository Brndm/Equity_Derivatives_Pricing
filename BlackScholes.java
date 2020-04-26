import java.text.DecimalFormat;

public class BlackScholes extends Algos {

    // Attributes
    private double S;
    private double K;
    private double R;
    private double V;
    private double T;
    private String type;

    // Constructors
    public BlackScholes(){
        super();
        S = 0;
        K = 0;
        R = 0;
        V = 0;
        T = 0;
        type = "European";
        name = "B&S";
    }

    public BlackScholes(double _S, double _K, double _R, double _V, double _T, double _type){
        super(); // call base class constructor
        S = _S;
        K = _K;
        R = _R;
        V = _V;
        T = _T;
        name = "B&S";
        if(_type == 1){
            type = "European";
        }else{
            type = "European";
            System.out.println("American options are not supported. Pricing performed as a european option");
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

    public String getType() { return type;}

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

    public void setType(String _type) {
        type = _type;
    }

    // Methods
    public void ToString(){
        System.out.println(this.name + " call price is: " + this.Call_Pricing());
        System.out.println(this.name + " put price is: " + this.Put_Pricing());
    }

    public String Call_Pricing(){
        DecimalFormat df = new DecimalFormat("#.0000");
        double d1 = (Math.log(S/K) + (R + Math.pow(V, 2) / 2) * T) / (V * Math.sqrt(T));
        double d2 = d1 - (V * Math.sqrt(T));

        return df.format(S * Stat_lib.cdf(d1) - K * Math.exp(-R * T) * Stat_lib.cdf(d2));
    }

    public String Put_Pricing(){
        DecimalFormat df = new DecimalFormat("#.0000");
        double d1 = (Math.log(S/K) + (R + Math.pow(V, 2) / 2) * T) / (V * Math.sqrt(T));
        double d2 = d1 - (V * Math.sqrt(T));

        return df.format(K * Math.exp(-R * T) * Stat_lib.cdf(-d2) - S * Stat_lib.cdf(-d1));
    }

}