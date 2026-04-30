package controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utilities.*;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase clave para el funcionamiento de la aplicación, aquí está toda la lógica de la app.
 */
public class TotocalcioController {
    /**
     * Atributos y objetos @FX
     */
    private int numeroConcursoActual = 0;
    ComparadorParticipante cmp=new ComparadorParticipante();
    MaxHeap<Participante> leaderboard =  new MaxHeap<>(cmp);
    private String[] apuestasUsuario = new String[7];

    //Se creará un temporizador para el reinicio de la app
    private PauseTransition temporizadorReinicio;
    // Un temporizador exclusivo para ocultar la notificación
    private PauseTransition temporizadorNotificacion;
    // Esta lista guardará los 7 partidos (6 mundiales + 1 bonus) que salieron en pantalla
    private List<Partido> rondaActual = new ArrayList<>();

    // === INYECCIONES FXML ===
    @FXML private Label lblTitulo_0, lblTitulo_1, lblTitulo_2, lblTitulo_3, lblTitulo_4, lblTitulo_5, lblTitulo_6;
    @FXML private Label lblLocal_0, lblLocal_1, lblLocal_2, lblLocal_3, lblLocal_4, lblLocal_5, lblLocal_6;
    @FXML private Label lblVisit_0, lblVisit_1, lblVisit_2, lblVisit_3, lblVisit_4, lblVisit_5, lblVisit_6;
    @FXML private ImageView imgLocal_0, imgLocal_1, imgLocal_2, imgLocal_3, imgLocal_4, imgLocal_5, imgLocal_6;
    @FXML private ImageView imgVisit_0, imgVisit_1, imgVisit_2, imgVisit_3, imgVisit_4, imgVisit_5, imgVisit_6;

    // === ARREGLOS PARA FACILITAR EL CÓDIGO ===
    private Label[] titulos;
    private Label[] locales;
    private Label[] visitantes;
    private ImageView[] imgLocales;
    private ImageView[] imgVisitantes;

    @FXML
    private Button btnSiguienteJugador;

    @FXML
    private AnchorPane idPantallaCarga;
    @FXML
    private Button idBotonIniziare;
    @FXML
    private VBox vboxListaRanking;
    @FXML
    private AnchorPane idPanelJuego;
    @FXML
    private Button btn_0_1;

    @FXML
    private Button btn_0_2;

    @FXML
    private Button btn_0_X;

    @FXML
    private Button btn_1_1;

    @FXML
    private Button btn_1_2;

    @FXML
    private Button btn_1_X;

    @FXML
    private Button btn_2_1;

    @FXML
    private Button btn_2_2;

    @FXML
    private Button btn_2_X;

    @FXML
    private Button btn_3_1;

    @FXML
    private Button btn_3_2;

    @FXML
    private Button btn_3_X;

    @FXML
    private Button btn_4_1;

    @FXML
    private Button btn_4_2;

    @FXML
    private Button btn_4_X;

    @FXML
    private Button btn_5_1;

    @FXML
    private Button btn_5_2;

    @FXML
    private Button btn_5_X;

    @FXML
    private Button btn_6_1;

    @FXML
    private Button btn_6_2;

    @FXML
    private Button btn_6_X;
    @FXML
    private HBox panelNotificacion;
    @FXML
    private Label lblNotificacion;
    @FXML
    private Button btn_enviar_apuesta;
    @FXML
    private Label lblConcorso;

