package com.iesdosmares.xml;

import java.sql.*;

public class ConexionPostgres {

	public static String HOST_POR_DEFECTO = "192.168.18.89";

	public static void main(String[] args) {
		ConexionPostgres prueba = new ConexionPostgres();

		String host = HOST_POR_DEFECTO;
		if (args.length > 0) {
			host = args[0];
		}
		prueba.principal(host);
	}

	private void principal(String host) {
		try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://" + host +":5432/odoo", "odoo", "odoo")) {

			System.out.println("Conexion establecida correctamente");

			String parametro = "S00001";
			String consulta = "select \n" +
					"  so.name as id_pedido, so.state as estado, so.date_order as fecha, rp.\"name\" as cliente,\n" +
					"  ru.login as usuario, rpu.name as comercial,\n" +
					"  so.invoice_status as facturado, so.amount_untaxed as total_sin_iva, so.amount_total as total,\n" +
					"  it.value as forma_pago,\n" +
					"  uc.name as campanya, us.name as fuente, um.name as medio, \n" +
					"  so.origin as origen_unk, so.client_order_ref as referencia_cliente, \n" +
					"  rp_fac.street as dir_fac1, rp_fac.street2 as dir_fac2, rp_fac.zip as dir_fac_cp, rp_fac.city as dir_fac_ciudad,\n" +
					"  rp_env.street as dir_env1, rp_env.street2 as dir_env2, rp_env.zip as dir_env_cp, rp_env.city as dir_env_ciudad\n" +
					"from sale_order so\n" +
					"left join utm_campaign uc on so.campaign_id = uc.id\n" +
					"left join utm_source us on so.source_id = us.id\n" +
					"left join utm_medium um on so.medium_id = um.id \n" +
					"left join res_partner rp on so.partner_id = rp.id\n" +
					"left join res_partner rp_fac on so.partner_invoice_id = rp_fac.id\n" +
					"left join res_partner rp_env on so.partner_invoice_id = rp_env.id\n" +
					"left join res_users ru on so.user_id = ru.id left join res_partner rpu on ru.partner_id = rpu.id\n" +
					"left join account_payment_term apt on so.payment_term_id = apt.id left join ir_translation it on it.name = 'account.payment.term,name' and it.lang = 'es_ES' and it.res_id = apt.id\n" +
					"where\n" +
					"\tso.name = ?";

			System.out.println("Realizando consulta:  " + consulta);

			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, parametro);
			ResultSet resultSet = statement.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();

			System.out.println("Mostrando resultados");

			int actual = 0;
			while (resultSet.next()) {
				actual++;
				System.out.println("Registro número: " + actual);
				// The column count starts from 1
				for (int i = 1; i <= columnCount; i++ ) {
					String name = rsmd.getColumnName(i);
					System.out.println("  " + name + ": " + resultSet.getString(name));
				}
			}
		} /*catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBC driver not found.");
			e.printStackTrace();
		}*/ catch (SQLException e) {
			System.out.println("Error en el acceso a la base de datos");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
