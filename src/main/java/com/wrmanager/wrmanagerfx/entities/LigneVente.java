package com.wrmanager.wrmanagerfx.entities;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

//PK = idVent , idProduit

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class LigneVente implements Serializable {

     @EqualsAndHashCode.Include
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;


     @Builder.Default
     @Basic(optional = false)
     @Column(nullable = false)
     private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;


     @ManyToOne(optional = false)
     @JoinColumn(name = "produit_id",nullable = false)
     private Produit produit;

     @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},optional = false)
     @JoinColumn(name = "vente_id",nullable = false)
     private Vente vente;

     @Basic(optional = false)
     @Column(nullable = false)
     private Integer prixVenteUnite;
     @Basic(optional = false)
     @Column(nullable = false)
     private Integer prixVenteTotale;

     @Basic(optional = false)
     @Column(nullable = false)
     private Integer qty;


     @Override
     public String toString() {
          return "LigneVente{" +
                  "id=" + id +
                  ", creeLe=" + creeLe +
                  ", produit=" + produit.getCodeBarre() +
                  ", vente=" + vente.getId() +
                  ", prixVenteUnite=" + prixVenteUnite +
                  ", prixVenteTotale=" + prixVenteTotale +
                  ", qty=" + qty +
                  '}';
     }
}
