package com.wrmanager.wrmanagerfx.entities;

import static com.wrmanager.wrmanagerfx.Constants.*;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
public class Categorie implements Serializable {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private String nom;
    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer marge = DEFAULT_MARGE_CATEGORIE;

    @Builder.Default
    private Integer joursAlerte = DEFAULT_JOURS_ALERTE_CATEGORIE;


    private Float qtyAlerte = DEFAULT_QTY_ALERTE_PRODUIT;


    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "categorie")
    private List<Produit> produits = new ArrayList<>();
































































































/*

    public void addProduit(Produit produit){

        this.produits.add(produit);
        produit.setCategorie(this);
    }
    public void removeProduit(Produit produit){

        this.produits.remove(produit);
        produit.setCategorie(new Categorie("Autres",20,10));
    }
*/

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marge=" + marge +
                ", joursAlerte=" + joursAlerte +
                ", creeLe=" + creeLe +
                '}';
    }
}





