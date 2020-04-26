abstract class Algos {
    // Attributes
    String name;

    // Constructor
    public Algos(){
        name = "";
    }

    // Methods
    abstract void ToString();

    public String Get_name() {
        return "I am " + this.name + " algorithm";
    }
}
