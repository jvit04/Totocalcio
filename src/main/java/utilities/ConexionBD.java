package utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Metodo que facilita la conexión a la base de datos.
public class ConexionBD {
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(Paths.UrlBaseDatos, Paths.USER, Paths.PASSWORD);
    }
    public static void guardarParticipante(String nombre, int puntos){
        String query = "select fn_guardarParticipante(?,?)";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, puntos);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar en BD: " + e.getMessage());
        }
    }
    public static int obtenerSiguienteConcurso(){
        String query = "select obtenerSiguienteConcurso()";
        try (Connection conn = conectar();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("siguiente_boleto");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el número de concurso: " + e.getMessage());
        }

        return 1;
    }
}