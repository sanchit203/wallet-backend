package com.walletbackend.entity;

import com.walletbackend.constants.UniqueConstraintName;
import com.walletbackend.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user", schema = "public", indexes = {
        @Index(columnList = "username", name = "username_user_idx")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = UniqueConstraintName.USERNAME_USER_UX),
        @UniqueConstraint(columnNames = "email", name = UniqueConstraintName.EMAIL_USER_UX)
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Column(name = "withdrawable_amount", nullable = false)
    private Double withdrawableAmount = 0.00;

    @Column(name = "bank_account_number", nullable = false)
    private String bankAccountNumber = "";

    @Column(name = "name_on_bank_account", nullable = false)
    private String nameOnBankAccount = "";

    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode = "";

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
