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
public class CriseTipoGrupoJpaController implements Serializable {

    public CriseTipoGrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseTipoGrupo criseTipoGrupo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseTipoGrupo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCriseTipoGrupo(criseTipoGrupo.getCtgId()) != null) {
                throw new PreexistingEntityException("CriseTipoGrupo " + criseTipoGrupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseTipoGrupo criseTipoGrupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseTipoGrupo = em.merge(criseTipoGrupo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseTipoGrupo.getCtgId();
                if (findCriseTipoGrupo(id) == null) {
                    throw new NonexistentEntityException("The criseTipoGrupo with id " + id + " no longer exists.");
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
            CriseTipoGrupo criseTipoGrupo;
            try {
                criseTipoGrupo = em.getReference(CriseTipoGrupo.class, id);
                criseTipoGrupo.getCtgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseTipoGrupo with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseTipoGrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseTipoGrupo> findCriseTipoGrupoEntities() {
        return findCriseTipoGrupoEntities(true, -1, -1);
    }

    public List<CriseTipoGrupo> findCriseTipoGrupoEntities(int maxResults, int firstResult) {
        return findCriseTipoGrupoEntities(false, maxResults, firstResult);
    }

    private List<CriseTipoGrupo> findCriseTipoGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseTipoGrupo.class));
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

    public CriseTipoGrupo findCriseTipoGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseTipoGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseTipoGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseTipoGrupo> rt = cq.from(CriseTipoGrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
