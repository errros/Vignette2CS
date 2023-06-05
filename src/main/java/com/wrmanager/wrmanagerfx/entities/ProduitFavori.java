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
@Table(name = "produitfavori")
@Entity
public class ProduitFavori{



    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;


    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;



    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private GroupeFavoris groupeFavoris;


    @Basic(optional = false)
    @Column(nullable = false,name = "roww")
    private Integer row;


    @Basic(optional = false)
    @Column(nullable = false,name = "columnn")
    private Integer column;


    @Override
    public String toString() {
        return "ProduitFavori{" +
                "id=" + id +
                ", produit=" + produit.getDesignation() +
                ", creeLe=" + creeLe +
                ", groupeFavoris=" + groupeFavoris.getNom() +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
