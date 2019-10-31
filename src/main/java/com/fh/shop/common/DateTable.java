package com.fh.shop.common;

import java.io.Serializable;
import java.util.List;

public class DateTable implements Serializable {
    private Integer draw;
    private Integer recordsFiltered;
    private Integer recordsTotal;
    private List data;


    public DateTable() {

    }

    public DateTable(Integer draw, Integer recordsFiltered, Integer recordsTotal, List data) {
        this.draw = draw;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
        this.data = data;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
