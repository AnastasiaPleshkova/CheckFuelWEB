package ru.pleshkova.dao;

import org.springframework.stereotype.Component;
import ru.pleshkova.models.FuelRecord;

import java.util.ArrayList;
import java.util.List;

@Component
public class FuelRecordDAO {
    private static int RECORDS_COUNT;
    List<FuelRecord> records;

    {
        records = new ArrayList<>();
        records.add(new FuelRecord(++RECORDS_COUNT,"20","20.08.2023"));
        records.add(new FuelRecord(++RECORDS_COUNT,"40","01.09.2023"));
        records.add(new FuelRecord(++RECORDS_COUNT,"60","20.09.2023"));
    }

    public List<FuelRecord> index() {
        return records;
    }

    public FuelRecord show(int id) {
        return records.stream().filter(fuelRecord->fuelRecord.getId() == id).findAny().orElse(null);
    }

    public void save(FuelRecord record) {
        record.setId(++RECORDS_COUNT);
        records.add(record);
    }
}
