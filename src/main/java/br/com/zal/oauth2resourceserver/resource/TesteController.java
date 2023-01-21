package br.com.zal.oauth2resourceserver.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {


    @GetMapping
    public ResponseEntity<Object> get(){
        SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Ola mundo alex");
    }
}
