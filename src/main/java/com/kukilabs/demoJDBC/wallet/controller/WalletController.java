package com.kukilabs.demoJDBC.wallet.controller;

import com.kukilabs.demoJDBC.responses.Response;
import com.kukilabs.demoJDBC.wallet.dto.EditWalletDto;
import com.kukilabs.demoJDBC.wallet.dto.StatusWalletDto;
import com.kukilabs.demoJDBC.wallet.dto.WalletDto;
import com.kukilabs.demoJDBC.wallet.entity.Wallet;
import com.kukilabs.demoJDBC.wallet.service.WalletService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;
    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Response<Wallet>> createWallet(@Validated @RequestBody WalletDto wallet){
        var createdWallet = walletService.createWallet(wallet);
        return Response.successfulResponse("Wallet created!");
    }
    @PutMapping("{userId}")
    public ResponseEntity<Response<EditWalletDto>> editWallet(@Valid @RequestBody EditWalletDto editWalletDto, @PathVariable long userId){
        var editWallet = walletService.editWallet(editWalletDto,userId);
        return Response.successfulResponse("Wallet updated successfully");
    }

    @GetMapping
    public ResponseEntity<Response<List<WalletDto>>> getAllWallet(){
        List<WalletDto> wallets = walletService.getAllWallets();
        return Response.successfulResponse("All wallet fetched", wallets);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<Response<List<WalletDto>>> getWalletByUserId(@PathVariable Long id){
        List<WalletDto> wallets = walletService.getWalletsByUserId(id);
        return Response.successfulResponse("Wallet Found", wallets);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<WalletDto>> getWalletByWalletId(@PathVariable Long id){
       WalletDto wallet = walletService.getWalletById(id);
        return Response.successfulResponse("Wallet Found", wallet);
    }

    @PutMapping("{walletId}/set-active")
    public ResponseEntity<Response<StatusWalletDto>> setActiveWallet(@PathVariable Long walletId) {
        StatusWalletDto updatedWallet = walletService.setActiveWallet(walletId);
        return Response.successfulResponse("Wallet set as active", updatedWallet);
    }
    @PutMapping("{walletId}/set-not-active")
    public ResponseEntity<Response<StatusWalletDto>> setNotActiveWallet(@PathVariable Long walletId) {
        StatusWalletDto updatedWallet = walletService.setNotActiveWallet(walletId);
        return Response.successfulResponse("Wallet set as not active", updatedWallet);
    }

    @PutMapping("/switch-active")
    public ResponseEntity<Response<StatusWalletDto>> switchActiveWallet(@RequestBody Long walletId, Long userId) {
        StatusWalletDto updatedWallet = walletService.setNotActiveWallet(walletId);
        return Response.successfulResponse("Wallet set as not active", updatedWallet);
    }


//    @PostMapping
//    public Wallet createWallet(@RequestBody Wallet wallet){
//        return walletService.createWallet(wallet);
//    }
}
