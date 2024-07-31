import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(GraficoAlmacen::new);
    }
}