package zym.stream.fileaccess;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static zym.stream.fileaccess.CellString.getCell;
import static zym.stream.fileaccess.CellString.getIntCellValue;
import static zym.stream.fileaccess.CellString.getStringCellValue;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 10:15
 * @desc 访问文件类
 */
public class FileAccess {
    private static final Logger log = Logger.getGlobal();
    private static final String path = "E:\\nihao\\01.xlsx";
    private static final String path1= "E:\\nihao\\02.xlsx";
    private static final Map<String, Integer> json = new HashMap<>();
    static {
        json.put("东向西", 1);
        json.put("西向东", 2);
        json.put("南向北", 3);
        json.put("北向南", 4);
    }

    //获取excel 中的数据
    public Optional<List<Device>> aquireDataList() {
        List<Device> devices = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            Iterator<Sheet> iterator = sheets.iterator();
            Integer count = 0;
            while (iterator.hasNext()) {
                count++;
                iterator.next();
            }
            for (int i=0;i<count;i++) {
                XSSFSheet sheet = sheets.getSheetAt(i);
                int rows = sheet.getLastRowNum();
                log.log(Level.INFO, "行数：" + rows);
                if (rows == 0) {
                    log.log(Level.WARNING, () -> "没有数据");
                    break;
                }

                IntStream.rangeClosed(1,rows)
                        .forEach(j->{
                            Device device = new Device();
                            XSSFRow row = sheet.getRow(j);
                            if(row != null) {
                                //读取cell
                                device.setRoadName(getCell(row.getCell(0)));
                                device.setType(Integer.valueOf(getCell(row.getCell(1))));
                                device.setDirect(getCell(row.getCell(2)));
                                device.setDeviceCode(getCell(row.getCell(3)));
                                device.setDirectCode(json.get(device.getDirect()));
                                devices.add(device);
                            }
                        });
            }
        } catch (FileNotFoundException e) {
            log.log(Level.WARNING, e, () -> "没有找到文件");
            return Optional.empty();
        } catch (IOException e) {
            log.log(Level.WARNING, e, () -> "读取文件报错");
            return Optional.empty();
        }
        return Optional.of(devices);
    }

    //获取excel 中的数据
    public Optional<List<Device>> aquireDeviceName() {
        List<Device> devices = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(path1)) {
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            Iterator<Sheet> iterator = sheets.iterator();
            Integer count = 0;
            while (iterator.hasNext()) {
                count++;
                iterator.next();
            }
            for (int i = 0; i < count; i++) {
                XSSFSheet sheet = sheets.getSheetAt(i);
                int rows = sheet.getLastRowNum();
                log.log(Level.INFO, "行数：" + rows);
                if (rows == 0) {
                    log.log(Level.WARNING, () -> "没有数据");
                    break;
                }
                IntStream.rangeClosed(1, rows)
                        .forEach(j -> {
                            Device device = new Device();
                            XSSFRow row = sheet.getRow(j);
                            if (row != null) {
                                //读取cell
                                device.setRoadName(getCell(row.getCell(1)));
                                device.setDeviceCode(getCell(row.getCell(0)));
                                devices.add(device);
                            }
                        });
            }
        } catch (FileNotFoundException e) {
            log.log(Level.WARNING, e, () -> "没有找到文件");
            return Optional.empty();
        } catch (IOException e) {
            log.log(Level.WARNING, e, () -> "读取文件报错");
            return Optional.empty();
        }
        return Optional.of(devices);
    }
}

