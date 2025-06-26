package julliiua.lab6.server;

import java.sql.SQLException;
import java.time.LocalDateTime;

import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.models.MusicGenre;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.utility.User;
import julliiua.lab6.server.managers.dbManagers.DDLManager;
import julliiua.lab6.server.managers.dbManagers.MusicBandDbManager;



public class test {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/laba7";
        String user = "postgres";
        String password = "123";
        DDLManager ddlManager = new DDLManager(url, user, password);
        User userDb = new User("1234", "1234");
        MusicBandDbManager productDatabaseManager = new MusicBandDbManager(url, user, password);
        ExecutionResponse resp1 = productDatabaseManager.addBand(userDb, new MusicBand(
                1,
                "Test Band",
                new julliiua.lab6.common.models.Coordinates(10.0, 20),
                5,
                MusicGenre.POP,
                new julliiua.lab6.common.models.Album("Best Album", 1000.0)
        ));
        ExecutionResponse resp2 = productDatabaseManager.removeMusicBand(userDb, 4);
        System.out.println(resp1.getMessage());
        System.out.println(resp2.getMessage());
    }
}
