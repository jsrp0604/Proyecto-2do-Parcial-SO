import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Pedido {
    private int numItems;
    private int numPedido;
    private int[] listaItems;
    private LinkedList<Integer> listaLinked = new LinkedList<>();
    private int distance;
    private int time;

    public Pedido(int numItems, int numPedido) {
        this.numItems = numItems;
        this.numPedido = numPedido;
    }

    public int[] generarListaItems() {
        Random rand = new Random();
        int randMax = 59; 
        int[] lista = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            int item = rand.nextInt(randMax) + 1;
            lista[i] = item;
        }

        Arrays.sort(lista);
        this.listaItems = lista;

        for (int i : lista) {
            listaLinked.add(i);
        }

        return lista;
    }

    public int getNumItems() {
        return numItems;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public int[] getListaItems() {
        return listaItems;
    }

    public LinkedList<Integer> getListaLinked() {
        return listaLinked;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public void setListaItems(int[] listaItems) {
        this.listaItems = listaItems;
    }

    public void setListaLinked(LinkedList<Integer> listaLinked) {
        this.listaLinked = listaLinked;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
