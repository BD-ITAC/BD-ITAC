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
public class AbreviacaoJpaController implements Serializable {

    public AbreviacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Abreviacao abreviacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(abreviacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Abreviacao abreviacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            abreviacao = em.merge(abreviacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = abreviacao.getAbvId();
                if (findAbreviacao(id) == null) {
                    throw new NonexistentEntityException("The abreviacao with id " + id + " no longer exists.");
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
            Abreviacao abreviacao;
            try {
                abreviacao = em.getReference(Abreviacao.class, id);
                abreviacao.getAbvId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The abreviacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(abreviacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Abreviacao> findAbreviacaoEntities() {
        return findAbreviacaoEntities(true, -1, -1);
    }

    public List<Abreviacao> findAbreviacaoEntities(int maxResults, int firstResult) {
        return findAbreviacaoEntities(false, maxResults, firstResult);
    }

    private List<Abreviacao> findAbreviacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Abreviacao.class));
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

    public Abreviacao findAbreviacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Abreviacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAbreviacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Abreviacao> rt = cq.from(Abreviacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Abreviacao findAbreviacaoAbrev(String abrev) {
        Abreviacao ab = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Abreviacao.findByAbvAbreviacao");
        query.setParameter("abvAbreviacao", abrev);
        try {
            ab = (Abreviacao) query.getSingleResult();
        } catch (javax.persistence.NoResultException enfe) {
            ab = null;
        } finally {
            em.close();
        }
        return ab;
    }
}
