package ru.pleshkova.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pleshkova.dao.FuelRecordDAO;

@Controller
@RequestMapping("/records")
public class FuelRecordsController {
    @Autowired
    private FuelRecordDAO fuelRecordDAO;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("records", fuelRecordDAO.index());
        return "records/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("record", fuelRecordDAO.show(id));
        return "records/show";
    }
}
