import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.util.Random;

/**
 * graficoAlmacen
 */
public class graficoAlmacen {
    
    public static Bloque[][] crearMatriz(int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];
        Bloque[][] matrizBloques = new Bloque[filas][columnas];
        
        int x = 6;  // Numero del primer item (en orden de creacion de matriz)
        int nSum = 6;   // Numero que se irá sumando 

        for (int i = 0; i < filas; i++) {
            if (i >= 2 && i <= 7)   {
                matriz[i] = new int[]{x, 0, 0, x + nSum, x + nSum * 2, 0, 0, x + nSum * 3, x + nSum * 4, 0, 0, x + nSum * 5, 
                                        x + nSum * 6, 0, 0, x + nSum * 7, x + nSum * 8, 0, 0, x + nSum * 9};  
                x = x - 1;
            }

            for (int j = 0; j < columnas; j++)  
                matrizBloques[i][j] = new Bloque(matriz[i][j]);
        }

        // Posicion de entrada y salilda al supermercado
        matrizBloques[9][1].setNumItem(-1);
        matrizBloques[9][2].setNumItem(-1);

        return matrizBloques;

    }
    
    private void initializeWindow(Bloque[][] matriz) {
        JFrame mainFrame = new JFrame("Pedidos Almacen");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1920, 1080);
        mainFrame.setLocationRelativeTo(null);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel labelPedidos = new JLabel("Num. de Pedidos:");
        JButton btnGenerar = new JButton("Generar");
        JButton btnEjecutar = new JButton("Ejecutar");
        JButton btnParar = new JButton("Parar");
        JButton btnFinalizar = new JButton("Finalizar");

        topPanel.add(labelPedidos);
        topPanel.add(btnGenerar);
        topPanel.add(btnEjecutar);
        topPanel.add(btnParar);
        topPanel.add(btnFinalizar);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(matriz.length, matriz[0].length));
        for (int row = 0; row < matriz.length; row++) {
            for (int col = 0; col < matriz[0].length; col++) {
                JLabel label = makeLabel(matriz[row][col]);
                centerPanel.add(label);
            }
        }
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private JLabel makeLabel(Bloque c) {
        JLabel label= new JLabel();
        // label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(50, 50));
        switch(c.getNumItem()) {
            case 0:
                label.setBackground(Color.WHITE);
            break;
            default:
                label.setBackground(Color.BLUE);
                break;

        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return label;
    }

    public static Pedido[] generarPedidos(int numPedidos) {
        Random rand = new Random();
        Pedido[] listaPedidos = new Pedido[numPedidos];
        
        int minItems = 10, maxItems = 15;

        for (int i = 0; i < numPedidos; i++) {
            int numItems = rand.nextInt(maxItems - minItems + 1) + minItems;
            Pedido pedido = new Pedido(numItems, i + 1);
            listaPedidos[i] = pedido;
            pedido.generarListaItems();
        }   
        
        return listaPedidos;
    }

    public static void main(String[] args) throws InterruptedException {
        // Definicion tamaño supermercado
        int filas = 10, columnas = 20;
        Bloque[][] matriz = crearMatriz(filas, columnas);
        graficoAlmacen almacen = new graficoAlmacen();
        almacen.initializeWindow(matriz);

        Pedido[] listaPedidos = generarPedidos(5);

        for (Pedido pedido : listaPedidos) {
            try {
                String nombreArchivo = String.format("Pedido #%d.txt", pedido.getNumPedido());
                File archivo = new File(nombreArchivo);
                if (archivo.createNewFile()) {
                    FileWriter writer = new FileWriter(nombreArchivo);
                    BufferedWriter buffWriter = new BufferedWriter(writer);
                    
                    for (int item : pedido.getListaItems()) {
                        buffWriter.write("Item #" + item + "\n");
                    }                    
                    System.out.println("Archivo creado: " + archivo.getName());

                    buffWriter.close();
                    writer.close();
                } else {
                    System.out.println("El archivo ya existe");
                }

            } catch (IOException e) {
            System.out.println("Error en la creacion del archivo");
            e.printStackTrace();
            }

        }


    }
}