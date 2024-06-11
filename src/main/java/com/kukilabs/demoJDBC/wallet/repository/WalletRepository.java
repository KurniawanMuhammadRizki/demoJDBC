package com.kukilabs.demoJDBC.wallet.repository;

import com.kukilabs.demoJDBC.wallet.dto.WalletDto;
import com.kukilabs.demoJDBC.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByUserId(Long userId);
}
