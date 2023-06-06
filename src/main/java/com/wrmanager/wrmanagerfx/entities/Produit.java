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

//NOM
    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private String designation;




    private String forme;



    private String dosage;


    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;



    @Builder.Default
    @ManyToOne(optional = false , cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "categorie_id ",nullable = false)
    private Categorie categorie = DEFAULT_CATEGORIE_PRODUIT;



    @Builder.Default
    @OneToMany(mappedBy = "produit")
    private List<ProduitFavori> favoris = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "produit")
    private List<Stock> stocks = new ArrayList<>();


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
    public void addStock(Stock stock){
        stocks.add(stock);
        stock.setProduit(this);

    }
    public void removeStock(Stock stock){
        stocks.remove(stock);
        stock.setProduit(null);

    }


    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", codeBarre='" + codeBarre + '\'' +
                ", designation='" + designation + '\'' +
                ", creeLe=" + creeLe +
                ", categorie=" + categorie +
                ", favoris=" + favoris +
                ", imagePath=" + imagePath +
                '}';
    }
}
