# Aplicacion en Java usando interfaz grafica Swing

---

## Descripcion
Implementar el simulador de un almacén rectangular que tiene 5
pasillos y 60 ubicaciones (12 ubicaciones por pasillo) donde van
alojados los ítems, en cada ubicación se aloja un ítem diferente
(no se lleva el registro de la cantidad de ítems en cada ubicación).

Los ítems van en cada ubicación de la siguiente manera:
* El ítem 4, va en la ubicación 4.
* El ítem 5, va en la ubicación 5, y así sucesivamente.

La velocidad de recorrido en el almacén es de 1 m/s y la
capacidad del carrito donde van los elementos del pedido es de
10 ítems.

# Creación de la lista de pedidos
* Los pedidos deben tener como mínimo 5 elementos hasta 10 elementos.
* Los elementos se pueden repetir en el pedido.
* Implementar un generador de lista de pedidos aleatorio que permita ingresar el número de pedidos a generar. No hay un número máximo para los pedidos.

# Algoritmo de Recorrido
* El algoritmo a utilizar sería el S-Shape; el cual, realiza movimientos como la S.
* Al iniciar el recorrido se busca un pasillo donde se encuentren ítems a recoger; caso contrario, continúo al siguiente pasillo (SShape: Caso 3).
* Si llego al final del recorrido, si ingreso desde el frente del almacén y ya no hay más para recoger, me regreso por el mismo pasillo y voy hacia el depot (S-Shape: Caso 1).
* Si llego al final del recorrido, si ingreso desde el fondo del almacén y ya no hay más para recoger, recorro todo el pasillo y regreso al depot (S-Shape: Caso 2).

# Resultados
* Al final de la animación se debe generar un archivo
denominado como resultados.txt donde se
almacene por cada pedido, la distancia total
recorrida y el tiempo de recorrido.

# Especificaciones Técnicas
* Programación Concurrente
* Usar un hilo para cada etapa del proceso: la generación de pedidos, el cálculo del recorrido, generación del archivo de resultados.txt y la recolección de ítems.
* Esta estructura divide el proceso en etapas, donde cada etapa es manejada por un hilo separado. Aunque el recorrido en sí es secuencia (un solo carrito en un momento dado), puedes utilizar hilos para manejar la generación de pedidos, el cálculo del recorrido, generación de archivo y la recolección de ítems de manera concurrente, mejorando la eficiencia del programa en general.

