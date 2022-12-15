package org.example;

import org.example.model.Personne;
import org.example.model.PersonnePK;
import org.example.model.PersonneWithPK;

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

        //Récupération de nouvellePersonne avec find() et getReference()
        Personne personneWithPK = em.find(Personne.class, 1);

        //Récupération avec Query
        Personne personneFromQuery = (Personne) em.createQuery("select p from Personne p where p.id = 1").getSingleResult();

        System.out.println(personneWithPK);
        System.out.println(personneFromQuery);
        em.close();
        emf.close();

    }
}