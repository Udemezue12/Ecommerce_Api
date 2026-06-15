package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;
    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "zip")
    private String zip;
    @Column(name = "local_government")
    private String localGovernment;

    @Column(name = "state")
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}