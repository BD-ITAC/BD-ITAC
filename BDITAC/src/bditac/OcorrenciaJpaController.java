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
public class OcorrenciaJpaController implements Serializable {

    public OcorrenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ocorrencia ocorrencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ocorrencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ocorrencia ocorrencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ocorrencia = em.merge(ocorrencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ocorrencia.getOcrId();
                if (findOcorrencia(id) == null) {
                    throw new NonexistentEntityException("The ocorrencia with id " + id + " no longer exists.");
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
            Ocorrencia ocorrencia;
            try {
                ocorrencia = em.getReference(Ocorrencia.class, id);
                ocorrencia.getOcrId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ocorrencia with id " + id + " no longer exists.", enfe);
            }
            em.remove(ocorrencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ocorrencia> findOcorrenciaEntities() {
        return findOcorrenciaEntities(true, -1, -1);
    }

    public List<Ocorrencia> findOcorrenciaEntities(int maxResults, int firstResult) {
        return findOcorrenciaEntities(false, maxResults, firstResult);
    }

    private List<Ocorrencia> findOcorrenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ocorrencia.class));
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

    public Ocorrencia findOcorrencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ocorrencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getOcorrenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ocorrencia> rt = cq.from(Ocorrencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Ocorrencia findOcorrenciaOcrid(int ocrid) {
        Ocorrencia oc = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ocorrencia.findByOcrId");
        query.setParameter("ocrId", ocrid);
        try {
            oc = (Ocorrencia) query.getSingleResult();
        } catch (javax.persistence.NoResultException enfe) {
            oc = null;
        } finally {
            em.close();
        }
        return oc;
    }

    public List<Ocorrencia> findOcorrenciaOcrGeo(Character car) {
        List rs;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Ocorrencia.findByOcrGeo");
        query.setParameter("ocrGeo", car);
        query.setMaxResults(100);
        rs = query.getResultList();
        em.close();
        return rs;
    }

    /*    @OrderBy("nome")
    public List<Cadastro> findCadastroPorNomes(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        int tempo = this.getCadastroCount();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            Root<Cadastro> cd = cq.from(Cadastro.class);
            cq.select(cd);
            cq.orderBy(cb.asc(cd.get("nome")));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            if (tempo > 1000) {
                q.setHint("javax.persistence.query.timeout", tempo + 2000);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
     */
    public List<Object[]> findOcorrenciaMsg() {
        List<Object[]> mensagem;
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT DISTINCT ocr_texto FROM ocorrencia");
        mensagem = query.getResultList();
        em.close();
        return mensagem;
    }
}
