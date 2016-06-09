/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditac;

import bditac.exceptions.NonexistentEntityException;
import bditac.exceptions.PreexistingEntityException;
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
public class CriseTipoTipoJpaController implements Serializable {

    public CriseTipoTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseTipoTipo criseTipoTipo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseTipoTipo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCriseTipoTipo(criseTipoTipo.getCttId()) != null) {
                throw new PreexistingEntityException("CriseTipoTipo " + criseTipoTipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseTipoTipo criseTipoTipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseTipoTipo = em.merge(criseTipoTipo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseTipoTipo.getCttId();
                if (findCriseTipoTipo(id) == null) {
                    throw new NonexistentEntityException("The criseTipoTipo with id " + id + " no longer exists.");
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
            CriseTipoTipo criseTipoTipo;
            try {
                criseTipoTipo = em.getReference(CriseTipoTipo.class, id);
                criseTipoTipo.getCttId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseTipoTipo with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseTipoTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseTipoTipo> findCriseTipoTipoEntities() {
        return findCriseTipoTipoEntities(true, -1, -1);
    }

    public List<CriseTipoTipo> findCriseTipoTipoEntities(int maxResults, int firstResult) {
        return findCriseTipoTipoEntities(false, maxResults, firstResult);
    }

    private List<CriseTipoTipo> findCriseTipoTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseTipoTipo.class));
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

    public CriseTipoTipo findCriseTipoTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseTipoTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseTipoTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseTipoTipo> rt = cq.from(CriseTipoTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
