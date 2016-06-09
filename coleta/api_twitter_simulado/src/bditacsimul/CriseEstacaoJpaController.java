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
public class CriseEstacaoJpaController implements Serializable {

    public CriseEstacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseEstacao criseEstacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseEstacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseEstacao criseEstacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseEstacao = em.merge(criseEstacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseEstacao.getCreId();
                if (findCriseEstacao(id) == null) {
                    throw new NonexistentEntityException("The criseEstacao with id " + id + " no longer exists.");
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
            CriseEstacao criseEstacao;
            try {
                criseEstacao = em.getReference(CriseEstacao.class, id);
                criseEstacao.getCreId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseEstacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseEstacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseEstacao> findCriseEstacaoEntities() {
        return findCriseEstacaoEntities(true, -1, -1);
    }

    public List<CriseEstacao> findCriseEstacaoEntities(int maxResults, int firstResult) {
        return findCriseEstacaoEntities(false, maxResults, firstResult);
    }

    private List<CriseEstacao> findCriseEstacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseEstacao.class));
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

    public CriseEstacao findCriseEstacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseEstacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseEstacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseEstacao> rt = cq.from(CriseEstacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<CriseEstacao> findCriseEstacaoCriseId(int crise) {
        EntityManager em = getEntityManager();
        List<CriseEstacao> ce = null;
        Query query = em.createNamedQuery("CriseEstacao.findByCriId");
        query.setParameter("criId", crise);
        try {
            ce = query.getResultList();
        } catch (javax.persistence.NoResultException enfe) {
            ce = null;
        } finally {
            em.close();
        }
        return ce;
    }
}
