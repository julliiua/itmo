package julliiua.lab6.server.managers.dbManagers;

import julliiua.lab6.common.models.Album;
import julliiua.lab6.common.models.Coordinates;
import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.models.MusicGenre;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.utility.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MusicBandDbManager {
    private final DbConnectionManager connectionManager;
    private static volatile MusicBandDbManager instance;


    public MusicBandDbManager(String url, String login, String password) {
        connectionManager = new DbConnectionManager(url, login, password);
    }

    public static MusicBandDbManager getInstance() {
        if (instance == null) {
            synchronized (MusicBandDbManager.class) {
                if (instance == null) {
                    instance = new MusicBandDbManager("jdbc:postgresql://localhost:5432/laba7", "postgres", "123");
                }
            }
        }
        return instance;
    }

    public MusicBandDbManager(DbConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    /**
     * Загружает объектов Product из базы данных
     *
     * @return Map<Integer, Person> - соответствие id и Product
     */
    public PriorityQueue<MusicBand> loadMusicBands() throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(
                "SELECT music_band.id, music_band.creator_id, music_band.name, music_band.coordinates_id, " +
                        "music_band.creation_date, music_band.number_of_participants, music_band.genre, " +
                        "music_band.best_album_id, coordinates.x, coordinates.y, " +
                        "album.name, album.sales " +
                        "FROM music_band JOIN coordinates ON music_band.coordinates_id = coordinates.id " +
                        "JOIN album ON music_band.best_album_id = album.id"
        );

        ResultSet result = statement.executeQuery();

        PriorityQueue<MusicBand> MusicBands = new PriorityQueue<>();
        while (result.next()) {
            Integer id = result.getInt("id");
            MusicBand band = new MusicBand(
                    id,
                    result.getString("name"),
                    new Coordinates(
                            result.getDouble("x"),
                            result.getInt("y")
                    ),
                    result.getInt("number_of_participants"),
                    MusicGenre.valueOf(result.getString("genre")),
                    new Album(
                            result.getLong("best_album_id"),
                            result.getString("name"),
                            result.getDouble("sales")
                    )
            );

            band.setCreatorId(result.getInt("creator_id"));
            MusicBands.add(band);
        }

        connection.close();
        return MusicBands;
    }

    public ExecutionResponse addBand(User user, MusicBand musicBand) throws SQLException {
        Connection connection = getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO music_band (name, coordinates_id, creation_date, number_of_participants, genre, " +
                            "best_album_id, creator_id) " +
                            "VALUES (?, ?, ?, ?, ?::MusicGenre, ?, " +
                            "(SELECT id FROM users WHERE users.name = ?)) " +
                            "RETURNING id"
            );

            statement.setString(1, musicBand.getName());
            statement.setInt(2, addCoordinates(musicBand.getCoordinates())); // Assuming addCoordinates returns the coordinates_id
            statement.setTimestamp(3, Timestamp.valueOf(musicBand.getCreationDate()));
            statement.setInt(4, musicBand.getNumberOfParticipants());
            statement.setString(5, musicBand.getGenre().toString());
            statement.setInt(6, addAlbum(musicBand.getBestAlbum())); // Assuming addAlbum returns the best_album_id
            statement.setString(7, user.getName()); // Assuming user.getName() returns the creator_id


            ResultSet result = statement.executeQuery();
            connection.close();

            result.next();

            return new ExecutionResponse(true, result.getString("id"));
        } catch (SQLException e) {
            return new ExecutionResponse(false, " Произошла ошибка при добавлении band в базу данных!" + e);
        }

    }
    public int addCoordinates(Coordinates coordinates) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id"
        );

        statement.setDouble(1, coordinates.getX());
        statement.setInt(2, coordinates.getY());

        ResultSet result = statement.executeQuery();
        result.next();

        int id = result.getInt("id");
        connection.close();

        return id;


    }

    public int addAlbum(Album album) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO album (name, sales) VALUES (?, ?) RETURNING id"
        );

        statement.setString(1, album.getName());
        statement.setDouble(2, album.getSales());

        ResultSet result = statement.executeQuery();
        result.next();

        int id = result.getInt("id");
        connection.close();

        return id;
    }

    public ExecutionResponse removeMusicBand(User user, int id) throws SQLException {
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM music_band WHERE id = ? AND creator_id = (SELECT id FROM users WHERE name = ?)"
            );
            // Проверяем, что пользователь является создателем группы
            statement.setInt(1, id);
            statement.setString(2, user.getName());

            int rowsAffected = statement.executeUpdate();
            connection.close();

            if (rowsAffected > 0) {
                return new ExecutionResponse(true, "MusicBand with id " + id + " was successfully removed.");
            } else {
                return new ExecutionResponse(false, "MusicBand with id " + id + " not found.");
            }
        } catch (SQLException e) {
            return new ExecutionResponse(false, "Error removing MusicBand: " + e.getMessage());
        }
    }

    public ExecutionResponse updateMusicBand(User user, MusicBand musicBand) throws SQLException {
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE music_band SET name = ?, coordinates_id = ?, creation_date = ?, " +
                            "number_of_participants = ?, genre = ?::MusicGenre, best_album_id = ? " +
                            "WHERE id = ? AND creator_id = (SELECT id FROM users WHERE name = ?)"
            );

            statement.setString(1, musicBand.getName());
            statement.setInt(2, addCoordinates(musicBand.getCoordinates()));
            statement.setTimestamp(3, Timestamp.valueOf(musicBand.getCreationDate()));
            statement.setInt(4, musicBand.getNumberOfParticipants());
            statement.setString(5, musicBand.getGenre().toString());
            statement.setInt(6, addAlbum(musicBand.getBestAlbum()));
            statement.setInt(7, musicBand.getId());
            statement.setString(8, user.getName());


            int rowsAffected = statement.executeUpdate();


            connection.close();

            if (rowsAffected > 0) {
                return new ExecutionResponse(true, "MusicBand with id " + musicBand.getId() + " was successfully updated.");
            } else {
                return new ExecutionResponse(false, "MusicBand with id " + musicBand.getId() + " not found or you are not the creator.");
            }
        } catch (SQLException e) {
            return new ExecutionResponse(false, "Error updating MusicBand: " + e.getMessage());
        }
    }

}


