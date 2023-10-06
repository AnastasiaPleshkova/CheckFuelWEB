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
    public String newRecord(@ModelAttribute("record") FuelRecord record) {
//        model.addAttribute("record", new FuelRecord());
        return "records/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("record") FuelRecord record){
        fuelRecordDAO.save(record);
        return "redirect:/records";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("record", fuelRecordDAO.show(id));
        return "records/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("records") FuelRecord record, @PathVariable("id") int id) {
        fuelRecordDAO.update(id, record);
        return "redirect:/records";
    }
}
