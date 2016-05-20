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
public class CriseTipoSubtipoJpaController implements Serializable {

    public CriseTipoSubtipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseTipoSubtipo criseTipoSubtipo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseTipoSubtipo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCriseTipoSubtipo(criseTipoSubtipo.getCtpId()) != null) {
                throw new PreexistingEntityException("CriseTipoSubtipo " + criseTipoSubtipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseTipoSubtipo criseTipoSubtipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseTipoSubtipo = em.merge(criseTipoSubtipo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseTipoSubtipo.getCtpId();
                if (findCriseTipoSubtipo(id) == null) {
                    throw new NonexistentEntityException("The criseTipoSubtipo with id " + id + " no longer exists.");
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
            CriseTipoSubtipo criseTipoSubtipo;
            try {
                criseTipoSubtipo = em.getReference(CriseTipoSubtipo.class, id);
                criseTipoSubtipo.getCtpId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseTipoSubtipo with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseTipoSubtipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseTipoSubtipo> findCriseTipoSubtipoEntities() {
        return findCriseTipoSubtipoEntities(true, -1, -1);
    }

    public List<CriseTipoSubtipo> findCriseTipoSubtipoEntities(int maxResults, int firstResult) {
        return findCriseTipoSubtipoEntities(false, maxResults, firstResult);
    }

    private List<CriseTipoSubtipo> findCriseTipoSubtipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseTipoSubtipo.class));
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

    public CriseTipoSubtipo findCriseTipoSubtipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseTipoSubtipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseTipoSubtipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseTipoSubtipo> rt = cq.from(CriseTipoSubtipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
