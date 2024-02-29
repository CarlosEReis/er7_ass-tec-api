package br.com.carloser7.asstecnica.infra.service;

import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.service.CadastroChamadoTecnicoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
public class FichaPdfChamadoService {

    @Autowired private CadastroChamadoTecnicoService cadastroChamadoService;

    public byte[] geraFichaChamdo(Integer chamadoId) throws JRException {
        ChamadoTecnico chamadoTecnico = cadastroChamadoService.buscar(chamadoId);
        return this.relatorioFichaChamadoTecnico(chamadoTecnico);
    }

    public  byte[] relatorioFichaChamadoTecnico (ChamadoTecnico chamado) throws JRException {
        List<ChamadoTecnico> chamadoTecnicos = Collections.singletonList(chamado);

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
