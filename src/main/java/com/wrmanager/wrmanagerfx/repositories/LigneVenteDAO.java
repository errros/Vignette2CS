package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.LigneVente;
import com.wrmanager.wrmanagerfx.entities.LigneVente;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LigneVenteDAO extends DAORepository<LigneVente,Long> {

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
            Optional.ofNullable(em.find(LigneVente.class,id)).ifPresent(LigneVente -> em.remove(LigneVente));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<LigneVente> getById(Long id) {
        return Optional.ofNullable(em.find(LigneVente.class,id));
    }

    @Override
    public Set<LigneVente> getAll() {
        List<LigneVente> LigneVentes = em.createQuery("SELECT c FROM LigneVente c").getResultList();
        return LigneVentes.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM LigneVente").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }





}
