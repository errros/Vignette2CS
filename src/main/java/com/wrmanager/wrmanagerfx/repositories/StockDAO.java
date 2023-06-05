package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.entities.Stock;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StockDAO extends DAORepository<Stock,Long>{
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
            Optional.ofNullable(em.find(Stock.class,id)).ifPresent(Stock -> {
                        em.remove(Stock);
                    }
            );
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }



    }

    @Override
    public Optional<Stock> getById(Long id) {
        return Optional.ofNullable(em.find(Stock.class,id));
    }

    @Override
    public Set<Stock> getAll() {
        List<Stock> Stocks = em.createQuery("SELECT c FROM Stock s").getResultList();
        return Stocks.stream().collect(Collectors.toSet());
    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Stock").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }



    }
}
