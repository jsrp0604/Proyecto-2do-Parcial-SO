import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Pedido {
    public int numItems, numPedido;
    public int[] listaItems;
    public LinkedList<Integer> listaLinked = new LinkedList<>();
    
    public Pedido(int numItems, int numPedido) {
        this.numItems = numItems;
        this.numPedido = numPedido;
    }

    public int[] generarListaItems()   {
        Random rand = new Random();
        int randMax = 59; // 59 porque se sumará uno en la generacion al azar (para evitar un item #0)
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
}
