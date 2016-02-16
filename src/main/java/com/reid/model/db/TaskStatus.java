package com.reid.model.db;

import java.lang.reflect.Field;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Version;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TaskStatus",
uniqueConstraints=
@UniqueConstraint(columnNames={"id"}))
@NamedQueries({
  @NamedQuery(name = TaskStatus.FIND_ALL_TASKSTATUS , query = "SELECT c FROM TaskStatus c"),
  @NamedQuery(name = TaskStatus.FIND_BY_ID , query = "SELECT c FROM TaskStatus c where c.id = :id"),
  @NamedQuery(name = TaskStatus.FIND_BY_BUILDING , query = "SELECT c FROM TaskStatus c where c.building.id = :id"),
  @NamedQuery(name = TaskStatus.FIND_BY_EMPLOYEE , query = "SELECT c FROM TaskStatus c where c.employee.id = :id"),
  @NamedQuery(name = TaskStatus.FIND_BY_BUILDING_AND_EMPLOYEE , query = "SELECT c FROM TaskStatus c where c.employee.id = :employeeId and c.building.id = :buildingId" )
})
public class TaskStatus implements Comparable<TaskStatus>{
  
  public static final String NAME = "TaskStatus";
  public static final String FIND_BY_ID = "findById";
  public static final String FIND_ALL_TASKSTATUS = "findAllTaskStatus";
  public static final String FIND_BY_BUILDING = "findTaskStatusByBuidling";
  public static final String FIND_BY_EMPLOYEE = "findTaskStatusByEmployee";
  public static final String FIND_BY_BUILDING_AND_EMPLOYEE = "findTaskStatusByBuildingAndEmployee";
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@JsonIgnore
  @ManyToOne(cascade =  {CascadeType.MERGE},fetch = FetchType.EAGER)
  @JoinColumn(unique = false, nullable = false, insertable = true, updatable = true)
  private ProjectTask projectTask;
	
	@JsonIgnore
	@ManyToOne(cascade =  {CascadeType.MERGE},fetch = FetchType.EAGER)
	@JoinColumn(unique = false, nullable = false, insertable = true, updatable = true)
  private Building building;
	
	@JsonIgnore
  @ManyToOne(cascade =  {CascadeType.MERGE},fetch = FetchType.EAGER)
	@JoinColumn(unique = false, nullable = false, insertable = true, updatable = true)
  private Employee employee;
	

  @Enumerated(EnumType.STRING)
  private StatusType statusType;
  
  @Version
  @Column
  private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectTask getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTask = projectTask;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	public StatusType getStatusType() {
		return statusType;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public Integer getVersion(){
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
        .append(id).append(projectTask).append(building).append(employee).toHashCode();
  }

  
	//Comparable Implementation
  public int compareTo(TaskStatus o) {	
     return new CompareToBuilder()
     .append(id, o.getId())
     .append(projectTask,o.getProjectTask())
     .append(building,o.getBuilding())
     .append(employee, o.getEmployee())
     .toComparison();
  }
  
  @Override
  public boolean equals(Object object) {
    if (object == null)
      return false;
    if (object == this)
      return true;
    if (object.getClass() != this.getClass())
      return false;

    TaskStatus o = (TaskStatus) object;
    return new EqualsBuilder()
        .append(id, o.getId())
        .append(building, o.getBuilding())
        .append(projectTask, o.getProjectTask())
        .append(employee,o.getEmployee())
        .append(statusType,o.getStatusType())
        .isEquals();
  } 
	
  
}
