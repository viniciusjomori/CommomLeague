package br.edu.ifsp.commomleague.app;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Boolean active;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createDate;

    private LocalDateTime editDate;

    @PrePersist
    private void onPersist() {
        this.setActive(true);
        this.setCreateDate(LocalDateTime.now());
    } 

    @PreUpdate
    private void onUpdate() {
        this.setEditDate(LocalDateTime.now());
    }

}
