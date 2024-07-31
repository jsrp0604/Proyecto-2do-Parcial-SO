import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class GraficoAlmacen extends JFrame{
    private JPanel panelAlmacen;
    private JTextField numOrdernes;
    private JButton btnGenerar, btnEjecutar, btnParar, btnFinalizar;
    private JLabel distanceLabel, timeLabel;
    private Pedido[] listaPedidos;
    private boolean executing;
    private Bloque[][] matriz;
    private final Object lock = new Object();
    
    public GraficoAlmacen() {
        setTitle("Pedidos Almacen");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeWindow();
    }

    public Bloque[][] crearMatriz(int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];
        Bloque[][] matrizBloques = new Bloque[filas][columnas];
        
        int x = 6;  // Numero del primer item (en orden de creacion de matriz)
        int nSum = 6;   // Numero que se irá sumando 

        for (int i = 0; i < filas; i++) {
            // Se crea una matriz de enteros como referencia para la matriz de bloques
            if (i >= 2 && i <= 7)   {
                matriz[i] = new int[]{x, 0, 0, x + nSum, x + nSum * 2, 0, 0, x + nSum * 3, x + nSum * 4, 0, 0, x + nSum * 5, 
                                        x + nSum * 6, 0, 0, x + nSum * 7, x + nSum * 8, 0, 0, x + nSum * 9};  
                x = x - 1;
            }
            for (int j = 0; j < columnas; j++)  
                matrizBloques[i][j] = new Bloque(matriz[i][j]);
        }
        // Cambiar a 9 la fila cuando termine prueba
        // Posicion de entrada y salilda al supermercado
        matrizBloques[10][1].setNumItem(-1);
        matrizBloques[10][2].setNumItem(-1);
        
        for (Bloque[] fila : matrizBloques) {
            System.out.println(Arrays.toString(fila));
        }
        return matrizBloques;
    }

    public void initializeWindow() {
        // Definicion tamaño matriz del almacen
        matriz = crearMatriz(11, 20); //Cambiar filas a 10 cuando termine prueba

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        JLabel labelPedidos = new JLabel("Num. de Pedidos:");
        numOrdernes = new JTextField(5);
        btnGenerar = new JButton("Generar");
        btnEjecutar = new JButton("Ejecutar");
        btnParar = new JButton("Parar");
        btnFinalizar = new JButton("Finalizar");

        topPanel.add(labelPedidos);
        topPanel.add(numOrdernes);
        topPanel.add(btnGenerar);
        topPanel.add(btnEjecutar);
        topPanel.add(btnParar);
        topPanel.add(btnFinalizar);
        add(topPanel, BorderLayout.NORTH);

        panelAlmacen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarAlmacen(g);
            }
        };
        add(panelAlmacen, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.add(new JLabel("Distancia total:"));
        distanceLabel = new JLabel("0");
        southPanel.add(distanceLabel);
        southPanel.add(new JLabel("Tiempo:"));
        timeLabel = new JLabel("0");
        southPanel.add(timeLabel);
        add(southPanel, BorderLayout.SOUTH);

        btnGenerar.addActionListener(e -> generarPedidos());
        btnEjecutar.addActionListener(e -> executeOrders());
        btnParar.addActionListener(e -> stopExecution());
        btnFinalizar.addActionListener(e -> finishSimulation());

        setVisible(true);
    }

    private void dibujarAlmacen(Graphics g) {
        // Draw warehouse layout based on the provided images
        int width = panelAlmacen.getWidth() / matriz[0].length;
        int height = panelAlmacen.getHeight() / matriz.length;

        for (int row = 0; row < matriz.length; row++) {
            for (int col = 0; col < matriz[0].length; col++) {
                // JLabel label = makeLabel(matriz[row][col]);
                Bloque bloque = matriz[row][col];
                g.setColor(bloque.getNumItem() == 00 ? Color.WHITE : bloque.getNumItem() == 77 ? Color.RED : Color.BLUE);
                g.fillRect(col * width, row * height, width, height);
                g.setColor(Color.BLACK);
                g.drawRect(col * width, row * height, width, height);
            }
        }
    }

    // private JLabel makeLabel(Bloque c) {
    //     JLabel label= new JLabel();
    //     // label.setHorizontalAlignment(JLabel.CENTER);
    //     label.setPreferredSize(new Dimension(50, 50));
    //     switch(c.getNumItem()) {
    //         case 0:
    //             label.setBackground(Color.WHITE);
    //         break;
    //         default:
    //             label.setBackground(Color.BLUE);
    //             break;
    //     }
    //     label.setOpaque(true);
    //     label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    //     return label;
    // }

    public void repaintWithDelay() {
        try {
            Thread.sleep(500); // Delay of 500 milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void generarPedidos() {
        int numOrder = Integer.parseInt(numOrdernes.getText());
        listaPedidos = new Pedido[numOrder];
        Random rand = new Random();

        for (int i = 0; i < numOrder; i++) {
            int numItems = 5 + rand.nextInt(6);// Rango de items permitidos en el carrito
            Pedido pedido = new Pedido(numItems, i + 1); // i + 1 representa el número del pedido
            listaPedidos[i] = pedido;
            pedido.generarListaItems();
        }   
        JOptionPane.showMessageDialog(this, "Pedidos Generados: " + listaPedidos.length);
    }

    private void executeOrders() {
        executing = true;
        Thread orderThread = new Thread(() -> {
            for (Pedido order : listaPedidos) {
                if (!executing) break;
                calculatePath(order);
            }
        });
        orderThread.start();
    }

    private void stopExecution() {
        executing = false;
    }

    private void calculatePath(Pedido order) {
        SShapeAlgorithm sShape = new SShapeAlgorithm();
        sShape.calculate(order, matriz, lock, this);
        // sShape.calculate(order, matriz, lock);
        repaint();
        distanceLabel.setText(String.valueOf(order.getDistance()));
        timeLabel.setText(String.valueOf(order.getTime()));
    }

    private void finishSimulation() {
        Thread resultThread = new Thread(() -> {
            try (FileWriter writer = new FileWriter("resultados.txt")) {
                for (Pedido order : listaPedidos) {
                    writer.write("Pedido " + order.getNumPedido() + "; Distancia: " + order.getDistance() + "; Tiempo: " + order.getTime() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        resultThread.start();
    }

    public void crearArchivos(Pedido[] listaPedidos) {
        for (Pedido pedido : listaPedidos) {
            try {
                String nombreArchivo = String.format("Pedido #%d.txt", pedido.getNumPedido());
                File archivo = new File(nombreArchivo);
                if (archivo.createNewFile()) {
                    // Se escribe en cada archivo creado con un Buffered Writer
                    FileWriter writer = new FileWriter(nombreArchivo);
                    BufferedWriter buffWriter = new BufferedWriter(writer);
                    
                    // Se accede a la listaItems de cada pedido para generar el archivo
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
