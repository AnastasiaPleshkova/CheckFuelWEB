package ru.pleshkova.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pleshkova.dao.FuelRecordDAO;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {
    private final FuelRecordDAO fuelRecordDAO;
    @Autowired
    public BatchController(FuelRecordDAO fuelRecordDAO){
        this.fuelRecordDAO = fuelRecordDAO;
    }

    @GetMapping()
    public String index(){
        return "batch/index";
    }

//    @GetMapping("/without")
//    public String withoutBatch(){
//        fuelRecordDAO.testMultipleUpdate();
//        return "redirect:/records";
//    }

    @GetMapping("/with")
    public String withBatch(){
        fuelRecordDAO.testMBatchUpdate();
        return "redirect:/records";
    }



}
