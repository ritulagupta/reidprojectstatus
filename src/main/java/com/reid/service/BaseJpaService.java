package com.reid.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.reid.exception.ServiceException;


public class BaseJpaService<T,K> {
	
	
	@PersistenceUnit
  private EntityManagerFactory entityManagerFactory;
	
	public final static String FIND_ALL_PREFIX     = "findAll"; 
  
  protected List<T> cachedModels;
	  
  public T create(T entity) throws ServiceException {
    EntityManager em = getEntityManager();
    EntityTransaction t = em.getTransaction();
    
    try {
      t.begin();
      em.persist(entity);
      t.commit();
      return entity;
    }
    catch (Exception ex) {
      throw handleJpaException(t, ex, "create()");
    }    
    finally {
      safelyCloseEntityManager(em);
    }
  }
  
  public T update(T entity)  throws ServiceException{
    EntityManager em = getEntityManager();
    EntityTransaction t = em.getTransaction();
    
    try {
      t.begin();
      System.out.println("Entity "+ entity);
      entity = em.merge(entity);
      em.lock(entity, LockModeType.OPTIMISTIC_FORCE_INCREMENT); //TODO - is this the default anyway?
      t.commit();  
      return entity;
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      throw handleJpaException(t, ex, "update()");
    }    
    finally {
      safelyCloseEntityManager(em);
    }
  }
  
  public T createOrUpdate(T entity) throws ServiceException {
    throw new ServiceException("OPERATION_FAILED", "Operation not supported");
  }
  
 
  public void remove(T entity) throws ServiceException {
    EntityManager em = getEntityManager();
    EntityTransaction t = em.getTransaction();
    
    try {
      t.begin();
      em.remove(em.merge(entity));
      t.commit();
    }
    catch (Exception ex) {
      throw handleJpaException(t, ex, "remove()");
    }    
    finally {
      safelyCloseEntityManager(em);
    }
  }
	  
   public T findById(K id) throws ServiceException {
     EntityManager em = getEntityManager();
     try {
       return em.find(getParameterizedClass(), id);
     }
     catch (Exception ex) {
       ex.printStackTrace();
       throw new ServiceException("OPERATION_FAILED", "Could not perform findById(" + id + ")", ex);
     }finally {
       safelyCloseEntityManager(em);
     }
   }

	  @SuppressWarnings("unchecked")
	  public List<T> findByNamedQuery(String queryName) throws ServiceException {
	    EntityManager em = getEntityManager();
	    try {
	      return em.createNamedQuery(queryName).getResultList();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	      throw new ServiceException("OPERATION_FAILED", "Could not perform findByNamedQuery(" + queryName + ")", ex);
	    }finally {
	      safelyCloseEntityManager(em);
	    }
	  }
	  
	  @SuppressWarnings("unchecked")
	  public List<T> findByQuery(Query query) throws ServiceException {
	    
	    try {
	      return query.getResultList();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	      throw new ServiceException("OPERATION_FAILED", "Could not perform findByQuery(" + query.toString() + ")", ex);
	    }
	  }
	  
	  @SuppressWarnings("unchecked")
	  public T findSingleResult(Query query) throws ServiceException {
	    //query.setMaxResults(1);
	    T obj  = null;
	    try {
	      obj = (T) query.getSingleResult();
	    }
	    catch(Exception ex) {
	      //ex.printStackTrace();
	      throw new ServiceException("OPERATION_FAILED", "Could not perform findSingleResultOrNull(" + query.toString() + ")", ex);
	    }
	    if (obj == null ) {
	     throw new ServiceException("NOT_FOUND", " No data found for findSingleResultOrNull(" + query.toString() + ")");
	    }
	    return obj;
	  }
	  
	  // proposed thread-safe replacement for findByQuery above
	  @SuppressWarnings("unchecked")
	  public List<T> findByQuery(final String queryName, Map<String,Object> params) throws ServiceException {
	    
	    EntityManager em = getEntityManager();
	    Query query = em.createNamedQuery(queryName);
	    for (Entry<String,?> entry : params.entrySet()) {
	      query.setParameter(entry.getKey(), entry.getValue());
	    }
	    
	    try {
	      return query.getResultList();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	      throw new ServiceException("OPERATION_FAILED", "Could not perform findByQuery(" + query.toString() + ")", ex);
	    } finally {
	      safelyCloseEntityManager(em);
	    }
	  }
	  
