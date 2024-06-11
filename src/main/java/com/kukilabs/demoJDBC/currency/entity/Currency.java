package com.kukilabs.demoJDBC.currency.entity;

import com.kukilabs.demoJDBC.currency.repository.CurrencyRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Setter
@Getter
@Data
@Entity
@Table(name = "currency", schema = "montrack")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_gen")
    @SequenceGenerator(name = "currency_id_gen", sequenceName = "currency_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}
