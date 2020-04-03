package com.ptac.model;

import java.time.LocalDate;

/**
 * Accounts all transaction in respect to user.
 * @author Mardolfh Del Rosario
 *
 */
public class PtacTransactionEntity {

	private LocalDate cre8Dt;
	private PtacUser user;
	private Double points;


    public PtacTransactionEntity(LocalDate cre8Dt, PtacUser user, Double points) {
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

	   @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((points == null) ? 0 : points.hashCode());
	        result = prime * result + ((user == null) ? 0 : user.hashCode());
	        return result;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        PtacTransactionEntity other = (PtacTransactionEntity) obj;
	        if (points == null) {
	            if (other.points != null)
	                return false;
	        } else if (!points.equals(other.points))
	            return false;
	        if (user == null) {
	            if (other.user != null)
	                return false;
	        } else if (!user.equals(other.user))
	            return false;
	        return true;
	    }


}
