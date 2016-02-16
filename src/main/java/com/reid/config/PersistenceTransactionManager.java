package com.reid.config;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Component;


@Component("persistenceTransactionManager")
public class PersistenceTransactionManager implements Runnable {
  
  
  private static PersistenceTransactionManager manager;
  
  @PersistenceUnit(unitName="testsPU")
  private EntityManagerFactory emf;
  
  public PersistenceTransactionManager(){  
  	System.out.println("Creating persistence transaction manager");
 
  }
  
  public EntityManagerFactory getEmf() {
    return emf;
  }

  public void setEmf(EntityManagerFactory emf) {
    this.emf = emf;
  }
  
  
  public EntityManager retrieveEntityManager() {
    return emf.createEntityManager();
  }
  
   public void initialisation() {
   }

  @Override
  public void run() {
    manager = this;
    new Thread(manager).start();
    
  }
  
  public void shutdown(){
    
  }
  
}