    /**
     * Metodo para inicializar la aplicación
     */
    public void initialize(){
        // 1. Agrupamos los elementos en orden (del slot 0 al 6)
        titulos = new Label[]{lblTitulo_0, lblTitulo_1, lblTitulo_2, lblTitulo_3, lblTitulo_4, lblTitulo_5, lblTitulo_6};
        locales = new Label[]{lblLocal_0, lblLocal_1, lblLocal_2, lblLocal_3, lblLocal_4, lblLocal_5, lblLocal_6};
        visitantes = new Label[]{lblVisit_0, lblVisit_1, lblVisit_2, lblVisit_3, lblVisit_4, lblVisit_5, lblVisit_6};
        imgLocales = new ImageView[]{imgLocal_0, imgLocal_1, imgLocal_2, imgLocal_3, imgLocal_4, imgLocal_5, imgLocal_6};
        imgVisitantes = new ImageView[]{imgVisit_0, imgVisit_1, imgVisit_2, imgVisit_3, imgVisit_4, imgVisit_5, imgVisit_6};

        // 2. Llenamos el tablero por primera vez
        llenarTablero();
        cargarLeaderboard();
        actualizarLeaderboardUI();
        idPantallaCarga.setVisible(true);
        numeroConcursoActual = ConexionBD.obtenerSiguienteConcurso();
        lblConcorso.setText(String.valueOf(numeroConcursoActual));

    }
    public void llenarTablero() {
        // Limpiamos la memoria de la partida anterior
        rondaActual.clear();

        // Traemos los datos frescos de la BD
        List<Partido> mundiales = ConexionBD.obtenerPartidos();
        Partido bonus = ConexionBD.obtenerPartidosBonus();

        // Juntamos todo en nuestra lista maestra de la ronda (7 partidos)
        rondaActual.addAll(mundiales);
        if(bonus != null) {
            rondaActual.add(bonus);
        }

        // Llenamos los 7 slots en 5 líneas de código
        for (int i = 0; i < rondaActual.size(); i++) {
            Partido p = rondaActual.get(i);

            titulos[i].setText(p.getTituloPartido());
            locales[i].setText(p.getEquipoLocal());
            visitantes[i].setText(p.getEquipoVisitante());

            try {
                // Cargamos las banderas dinámicamente desde la carpeta resources
                imgLocales[i].setImage(new Image(getClass().getResourceAsStream("/imagenes/" + p.getRutaBanderaLocal())));
                imgVisitantes[i].setImage(new Image(getClass().getResourceAsStream("/imagenes/" + p.getRutaBanderaVisitante())));
            } catch (Exception e) {
                System.out.println("Error cargando imagen del partido " + i + ": " + e.getMessage());
            }
        }
    }
    /**
     * Metodo que carga la tabla de posiciones (leaderboard) actual, guardandola en el Heap
     */
    public void cargarLeaderboard(){
        //llamo a la función de la base de datos
        String query = "SELECT * FROM fn_selectAll()";

        try(Connection connection = ConexionBD.conectar();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery()){

            //iteración sobre el set de resultados rs
            while(rs.next()){
                String nombre = rs.getString("nombre_jugador");
                int puntos = rs.getInt("puntos_obtenidos");

                //se crea un objeto Participante y se lo guarda en el Heap
                Participante participante = new Participante(nombre, puntos);
                leaderboard.insertar(participante);
            }
        }catch (SQLException e){
            System.out.println("Error al cargar la base de datos :( :" + e.getMessage());
        }
    }

    /**
     * Metodo que actualiza la tabla de posiciones en el UI
     * Es la parte visual, también genera colores diferentes para los primeros tres puestos
     */
    public void actualizarLeaderboardUI() {
        // 1. Limpiar la interfaz previa
        vboxListaRanking.getChildren().clear();
        //2. Obtener la lista de los mejores N participantes
        List<Participante> topParticipantes = leaderboard.obtenerTopN(10);

        int puesto = 1;
        for (Participante p : topParticipantes) {
            // 3. Crear el contenedor de la fila
            HBox fila = new HBox();
            fila.setSpacing(20);

            // Variables para guardar el estilo dinámico
            String estiloFila = "-fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5; ";
            String colorTextoPuesto = "-fx-font-weight: bold; ";

            // Lógica del Podio (Oro, Plata, Bronce)
            if (puesto == 1) {
                // ORO: Fondo amarillo muy suave, borde dorado
                estiloFila += "-fx-background-color: #FFFBE6; -fx-border-color: #FFD700; -fx-border-width: 2px;";
                colorTextoPuesto += "-fx-text-fill: #B8860B;"; // Oro oscuro para contraste
            } else if (puesto == 2) {
                // PLATA: Fondo gris muy suave, borde plateado
                estiloFila += "-fx-background-color: #F8F9FA; -fx-border-color: #8f8f8f; -fx-border-width: 2px;";
                colorTextoPuesto += "-fx-text-fill: #6C757D;"; // Gris oscuro
            } else if (puesto == 3) {
                // BRONCE: Fondo naranja muy suave, borde bronce
                estiloFila += "-fx-background-color: #FFF3E0; -fx-border-color: #CD7F32; -fx-border-width: 2px;";
                colorTextoPuesto += "-fx-text-fill: #8B4513;"; // Café oscuro
            } else {
                // RESTO: Blanco normal sin borde
                estiloFila += "-fx-background-color: white;";
                colorTextoPuesto += "-fx-text-fill: #1a2b4c;"; // Azul del diseño
            }

            fila.setStyle(estiloFila);

            // 4. Crear los Labels
            Label lblPuesto = new Label("#" + puesto);
            lblPuesto.setStyle(colorTextoPuesto); // Aplicamos el color del metal al número

            Label lblNombre = new Label(p.getNombre());
            lblNombre.setPrefWidth(150);

            Label lblPuntos = new Label(p.getPuntos() + " pts");
            lblPuntos.setStyle("-fx-font-weight: bold;");

            // 5. Agregar elementos a la fila y la fila al VBox
            fila.getChildren().addAll(lblPuesto, lblNombre, lblPuntos);
            vboxListaRanking.getChildren().add(fila);

            puesto++;
        }
    }

