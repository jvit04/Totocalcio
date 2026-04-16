package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.ComparadorParticipante;
import utilities.ConexionBD;
import utilities.MaxHeap;
import utilities.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TotocalcioController {
    ComparadorParticipante cmp=new ComparadorParticipante();
    MaxHeap<Participante> leaderboard =  new MaxHeap<>(cmp);
    @FXML
    private AnchorPane idPantallaCarga;
    @FXML
    private Button idBotonIniziare;
    @FXML
    private VBox vboxListaRanking;


    public void initialize(){
        cargarLeaderboard();
            idPantallaCarga.setVisible(true);
    }
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


    public void actualizarLeaderboardUI() {
        // 1. Limpiar la interfaz previa
        vboxListaRanking.getChildren().clear();

        List<Participante> topParticipantes = leaderboard.obtenerTopN(5);

        int puesto = 1;
        for (Participante p : topParticipantes) {
            // 3. Crear el contenedor de la fila (HBox para nombre y puntos)
            HBox fila = new HBox();
            fila.setSpacing(20);
            fila.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-background-radius: 5;");

            // 4. Crear los Labels
            Label lblPuesto = new Label("#" + puesto);
            lblPuesto.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a2b4c;");

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


    @FXML
    void ocultarPantalla(ActionEvent event) {
        idPantallaCarga.setVisible(false);
    }
}
