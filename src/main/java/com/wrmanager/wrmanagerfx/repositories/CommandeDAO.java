package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.Commande;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandeDAO extends DAORepository<Commande,Long> {

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
            Optional.ofNullable(em.find(Commande.class,id)).ifPresent(Commande -> em.remove(Commande));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }
    @Override
    public Commande update(Commande obj) {

        try {

            em.getTransaction().begin();
            em.find(Commande.class,obj.getId());
            em.merge(obj);

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return obj;


    }



    @Override
    public Optional<Commande> getById(Long id) {
        return Optional.ofNullable(em.find(Commande.class,id));
    }

    @Override
    public Set<Commande> getAll() {
        List<Commande> Commandes = em.createQuery("SELECT c FROM Commande c").getResultList();
        return Commandes.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Commande").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }



}
