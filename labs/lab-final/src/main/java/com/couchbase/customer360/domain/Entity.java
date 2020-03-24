package com.couchbase.customer360.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public abstract class Entity {
	private String id;
	private Date created;
	private Date updated;
	private long cas;
	private String type;

	protected Entity() {
		created = updated = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public String getClassName() {
		return getClass().getName();
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public long getCas() {
		return cas;
	}

	public void setCas(long cas) {
		this.cas = cas;
	}

	/**
	 * Generate a key using elements of the entity as TYPE::ID
	 *
	 * @return Generated key
	 */
	public String genKey() {
		return getType() + "::" + getId();
	}

	@Override
	public String toString() {
		return "Entity{" +
				"id='" + id + '\'' +
				", created=" + created +
				", updated=" + updated +
				", cas=" + cas +
				'}';
	}
}