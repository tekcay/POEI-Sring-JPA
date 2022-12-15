package org.example;

import org.example.model.Personne;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demojpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Personne nouvellePersonne = new Personne();
        nouvellePersonne.setNom("toto");
        nouvellePersonne.setPrenom("tata");

        em.persist(nouvellePersonne);
        transaction.commit();
        em.close();
        emf.close();

    }
}