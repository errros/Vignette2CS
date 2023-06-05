package com.wrmanager.wrmanagerfx.entities;

import com.wrmanager.wrmanagerfx.models.SystemMeasure;
import javafx.beans.property.*;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;



@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class LigneAchat implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

    @Basic(optional = false)
    @Column(nullable = false)
    private Float qtyTotale;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixAchatTotale;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixVenteTotale;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixAchatUnite;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prixVenteUnite;
    @Basic(optional = false)
    @Column(nullable = false)
    private Float qtyUnite;


    @OneToOne(mappedBy = "ligneAchat" , cascade = CascadeType.REMOVE)
    private Notification notification;


    private Date datePeremption;






    @ManyToOne
    private Produit produit;



    @ManyToOne(optional = false)
    @JoinColumn(name = "achat_id")
    private Achat achat;

    private String remarque;

    @Transient
    private Integer totale;





    public Integer getTotale() {
        return Math.round(qtyTotale * prixAchatTotale);
    }



    @Override
    public String toString() {
        return "LigneAchat{" +
                "id=" + id +
                ", creeLe=" + creeLe +
                ", qtyTotale=" + qtyTotale +
                ", prixAchatTotale=" + prixAchatTotale +
                ", prixVenteTotale=" + prixVenteTotale +
                ", prixAchatUnite=" + prixAchatUnite +
                ", prixVenteUnite=" + prixVenteUnite +
                ", qtyUnite=" + qtyUnite +
                ", datePeremption=" + datePeremption +
                ", produit=" + produit.getCodeBarre() +
                ", achat=" + achat.getId() +
                ", remarque='" + remarque + '\'' +
                '}';
    }
}


