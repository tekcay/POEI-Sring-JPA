package org.example.exercice1;

import org.example.model.Personne;

import javax.persistence.*;

public class Test {
    
    public static void init() {
        System.out.println("Hello world!");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demojpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transac = em.getTransaction();

        transac.begin();
        createProducts(5, em, transac);
        transac.commit();

        transac.begin();
        System.out.println(getInfoOfProductById(2, em));
        transac.commit();


        transac.begin();
        removeProductById(2, em);
        transac.commit();

        transac.begin();
        modifyProductPriceById(3, em, 10);
        transac.commit();

        em.close();
        emf.close();

    }

    private static void createProducts(int amount, EntityManager em, EntityTransaction transac) {
        Produit produit;

        //pas de set de l'ID
        for(int i = 0; i < 5; i++){
            produit = new Produit();
            produit.setMarque("marque" + i);
            produit.setReference("reference" + i);
            produit.setPrix(i);
            produit.setStock(i * i);
            em.persist(produit);
            transac.commit();
            transac.begin();
            produit = null;
        }
    }

    private static Produit getInfoOfProductById(int id, EntityManager em) {
        return (Produit) em.createNativeQuery("SELECT * from produit WHERE id=" + id, Produit.class).getSingleResult();
    }

    private static void removeProductById(int id, EntityManager em) {
        em.remove(getInfoOfProductById(id, em));
    }


    private static void modifyProductPriceById(int id, EntityManager em, double price) {
        Produit produit = getInfoOfProductById(id, em);
        produit.setPrix(price);
        em.flush();
    }

}
