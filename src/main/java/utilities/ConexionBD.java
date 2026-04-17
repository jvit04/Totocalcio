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
        // En Postgres, llamar a una función con SELECT está perfecto
        String query = "SELECT fn_guardarParticipante(?, ?)";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setInt(2, puntos);

            // CAMBIO AQUÍ: Usamos execute() en lugar de executeUpdate()
            pstmt.execute();

        } catch (SQLException e) {
            System.out.println("Error al guardar en BD: " + e.getMessage());
        }
    }

    public static int obtenerSiguienteConcurso(){
        String query = "SELECT obtenerSiguienteConcurso()";
        try (Connection conn = conectar();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                // CAMBIO AQUÍ: Pedimos la columna 1 en lugar del nombre
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el número de concurso: " + e.getMessage());
        }

        return 1;
    }
//Comando para reinciar los datos de la base, usar en pgAdmin: TRUNCATE TABLE participante RESTART IDENTITY;
}