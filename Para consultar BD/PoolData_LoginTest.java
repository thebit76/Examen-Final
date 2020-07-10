package consultasDB;

import utils.ConsultarDB;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class PoolData_LoginTest {
	
	static List<HashMap<String, Object>> resultados;


	public static List<HashMap<String, Object>> devolverUsuarioLogin () throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		HashMap<String, Object> db = new HashMap<String, Object>();
		HashMap<String, Object> salidaDb = new HashMap<String, Object>();
		salidaDb.put("email","" );
		salidaDb.put("password","" );

        String query =
        		"SELECT email,password \r\n" +
        		"FROM test.users \r\n" +
        		" where idUser='mmondragon'";
		
        db.put("query", query);
		db.put("columnas", salidaDb);
		
		resultados = ConsultarDB.consultarBD(db);
		
		return resultados;
	}

	
}
