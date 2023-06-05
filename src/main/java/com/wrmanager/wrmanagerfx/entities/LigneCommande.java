package com.wrmanager.wrmanagerfx.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class LigneCommande implements Serializable {

   @EqualsAndHashCode.Include
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String produit;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer qty;

    private String remarque;


    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

    @ManyToOne(optional = false)
    @JoinColumn(name = "commande_id")
    private Commande commande;


 @Override
 public String toString() {
  return "LigneCommande{" +
          "id=" + id +
          ", produit='" + produit + '\'' +
          ", qty=" + qty +
          ", remarque='" + remarque + '\'' +
          ", creeLe=" + creeLe +
          ", commande=" +
          '}';
 }
}
