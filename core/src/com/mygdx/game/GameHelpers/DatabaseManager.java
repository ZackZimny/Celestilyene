package com.mygdx.game.GameHelpers;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    public Connection connect(){
        Connection conn = null;
        try {
            // create a connection to the database
            String url = "jdbc:sqlite:celestilyne.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            CrashLogHandler.logSevere("There has been a problem connecting to the database: \n", e.getMessage());
        }
        return conn;
    }

    public void createDatabase() {
        try {
            Connection conn = connect();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                EventLogHandler.log("A new database has been created.");
                EventLogHandler.log("The driver is " + meta.getDriverName());
                conn.close();
            }
        } catch (SQLException e) {
            CrashLogHandler.logSevere("There has been an issue creating the database: \n", e.getMessage());
        }
    }

    private void runSql(String sql) throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        conn.close();
    }


    private boolean tableIsEmpty(String table){
        String sql = String.format("SELECT * FROM %s;", table);
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            conn.close();
            //rs.next() returns if there is a result from the ResultSet
            return !rs.next();
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue determining if scores are empty. ",
                    e.getMessage());
            //The application will be closed before this could ever return
            return false;
        }
    }

    public void createTables() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY, name TEXT NOT NULL, " +
                    "score INTEGER NOT NULL);";
            runSql(sql);
            if(tableIsEmpty("records")){
                insertScore(0, "DEFAULT");
            }
            sql = "CREATE TABLE IF NOT EXISTS runtime_configurations(musicVolume INTEGER NOT NULL," +
                    " sfxVolume INTEGER NOT NULL, name TEXT NOT NULL, fullscreen INTEGER NOT NULL);";
            runSql(sql);
            if(tableIsEmpty("runtime_configurations")){
                sql = "INSERT INTO runtime_configurations(musicVolume, sfxVolume, name, fullscreen) " +
                        "VALUES (50, 50, 'DEFAULT', 0)";
                runSql(sql);
            }
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue creating the tables in the database:", e.getMessage());
        }
    }

    public void insertScore(int score, String name){
        String sql = "INSERT INTO records (name, score) VALUES (?,?);";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
            connection.close();
            EventLogHandler.log("A new record has been added to the database.");
        } catch (SQLException e) {
            CrashLogHandler.logSevere("There has been an issue inserting a score into the database:",
                    e.getMessage());
        }
    }

    public void updateRuntimeConfigurations(RuntimeConfigurations runtimeConfigurations){
        String sql = "UPDATE runtime_configurations SET musicVolume = ?, sfxVolume = ?, name = ?, fullscreen = ?;";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, runtimeConfigurations.getMusicVolume());
            preparedStatement.setInt(2, runtimeConfigurations.getSfxVolume());
            preparedStatement.setString(3, runtimeConfigurations.getName());
            preparedStatement.setInt(4, runtimeConfigurations.isFullscreen() ? 1 : 0);
            preparedStatement.executeUpdate();
            connection.close();
            EventLogHandler.log("New runtime configurations have been added to the database. ");
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue adding runtime configurations into the database:",
                    e.getMessage());
        }
    }

    public ArrayList<Record> getRecords(){
        String sql = "SELECT name, score FROM records ORDER BY score DESC;";
        ArrayList<Record> records = new ArrayList<>();
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                records.add(new Record(resultSet.getString(1), resultSet.getInt(2)));
            }
            connection.close();
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue getting scores from the database: ",
                    e.getMessage());
        }
        return records;
    }

    public RuntimeConfigurations getRuntimeConfigurations(){
        String sql = "SELECT musicVolume, sfxVolume, name, fullscreen FROM runtime_configurations;";
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            RuntimeConfigurations runtimeConfigurations = new RuntimeConfigurations(resultSet.getInt(1),
                    resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getInt(4) == 1);
            connection.close();
            return runtimeConfigurations;
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue getting runtime configurations from the database: ",
                    e.getMessage());
        }
        return new RuntimeConfigurations();
    }
}
