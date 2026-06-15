package com.uchechukwu.store.entities;

import com.uchechukwu.store.enums.VehicleType;
import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "riders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;


    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Size(max = 50)
    @Column(name = "vehicle_plate_number")
    private String vehiclePlateNumber;


    @Column(name = "is_available")
    private Boolean isAvailable;


    @Column(name = "resource_type")
    private String resourceType;


    @Column(name = "image_hash")
    private String imageHash;


    @Column(name = "public_id")
    private String publicId;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;


    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Builder.Default
    @OneToMany(mappedBy = "rider", fetch = FetchType.LAZY)
    private List<Delivery> deliveries = new ArrayList<>();

    public String getFullName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
}