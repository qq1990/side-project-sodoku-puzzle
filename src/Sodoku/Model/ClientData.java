package Sodoku.Model;

public class ClientData {
    String data;

    /**
     * construct hopper client data
     * @param data string in
     */
    public ClientData(String data){
        this.data = data;
    }

    /**
     * get this data
     * @return string this data
     */
    public String getData(){
        return this.data;
    }

    /**
     * override toString
     * @return this data
     */
    @Override
    public String toString() {
        return data;
    }
}
