package com.mycompany.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planning")
public class Planning {
	private String sprintnr;
	private Date sdate;
	private Date edate;
	private String devs;
	private List<String> cols;
	private List<List<String>> rows;
	private int wdays;
	private int spoints;
	private String velocity;
	private int spconsumed;
	private int spremain;
	private String spadded;

	public String getSprintnr() {
		return sprintnr;
	}
	public void setSprintnr(String sprintnr) {
		this.sprintnr = sprintnr;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public String getDevs() {
		return devs;
	}
	public void setDevs(String devs) {
		this.devs = devs;
	}
	public List<String> getCols() {
		return cols;
	}
	public void setCols(List<String> cols) {
		this.cols = cols;
	}
	public List<List<String>> getRows() {
		return rows;
	}
	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}
	public int getWdays() {
		return wdays;
	}
	public void setWdays(int wdays) {
		this.wdays = wdays;
	}
	public int getSpoints() {
		return spoints;
	}
	public void setSpoints(int spoints) {
		this.spoints = spoints;
	}
	public String getVelocity() {
		return velocity;
	}
	public void setVelocity(String velocity) {
		this.velocity = velocity;
	}
	public int getSpconsumed() {
		return spconsumed;
	}
	public void setSpconsumed(int spconsumed) {
		this.spconsumed = spconsumed;
	}
	public int getSpremain() {
		return spremain;
	}
	public void setSpremain(int spremain) {
		this.spremain = spremain;
	}
	public String getSpadded() {
		return spadded;
	}
	public void setSpadded(String spadded) {
		this.spadded = spadded;
	}	
}
