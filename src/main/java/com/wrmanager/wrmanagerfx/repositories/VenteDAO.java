package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.Vente;
import com.wrmanager.wrmanagerfx.entities.Vente;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class VenteDAO extends DAORepository<Vente,Long>{

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
            Optional.ofNullable(em.find(Vente.class,id)).ifPresent(Vente -> em.remove(Vente));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<Vente> getById(Long id) {
        return Optional.ofNullable(em.find(Vente.class,id));
    }

    @Override
    public Set<Vente> getAll() {
        List<Vente> Ventes = em.createQuery("SELECT c FROM Vente c").getResultList();
        return Ventes.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Vente").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }



}