    /**
     * Metodo que registra el boton pulsado por el usuario, me lo recomendó la IA y me parece sumamente fascinante
     * Identifica que boton fue presionado y gracias al formato del id (btn_fila_opcion) permite saber a qué fila exacta
     * pertenece
     * @param event
     */
    @FXML
public void registrarApuesta(ActionEvent event){
    //1. Identificar que botón se presionó
    Button botonPresionado = (Button) event.getSource();
    String idBoton = botonPresionado.getId();

    //Condición de seguridad
    if (idBoton == null) {
        System.out.println("Error: Al botón le falta el ID");
        return;
    }

    //2. Extraer el número de fila (del 0 al 7) basado en el ID
    //Se aprovechará el formato de los id de los botones. Ej: btn_fila_opción
    //se usara un split por guion bajo "_"
    String[] partes = idBoton.split("_");
    //como la fila esta en la segunda posición ahora sabemos donde estamos ubicados
    int indiceFila = Integer.parseInt(partes[1]);

    //Obtenemos el texto del boton para saber que presionó el usuario
    String valorElegido = botonPresionado.getText();

    // 3. Guardamos en memoria sus apuestas
    apuestasUsuario[indiceFila]=valorElegido;

    //4. Limpiamos los botones de la fila y cambiamos el boton seleccionado
    limpiarBotonesDeLaFila(indiceFila);
    botonPresionado.getStyleClass().add("boton-seleccionado");
}
//Metodo auxiliar para limpiar los botones
private void limpiarBotonesDeLaFila(int fila){
        String[] opciones = { "1", "X", "2"};
        for (String opcion:opciones){
            //Buscamos el botón aprovechando el ID
            String idBuscado = "#btn_" + fila + "_" + opcion;
            Node nodo = idPanelJuego.lookup(idBuscado);

            if(nodo !=null){
                //Quitamos el estilo CSS
                nodo.getStyleClass().remove("boton-seleccionado");
            }
        }
}

    /**
     * Esto es para cuando el usuario de en el botón "INIZIARE" se oculte la pantalla de bloqueo
     * @param event
     */
    @FXML
    void ocultarPantalla(ActionEvent event) {
        idPantallaCarga.setVisible(false);
    }

