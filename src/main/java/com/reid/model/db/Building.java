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
@Table(name="building",
uniqueConstraints=
@UniqueConstraint(columnNames={"id"}))
@NamedQueries({
	@NamedQuery(name = Building.FIND_ALL_BUILDINGS, query = "SELECT t from Building t"),
	@NamedQuery(name = Building.FIND_BUILDING_BY_ID, query = "SELECT t FROM Building t where t.id=:id order by id")
})
public class Building implements Comparable<Building>{
	public static final String FIND_BUILDING_BY_ID = "findBuildingById";
	public static final String FIND_ALL_BUILDINGS = "findAllBuilding";
	public static final String NAME = "Building";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotEmpty
	@Column(name="buildingName",columnDefinition = "VARCHAR(256)")
	private String buildingName;

	@NotEmpty
	@Column(name="address",columnDefinition = "VARCHAR(500)")
	private String address;
	
  @Version
  @Column
  private Integer version;
	
	public Long getId() {
		return id;
	}	

	public void setId(Long buildingId) {
		this.id = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buldingName) {
		this.buildingName = buldingName;
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
        .append(id).append(buildingName).append(address).toHashCode();
  }
	
	//Comparable Implementation
	@Override
	public int compareTo(Building o) {
		return new CompareToBuilder()
    .append(id, o.getId())
    .append(buildingName, o.getBuildingName())
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

    Building o = (Building) object;
    return new EqualsBuilder()
        .append(id, o.getId())
        .append(buildingName, o.getBuildingName())
        .append(address, o.getAddress())
        .isEquals();
  } 
	
	
}
