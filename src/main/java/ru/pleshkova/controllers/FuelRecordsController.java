package ru.pleshkova.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pleshkova.dao.FuelRecordDAO;
import ru.pleshkova.models.FuelRecord;

import javax.validation.Valid;

@Controller
@RequestMapping("/records")
public class FuelRecordsController {
    private final FuelRecordDAO fuelRecordDAO;
    @Autowired
    public FuelRecordsController(FuelRecordDAO fuelRecordDAO) {
        this.fuelRecordDAO = fuelRecordDAO;
    }

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
        return "records/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("record") @Valid FuelRecord record, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "records/new";
        fuelRecordDAO.save(record);
        return "redirect:/records";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("record", fuelRecordDAO.show(id));
        return "records/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("record") @Valid FuelRecord record, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "records/edit";
        fuelRecordDAO.update(id, record);
        return "redirect:/records";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        fuelRecordDAO.delete(id);
        return "redirect:/records";
    }
}
