package com.example.nobs.transactions;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nobs.Command;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransferService implements Command<TransferDTO, String> {

    private final BankAccountRepository bankAccountRepository;

    public TransferService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public ResponseEntity<String> execute(TransferDTO transfer) {
        
        Optional<BankAccount> fromAccount = bankAccountRepository.findById(transfer.getFromUser());
        Optional<BankAccount> toAccount = bankAccountRepository.findById(transfer.getToUser());

        if(fromAccount.isEmpty() || toAccount.isEmpty()){
            throw new RuntimeException("User not found");
        }

        BankAccount from = fromAccount.get();
        BankAccount to = toAccount.get();

        //add and deduct

        add(to, transfer.getAmount());
        System.out.println("After adding, before deducting: ");
        System.out.println(bankAccountRepository.findById(to.getName()));
        deduct(from, transfer.getAmount());


        return ResponseEntity.ok("Success");
    }


    private void deduct(BankAccount bankAccount, double amount) {

        if(bankAccount.getBalance() < amount){
            throw new RuntimeException("Insufficient funds");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount);
    }

    private void add(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);
    }


}
