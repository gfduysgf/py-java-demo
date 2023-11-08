package com.predict.javademo.controller;

import com.predict.javademo.utils.pythonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Controller
@CrossOrigin
@ResponseBody
@RestController
public class predictApiController {

    @PostMapping("/predict")
    public String predict(@RequestParam("img") MultipartFile[] img,@RequestParam("model") String model){
        return pythonUtils.pytest(img,model);
    }
}
