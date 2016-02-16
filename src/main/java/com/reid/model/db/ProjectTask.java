package com.reid.model.db;


import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.UniqueConstraint;
import javax.persistence.Table;

@Entity
@Table(name="projectTask",
uniqueConstraints=
@UniqueConstraint(columnNames={"id"}))
@NamedQueries({
	@NamedQuery(name = ProjectTask.FIND_ALL_TASKS, query = "SELECT t from ProjectTask t"),
	@NamedQuery(name = ProjectTask.FIND_TASK_BY_ID, query = "SELECT t FROM ProjectTask t where t.id=:id order by id")
})
public class ProjectTask implements Comparable<ProjectTask>{
	
	public static final String FIND_TASK_BY_ID = "findTaskById";
	public static final String FIND_ALL_TASKS = "findAllProjectTask";
	public static final String NAME = "ProjectTask";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotEmpty
	@Column(name="taskName",columnDefinition = "VARCHAR(500)")
	private String taskName;
	
  @Version
  @Column
  private Integer version;

	public Long getId() {
		return id;
	}	

	public void setId(Long projectId) {
		this.id = projectId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	@Override
  public String toString() {
    return (new ReflectionToStringBuilder(this) {
      protected boolean accept(Field f) {
          return super.accept(f);
      }
    }).toString();
  }
	
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(this.getClass())
        .append(id).append(taskName).toHashCode();
  }
	
	//Comparable Implementation
	@Override
	public int compareTo(ProjectTask o) {
		return new CompareToBuilder()
    .append(id, o.getId())
    .append(taskName, o.getTaskName()).toComparison();
	}
	
	 @Override
	  public boolean equals(Object object) {
	    if (object == null)
	      return false;
	    if (object == this)
	      return true;
	    if (object.getClass() != this.getClass())
	      return false;

	    ProjectTask o = (ProjectTask) object;
	    return new EqualsBuilder()
	        .append(id, o.getId())
	        .append(taskName, o.getTaskName())
	        .isEquals();
	  } 
	
	

}
