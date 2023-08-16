package br.com.carloser7.asstecnica.service;

import br.com.carloser7.asstecnica.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.repository.ChamadoTecnicoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
public class ChamadoTecnicoService {

    @Autowired
    private ChamadoTecnicoRepository repository;

    public byte[] relatorioFichaChamadoTecnico(Integer idChamado) throws JRException {
        ChamadoTecnico chamadoTecnico = this.repository
                .findById(idChamado)
                .orElseThrow(
                () -> new EmptyResultDataAccessException("Produto com o id " + idChamado + " não foi encontrado", 1));

        List<ChamadoTecnico> chamadoTecnicos = Collections.singletonList(chamadoTecnico);

        InputStream fichaChamado = getClass().getResourceAsStream("/relatorios/ficha-chamado.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(fichaChamado);
        JRSaver.saveObject(jasperReport, "ficha-chamado.jasper");

        InputStream contatos = getClass().getResourceAsStream("/relatorios/ficha-chamado-contatos-embed.jrxml");
        JRSaver.saveObject(JasperCompileManager.compileReport(contatos), "ficha-chamado-contatos-embed.jasper");

        InputStream ocorrencias = getClass().getResourceAsStream("/relatorios/ficha-chamado-ocorrencias-embed.jrxml");
        JRSaver.saveObject(JasperCompileManager.compileReport(ocorrencias), "ficha-chamado-ocorrencias-embed.jasper");

        JasperPrint jasperPrint = JasperFillManager
                .fillReport(jasperReport, null, new JRBeanCollectionDataSource(chamadoTecnicos));
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
