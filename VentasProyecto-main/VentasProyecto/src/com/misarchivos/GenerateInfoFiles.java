package com.misarchivos;  // Paquete donde está ubicada la clase

// Importaciones necesarias para manejar la escritura de archivos y generación de números aleatorios
import java.io.BufferedWriter;  
import java.io.FileWriter;  
import java.io.IOException;
import java.util.Random;

/**
 * Clase principal para generar archivos de información de productos, vendedores y ventas aleatorias.
 */
public class GenerateInfoFiles {

    public static void main(String[] args) {
        int numberOfSalesmen = 5;  // Número de vendedores a generar
        int numberOfProducts = 10; // Número de productos a generar
        
        // Generar la información de los productos y almacenarla en un archivo
        createProductsFile(numberOfProducts);
        
        // Generar la información de los vendedores y para cada vendedor generar archivos de ventas
        createSalesManInfoFile(numberOfSalesmen);
        
        // Confirmación de que los archivos se han generado correctamente
        System.out.println("Archivos de prueba generados exitosamente.");
    }

    /**
     * Genera un archivo de ventas aleatorias para un vendedor específico.
     *
     * @param id ID del vendedor (utilizado en el nombre del archivo)
     * @param numberOfSales Número de ventas aleatorias a generar
     */
    public static void createSalesMenFile(long id, int numberOfSales) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Ventas_del_vendedor_de_codigo_#" + id + ".txt"))) {
            // Genperador de números aleatorios
            Random rand = new Random();
            // Generar un conjunto de ventas aleatorias para el vendedor
            for (int i = 0; i < numberOfSales; i++) {
                int productId = rand.nextInt(10) + 1;  // Genera un ID de producto aleatorio entre 1 y 10
                int quantitySold = rand.nextInt(50) + 1;  // Genera una cantidad vendida aleatoria entre 1 y 50
                // Escribe cada venta en el archivo en formato "ID_producto; cantidad_vendida;"
                writer.write("Ventas del producto N°: "+productId + ". Cantidad: " + quantitySold + ";\n");
            }
        } catch (IOException e) {
            // Mensaje de error si ocurre un problema al crear el archivo
            System.out.println("Error al crear el archivo de ventas para el vendedor con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Genera un archivo con información de productos.
     *
     * @param productsCount Número de productos a generar
     */
    public static void createProductsFile(int productsCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Nombres_de_los_productos.txt"))) {
            // Generador de números aleatorios
            Random rand = new Random();
            // Lista predefinida de nombres de productos
            String[] productNames = {"Café Orgánico", "Leche en Polvo", "Pan de Molde", "Queso Doble Crema", "Jamón York"};
            
            // Generar la información de los productos
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;  // ID único del producto
                String productName = productNames[i % productNames.length];  // Nombre del producto (de la lista)
                // Generar un precio aleatorio entre 10 y 100
                double price = 10.0 + (100.0 - 10.0) * rand.nextDouble();
                // Escribir cada producto en el archivo en formato "ID_producto; Nombre_producto; Precio_producto"
                writer.write("Id del producto: "+productId + ". Nombre del producto:" + productName + ". Precio: " + String.format("%.2f", price) + "\n");
            }
        } catch (IOException e) {
            // Mensaje de error si ocurre un problema al crear el archivo
            System.out.println("Error al crear el archivo de productos: " + e.getMessage());
        }
    }

    /**
     * Genera un archivo con información de vendedores y un archivo de ventas para cada vendedor.
     *
     * @param salesmanCount Número de vendedores a generar
     */
    public static void createSalesManInfoFile(int salesmanCount) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Informacion_por_cada_vendedor.txt"))) {
        // Listas predefinidas de nombres y apellidos de vendedores
        String[] names = {"Carlos", "Alejandro", "Maria", "Luis", "Ana"};
        String[] surnames = {"Olivares", "Gomez", "Arango", "Diaz", "Martinez"};
        Random rand = new Random();  // Generador de números aleatorios
        
        // Generar la información de los vendedores
        for (int i = 0; i < salesmanCount; i++) {
            String docType = "CC";  // Tipo de documento del vendedor (por ejemplo, cédula de ciudadanía)
            long docNumber = 100000000L + rand.nextInt(900000000); // Número de documento único para cada vendedor
            String name = names[rand.nextInt(names.length)];  // Nombre aleatorio de la lista
            String surname = surnames[rand.nextInt(surnames.length)];  // Apellido aleatorio de la lista
            
            // Escribir la información del vendedor en el archivo
            writer.write(docType + " N°: " + docNumber + ". Nombre: " + name + ". Apellido: " + surname + "\n");
            
            // Generar un archivo de ventas aleatorias para este vendedor
            int[][] sales = generateSales(docNumber, 5);  // Generar 5 ventas aleatorias por vendedor
            
            // Ordenar las ventas en orden de mayor a menor usando el método burbuja
            bubbleSort(sales);
            
            // Escribir las ventas ordenadas en el archivo
            writeSalesToFile(docNumber, sales);
        }
    } catch (IOException e) {
        // Mensaje de error si ocurre un problema al crear el archivo
        System.out.println("Error al crear el archivo de información de vendedores: " + e.getMessage());
    }
}

/**
 * Genera ventas aleatorias para un vendedor específico.
 *
 * @param id ID del vendedor
 * @param numberOfSales Número de ventas a generar
 * @return Un arreglo bidimensional que contiene las ventas generadas, donde cada fila contiene [ID_producto, cantidad_vendida]
 */
private static int[][] generateSales(long id, int numberOfSales) {
    Random rand = new Random();
    int[][] sales = new int[numberOfSales][2];  // Arreglo para almacenar las ventas

    // Generar ventas aleatorias
    for (int i = 0; i < numberOfSales; i++) {
        int productId = rand.nextInt(10) + 1;  // ID del producto entre 1 y 10
        int quantitySold = rand.nextInt(50) + 1;  // Cantidad vendida entre 1 y 50
        sales[i][0] = productId;  // Almacena el ID del producto
        sales[i][1] = quantitySold;  // Almacena la cantidad vendida
    }
    
    return sales;
}

/**
 * Ordena un arreglo bidimensional de ventas utilizando el método de burbuja en orden de mayor a menor.
 *
 * @param sales Arreglo de ventas a ordenar
 */
private static void bubbleSort(int[][] sales) {
    int n = sales.length;
    boolean swapped;

    // Implementación del algoritmo de burbuja
    do {
        swapped = false;
        for (int i = 0; i < n - 1; i++) {
            // Comparar la cantidad vendida en la columna 1 y ordenar de mayor a menor
            if (sales[i][1] < sales[i + 1][1]) {
                // Intercambiar las filas si están en el orden incorrecto
                int[] temp = sales[i];
                sales[i] = sales[i + 1];
                sales[i + 1] = temp;
                swapped = true;
            }
        }
    } while (swapped);
}

/**
 * Escribe las ventas en un archivo específico para un vendedor.
 *
 * @param id ID del vendedor
 * @param sales Arreglo bidimensional con las ventas ordenadas
 */
private static void writeSalesToFile(long id, int[][] sales) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Ventas_del_vendedor_de_codigo #" + id + ".txt"))) {
        // Escribir cada venta en el archivo
        for (int[] sale : sales) {
            writer.write(sale[0] + ";" + sale[1] + ";\n");  // Formato "ID_producto; cantidad_vendida;"
        }
    } catch (IOException e) {
        System.out.println("Error al crear el archivo de ventas para el vendedor con ID " + id + ": " + e.getMessage());
    }
}

}
