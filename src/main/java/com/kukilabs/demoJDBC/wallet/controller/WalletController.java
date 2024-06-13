package com.kukilabs.demoJDBC.wallet.controller;

import com.kukilabs.demoJDBC.auth.entity.UserAuth;
import com.kukilabs.demoJDBC.responses.Response;
import com.kukilabs.demoJDBC.user.entity.User;
import com.kukilabs.demoJDBC.user.service.UserService;
import com.kukilabs.demoJDBC.wallet.dto.EditWalletDto;
import com.kukilabs.demoJDBC.wallet.dto.StatusWalletDto;
import com.kukilabs.demoJDBC.wallet.dto.SwitchActiveWalletDto;
import com.kukilabs.demoJDBC.wallet.dto.WalletDto;
import com.kukilabs.demoJDBC.wallet.entity.Wallet;
import com.kukilabs.demoJDBC.wallet.service.WalletService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;
    private final UserService userService;

    public WalletController(WalletService walletService, UserService userService){
        this.walletService = walletService;
        this.userService = userService;
    }

    public Long getAuthorizedUserId(){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        String requesterEmail = auth.getName();
        User user = userService.getUserByEmail(requesterEmail);
        return user.getId();
    }

    @PostMapping
    public ResponseEntity<Response<Wallet>> createWallet(@Validated @RequestBody WalletDto wallet){
        Long userId = getAuthorizedUserId();
        var createdWallet = walletService.createWallet(wallet, userId);
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

    @GetMapping("user")
    public ResponseEntity<Response<List<WalletDto>>> getWalletByUserId(){
       Long userId = getAuthorizedUserId();
        List<WalletDto> wallets = walletService.getWalletsByUserId(userId);
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
    public ResponseEntity<Response<StatusWalletDto>> switchActiveWallet(@RequestBody SwitchActiveWalletDto switchActiveWalletDto) {
        Long userId = getAuthorizedUserId();
        StatusWalletDto updatedWallet = walletService.switchActiveWallet(switchActiveWalletDto.getWalletId(),userId );
        return Response.successfulResponse("Wallet switched successfully", updatedWallet);
    }

}
