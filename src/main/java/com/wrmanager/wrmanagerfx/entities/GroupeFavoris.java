package com.wrmanager.wrmanagerfx.entities;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class GroupeFavoris {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String nom;


    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;



    @Builder.Default
    @OneToMany(mappedBy = "groupeFavoris",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<ProduitFavori> produitFavoris = new ArrayList<>();


    public void addProduitFavori(ProduitFavori pf){

        produitFavoris.add(pf);
        pf.getProduit().getFavoris().add(pf);
        pf.setGroupeFavoris(this);

    }
    public void removeProduitFavori(ProduitFavori pf){


        produitFavoris.remove(pf);
        pf.setGroupeFavoris(null);

    }

    @Override
    public String toString() {
        return "GroupeFavoris{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", produitFavoris=" + produitFavoris.stream().map(produitFavori -> produitFavori.getProduit().getDesignation()).collect(Collectors.toSet()) +
                '}';
    }
}
