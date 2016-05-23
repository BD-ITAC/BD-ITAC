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
public class CriseTipoSubgrupoJpaController implements Serializable {

    public CriseTipoSubgrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseTipoSubgrupo criseTipoSubgrupo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseTipoSubgrupo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCriseTipoSubgrupo(criseTipoSubgrupo.getCtsId()) != null) {
                throw new PreexistingEntityException("CriseTipoSubgrupo " + criseTipoSubgrupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseTipoSubgrupo criseTipoSubgrupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseTipoSubgrupo = em.merge(criseTipoSubgrupo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseTipoSubgrupo.getCtsId();
                if (findCriseTipoSubgrupo(id) == null) {
                    throw new NonexistentEntityException("The criseTipoSubgrupo with id " + id + " no longer exists.");
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
            CriseTipoSubgrupo criseTipoSubgrupo;
            try {
                criseTipoSubgrupo = em.getReference(CriseTipoSubgrupo.class, id);
                criseTipoSubgrupo.getCtsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseTipoSubgrupo with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseTipoSubgrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseTipoSubgrupo> findCriseTipoSubgrupoEntities() {
        return findCriseTipoSubgrupoEntities(true, -1, -1);
    }

    public List<CriseTipoSubgrupo> findCriseTipoSubgrupoEntities(int maxResults, int firstResult) {
        return findCriseTipoSubgrupoEntities(false, maxResults, firstResult);
    }

    private List<CriseTipoSubgrupo> findCriseTipoSubgrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseTipoSubgrupo.class));
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

    public CriseTipoSubgrupo findCriseTipoSubgrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseTipoSubgrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseTipoSubgrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseTipoSubgrupo> rt = cq.from(CriseTipoSubgrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
