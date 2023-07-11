package com.example.finalproject.Model;


import java.util.Date;

public class Filter {
    private String status;
    private Date dateStart;
    private Date dateEnd;

    public Filter() {
    }

    public Filter(String status, Date dateStart, Date dateEnd) {
        this.status = status;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
