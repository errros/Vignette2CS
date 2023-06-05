package com.wrmanager.wrmanagerfx.entities;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.models.SystemMeasure;
import lombok.*;

import javax.persistence.*;
import java.net.URI;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.wrmanager.wrmanagerfx.Constants.*;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produit {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private String codeBarre;


    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private String designation;
    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Float qtyTotale = 0f;
    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixAchatTotale = 0;
    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixVenteTotale = 0;
    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixAchatUnite = 0;
    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixVenteUnite = 0;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Basic(optional = false)
    @Column(nullable = false)
    private SystemMeasure systemMeasure = SystemMeasure.UNITE;

    @Basic(optional = false)
    @Column(nullable = false)
    private Float qtyUnite;

    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Boolean estPerissable = false;



    private Date datePeremption;


    @Builder.Default
    private Float qtyAlerte = DEFAULT_QTY_ALERTE_PRODUIT;

    private Integer joursAlerte;



    @Builder.Default
    @ManyToOne(optional = false , cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "categorie_id ",nullable = false)
    private Categorie categorie = DEFAULT_CATEGORIE_PRODUIT;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "achat" )
    private List<LigneAchat> ligneAchats = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "produit")
    private List<LigneVente> ligneVentes = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "produit")
    private List<ProduitFavori> favoris = new ArrayList<>();


    private URI imagePath;


    public void addCategorie(Categorie categorie){
        this.setCategorie(categorie);
        categorie.getProduits().add(this);

    }
    public void removeCategorie(Categorie categorie){
        this.setCategorie(DEFAULT_CATEGORIE_PRODUIT);
         categorie.getProduits().remove(this);

    }
    public void addFavori(ProduitFavori produitFavori){
        favoris.add(produitFavori);
        produitFavori.setProduit(this);

    }
    public void removeFavori(ProduitFavori produitFavori){
        favoris.remove(produitFavori);
        produitFavori.setProduit(null);

    }




    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", codeBarre='" + codeBarre + '\'' +
                ", designation='" + designation + '\'' +
                ", creeLe=" + creeLe +
                ", qtyTotale=" + qtyTotale +
                ", prixAchatTotale=" + prixAchatTotale +
                ", prixVenteTotale=" + prixVenteTotale +
                ", prixAchatUnite=" + prixAchatUnite +
                ", prixVenteUnite=" + prixVenteUnite +
                ", systemMeasure=" + systemMeasure +
                ", qtyUnite=" + qtyUnite +
                ", estPerissable=" + estPerissable +
                ", datePeremption=" + datePeremption +
                ", qtyAlerte=" + qtyAlerte +
                ", categorie=" + categorie.getNom() +
                '}';
    }
}
