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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * graficoAlmacen
 */
public class graficoAlmacen {
    
    // Revisar linea 42
    public static Bloque[][] crearMatriz(int filas, int columnas) {

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

        return matrizBloques;

    }
    
    public static void imprimirMatriz(Bloque[][] matrizBloques)    {
        for (Bloque[] fila : matrizBloques) {
            System.out.println(Arrays.toString(fila));
        }
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
        
        // Rango de items permitidos en el carrito
        int minItems = 5, maxItems = 10;

        for (int i = 0; i < numPedidos; i++) {
            int numItems = rand.nextInt(maxItems - minItems + 1) + minItems;
            Pedido pedido = new Pedido(numItems, i + 1); // i + 1 representa el número del pedido
            listaPedidos[i] = pedido;
            pedido.generarListaItems();
        }   
        
        return listaPedidos;
    }

    public static void crearArchivos(Pedido[] listaPedidos) {
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

    public static void sShape(Pedido p, Bloque[][] matriz) {
        int filaIzq = 1, filaDer = 2;
        int limite = 12;
        int ultItem;
        boolean arribaPasillo = false;
        
        LinkedList<Integer> listaItems = p.getListaLinked();

        // se recorre lo inicial (la entrada al almacen)
        matriz[8][1].setNumItem(77);
        matriz[9][1].setNumItem(77);
        
        while (listaItems.size() > 0)  {
            ultItem = listaItems.pop();
            
            if (ultItem <= limite)   {
                if (listaItems.size() == 0)  //previene un null pointer exception
                    listaItems.add(ultItem);
             
                if (listaItems.peek() > limite) {
                    // esta en pasillo
                    // no hay mas items en pasillo 
                    
                    // dibujo raya hacia arriba/abajo 
                    for (int i = 1; i <= 8; i++) {
                        matriz[i][filaIzq].setNumItem(77);
                    }

                    // se movio arriba/abajo
                    arribaPasillo = !arribaPasillo;
                }
                
                else if (listaItems.getLast() <= limite)    {
                    // todos los items restantes se encuentran dentro del pasillo 
                    // mover hasta ult item y regresar
                    
                    // arribaPasillo => linea continua hacia abajo y regresa
                    // !arribaPasillo => linea gira en u y regresa 

                    if (arribaPasillo)  {
                        for (int i = 1; i <= 9; i++) {
                            matriz[i][filaIzq].setNumItem(77);
                        }
                        listaItems.clear();
                    } 
                    
                    else  {
                        boolean flag = true;
                        int i = 2;  //se comienza en la fila más arriba del pasillo
                        listaItems.addFirst(ultItem); //se añade de vuelta el ult item
                        
                        // se buscara el item más alejado (más arriba en pasillo)
                        // se dibujara desde la posicion del item más alejado
                        
                        while (flag) {
                            if (listaItems.contains(matriz[i][filaIzq - 1].getNumItem())) {
                                for (int x = i; x <= 9; x++) {
                                    matriz[x][filaIzq].setNumItem(77);
                                    matriz[x][filaDer].setNumItem(77);
                                }
                                listaItems.clear();
                                flag = false;
                            }
                            
                            else if (listaItems.contains(matriz[i][filaDer + 1].getNumItem())) {
                                for (int x = i; x <= 9; x++) {
                                    matriz[x][filaIzq].setNumItem(77);
                                    matriz[x][filaDer].setNumItem(77);
                                }
                                listaItems.clear();
                                flag = false;
                            }

                            i++;
                        }
                    }
                    
                    for (int j = 2; j <= filaIzq; j++) { // se regresa al punto inicial 
                        matriz[9][j].setNumItem(77);
                    }
                }
            } 
            
            else if (ultItem > limite)  {
                // mover derecha (arriba/abajo)
                if (arribaPasillo)  {
                    for (int i = filaIzq; i <= filaIzq + 4; i++) {
                        matriz[1][i].setNumItem(77);
                    }
                } else  {
                    for (int i = filaIzq; i <= filaIzq + 4; i++) {
                        matriz[8][i].setNumItem(77);
                    }
                }
                
                // devuelvo item a lista
                listaItems.addFirst(ultItem);
                                
                filaIzq = filaIzq + 4;
                filaDer = filaDer + 4;
                limite = limite + 12;
            } 
        }
    }

    // Revisar linea 263 
    public static void main(String[] args) throws InterruptedException {
        // Definicion tamaño matriz del almacen
        int filas = 11, columnas = 20; //cambiar filas a 10 cuando termine prueba
        Bloque[][] matriz = crearMatriz(filas, columnas);
        
        // graficoAlmacen almacen = new graficoAlmacen();
        // almacen.initializeWindow(matriz);

        Pedido[] listaPedidos = generarPedidos(5);

        crearArchivos(listaPedidos);
        
        // Prueba sShape
        ///////////////////////////////////////////////////////////////////
        
        String listaString = listaPedidos[0].getListaLinked().toString();
        
        sShape(listaPedidos[0], matriz);

        System.out.println("\n");
        System.out.println(listaString);
        imprimirMatriz(matriz);

        ///////////////////////////////////////////////////////////////////

    }
}