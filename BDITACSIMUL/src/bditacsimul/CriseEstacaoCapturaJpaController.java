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
public class CriseEstacaoCapturaJpaController implements Serializable {

    public CriseEstacaoCapturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriseEstacaoCaptura criseEstacaoCaptura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(criseEstacaoCaptura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriseEstacaoCaptura criseEstacaoCaptura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            criseEstacaoCaptura = em.merge(criseEstacaoCaptura);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criseEstacaoCaptura.getCecId();
                if (findCriseEstacaoCaptura(id) == null) {
                    throw new NonexistentEntityException("The criseEstacaoCaptura with id " + id + " no longer exists.");
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
            CriseEstacaoCaptura criseEstacaoCaptura;
            try {
                criseEstacaoCaptura = em.getReference(CriseEstacaoCaptura.class, id);
                criseEstacaoCaptura.getCecId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criseEstacaoCaptura with id " + id + " no longer exists.", enfe);
            }
            em.remove(criseEstacaoCaptura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriseEstacaoCaptura> findCriseEstacaoCapturaEntities() {
        return findCriseEstacaoCapturaEntities(true, -1, -1);
    }

    public List<CriseEstacaoCaptura> findCriseEstacaoCapturaEntities(int maxResults, int firstResult) {
        return findCriseEstacaoCapturaEntities(false, maxResults, firstResult);
    }

    private List<CriseEstacaoCaptura> findCriseEstacaoCapturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriseEstacaoCaptura.class));
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

    public CriseEstacaoCaptura findCriseEstacaoCaptura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriseEstacaoCaptura.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriseEstacaoCapturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriseEstacaoCaptura> rt = cq.from(CriseEstacaoCaptura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
