package com.example.nobs.transactions;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class BankController {

    private final TransferService transferService;

    public BankController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO transferDTO) {
      
        return transferService.execute(transferDTO);
    }
    

}
