/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

import bditacsimul.exceptions.NonexistentEntityException;
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
public class CriseSensorTipoJpaController implements Serializable {

    public CriseSensorTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseSensorTipo criseSensorTipo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseSensorTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseSensorTipo criseSensorTipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseSensorTipo = em.merge(criseSensorTipo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseSensorTipo.getCstId();
                if (findCriseSensorTipo(id) == null) {
                    throw new NonexistentEntityException("The criseSensorTipo with id " + id + " no longer exists.");
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
            CriseSensorTipo criseSensorTipo;
            try {
                criseSensorTipo = em.getReference(CriseSensorTipo.class, id);
                criseSensorTipo.getCstId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseSensorTipo with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseSensorTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseSensorTipo> findCriseSensorTipoEntities() {
        return findCriseSensorTipoEntities(true, -1, -1);
    }

    public List<CriseSensorTipo> findCriseSensorTipoEntities(int maxResults, int firstResult) {
        return findCriseSensorTipoEntities(false, maxResults, firstResult);
    }

    private List<CriseSensorTipo> findCriseSensorTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseSensorTipo.class));
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

    public CriseSensorTipo findCriseSensorTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseSensorTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseSensorTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseSensorTipo> rt = cq.from(CriseSensorTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
