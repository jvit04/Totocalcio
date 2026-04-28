package utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que facilita la conexión a la base de datos.
 */

public class ConexionBD {
    /**
     * Metodo para conectar con la base, usa atributos guardados en la clase Paths
     * @return la conexión con la base
     * @throws SQLException en caso de que no se pueda conectar
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(Paths.UrlBaseDatos, Paths.USER, Paths.PASSWORD);
    }

    /**
     * Metodo para almacenar los participantes en la base de datos
     * @param nombre
     * @param puntos
     */
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

    /**
     * Este metodo permite que el número del concurso, ubicado en la esquina superior izquierda, crezca secuencialmente.
     * @return el número del último registro más uno
     */
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
//Comando para reiniciar los datos de la base, usar en pgAdmin: TRUNCATE TABLE participante RESTART IDENTITY;
}