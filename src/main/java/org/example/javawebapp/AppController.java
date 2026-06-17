package org.example.javawebapp;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class AppController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("fullName", "Mensah Peprah Emmanuel");
        model.addAttribute("labName", "ECS CI/CD");
        model.addAttribute("labName", "Text");
        model.addAttribute("taskInfo", getTaskMetadata());
        return "index";
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK";
    }

    private String getTaskMetadata() {
        try {
            String metadataUri = System.getenv("ECS_CONTAINER_METADATA_URI_V4");
            if (metadataUri == null) return "Running locally - no ECS metadata available";

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(metadataUri + "/task", String.class);
            return response;
        } catch (Exception e) {
            return "Metadata unavailable: " + e.getMessage();
        }
    }
}
