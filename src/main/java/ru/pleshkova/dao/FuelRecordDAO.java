package ru.pleshkova.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.pleshkova.models.FuelRecord;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FuelRecordDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FuelRecordDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FuelRecord> index() {
        return jdbcTemplate.query("SELECT * FROM archive_records", new FuelRecordMapper());
    }

    public FuelRecord show(int id) {
        return jdbcTemplate.query("SELECT * FROM archive_records WHERE idarchive_records=?", new Object[]{id}, new FuelRecordMapper())
                .stream().findAny().orElse(null);
    }

    public void save(FuelRecord record) {
        jdbcTemplate.update("INSERT INTO archive_records (currentKM, date, litres, kmonlitresREAL) VALUES(?, ?, 0, 0)",
                record.getKm(), record.getDate());
    }

    public void update(int id, FuelRecord updateRecord) {
        jdbcTemplate.update("UPDATE archive_records SET currentKM=?, date=? WHERE idarchive_records = ?",
                updateRecord.getKm(), updateRecord.getDate(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM archive_records WHERE idarchive_records = ?", id);
    }

    // тестируем пакетную вставку
    public void testMBatchUpdate(){
        List<FuelRecord> list = new ArrayList<>();
                list.add(new FuelRecord(1,"1","1"));
                list.add(new FuelRecord(2,"2", "2"));
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO archive_records (currentKM, date, litres, kmonlitresREAL) VALUES(?, ?, 0, 0)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, list.get(i).getKm());
                        preparedStatement.setString(2, list.get(i).getDate());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });

        long after = System.currentTimeMillis();
        System.out.println("Time batch: " + (after - before));

    }



}
