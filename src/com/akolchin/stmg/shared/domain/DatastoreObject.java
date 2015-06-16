package com.akolchin.stmg.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;

public class DatastoreObject implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1991609332039694818L;

	@Id
	private Long id;
	private Integer version = 0;

	@SuppressWarnings("unused")
	private Date doCreated = new Date();

	@SuppressWarnings("unused")
	private Date doModified;

	public DatastoreObject() {
	}

	/**
	 * Auto-increment version # whenever persisted
	 */
	@OnSave
	void onSave() {
		this.version++;
		this.doModified = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isSaved() {
		return (id != null);
	}
}
