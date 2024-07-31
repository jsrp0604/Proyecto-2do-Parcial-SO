public class Bloque {
    private int numItem;
    private boolean enPedido;

    public Bloque(int numItem) {
        this.numItem = numItem;
    }

    public int getNumItem() {
        return numItem;
    }

    public void setNumItem(int numItem) {
        this.numItem = numItem;
    }

    public boolean isEnPedido() {
        return enPedido;
    }

    public void setEnPedido(boolean enPedido) {
        this.enPedido = enPedido;
    }

    @Override
    public String toString() {
        String s = String.format("%02d", numItem);
        return s;
    }
}
