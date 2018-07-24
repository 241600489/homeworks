package zym.stream.fileaccess;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.text.DecimalFormat;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 11:46
 * @desc cell
 */
public class CellString {
    public static String getStringCellValue(Cell cell) {
        return cell.getStringCellValue();
    }

    public static Integer getIntCellValue(XSSFCell cell) {

        return Integer.valueOf(cell.getStringCellValue());
    }
    public static String getCell(XSSFCell cell) {
        DecimalFormat df = new DecimalFormat("#");
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case XSSFCell.CELL_TYPE_STRING:

                return cell.getStringCellValue();
            case XSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case XSSFCell.CELL_TYPE_BLANK:
                return "";
            case XSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case XSSFCell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }
        return "";
    }
}
