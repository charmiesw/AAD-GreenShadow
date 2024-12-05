package lk.ijse.gsbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aad/gs/healthTest")
public class HealthTestController {
    @GetMapping
    public String getHealthTest(){
        return "Green Shadow Application Run Successfully..!!";
    }
}