	//proposed thread-safe replacement for findSingleResult above
	  @SuppressWarnings("unchecked")
	  public T findSingleResult(final String queryName, Map<String,Object> params) throws ServiceException {
	    
	    EntityManager em = getEntityManager();
	    Query query = em.createNamedQuery(queryName);
	    for (Entry<String,?> entry : params.entrySet()) {
	      query.setParameter(entry.getKey(), entry.getValue());
	    }
	    
	    T obj  = null;
	    try {
	      obj = (T) query.getSingleResult();
	    }
	    catch(Exception ex) {
	      //ex.printStackTrace();
	      throw new ServiceException("OPERATION_FAILED", "Could not perform findSingleResult(" + query.toString() + ")", ex);
	    } finally {
	      safelyCloseEntityManager(em);
	    }
	    if (obj == null ) {
	     throw new ServiceException("NOT_FOUND", " No data found for findSingleResult(" + query.toString() + ")");
	    }
	    
	    return obj;
	  }
	  
	  @SuppressWarnings("unchecked")
	  public List<T> findAll() throws ServiceException {
	    EntityManager em = getEntityManager();
	    try {
	      return em.createNamedQuery(FIND_ALL_PREFIX + getParameterizedClassName()).getResultList();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	      throw new ServiceException("OPERATION_FAILED", "Could not perform findAll()", ex);
	    } finally {
	      safelyCloseEntityManager(em);
	    }
	  }
	  
//	  @SuppressWarnings("unchecked")
//	  public Set<T> findAllSet() throws ServiceException {
//	    EntityManager em = getEntityManager();
//	    try {
//	      return (HashSet<T>) em.createNamedQuery(FIND_ALL_PREFIX + getParameterizedClassName()).getResultList();      
//	    }
//	    catch (Exception ex) {
//	      ex.printStackTrace();
//	      throw new ServiceException(MessageCode.OPERATION_FAILED, "Could not perform findAllSet()", ex);
//	    } finally {
//	      safelyCloseEntityManager(em);
//	    }
//	  }
//	  
//	  public Long getTotalResult() throws ServiceException {
//	    EntityManager em = getEntityManager();
//	    try {
//	      return (Long) em.createNamedQuery(TOTAL_RESULT_PREFIX + getParameterizedClassName()).getSingleResult();
//	    }
//	    catch (Exception ex) {
//	      throw new ServiceException(MessageCode.OPERATION_FAILED, "Could not perform getTotalResult()", ex);
//	    } finally {
//	      safelyCloseEntityManager(em);
//	    }
//	  }
//	  
	  
	  
  public EntityManager getEntityManager() {
    return entityManagerFactory.createEntityManager();
  }
  
  // Protected methods -----------------------------------------------------------------------------------------------------
	
  
  protected Query getQuery(String queryName) {
    return getEntityManager().createNamedQuery(queryName);
  }
	  
	
  protected void safelyCloseEntityManager(EntityManager em) {
    if (em != null) {
      em.close();
    }
  }
  
  // Private methods -------------------------------------------------------------------------------------------------------

  
  private ServiceException handleJpaException(EntityTransaction t, Exception ex, String operationName) throws ServiceException {
    ex.printStackTrace();
    try {
      t.rollback();
    } 
    catch (Exception ex2) {
     return new ServiceException("OPERATION_FAILED", "Could not perform operation: " + operationName, ex2);
    }
    return new ServiceException("OPERATION_FAILED", "Could not perform operation: " + operationName, ex);
  }
  
  
  @SuppressWarnings("unchecked")
	private Class<T> getParameterizedClass() {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
  }
  
  private String getParameterizedClassName() {
    String fullClassName = getParameterizedClass().getCanonicalName();
    return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
  }
	  
}

