package com.example.hcm23_java14_team2.helper;

import com.example.hcm23_java14_team2.model.entities.Enum.Level;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusSyllabus;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.repository.SyllabusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Code", "Course_objective", "Duration", "Level", "Principle", "Status", "Technical_req", "Name", "Version", "Create_by","Create_date","Modified_by","Modified_date" };
    static String SHEET = "Syllabus";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<Syllabus> excelToTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Syllabus> syllabusList = new ArrayList<Syllabus>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Syllabus syllabus = new Syllabus();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            syllabus.setCodeName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            syllabus.setCourseObjective(currentCell.getStringCellValue());
                            break;
                        case 2:
                            syllabus.setDuration((int)currentCell.getNumericCellValue());
                            break;
                        case 3:
                            String levelValue = currentCell.getStringCellValue();
                            Level level = Level.valueOf(levelValue.toUpperCase());
                            syllabus.setLevel(level);
                            break;
                        case 4:
                            syllabus.setPrinciple(currentCell.getStringCellValue());
                            break;
                        case 5:
                            String statusValue = currentCell.getStringCellValue();
                            StatusSyllabus statusSyllabus = StatusSyllabus.valueOf(statusValue.toUpperCase());
                            syllabus.setStatus(statusSyllabus);
                            break;
                        case 6:
                            syllabus.setTechnicalReq(currentCell.getStringCellValue());
                            break;
                        case 7:
                            syllabus.setTopicName(currentCell.getStringCellValue());
                            break;
                        case 8:
                            syllabus.setVersion((float)currentCell.getNumericCellValue());
                            break;
                        case 9:
                            syllabus.setCreateBy(currentCell.getStringCellValue());
                            break;
                        case 10:
                            try {
                                Date date = currentCell.getDateCellValue();
                                syllabus.setCreateDate(date);
                            } catch (Exception e) {
                                System.out.println("Error reading date: " + e.getMessage());
                            }
                            break;
                        case 11:
                            syllabus.setModifiedBy(currentCell.getStringCellValue());
                            break;
                        case 12:
                            try {
                                Date date = currentCell.getDateCellValue();
                                syllabus.setModifiedDate(date);
                            } catch (Exception e) {
                                System.out.println("Error reading date: " + e.getMessage());
                            }
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                syllabusList.add(syllabus);
            }
            workbook.close();
            return syllabusList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream tutorialsToExcel(List<Syllabus> syllabusList) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Syllabus syllabus : syllabusList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(syllabus.getId());
                row.createCell(1).setCellValue(syllabus.getCodeName());
                row.createCell(2).setCellValue(syllabus.getTopicName());
                row.createCell(3).setCellValue(syllabus.getVersion());
                row.createCell(4).setCellValue(syllabus.getPrinciple());
                row.createCell(5).setCellValue(syllabus.getCourseObjective());
                row.createCell(6).setCellValue(syllabus.getDuration());
                row.createCell(7).setCellValue(syllabus.getTechnicalReq());
                row.createCell(8).setCellValue(String.valueOf(syllabus.getStatus()));
                row.createCell(9).setCellValue(String.valueOf(syllabus.getLevel()));
                row.createCell(10).setCellValue(syllabus.getCreateBy());
                row.createCell(11).setCellValue(String.valueOf(syllabus.getCreateDate()));
                row.createCell(12).setCellValue(syllabus.getModifiedBy());
                row.createCell(13).setCellValue(String.valueOf(syllabus.getModifiedDate()));
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
