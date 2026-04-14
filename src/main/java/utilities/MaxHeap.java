package utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementación de una estructura de datos Max-Heap.
 * Optimizada para gestionar una tabla de posiciones (Leaderboard).
 * @param <E> El tipo de elemento que almacenará el Heap (ej. Participante).
 */
public class MaxHeap<E> {
    private ArrayList<E> datos;
    private int n;
    private Comparator<E> cmp;

    /**
     * Constructor del Max-Heap.
     * @param cmp El comparador utilizado para establecer quién tiene más puntos.
     */
    public MaxHeap(Comparator<E> cmp) {
        this.n = 0;
        this.datos = new ArrayList<>();
        this.cmp = cmp;
    }

    // --- MÉTODOS DE CÁLCULO DE ÍNDICES ---

    private int indicePadre(int i) {
        if (i == 0) return -1;
        return (i - 1) / 2;
    }

    private int indiceHijoIzq(int r) {
        int valor = r * 2 + 1;
        return (valor >= n) ? -1 : valor;
    }

    private int indiceHijoDer(int r) {
        int valor = 2 * r + 2;
        return (valor >= n) ? -1 : valor;
    }

    // --- LÓGICA DE ORDENAMIENTO (MAX-HEAP) ---

    private int getMayor(int raiz, int izq, int der) {
        int mayor = raiz;
        // Compara con el hijo izquierdo si existe
        if (izq != -1 && cmp.compare(datos.get(mayor), datos.get(izq)) < 0) {
            mayor = izq;
        }
        // Compara con el hijo derecho si existe
        if (der != -1 && cmp.compare(datos.get(mayor), datos.get(der)) < 0) {
            mayor = der;
        }
        return mayor;
    }

    private void intercambio(int x, int y) {
        E tmp = datos.get(x);
        datos.set(x, datos.get(y));
        datos.set(y, tmp);
    }

    /**
     * Ajusta el árbol hacia abajo (hundimiento).
     * Se usa al extraer el máximo.
     */
    private void autoAjuste(int iDanno) {
        int iMayor = getMayor(iDanno, this.indiceHijoIzq(iDanno), this.indiceHijoDer(iDanno));
        if (iDanno == iMayor) return; // Si ya es el mayor, todo está en orden

        intercambio(iDanno, iMayor);
        autoAjuste(iMayor); // Recursividad hacia abajo
    }

    /**
     * Ajusta el árbol hacia arriba (flotar).
     * Se usa al insertar un nuevo elemento para que suba si tiene muchos puntos.
     */
    private void flotar(int i) {
        int padre = indicePadre(i);
        // Si hay padre y el hijo es mayor que el padre, se intercambian y sigue subiendo
        if (padre != -1 && cmp.compare(datos.get(padre), datos.get(i)) < 0) {
            intercambio(i, padre);
            flotar(padre);
        }
    }

    // --- MÉTODOS PÚBLICOS PARA LA APLICACIÓN ---

    /**
     * Agrega un nuevo participante al leaderboard en tiempo logarítmico O(log n).
     */
    public void insertar(E elemento) {
        datos.add(elemento);
        n++;
        flotar(n - 1); // El nuevo elemento entra al final y "flota" hasta su posición
    }

    /**
     * Extrae al participante con la mayor puntuación (la cima del Heap).
     * @return El elemento mayor, o null si está vacío.
     */
    public E desencolar() {
        if (n == 0) return null;

        E eliminado = datos.get(0);

        if (n == 1) {
            datos.remove(0);
            n--;
            return eliminado;
        }

        // Movemos el último elemento a la raíz y eliminamos la cola
        datos.set(0, datos.get(n - 1));
        datos.remove(n - 1);
        n--;

        // Hundimos la nueva raíz para que vuelva a ser un Max-Heap
        this.autoAjuste(0);
        return eliminado;
    }

    /**
     * Utilidad para saber si el Leaderboard está vacío.
     */
    public boolean isEmpty() {
        return n == 0;
    }

    public List<E> obtenerTopN(int n){
        ArrayList<E> topN= new ArrayList<>();
        //caso base: lista vacía
        if(this.isEmpty())return topN;

        //1. se realiza una copia del heap actual
        MaxHeap<E> heapTemporal = new MaxHeap<>(this.cmp);

        //2. uso la función de construir heap con los datos del original
        heapTemporal.construirHeap(new ArrayList<>(this.datos));

        //3. obtengo el maximo a extraer
        int cantidadAExtraer = Math.min(n,heapTemporal.n);

        //4. Desencolar N veces (o hasta que quede vacío el heap) en la copia
        for(int i =0; i< cantidadAExtraer;i++){
            //obtenemos la raíz que será el dato mayor
            topN.add(heapTemporal.desencolar());
        }
        return topN;
    }

    public void construirHeap(ArrayList<E> Amalo){
        this.datos = new ArrayList<>(Amalo);
        this.n = datos.size();
        // Se ajustan los nodos desde el último padre hasta la raíz
        for (int i = n/2 - 1; i >= 0; i--) {
            this.autoAjuste(i);
        }
    }
}