package com.reid.model.db;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="employee",
uniqueConstraints=
@UniqueConstraint(columnNames={"id"}))
@NamedQueries({
	@NamedQuery(name = Employee.FIND_ALL_EMPLOYEES, query = "SELECT e from Employee e"),
	@NamedQuery(name = Employee.FIND_EMPLOYEE_BY_ID, query = "SELECT e FROM Employee e where e.id=:id order by id")
})
public class Employee implements Comparable<Employee>{
	
	public static final String FIND_EMPLOYEE_BY_ID = "findEmployeeById";
	public static final String FIND_ALL_EMPLOYEES = "findAllEmployee";
	public static final String NAME = "Employee";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotEmpty
	@Column(name="firstName",columnDefinition = "VARCHAR(256)")
	private String firstName;

	@NotEmpty
	@Column(name="lastName",columnDefinition = "VARCHAR(256)")
	private String lastName;
	
	@NotEmpty
	@Column(name="address",columnDefinition = "VARCHAR(256)")
	private String address;
	
  @Version
  @Column
  private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
        .append(id).append(firstName).append(lastName).append(address).toHashCode();
  }
	
	//Comparable Implementation
	@Override
	public int compareTo(Employee o) {
		return new CompareToBuilder()
    .append(id, o.getId())
    .append(firstName, o.getFirstName())
    .append(lastName, o.getLastName())
    .append(address, o.getAddress()).toComparison();
	}
	
	 @Override
	  public boolean equals(Object object) {
	    if (object == null)
	      return false;
	    if (object == this)
	      return true;
	    if (object.getClass() != this.getClass())
	      return false;

	    Employee o = (Employee) object;
	    return new EqualsBuilder()
	        .append(id, o.getId())
	        .append(firstName, o.getFirstName())
	        .append(lastName, o.getLastName())
	        .append(address, o.getAddress())
	        .isEquals();
	  } 
	

}
