package ru.pleshkova.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pleshkova.dao.FuelRecordDAO;
import ru.pleshkova.models.FuelRecord;

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
    @GetMapping("/new")
    public String newRecord(Model model) {
        model.addAttribute("record", new FuelRecord());
        return "records/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("record") FuelRecord record){
        fuelRecordDAO.save(record);
        return "redirect:/records";
    }
}
