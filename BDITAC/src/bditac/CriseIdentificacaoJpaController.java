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
public class CriseIdentificacaoJpaController implements Serializable {

    public CriseIdentificacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseIdentificacao criseIdentificacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseIdentificacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseIdentificacao criseIdentificacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseIdentificacao = em.merge(criseIdentificacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseIdentificacao.getCiiId();
                if (findCriseIdentificacao(id) == null) {
                    throw new NonexistentEntityException("The criseIdentificacao with id " + id + " no longer exists.");
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
            CriseIdentificacao criseIdentificacao;
            try {
                criseIdentificacao = em.getReference(CriseIdentificacao.class, id);
                criseIdentificacao.getCiiId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseIdentificacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseIdentificacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseIdentificacao> findCriseIdentificacaoEntities() {
        return findCriseIdentificacaoEntities(true, -1, -1);
    }

    public List<CriseIdentificacao> findCriseIdentificacaoEntities(int maxResults, int firstResult) {
        return findCriseIdentificacaoEntities(false, maxResults, firstResult);
    }

    private List<CriseIdentificacao> findCriseIdentificacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseIdentificacao.class));
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

    public CriseIdentificacao findCriseIdentificacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseIdentificacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseIdentificacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseIdentificacao> rt = cq.from(CriseIdentificacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<CriseIdentificacao> findCriseIdentificacaoTipo(int tipo) {
        List rs;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("CriseIdentificacao.findByCiiTipo");
        query.setParameter("ciiTipo", tipo);
        rs = query.getResultList();
        em.close();
        return rs;
    }
}
