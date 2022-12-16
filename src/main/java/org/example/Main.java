package org.example;

import org.example.model.Personne;
import org.example.model.PersonneWithPK;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demojpa");

        EntityManager em = emf.createEntityManager();

        EntityTransaction transac = em.getTransaction();

        transac.begin();

        //Création de Personne
        Personne nouvellePersonne;
        //pas de set de l'ID
        for(int i= 0; i<=20; i++){
            nouvellePersonne = new Personne();
            nouvellePersonne.setNom("nom"+(i+1));
            nouvellePersonne.setPrenom("prenom"+(i+1));
            em.persist(nouvellePersonne);
            transac.commit();
            transac.begin();
            nouvellePersonne = null;
        }



        PersonneWithPK personneWithPK = new PersonneWithPK();
        personneWithPK.setNom("titi");
        personneWithPK.setPrenom("tutu");
        em.merge(personneWithPK);
        transac.commit();

        // Récuperation de personne avec find et getReference
        transac.begin();

        Personne personne = em.find(Personne.class,1);
        System.out.println("Personne avec l'ID 1 (find) : "+personne.getNom()+" "+personne.getPrenom());

        Personne personne1 = em.getReference(Personne.class,1);
        System.out.println("Personne avec l'ID 1 (getReference) : "+personne1.getNom()+" "+personne1.getPrenom());


        transac.commit();

        // Reccuperation avec Query

        //createQuery single result
        Query query = em.createQuery("select p from Personne p where p.nom='nom12'");
        Personne personne2 =(Personne) query.getSingleResult();
        System.out.println("Personne avec le nom = nom12 a pour prenom "+personne2.getPrenom()+" et l'id : "+personne2.getId());


        //createQuery result list
        System.out.println("Liste des personnes avec l'id supérieur à 5");
        Query query1=em.createQuery("select  p from Personne p where p.id > 5");
        List noms = query1.getResultList();
        //on recupere une liste d'objet
        for(Object nom : noms){
            Personne tmp =(Personne) nom;
            System.out.println("nom = "+tmp.getNom());
        }

        // Utilisation du setParameter
        System.out.println("Liste de personne avec id superieur au parametre set");
        Query query2=em.createQuery("select p from Personne p where p.id > :id");
        query2.setParameter("id",15);
        List noms2 = query2.getResultList();
        //on recupere une liste d'objet
        for(Object nom : noms2){
            Personne tmp =(Personne) nom;
            System.out.println("nom = "+tmp.getNom());
        }

        //Modification

        em.close();
        emf.close();

    }
}