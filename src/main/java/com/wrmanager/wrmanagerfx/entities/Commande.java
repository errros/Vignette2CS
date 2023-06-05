package com.wrmanager.wrmanagerfx.entities;

import com.wrmanager.wrmanagerfx.models.EtatCommande;
import javafx.beans.property.*;
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
public class Commande implements Serializable {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String client;


    private String num;

    private String adr;

    @Basic(optional = false)
    @Column(nullable = false)
    private Boolean paye ;

    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

    @Builder.Default
    @OneToMany(mappedBy = "commande",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<LigneCommande> lignesCommande = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vendeur_id",nullable = false )
    private Vendeur vendeur;


    public void addLigneCommande(LigneCommande lc){
        lc.setCommande(this);
        this.lignesCommande.add(lc);
    }
    public void removeLigneCommande(LigneCommande lc){
        lc.setCommande(null);
        this.lignesCommande.remove(lc);
    }

    public void addVendeur(Vendeur vendeur){
       this.setVendeur(vendeur);
       vendeur.getCommandes().add(this);
    }
    public void removeVendeur(Vendeur vendeur){
       this.setVendeur(null);
       vendeur.getCommandes().remove(this);
    }


    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", num='" + num + '\'' +
                ", adr='" + adr + '\'' +
                ", paye=" + paye +
                ", creeLe=" + creeLe +
                '}';
    }
}

