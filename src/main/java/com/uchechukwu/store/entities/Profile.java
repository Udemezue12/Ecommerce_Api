package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;
    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "image_hash")
    private String imageHash;
    @Column(name = "resource_type")
    private String resourceType;
    @Column(name = "public_id")
    private String publicId;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}