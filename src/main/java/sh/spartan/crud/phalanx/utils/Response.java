package sh.spartan.crud.phalanx.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Response<T> implements Serializable {
	@JsonIgnore
	T t;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7833402785400823457L;
	private long code;
	private String msg;
	private String type;
	private Object recordset;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getRecordset() {
		return recordset;
	}

	public void setRecordset(Object recordset) {
		this.recordset = recordset;
	}
}