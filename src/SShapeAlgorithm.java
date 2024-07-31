import java.util.LinkedList;

public class SShapeAlgorithm {

    public void calculate(Pedido p, Bloque[][] matriz, Object lock, GraficoAlmacen grafico) {
        int filaIzq = 1, filaDer = 2;
        int limite = 12;
        int ultItem;
        boolean arribaPasillo = false;
        int distance = 0;
        
        LinkedList<Integer> listaItems = p.getListaLinked();

        synchronized (lock) {
            // Initial warehouse entry
            // Se recorre lo inicial (la entrada al almacen)
            matriz[8][1].setNumItem(77);
            matriz[9][1].setNumItem(77);
            distance += 2;
            grafico.repaintWithDelay();
            
            while (listaItems.size() > 0)  {
                ultItem = listaItems.pop();
                if (ultItem <= limite)   {
                    if (listaItems.size() == 0) //previene un null pointer exception
                        listaItems.add(ultItem);
                    if (listaItems.peek() > limite) {
                        // esta en pasillo, no hay mas items en pasillo , dibujo raya hacia arriba/abajo 
                        for (int i = 1; i <= 8; i++) {
                            matriz[i][filaIzq].setNumItem(77);
                            distance++;
                            grafico.repaintWithDelay();
                        }
                        // se movio arriba/abajo
                        arribaPasillo = !arribaPasillo;
                    }
                    else if (listaItems.getLast() <= limite) {
                        // todos los items restantes se encuentran dentro del pasillo 
                        // mover hasta ult item y regresar
                        // arribaPasillo => linea continua hacia abajo y regresa
                        // !arribaPasillo => linea gira en u y regresa 
                        if (arribaPasillo)  {
                            for (int i = 1; i <= 9; i++) {
                                matriz[i][filaIzq].setNumItem(77);
                                distance++;
                                grafico.repaintWithDelay();
                            }
                            listaItems.clear();
                        }
                        else  {
                            boolean flag = true;
                            int i = 2; //Se comienza en la fila más arriba del pasillo
                            listaItems.addFirst(ultItem); //Se añade de vuelta el ult item
                            
                            // se buscara el item más alejado (más arriba en pasillo)
                            // se dibujara desde la posicion del item más alejado

                            while (flag) {
                                if (listaItems.contains(matriz[i][filaIzq - 1].getNumItem())) {
                                    for (int x = i; x <= 9; x++) {
                                        matriz[x][filaIzq].setNumItem(77);
                                        matriz[x][filaDer].setNumItem(77);
                                        distance += 2;
                                        grafico.repaintWithDelay();
                                    }
                                    listaItems.clear();
                                    flag = false;
                                }
                                else if (listaItems.contains(matriz[i][filaDer + 1].getNumItem())) {
                                    for (int x = i; x <= 9; x++) {
                                        matriz[x][filaIzq].setNumItem(77);
                                        matriz[x][filaDer].setNumItem(77);
                                        distance += 2;
                                        grafico.repaintWithDelay();
                                    }
                                    listaItems.clear();
                                    flag = false;
                                }
                                i++;
                            }
                        }
                        for (int j = 2; j <= filaIzq; j++) {
                            matriz[9][j].setNumItem(77);
                            distance++;
                            grafico.repaintWithDelay();
                        }
                    }
                } else if (ultItem > limite)  {
                    if (arribaPasillo)  {
                        for (int i = filaIzq; i <= filaIzq + 4; i++) {
                            matriz[1][i].setNumItem(77);
                            distance++;
                            grafico.repaintWithDelay();
                        }
                    } else  {
                        for (int i = filaIzq; i <= filaIzq + 4; i++) {
                            matriz[8][i].setNumItem(77);
                            distance++;
                            grafico.repaintWithDelay();
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
        p.setDistance(distance);
        p.setTime(distance);
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
}
