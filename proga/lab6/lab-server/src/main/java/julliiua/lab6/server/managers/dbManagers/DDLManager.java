package julliiua.lab6.server.managers.dbManagers;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс для работы с ddl-запросами по типу создания таблиц и удаления таблиц.
 */
public class DDLManager {
    private final DbConnectionManager connectionManager;

    public DDLManager(String url, String login, String password) {
        connectionManager = new DbConnectionManager(url, login, password);
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    public void dropTables() throws SQLException {
        String query = """
                BEGIN;

                DROP TABLE IF EXISTS music_band CASCADE;
                DROP TABLE IF EXISTS album CASCADE;
                DROP TABLE IF EXISTS coordinates CASCADE;
                DROP TABLE IF EXISTS users CASCADE;

                DROP TYPE IF EXISTS MusicGenre CASCADE;

                END;
                """;
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        System.out.println("Таблицы удалены");
    }



    public void createTables() throws SQLException {
        String query = """
            
            BEGIN;
                CREATE TYPE MusicGenre AS ENUM (
                    'RAP', 'POP', 'BLUES', 'POST_PUNK', 'HIP_HOP'
                );
                CREATE TABLE IF NOT EXISTS users (
                    id              SERIAL PRIMARY KEY,
                    name            VARCHAR(40) UNIQUE      NOT NULL,
                    password_digest VARCHAR(96)             NOT NULL,
                    salt            VARCHAR(10)             NOT NULL,
                    creation_date   TIMESTAMP DEFAULT NOW() NOT NULL
                );

                CREATE TABLE IF NOT EXISTS coordinates (
                    id SERIAL PRIMARY KEY,
                    x DOUBLE PRECISION NOT NULL,
                    y INTEGER NOT NULL CONSTRAINT positive_y CHECK (y > -506)
                );

                CREATE TABLE IF NOT EXISTS album (
                    id SERIAL PRIMARY KEY,
                    name TEXT NOT NULL,
                    sales DOUBLE PRECISION NOT NULL CONSTRAINT positive_sales CHECK (sales > 0)
                );

                CREATE TABLE IF NOT EXISTS music_band (
                    id SERIAL PRIMARY KEY,
                    creator_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    coordinates_id INTEGER REFERENCES coordinates(id),
                    creation_date TIMESTAMP DEFAULT NOW() NOT NULL,
                    number_of_participants INTEGER NOT NULL CONSTRAINT positive_participants CHECK (number_of_participants > 0),
                    genre MusicGenre NOT NULL,
                    best_album_id INTEGER REFERENCES album(id)
        
                );
                
                
                COMMIT;""";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        System.out.println("Таблицы созданы");
    }
}
