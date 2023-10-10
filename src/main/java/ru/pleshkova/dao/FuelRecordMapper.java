package ru.pleshkova.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.pleshkova.models.FuelRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuelRecordMapper implements RowMapper<FuelRecord> {
    @Override
    public FuelRecord mapRow(ResultSet resultSet, int i) throws SQLException {
       return new FuelRecord(resultSet.getInt("idarchive_records"), resultSet.getString("currentKM"),
               resultSet.getString("date"));
    }
}
