package com.example.single_page_application.controller;

import com.example.single_page_application.model.Detail;
import com.example.single_page_application.service.GeneratePDFReport;
import com.example.single_page_application.service.IDetailService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/details")
@AllArgsConstructor
public class DetailController {

    private final IDetailService detailService;

    @GetMapping("/read/all")
    public String admin(Model model) {
        model.addAttribute("details", detailService.getAllDetails());
        return "ShowDetails";
    }

    @GetMapping("/createDetail")
    public String createDetail() {
        return "create";
    }

    @PostMapping("/createDetail")
    public String createDetail(Detail detail) {
        detailService.createDetail(detail);
        return "redirect:/details/read/all";
    }

    @PostMapping("/deleteDetail")
    private String deleteDetail(Long id){
        detailService.deleteDetail(id);
        return "redirect:/details/read/all";
    }

    @PostMapping("/addCount")
    private String addCount(Long id){
        detailService.addCountDetail(id);
        return "redirect:/details/read/all";
    }

    @GetMapping(value = "/pdfreport")
    public ResponseEntity<InputStreamResource> citiesReport() throws IOException {

        List<Detail> details = (List<Detail>) detailService.getAllDetails();

        ByteArrayInputStream bis = GeneratePDFReport.detailsReport(details);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cars.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
