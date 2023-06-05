package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.entities.Vendeur;
import com.wrmanager.wrmanagerfx.entities.Vendeur;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class VendeurDAO extends DAORepository<Vendeur,Long> {

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
            Optional.ofNullable(em.find(Vendeur.class,id)).ifPresent(Vendeur -> em.remove(Vendeur));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<Vendeur> getById(Long id) {
        return Optional.ofNullable(em.find(Vendeur.class,id));
    }

    @Override
    public Set<Vendeur> getAll() {
        List<Vendeur> Vendeurs = em.createQuery("SELECT c FROM Vendeur c").getResultList();
        return Vendeurs.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Vendeur").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }


}
