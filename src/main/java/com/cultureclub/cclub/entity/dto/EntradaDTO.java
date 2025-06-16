package com.cultureclub.cclub.entity.dto;

import java.sql.Date;
import lombok.Data;

@Data
public class EntradaDTO {
    private Long idEntrada;
    private String tipoEntrada;
    private Long idEvento;
    private Long idCompradorUsuario;
    private Date fechaCompra;
    private Date fechaUso;
    private int precioPagado;
}