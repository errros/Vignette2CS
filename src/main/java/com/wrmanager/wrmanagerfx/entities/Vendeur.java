package com.wrmanager.wrmanagerfx.entities;

import com.wrmanager.wrmanagerfx.models.Role;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Vendeur implements Serializable {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EqualsAndHashCode.Include
    @Basic(optional = false)
    @Column(nullable = false , unique = true)
    private  String username;
    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    private String num;
    @Basic(optional = false)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @Basic(optional = false)
    @Column(nullable = false)
    private Timestamp creeLe  = Timestamp.valueOf(LocalDateTime.now());;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "vendeur")
      private Set<Vente> ventes = new HashSet<>() ;

    @Builder.Default
      @OneToMany(mappedBy = "vendeur",fetch = FetchType.LAZY)
      private Set<Achat> achats = new HashSet<>();

    @Builder.Default
      @OneToMany(fetch = FetchType.LAZY,mappedBy = "vendeur",cascade = {CascadeType.MERGE })
     private Set<Commande> commandes = new HashSet<>();


    @Override
    public String toString() {
        return "Vendeur{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", num='" + num + '\'' +
                ", role=" + role +
                ", creeLe=" + creeLe +
                '}';
    }
}

