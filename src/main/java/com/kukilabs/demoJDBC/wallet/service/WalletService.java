package com.kukilabs.demoJDBC.wallet.service;

import com.kukilabs.demoJDBC.currency.entity.Currency;
import com.kukilabs.demoJDBC.currency.repository.CurrencyRepository;
import com.kukilabs.demoJDBC.exceptions.ApplicationException;
import com.kukilabs.demoJDBC.exceptions.DataNotFoundException;
import com.kukilabs.demoJDBC.user.entity.User;
import com.kukilabs.demoJDBC.user.repository.UserRepository;
import com.kukilabs.demoJDBC.wallet.dto.EditWalletDto;
import com.kukilabs.demoJDBC.wallet.dto.StatusWalletDto;
import com.kukilabs.demoJDBC.wallet.dto.WalletDto;
import com.kukilabs.demoJDBC.wallet.entity.Wallet;
import com.kukilabs.demoJDBC.wallet.repository.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository, CurrencyRepository currencyRepository){
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
    }
    @Transactional
    public Wallet createWallet(WalletDto wallet, Long userId){
        Optional<Currency> currency = currencyRepository.findById(wallet.getCurrencyId());
        if (currency.isEmpty()){
            throw new DataNotFoundException("currency not found");
        }

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DataNotFoundException("user not found");
        }

        Wallet newWallet = new Wallet();
        newWallet.setName(wallet.getName());
        newWallet.setStartAmount(wallet.getStartAmount());
        newWallet.setCurrentAmount(wallet.getCurrentAmount());
        newWallet.setActive(false);
        newWallet.setCurrency(currency.get());
        newWallet.setUser(user.get());
        newWallet.setCreatedAt(Instant.now());
        newWallet.setUpdatedAt(Instant.now());
        return walletRepository.save(newWallet);
    }
    @Transactional
    public EditWalletDto editWallet(EditWalletDto editWalletDto, Long userId){
        Optional<Wallet> wallet = walletRepository.findById(editWalletDto.getId());
        if(wallet.isEmpty()){
            throw new DataNotFoundException("Wallet not found");
        }

        Wallet userWallet = wallet.get();
        if(!userWallet.getUser().getId().equals(userId)){
            throw new ApplicationException(HttpStatus.UNAUTHORIZED,"You do not have permission to edit this wallet");
        }

        userWallet.setName(editWalletDto.getName());
        userWallet.setCurrentAmount(editWalletDto.getCurrentAmount());
        userWallet.setStartAmount(editWalletDto.getStartAmount());
        userWallet.setUpdatedAt(Instant.now());

        walletRepository.save(userWallet);

        return editWalletDto;
    }

    public List<WalletDto> getAllWallets() {
        List<Wallet> wallets =  walletRepository.findAll();
        return wallets.stream()
                .map(wallet -> {
                    WalletDto dto = new WalletDto();
                    dto.setId(wallet.getId());
                    dto.setName(wallet.getName());
                    dto.setStartAmount(wallet.getStartAmount());
                    dto.setCurrentAmount(wallet.getCurrentAmount());
                    dto.setCurrencyId(wallet.getCurrency().getId());
                    dto.setUserId(wallet.getUser().getId());
                    dto.setActive(wallet.isActive());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<WalletDto> getWalletsByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }

        List<Wallet> wallets = walletRepository.findByUserId(user.get().getId());

        return wallets.stream()
                .map(wallet -> {
                    WalletDto dto = new WalletDto();
                    dto.setId(wallet.getId());
                    dto.setName(wallet.getName());
                    dto.setStartAmount(wallet.getStartAmount());
                    dto.setCurrentAmount(wallet.getCurrentAmount());
                    dto.setCurrencyId(wallet.getCurrency().getId());
                    dto.setUserId(wallet.getUser().getId());
                    dto.setActive(wallet.isActive());
                    return dto;
                })
                .collect(Collectors.toList());
    }
//    public StatusWalletDto switchActiveWallet(Long walletId, Long userId) {
//        Optional<Wallet> walletOpt = walletRepository.findById(walletId);
//        if (walletOpt.isEmpty()) {
//            throw new DataNotFoundException("Wallet not found");
//        }
//
//        Wallet wallet = walletOpt.get();
////        if (!wallet.getUser().getId().equals(userId)) {
////            String walletID = wallet.getUser().getId().toString();
////            throw new DataNotFoundException("Wallet does not belong to the user " + walletID);
////        }
//
//        List<Wallet> userWallets = walletRepository.findByUserId(userId);
//        for (Wallet w : userWallets) {
//            w.setActive(false);
//        }
//        walletRepository.saveAll(userWallets);
//
//        if (wallet.isActive()) {
//            wallet.setActive(false);
//            wallet.setUpdatedAt(Instant.now());
//            walletRepository.save(wallet);
//            throw new ApplicationException(HttpStatus.OK, "You are not have active wallet");
//        } else {
//            wallet.setActive(true);
//            wallet.setUpdatedAt(Instant.now());
//            Wallet updatedWallet = walletRepository.save(wallet);
//            StatusWalletDto statusWalletDto = new StatusWalletDto();
//            statusWalletDto.setId(updatedWallet.getId());
//            statusWalletDto.setName(updatedWallet.getName());
//            statusWalletDto.setActive(updatedWallet.isActive());
//            return statusWalletDto;
//        }
//    }

    public StatusWalletDto switchActiveWallet(Long walletId, Long userId) {
        Optional<Wallet> walletOpt = walletRepository.findById(walletId);
        if (walletOpt.isEmpty()) {
            throw new DataNotFoundException("Wallet not found");
        }

        Wallet wallet = walletOpt.get();
        if (!wallet.getUser().getId().equals(userId)) {
            throw new DataNotFoundException("Wallet does not belong to the user");
        }

        List<Wallet> userWallets = walletRepository.findByUserId(userId);
        for (Wallet w : userWallets) {
            if (w.isActive()) {
                w.setActive(false);
                w.setUpdatedAt(Instant.now());
            }
        }

        walletRepository.saveAll(userWallets);
        if (wallet.isActive()){
            wallet.setActive(false);
        } else {
            wallet.setActive(true);
        }
        //wallet.setActive(!wallet.isActive());
        wallet.setUpdatedAt(Instant.now());
        walletRepository.save(wallet);

        StatusWalletDto statusWalletDto = new StatusWalletDto();
        statusWalletDto.setId(wallet.getId());
        statusWalletDto.setName(wallet.getName());
        statusWalletDto.setActive(wallet.isActive());
        return statusWalletDto;
    }

    public StatusWalletDto switchActiveaWallet(Long walletId, Long userId) {
        Optional<Wallet> walletOpt = walletRepository.findById(walletId);
        if (walletOpt.isEmpty()) {
            throw new DataNotFoundException("Wallet not found");
        }

        Wallet wallet = walletOpt.get();
        if (!wallet.getUser().getId().equals(userId)) {
            throw new DataNotFoundException("Wallet does not belong to the user ");
        }

        List<Wallet> userWallets = walletRepository.findByUserId(userId);
        for (Wallet w : userWallets) {
            w.setActive(false);
        }
        walletRepository.saveAll(userWallets);

        if (wallet.isActive()) {
            wallet.setActive(false);
            wallet.setUpdatedAt(Instant.now());
            walletRepository.save(wallet);
            throw new ApplicationException(HttpStatus.OK, "You are not have active wallet");
        } else {
            wallet.setActive(true);
            wallet.setUpdatedAt(Instant.now());
            Wallet updatedWallet = walletRepository.save(wallet);
            StatusWalletDto statusWalletDto = new StatusWalletDto();
            statusWalletDto.setId(updatedWallet.getId());
            statusWalletDto.setName(updatedWallet.getName());
            statusWalletDto.setActive(updatedWallet.isActive());
            return statusWalletDto;
        }
    }

    public WalletDto getWalletById(Long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isEmpty()){
            throw new DataNotFoundException("Wallet not found");
        }
        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.get().getId());
        walletDto.setName(wallet.get().getName());
        walletDto.setStartAmount(wallet.get().getStartAmount());
        walletDto.setCurrentAmount(wallet.get().getCurrentAmount());
        walletDto.setCurrencyId(wallet.get().getCurrency().getId());
        walletDto.setActive(wallet.get().isActive());
        walletDto.setUserId(wallet.get().getUser().getId());
        return walletDto;
    }

    public StatusWalletDto setActiveWallet(Long walletId) {
        Optional<Wallet> walletOpt = walletRepository.findById(walletId);
        if (walletOpt.isEmpty()) {
            throw new DataNotFoundException("Wallet not found");
        }
        Wallet wallet = walletOpt.get();
        wallet.setActive(true);
        wallet.setUpdatedAt(Instant.now());

        Wallet updatedWallet = walletRepository.save(wallet);

        StatusWalletDto statusWalletDto = new StatusWalletDto();
        statusWalletDto.setId(updatedWallet.getId());
        statusWalletDto.setName(updatedWallet.getName());
        statusWalletDto.setActive(updatedWallet.isActive());
        return statusWalletDto;
    }

    public StatusWalletDto setNotActiveWallet(Long walletId) {
        Optional<Wallet> walletOpt = walletRepository.findById(walletId);
        if (walletOpt.isEmpty()) {
            throw new DataNotFoundException("Wallet not found");
        }
        Wallet wallet = walletOpt.get();
        wallet.setActive(false);
        wallet.setUpdatedAt(Instant.now());

        Wallet updatedWallet = walletRepository.save(wallet);

        StatusWalletDto statusWalletDto = new StatusWalletDto();
        statusWalletDto.setId(updatedWallet.getId());
        statusWalletDto.setName(updatedWallet.getName());
        statusWalletDto.setActive(updatedWallet.isActive());
        return statusWalletDto;
    }

//    public List<Wallet> getWalletsByUserId(Long userId) {
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isEmpty()){
//            throw new DataNotFoundException("user not found");
//        }
//        return walletRepository.findByUserId(user.get().getId());
//    }
}
