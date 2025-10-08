package br.com.phkaiser.bybo.app.service;

import br.com.phkaiser.bybo.core.domain.entity.Campo;
import br.com.phkaiser.bybo.core.domain.entity.Item;
import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportService {

    public void exportarParaExcel(Tabela tabela, List<Item> itens, File arquivo) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(tabela.getNome());

            // 1. Criar o cabeçalho
            Row headerRow = sheet.createRow(0);
            List<Campo> campos = tabela.getCampos();
            for (int i = 0; i < campos.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(campos.get(i).getNome());
            }

            // 2. Preencher os dados
            int rowNum = 1;
            for (Item item : itens) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < campos.size(); i++) {
                    String nomeCampo = campos.get(i).getNome();
                    Object valor = item.getValores().get(nomeCampo);
                    Cell cell = row.createCell(i);
                    if (valor != null) {
                        if (valor instanceof Number) {
                            cell.setCellValue(((Number) valor).doubleValue());
                        } else {
                            cell.setCellValue(valor.toString());
                        }
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            // 3. Escrever o arquivo
            try (FileOutputStream outputStream = new FileOutputStream(arquivo)) {
                workbook.write(outputStream);
            }
        }
    }
}