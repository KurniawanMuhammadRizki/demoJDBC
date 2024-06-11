package com.kukilabs.demoJDBC.wallet.entity;

import com.kukilabs.demoJDBC.currency.entity.Currency;
import com.kukilabs.demoJDBC.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.bind.Name;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "Wallet", schema = "montrack")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_id_gen")
    @SequenceGenerator(name = "wallet_id_gen", sequenceName = "wallet_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Start amount cannot be null")
    @Min(value = 0, message = "Start amount must be zero or positive")
    @Column(name = "start_amount", nullable = false)
    private Long startAmount;

    @NotNull(message = "Current amount cannot be null")
    @Min(value = 0, message = "Current amount must be zero or positive")
    @Column(name = "current_amount", nullable = false)
    private Long currentAmount;


    @ColumnDefault("FALSE")
    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull(message = "Currency cannot be null")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
