package com.wrmanager.wrmanagerfx.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;



@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Notification {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String contenu;


    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

    @Builder.Default
    private Boolean vu = false;


    @OneToOne
    @JoinColumn(name = "ligneAchat_id")
    private LigneAchat ligneAchat;


    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", creeLe=" + creeLe +
                ", vu=" + vu +
                ", ligneAchat=" + ligneAchat +
                '}';
    }
}
