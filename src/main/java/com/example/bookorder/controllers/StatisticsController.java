package com.example.bookorder.controllers;

import com.example.bookorder.models.forms.StatisticsForm;
import com.example.bookorder.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/${version.name}")
public class StatisticsController {

    private final OrderService orderService;

    public StatisticsController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping ("/viewTable")
    public String getOrdersByDateTable(Model model, @RequestParam("customerId") String customerId) {
        List<StatisticsForm> statistics = orderService.getStatistics(customerId);
        model.addAttribute("statistics", statistics);
        return "view";
    }
}