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
public class CriseTipoClasseJpaController implements Serializable {

    public CriseTipoClasseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseTipoClasse criseTipoClasse) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseTipoClasse);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCriseTipoClasse(criseTipoClasse.getCtcId()) != null) {
                throw new PreexistingEntityException("CriseTipoClasse " + criseTipoClasse + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseTipoClasse criseTipoClasse) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseTipoClasse = em.merge(criseTipoClasse);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseTipoClasse.getCtcId();
                if (findCriseTipoClasse(id) == null) {
                    throw new NonexistentEntityException("The criseTipoClasse with id " + id + " no longer exists.");
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
            CriseTipoClasse criseTipoClasse;
            try {
                criseTipoClasse = em.getReference(CriseTipoClasse.class, id);
                criseTipoClasse.getCtcId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseTipoClasse with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseTipoClasse);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseTipoClasse> findCriseTipoClasseEntities() {
        return findCriseTipoClasseEntities(true, -1, -1);
    }

    public List<CriseTipoClasse> findCriseTipoClasseEntities(int maxResults, int firstResult) {
        return findCriseTipoClasseEntities(false, maxResults, firstResult);
    }

    private List<CriseTipoClasse> findCriseTipoClasseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseTipoClasse.class));
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

    public CriseTipoClasse findCriseTipoClasse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseTipoClasse.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseTipoClasseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseTipoClasse> rt = cq.from(CriseTipoClasse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
