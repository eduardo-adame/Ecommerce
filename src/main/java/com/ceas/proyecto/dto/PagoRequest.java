package com.ceas.proyecto.dto;

public class PagoRequest {
    private Long idVenta;
    private String moneda;

    public PagoRequest() {
    }
    public PagoRequest(Long idVenta, String moneda) {
        this.idVenta = idVenta;
        this.moneda = moneda;
    }
    public Long getIdVenta() {
        return idVenta;
    }
    public String getMoneda() {
        return moneda;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
