package br.com.zal.oauth2resourceserver.resource;

import br.com.zal.oauth2resourceserver.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class TesteController {


    private final PrayerRepository repository;
    @GetMapping
    public ResponseEntity<Object> get(){
        SecurityContextHolder.getContext().getAuthentication();
        var resu = this.repository.findAll();
        return ResponseEntity.ok("Ola mundo alex");
    }
}
