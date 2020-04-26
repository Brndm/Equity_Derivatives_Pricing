import java.text.DecimalFormat;

public class Binomial extends Algos {
    // Attributs
    private double S;
    private double K;
    private double R;
    private double V;
    private double T;
    private double p;
    private double u;
    private double d;
    private double N;
    private double[][] tree;
    private String type;

    // Constructor
    public Binomial(double _S, double _K, double _R, double _V, double _T, double _N, int _type){
        super();
        S = _S;
        K = _K;
        R = _R;
        V = _V;
        T = _T;
        N = _N;

        if(_type == 1){
            type = "European";
        }else{
            type = "American";
        }

        // Hull formulas
        double delta_T = _T / _N;
        name = "Binomial Tree";
        u = Math.exp(V * Math.sqrt(delta_T));
        try{
            d = 1.0/u;
        }catch(Exception e){
            d = Math.exp(-V * Math.sqrt(delta_T));
        }
        p = (Math.exp(R * delta_T) - d) / (u - d);
        tree = Generate_tree(_N);
    }

    // Accessors
    public double[][] Get_tree(){
        return this.tree;
    }


    // Methods
    public void ToString(){
        System.out.println(this.type + " " + this.name + " call price is: " + this.Call_Pricing());
        System.out.println(this.type + " " + this.name + " put price is: " + this.Put_Pricing());
    }

    public double[][] Generate_tree(double _N){
        int depth = (int)_N + 1;
        double [][] tree = new double[depth][depth];
        int last = tree.length - 1;

        for(int i = 0; i <= last; i++){
            for(int j = 0; j <= i; j++){
                tree[i][j] = S * Math.pow(u,j) * Math.pow(d,i-j);
            }
        }

        return tree;
    }

    public double[][] Eur_Pricing_matrix(int opt_type){
        // fill pricing tree
        double delta_T = T / N;
        double[][] pricing_tree = new double[this.tree.length][this.tree.length];
        int last = this.tree.length-1;

        // Compute at last nodes option price
        for(int i = 0; i < pricing_tree.length ; i++){
            if (opt_type == 1){
                pricing_tree[last][i] = Math.max(tree[last][i] - K, 0);
            }else{
                pricing_tree[last][i] = Math.max(K - tree[last][i], 0);
            }
        }

        for(int i = last-1; i >= 0; i--){
            for(int j = 0; j <= i; j++){
                // Choose Call or Put formula
                pricing_tree[i][j] = (Math.exp(-R * delta_T) * (p * pricing_tree[i + 1][j + 1] + (1 - p) * pricing_tree[i + 1][j]));
            }
        }

        return pricing_tree;
    }

    public double[][] Ame_Pricing_matrix(int opt_type){
        // fill pricing tree
        double delta_T = T / N;
        double[][] pricing_tree = new double[this.tree.length][this.tree.length];
        int last = this.tree.length-1;

        // Compute at last nodes option price
        for(int i = 0; i < pricing_tree.length ; i++){
            if (opt_type == 1){
                pricing_tree[last][i] = Math.max(tree[last][i] - K, 0);
            }else{
                pricing_tree[last][i] = Math.max(K - tree[last][i], 0);
            }
        }

        for(int i = last-1; i >= 0; i--){
            for(int j = 0; j <= i; j++){
                // Choose Call or Put formula
                if (opt_type == 1) {
                    pricing_tree[i][j] = Math.max(Math.exp(-R * delta_T) * (p * pricing_tree[i + 1][j + 1] + (1 - p) * pricing_tree[i + 1][j]), tree[i][j] - K);
                }else{
                    pricing_tree[i][j] = Math.max(Math.exp(-R * delta_T) * (p * pricing_tree[i + 1][j + 1] + (1 - p) * pricing_tree[i + 1][j]), K - tree[i][j]);
                }
            }
        }

        return pricing_tree;
    }

    public String Call_Pricing(){
        double[][] pricemat;
        DecimalFormat df = new DecimalFormat("#.0000");

        if(this.type == "European"){
            pricemat = Eur_Pricing_matrix(1);
        }else{
            pricemat = Ame_Pricing_matrix(1);
        }
        return df.format(pricemat[0][0]);
    }

    public String Put_Pricing(){
        double[][] pricemat;
        DecimalFormat df = new DecimalFormat("#.0000");

        if(this.type == "European"){
            pricemat = Eur_Pricing_matrix(0);
        }else{
            pricemat = Ame_Pricing_matrix(0);
        }
        return df.format(pricemat[0][0]);
    }

}
