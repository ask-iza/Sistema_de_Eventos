package com.sistemaevento.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.io.FileOutputStream;

public class CertificadoUtil {

    public static void gerarCertificadoPDF(String nomeParticipante, String nomeEvento, String data, String nomePalestrante) {
        try {
            String desktopPath = System.getProperty("user.home") + "/Desktop";
            String nomeArquivo = desktopPath + "/certificado_" + nomeParticipante.replace(" ", "_") + ".pdf";

            // Documento horizontal
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, new FileOutputStream(nomeArquivo));
            doc.open();

            // Fontes
            Font titulo = new Font(Font.FontFamily.HELVETICA, 26, Font.BOLD);
            Font texto = new Font(Font.FontFamily.HELVETICA, 16);
            Font assinaturaFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);

            // Título
            Paragraph tituloParagrafo = new Paragraph("CERTIFICADO DE PARTICIPAÇÃO\n\n", titulo);
            tituloParagrafo.setAlignment(Element.ALIGN_CENTER);
            doc.add(tituloParagrafo);

            // Corpo
            Paragraph corpo = new Paragraph(String.format(
                "Certificamos que %s participou do evento\n\"%s\", realizado em %s,\npalestrado por %s.\n\n\n",
                nomeParticipante, nomeEvento, data, nomePalestrante
            ), texto);
            corpo.setAlignment(Element.ALIGN_CENTER);
            doc.add(corpo);

            // Espaços para assinaturas
            Paragraph espacamento = new Paragraph("\n\n\n");
            doc.add(espacamento);

            PdfPTable assinaturaTable = new PdfPTable(2);
            assinaturaTable.setWidthPercentage(80);
            assinaturaTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            assinaturaTable.setWidths(new float[]{1, 1});

            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(new Paragraph("______________________________", assinaturaFont));
            cell1.addElement(new Paragraph(nomeParticipante, assinaturaFont));

            PdfPCell cell2 = new PdfPCell();
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.addElement(new Paragraph("______________________________", assinaturaFont));
            cell2.addElement(new Paragraph(nomePalestrante, assinaturaFont));

            assinaturaTable.addCell(cell1);
            assinaturaTable.addCell(cell2);
            doc.add(assinaturaTable);

            doc.close();
            JOptionPane.showMessageDialog(null, "Certificado salvo na área de trabalho!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar o certificado PDF.");
        }
    }
}
