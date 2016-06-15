package org.spark.model.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.UUID;

public class CriseCount implements Serializable {
	
	private UUID id;
	private Integer idcrise;
	private long count;
	private Timestamp lastupdate;
	private Integer crisetype;

	public CriseCount() {
	}

	public CriseCount(Integer idCrise, long count, Timestamp date, CriseType criseType) {
		UUID uid = UUID.fromString("45400630-8cf0-11bd-b23e-10b96e4ef00d");
		this.id = uid.randomUUID();
		this.idcrise = idCrise;
		this.count = count;
		this.lastupdate = date;
		this.crisetype = criseType.getTipo();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long value) {
		this.count = value;
	}

	@Override
	public String toString() {
		return MessageFormat.format("CriseCount'{'id={0}, idCrise={1}, count={2}'}'", id, getIdcrise(), count);
	}

	public Timestamp getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Timestamp lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Integer getIdcrise() {
		return idcrise;
	}

	public void setIdcrise(Integer idcrise) {
		this.idcrise = idcrise;
	}

	public Integer getCrisetype() {
		return crisetype;
	}

	public void setCrisetype(Integer crisetype) {
		this.crisetype = crisetype;
	}
}
