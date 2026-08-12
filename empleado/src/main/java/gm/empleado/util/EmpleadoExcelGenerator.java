package gm.empleado.util;

import gm.empleado.modelo.Empleado;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class EmpleadoExcelGenerator {

    public static byte[] generarReporteExcel(List<Empleado> empleados) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Empleados");

            // Estilo Encabezado
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Encabezados
            String[] columnas = {"ID", "Nombre", "Departamento", "Sueldo (RD$)"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // Estilo Formato Moneda
            CellStyle currencyStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            currencyStyle.setDataFormat(format.getFormat("$#,##0.00"));

            // Cargar datos
            int rowIdx = 1;
            for (Empleado emp : empleados) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(emp.getIdEmpleado());
                row.createCell(1).setCellValue(emp.getNombreEmpleado());
                row.createCell(2).setCellValue(emp.getDepartamento());

                Cell sueldoCell = row.createCell(3);
                sueldoCell.setCellValue(emp.getSueldo());
                sueldoCell.setCellStyle(currencyStyle);
            }

            // Autoajustar ancho de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