    /**
     * Este metodo es para el botón de enviar apuesta, procesa los resultados enviados por parte del jugador
     * En caso de que no haya enviado alguuno, le llega una advertencia
     * @param event
     */
    @FXML
    void procesarApuesta(ActionEvent event) {
        //Verificar si el usuario respondió todas las preguntas
        for (int i = 0; i < apuestasUsuario.length; i++) {
            if(apuestasUsuario[i]==null){
                mostrarNotificacion("Attenzione! Devi rispondere tutti le partite", true);
                return;
            }
        }
        // Calculo de puntos dinámico
        int puntosObtenidos = 0;

        for (int i = 0; i < apuestasUsuario.length; i++) {
            // Extraemos la respuesta correcta directamente del objeto Partido en esa posición
            String resultadoCorrecto = rondaActual.get(i).getResultadoReal();

            // Comparamos lo que presionó el usuario con la respuesta de la base de datos
            if(apuestasUsuario[i].equals(resultadoCorrecto)){
                if(i == 6){
                    puntosObtenidos += 7; // El partido Bonus vale 7 puntos
                } else {
                    puntosObtenidos += 5; // Los partidos de Mundial valen 5 puntos
                }
            }
        }

        //3.Generación de usuario y persistencia
        String nombreJugador = NameGenerator.generarNombreAleatorio();
        ConexionBD.guardarParticipante(nombreJugador,puntosObtenidos);
        Participante participante =  new Participante(nombreJugador,puntosObtenidos);
        leaderboard.insertar(participante);
        actualizarLeaderboardUI();

        String mensajeExito = "Successo! " + nombreJugador + " ha totalizzato " + puntosObtenidos + " pti.";
        mostrarNotificacion(mensajeExito, false);

        btn_enviar_apuesta.setVisible(false); //Oculto el botón de enviar
        btnSiguienteJugador.setVisible(true); //Muestro el botón de siguiente

        //4. Finalmente, reiniciamos el tablero para el siguiente jugador
        temporizadorReinicio = new PauseTransition(Duration.seconds(12));
        temporizadorReinicio.setOnFinished(e -> reiniciarTablero());
        temporizadorReinicio.play();
    }

    /**
     * Metodo para reiniciar el tablero y dejarlo como estaba antes del juego
     */
    private void reiniciarTablero(){
        llenarTablero();
        //Si el usuario presionó el botón antes de los 12 segundos, cancelamos el reloj
        if (temporizadorReinicio != null) {
            temporizadorReinicio.stop();
        }
        //Se elimina todas las apuestas realizadas en memoria
        Arrays.fill(apuestasUsuario, null);
        //Se busca todos los botones dentro del panel y se retira el CSS de boton seleccionado
        idPanelJuego.lookupAll(".boton-seleccionado").forEach(nodo ->{
            nodo.getStyleClass().remove("boton-seleccionado");
        });
        //Dejamos los botones como estaban
        btn_enviar_apuesta.setVisible(true);
        btnSiguienteJugador.setVisible(false);

        //Volvemos a mostrar la pantalla carga
        idPantallaCarga.setVisible(true);
        numeroConcursoActual = ConexionBD.obtenerSiguienteConcurso();
        lblConcorso.setText(String.valueOf(numeroConcursoActual));

        //En caso se ejecute la aplicación en dos laptops, para la sincronización se deberá consultar de nuevo a la base
        // 1. Vaciamos el Heap actual creando uno nuevo
        leaderboard = new MaxHeap<>(new ComparadorParticipante());
        // 2. Volvemos a consultar la nube
        cargarLeaderboard();
        // 3. Dibujamos el Top 5 actualizado
        actualizarLeaderboardUI();
    }
    @FXML
    void accionSiguienteJugador(ActionEvent event){
        reiniciarTablero();
    }

    private void mostrarNotificacion(String mensaje, boolean esError){
        //1. Se configura el texto
        lblNotificacion.setText(mensaje);

        //2. Se limpia los colores anteriores
        panelNotificacion.getStyleClass().removeAll("notificacion-error", "notificacion-exito");

        //3. Se asigna el color correcto
        if (esError){
            panelNotificacion.getStyleClass().add("notificacion-error");
        }else {
            panelNotificacion.getStyleClass().add("notificacion-exito");
        }

        //4. Se muestra en pantalla
        panelNotificacion.setVisible(true);

        //5. Se configura un temporizador para que desaparezca solo
        if(temporizadorNotificacion!=null){
            temporizadorNotificacion.stop();
        }
        temporizadorNotificacion = new PauseTransition(Duration.seconds(3));
        temporizadorNotificacion.setOnFinished(e -> panelNotificacion.setVisible(false));
        temporizadorNotificacion.play();
    }
}

