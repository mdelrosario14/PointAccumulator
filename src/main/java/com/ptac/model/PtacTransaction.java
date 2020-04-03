package com.ptac.model;

import java.time.LocalDate;

/**
 * Accounts all transaction in respect to user.
 * @author Mardolfh Del Rosario
 *
 */
public class PtacTransaction {

	private LocalDate cre8Dt;
	private PtacUser user;
	private Double points;


    public PtacTransaction(LocalDate cre8Dt, PtacUser user, Double points) {
        this.cre8Dt = cre8Dt;
        this.user = user;
        this.points = points;
    }

	public LocalDate getCre8Dt() {
		return cre8Dt;
	}
	public void setCre8Dt(LocalDate cre8Dt) {
		this.cre8Dt = cre8Dt;
	}
	public PtacUser getUser() {
		return user;
	}
	public void setUser(PtacUser user) {
		this.user = user;
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}

}
