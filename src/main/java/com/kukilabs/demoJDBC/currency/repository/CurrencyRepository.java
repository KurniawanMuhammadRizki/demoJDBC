package com.kukilabs.demoJDBC.currency.repository;

import com.kukilabs.demoJDBC.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
