import java.util.Random;
import java.io.File;

public class Pedido {
    public int numItems, numPedido;
    public int[] listaItems;
    
    public Pedido(int numItems, int numPedido) {
        this.numItems = numItems;
        this.numPedido = numPedido;
    }

    public int[] generarListaItems()   {
        Random rand = new Random();
        int randMax = 59; // 59 porque se sumará uno en la generacion al azar (para evitar un item #0)
        int[] lista = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            int item = rand.nextInt(randMax + 1);
            lista[i] = item;
        }

        this.listaItems = lista;

        return lista;
    }

    public void generarArchivo()    {
        // generar archivo
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

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public void setListaItems(int[] listaItems) {
        this.listaItems = listaItems;
    }
}
