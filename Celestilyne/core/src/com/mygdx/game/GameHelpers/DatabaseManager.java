package com.mygdx.game.GameHelpers;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    /**
     * Establishes connection to SQLite database
     * @return Connection variable
     */
    public Connection connect(){
        Connection conn = null;
        try {
            // create a connection to the database
            String url = "jdbc:sqlite:celestilyne.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            CrashLogHandler.logSevere("There has been a problem connecting to the database: ", e.getMessage());
        }
        return conn;
    }

    /**
     * Loads in Celestilyne.db file
     */
    public void createDatabase() {
        try {
            Connection conn = connect();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                EventLogHandler.log("The database has been loaded successfully. ");
                EventLogHandler.log("The driver is " + meta.getDriverName());
                conn.close();
            }
        } catch (SQLException e) {
            CrashLogHandler.logSevere("There has been an issue creating the database: ", e.getMessage());
        }
    }

    /**
     * Runs any sql string given on database
     * @param sql sql string to run
     * @throws SQLException if there is an error in the query
     */
    private void runSql(String sql) throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        conn.close();
    }


    /**
     * determines if the table given in the parameters is given
     * @param table table to check
     * @return if the table in parameter has no data
     */
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

    /**
     * Loads in all tables if they do no exist
     */
    public void createTables() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY, name TEXT NOT NULL, " +
                    "score INTEGER NOT NULL);";
            runSql(sql);
            if(tableIsEmpty("records")){
                insertScore(0, "DEFAULT");
            }
            sql = "CREATE TABLE IF NOT EXISTS time_records(id INTEGER PRIMARY KEY, name TEXT NOT NULL, " +
                    "time INTEGER NOT NULL);";
            runSql(sql);
            if(tableIsEmpty("time_records")){
                insertTime(60 * 30, "DEFAULT");
            }
            runSql(sql);
            EventLogHandler.log("Records table has been properly loaded.");
            sql = "CREATE TABLE IF NOT EXISTS runtime_configurations(musicVolume INTEGER NOT NULL," +
                    " sfxVolume INTEGER NOT NULL, name TEXT NOT NULL, fullscreen INTEGER NOT NULL);";
            runSql(sql);
            EventLogHandler.log("runtime_configurations table has been properly loaded.");
            if(tableIsEmpty("runtime_configurations")){
                sql = "INSERT INTO runtime_configurations(musicVolume, sfxVolume, name, fullscreen) " +
                        "VALUES (50, 50, 'DEFAULT', 0)";
                runSql(sql);
                EventLogHandler.log("Default runtime_configurations have been added.");
            }
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue creating the tables in the database:", e.getMessage());
        }
    }

    /**
     * Adds a new record to the database
     * @param score number of levels beaten in the game
     * @param name name of the player that made the new record
     */
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

    public void insertTime(float time, String name){
        String sql = "INSERT INTO time_records (name, time) VALUES (?,?);";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setFloat(2, time);
            preparedStatement.executeUpdate();
            connection.close();
            EventLogHandler.log("A new time record has been added to the database.");
        } catch (SQLException e) {
            CrashLogHandler.logSevere("There has been an issue inserting a score into the database:",
                    e.getMessage());
        }
    }

    /**
     * Updates table runtime_configurations with the runtimeConfigurations passed in through the parameter
     * @param runtimeConfigurations current state of runtimeConfigurations to publish to the database
     */
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

    /**
     * gets all records in the records table
     * @return an ArrayList of all records
     */
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
            EventLogHandler.log("Records have been successfully gathered from the database. ");
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue getting scores from the database: ",
                    e.getMessage());
        }
        return records;
    }

    public ArrayList<TimeRecord> getTimeRecords(){
        String sql = "SELECT name, time FROM time_records ORDER BY time ASC;";
        ArrayList<TimeRecord> records = new ArrayList<>();
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                records.add(new TimeRecord(resultSet.getFloat(2), resultSet.getString(1)));
            }
            connection.close();
            EventLogHandler.log("Time Records have been successfully gathered from the database. ");
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue getting scores from the database: ",
                    e.getMessage());
        }
        return records;
    }


    /**
     * Gets runtime configurations from the database
     * @return state of runtime configurations loaded from the database
     */
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
            EventLogHandler.log("Runtime configurations have been successfully loaded from the database.");
            return runtimeConfigurations;
        } catch (SQLException e){
            CrashLogHandler.logSevere("There has been an issue getting runtime configurations from the database: ",
                    e.getMessage());
        }
        return new RuntimeConfigurations();
    }
}
