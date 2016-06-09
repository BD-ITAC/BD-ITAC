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
public class PalavraJpaController implements Serializable {

    public PalavraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Palavra palavra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(palavra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Palavra palavra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            palavra = em.merge(palavra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palavra.getPalId();
                if (findPalavra(id) == null) {
                    throw new NonexistentEntityException("The palavra with id " + id + " no longer exists.");
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
            Palavra palavra;
            try {
                palavra = em.getReference(Palavra.class, id);
                palavra.getPalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palavra with id " + id + " no longer exists.", enfe);
            }
            em.remove(palavra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palavra> findPalavraEntities() {
        return findPalavraEntities(true, -1, -1);
    }

    public List<Palavra> findPalavraEntities(int maxResults, int firstResult) {
        return findPalavraEntities(false, maxResults, firstResult);
    }

    private List<Palavra> findPalavraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palavra.class));
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

    public Palavra findPalavra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palavra.class, id);
        } finally {
            em.close();
        }
    }

    public int getPalavraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palavra> rt = cq.from(Palavra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Palavra findPalavraPalavra(String pal) {
        Palavra pl = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Palavra.findByPalPalavra");
        query.setParameter("palPalavra", pal);
        try {
            pl = (Palavra) query.getSingleResult();
        } catch (javax.persistence.NoResultException enfe) {
            pl = null;
        } finally {
            em.close();
        }
        return pl;
    }

}
