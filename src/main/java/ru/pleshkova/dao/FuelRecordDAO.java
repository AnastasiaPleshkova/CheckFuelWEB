package ru.pleshkova.dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.springframework.stereotype.Component;
import ru.pleshkova.models.FuelRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FuelRecordDAO {
    private static int RECORDS_COUNT;
    private static final String URL = "jdbc:mysql://localhost:3306/checkfuelchema";
    private static final String USERNAME = "root";
    private static final Path passwordPath = Paths.get("C:/Users/HP650/Documents/Java Projects/CheckFuelWEB/src/main/resources/ru/pleshkova/db/password.txt");
    private static final String PASSWORD = getPassword();
    private static Connection connection;

    private static String getPassword() {
        if (Files.exists(passwordPath)) {
            try (BufferedReader rd = Files.newBufferedReader(passwordPath)) {
                return rd.readLine().trim();
            } catch (IOException ex) {
                System.out.println("Couldn't read file");
                ex.printStackTrace();
            }
        } else {
            System.out.println("File with password not found");
        }
        return "";
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.printf("try to get connection...%s, %s, %s..\n", URL, USERNAME, PASSWORD);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection succeeded");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Did not connect");
        }
    }

    public List<FuelRecord> index() {
        List<FuelRecord> records = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM archive_records";
            ResultSet resultSet = statement.executeQuery(SQL);
            while(resultSet.next()) {
                FuelRecord record = new FuelRecord();
                record.setId(resultSet.getInt("idarchive_records"));
                record.setKm(resultSet.getString("currentKM"));
                record.setDate(resultSet.getString("date"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public FuelRecord show(int id) {
        FuelRecord record = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM archive_records WHERE idarchive_records=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            record = new FuelRecord(rs.getInt("idarchive_records"), rs.getString("currentKM"), rs.getString("date"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return record;
    }

    public void save(FuelRecord record) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO archive_records " +
                    "(currentKM, date, litres, kmonlitresREAL) VALUES(?, ?, 0, 0)");
            preparedStatement.setString(1, record.getKm());
            preparedStatement.setString(2, record.getDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, FuelRecord updateRecord) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE archive_records SET currentKM=?, date=? WHERE idarchive_records = ?");
            preparedStatement.setString(1, updateRecord.getKm());
            preparedStatement.setString(2, updateRecord.getDate());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM archive_records WHERE idarchive_records = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
