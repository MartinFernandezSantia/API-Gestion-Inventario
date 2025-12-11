package com.lmfm.api.bd;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateDatabaseAndTables {
    public static void create() {
        // String createDbSQL = "CREATE DATABASE IF NOT EXISTS hpc_db";
        // String useDbSQL = "USE hpc_db";
        String permisosTableSQL =
                "CREATE TABLE IF NOT EXISTS permisos (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(256) UNIQUE, " +
                "borrado BOOLEAN DEFAULT FALSE)";
        String usuariosTableSQL =
                "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(50) NOT NULL, " +
                "apellido VARCHAR(50) NOT NULL, " +
                "fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "legajo INT UNIQUE NOT NULL, " +
                "contraseña VARCHAR(256) NOT NULL, " +
                "permiso_id INT, " +
                "borrado BOOLEAN DEFAULT FALSE, " +
                "FOREIGN KEY (permiso_id) REFERENCES permisos(id))";
        String sectoresTableSQL =
                "CREATE TABLE IF NOT EXISTS sectores (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(100) UNIQUE NOT NULL, " +
                "borrado BOOLEAN DEFAULT FALSE)";
        String subsectoresTableSQL =
                "CREATE TABLE IF NOT EXISTS subsectores (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(100) UNIQUE NOT NULL, " +
                "sector_id INT, " +
                "borrado BOOLEAN DEFAULT FALSE, " +
                "FOREIGN KEY (sector_id) REFERENCES sectores(id))";
        String categoriasTableSQL =
                "CREATE TABLE IF NOT EXISTS categorias (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(100) UNIQUE NOT NULL, " +
                "borrado BOOLEAN DEFAULT FALSE)";
        String articulosTableSQL =
                "CREATE TABLE IF NOT EXISTS articulos (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "codigo INT UNIQUE NOT NULL, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "stock INT NOT NULL, " +
                "limite INT, " +
                "fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "categoria_id INT, " +
                "borrado BOOLEAN DEFAULT FALSE, " +
                "FOREIGN KEY (categoria_id) REFERENCES categorias(id))";
        String turnosTableSQL =
                "CREATE TABLE IF NOT EXISTS turnos (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(100) UNIQUE NOT NULL, " +
                "hora_inicio TIME NOT NULL, " +
                "hora_fin TIME NOT NULL, " +
                "borrado BOOLEAN DEFAULT FALSE)";
        String movimientosInventarioTableSQL =
                "CREATE TABLE IF NOT EXISTS movimientos_inventario (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "articulo_id INT NOT NULL, " +
                "usuario_id INT NOT NULL, " +
                "turno_id INT NOT NULL, " +
                "subsector_id INT NOT NULL, " +
                "cantidad INT NOT NULL, " +
                "tipo_movimiento BOOL NOT NULL, " +
                "fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "es_pedido BOOL DEFAULT 0, " +
                "es_diferencia BOOL DEFAULT 0, " +
                "FOREIGN KEY (articulo_id) REFERENCES articulos(id), " +
                "FOREIGN KEY (usuario_id) REFERENCES usuarios(id), " +
                "FOREIGN KEY (turno_id) REFERENCES turnos(id), " +
                "FOREIGN KEY (subsector_id) REFERENCES subsectores(id))";
        String balancesTableSQL =
                "CREATE TABLE IF NOT EXISTS balances (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "articulo_id INT NOT NULL, " +
                "stock INT NOT NULL, " +
                "stock_real INT NOT NULL, " +
                "fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (articulo_id) REFERENCES articulos(id))";

        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement()) {

            // Crear la Base de Datos
        //     stmt.executeUpdate(createDbSQL);
        //     System.out.println("Base de datos creada.");

        //     // Usar BD
        //     stmt.executeUpdate(useDbSQL);

            // Crear tablas independientes
            stmt.executeUpdate(permisosTableSQL);
            stmt.executeUpdate(sectoresTableSQL);
            stmt.executeUpdate(categoriasTableSQL);
            stmt.executeUpdate(turnosTableSQL);
            System.out.println("Tablas independientes creadas.");

            // Crear tablas dependientes
            stmt.executeUpdate(usuariosTableSQL);
            stmt.executeUpdate(subsectoresTableSQL);
            stmt.executeUpdate(articulosTableSQL);
            stmt.executeUpdate(movimientosInventarioTableSQL);
            stmt.executeUpdate(balancesTableSQL);
            System.out.println("Tablas dependientes creadas.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropDb() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DROP TABLE IF EXISTS balances");
            stmt.execute("DROP TABLE IF EXISTS movimientos_inventario");
            stmt.execute("DROP TABLE IF EXISTS articulos");
            stmt.execute("DROP TABLE IF EXISTS subsectores");
            stmt.execute("DROP TABLE IF EXISTS usuarios");
            stmt.execute("DROP TABLE IF EXISTS turnos");
            stmt.execute("DROP TABLE IF EXISTS categorias");
            stmt.execute("DROP TABLE IF EXISTS sectores");
            stmt.execute("DROP TABLE IF EXISTS permisos");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        dropDb();
        create();
    }
}
