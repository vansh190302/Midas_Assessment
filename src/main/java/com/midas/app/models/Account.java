package com.midas.app.models;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
public enum ProviderType {
    STRIPE
}

public class User {
    // Existing fields

    private ProviderType providerType;
    private String providerId;

    // Getters and setters for the new fields
}
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
  @Id
  @Column(name = "id")
  @GeneratedValue
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "created_at")
  @CreationTimestamp
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private OffsetDateTime updatedAt;
}
@RestController
@RequestMapping("/signup")
public class SignupController {

    @PostMapping
    public ResponseEntity<String> signup(@RequestBody User user) {
        user.setProviderType(ProviderType.STRIPE);
        Workflow.newExternalWorkflow(SignupWorkflow.class, user.getId()).signupWorkflow(user.getId());

        return ResponseEntity.ok("Signup successful");
    }
}