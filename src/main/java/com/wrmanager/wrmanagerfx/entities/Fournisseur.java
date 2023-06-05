package com.wrmanager.wrmanagerfx.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Fournisseur implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String nom;

    private String prenom;

    private String num1;

    private String num2;

    private String adr;

    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;


    @Builder.Default
    @OneToMany(mappedBy = "fournisseur")
    Set<Achat> achats = new HashSet<>();


    @Override
    public String toString() {
        return "Fournisseur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", num1='" + num1 + '\'' +
                ", num2='" + num2 + '\'' +
                ", adr='" + adr + '\'' +
                ", creeLe=" + creeLe +
                '}';
    }
}
