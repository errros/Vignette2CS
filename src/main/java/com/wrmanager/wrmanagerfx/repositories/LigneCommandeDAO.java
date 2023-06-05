package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.LigneCommande;
import com.wrmanager.wrmanagerfx.entities.LigneCommande;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LigneCommandeDAO extends DAORepository<LigneCommande,Long> {

    static {
        try {

            em = Persistence.createEntityManagerFactory("that-unit").createEntityManager();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void deleteById(Long id) {
        try {
            em.getTransaction().begin();
            Optional.ofNullable(em.find(LigneCommande.class,id)).ifPresent(LigneCommande -> em.remove(LigneCommande));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<LigneCommande> getById(Long id) {
        return Optional.ofNullable(em.find(LigneCommande.class,id));
    }

    @Override
    public Set<LigneCommande> getAll() {
        List<LigneCommande> LigneCommandes = em.createQuery("SELECT c FROM LigneCommande c").getResultList();
        return LigneCommandes.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM LigneCommande").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }



}
