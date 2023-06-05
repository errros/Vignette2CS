package com.wrmanager.wrmanagerfx.entities;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Stock {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Basic(optional = false)
    @Column(nullable = false)
    private String lot;



    @Basic(optional = false)
    @Column(nullable = false)
    private Integer ppa;



    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private Date expirationDate;



    private String fournisseur;


    private Integer qty = 0;


    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;


    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", lot='" + lot + '\'' +
                ", ppa=" + ppa +
                ", expirationDate=" + expirationDate +
                ", fournisseur='" + fournisseur + '\'' +
                ", qty=" + qty +
                ", produit=" + produit +
                '}';
    }
}
