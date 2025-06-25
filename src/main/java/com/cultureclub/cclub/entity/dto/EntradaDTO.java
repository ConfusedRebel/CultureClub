package com.cultureclub.cclub.entity.dto;

import java.sql.Date;
import lombok.Data;

@Data
public class EntradaDTO {
    private Long idEntrada = 0L;
    private String tipoEntrada = "";
    private Long idEvento = 0L;
    private Long idCompradorUsuario = 0L;
    private Date fechaCompra = new Date(System.currentTimeMillis());
    private Date fechaUso = new Date(System.currentTimeMillis());
    private int precioPagado = 0;
}