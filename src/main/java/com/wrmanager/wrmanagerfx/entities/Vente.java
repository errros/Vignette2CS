package com.wrmanager.wrmanagerfx.entities;

import javafx.beans.property.*;
import lombok.*;

import javax.persistence.*;
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
public class Vente {

     @EqualsAndHashCode.Include
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     private String client;

     @Builder.Default
     @Basic(optional = false)
     @Column(nullable = false)
     private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

     @Builder.Default
     @OneToMany(fetch = FetchType.EAGER,mappedBy = "vente")
     Set<LigneVente> ligneVentes = new HashSet<>();

     @Basic(optional = false)
     @Column(nullable = false)
     private Integer totale ;

     @ManyToOne(optional = false)
     @JoinColumn(name = "vendeur_id")
     private Vendeur vendeur;




     @Override
     public String toString() {
          return "Vente{" +
                  "id=" + id +
                  ", client='" + client + '\'' +
                  ", creeLe=" + creeLe +
                  ", totale=" + totale +
                  '}';
     }
}
