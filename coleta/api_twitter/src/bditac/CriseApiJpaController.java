/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditac;

import bditac.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Henrique
 */
public class CriseApiJpaController implements Serializable {

    public CriseApiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseApi criseApi) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseApi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseApi criseApi) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseApi = em.merge(criseApi);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseApi.getCapId();
                if (findCriseApi(id) == null) {
                    throw new NonexistentEntityException("The criseApi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CriseApi criseApi;
            try {
                criseApi = em.getReference(CriseApi.class, id);
                criseApi.getCapId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseApi with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseApi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseApi> findCriseApiEntities() {
        return findCriseApiEntities(true, -1, -1);
    }

    public List<CriseApi> findCriseApiEntities(int maxResults, int firstResult) {
        return findCriseApiEntities(false, maxResults, firstResult);
    }

    private List<CriseApi> findCriseApiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseApi.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CriseApi findCriseApi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseApi.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseApiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseApi> rt = cq.from(CriseApi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
