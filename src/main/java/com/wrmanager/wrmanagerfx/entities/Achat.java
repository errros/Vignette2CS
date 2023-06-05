package com.wrmanager.wrmanagerfx.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Achat implements Serializable {

     @EqualsAndHashCode.Include
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     @Builder.Default
     @Basic(optional = false)
     @Column(nullable = false)
     private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;


     private Integer totale;

     @Builder.Default
     private Boolean paye = true;

     @Builder.Default
     @OneToMany(fetch = FetchType.LAZY,mappedBy = "achat",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
     private List<LigneAchat> ligneAchats = new ArrayList<>();

     @ManyToOne
     @JoinColumn(nullable = false,name = "fournisseur_id")
     private Fournisseur fournisseur;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "vendeur_id" )
     private Vendeur vendeur;




     public Integer getTotale() {
          return  ligneAchats.stream().map(LigneAchat::getPrixVenteTotale).reduce(0,Integer::sum);
     }

     @Override
     public String toString() {
          return "Achat{" +
                  "id=" + id +
                  ", creeLe=" + creeLe +
                  ", totale=" + totale +
                  ", paye=" + paye +
                  '}';
     }
}
