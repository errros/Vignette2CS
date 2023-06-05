package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FournisseurDAO extends DAORepository<Fournisseur,Long >{

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
            Optional.ofNullable(em.find(Fournisseur.class,id)).ifPresent(Fournisseur -> em.remove(Fournisseur));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<Fournisseur> getById(Long id) {
        return Optional.ofNullable(em.find(Fournisseur.class,id));
    }

    @Override
    public Set<Fournisseur> getAll() {
        List<Fournisseur> Fournisseurs = em.createQuery("SELECT c FROM Fournisseur c").getResultList();
        return Fournisseurs.stream().collect(Collectors.toSet());

    }

    @Override
    public Fournisseur update(Fournisseur obj) {

        try {

            em.getTransaction().begin();
            em.find(Fournisseur.class,obj.getId());
            em.merge(obj);

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return obj;


    }


    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Fournisseur").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }


}
