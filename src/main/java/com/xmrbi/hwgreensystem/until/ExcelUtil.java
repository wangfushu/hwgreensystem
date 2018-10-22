package com.xmrbi.hwgreensystem.until;


import com.xmrbi.hwgreensystem.domain.db.SysBaseInformation;
import com.xmrbi.hwgreensystem.domain.vo.StatisticalReportVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by wangfs on 2017/7/26.
 * helloWorld
 */
public class ExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);


    public static byte[] exportReportExcel(List<StatisticalReportVo> data, String fileName, List<SysBaseInformation> Title) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(fileName);
        HSSFRow row2 = sheet.createRow(1);
        HSSFRow row3 = sheet.createRow(2);
        sheet.setColumnWidth(0,4000);
        row2.createCell(0).setCellValue("单位");
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        for (Integer i = 0; i < Title.size(); i++) {
            sheet.setColumnWidth(2*(i+1)-1,4000);
            sheet.setColumnWidth(2*(i+1),4000);
            row2.createCell(2*(i+1)-1).setCellValue(Title.get(i).getBiDescription());
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 2*(i+1)-1, 2*(i+1)));

            row3.createCell(2*(i+1)-1).setCellValue("免征车辆(辆)");
            row3.createCell(2*(i+1)).setCellValue("免征金额(元)");
        }
        setHeader(wb, sheet, fileName);
        int index = 3;
        for (int i=0;i<data.size();i++) {
            HSSFRow rowIndex = sheet.createRow(index++);
            rowIndex.createCell(0).setCellValue(data.get(i).getPlazaName());
            for (int j=0;j<Title.size();j++){
                rowIndex.createCell(2*(j+1)-1).setCellValue(data.get(i).getResult().get("num"+Title.get(j).getBiValue()));
                rowIndex.createCell(2*(j+1)).setCellValue(data.get(i).getResult().get("fee"+Title.get(j).getBiValue()));
            }
        }
        return getBytes(wb);
    }


/*    public static List<OrderExcelVO> readPostcodeFromExcel(InputStream inputStream, String fileName) throws IOException {
        List<OrderExcelVO> result = Lists.newArrayList();
        Workbook wb = null;
        try {
            if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook(inputStream);
            } else if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException("读取的不是excel文件");
            }

            Sheet sheet = wb.getSheetAt(0);
            int rowSize = sheet.getLastRowNum() + 1;
            for (int j = 1; j < rowSize; j++) {//遍历行
                Row row = sheet.getRow(j);
                OrderExcelVO orderExcelVO = new OrderExcelVO();
                if (row == null) {//略过空行
                    continue;
                }
                orderExcelVO.setOrderCode(getCellValue(row, 0));
                orderExcelVO.setPostCode(getCellValue(row, 1));
                result.add(orderExcelVO);
            }

            return result;
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }

    public static List<OrderExcelVO> readFromMangguoExcel(InputStream inputStream, String fileName) throws IOException {
        List<OrderExcelVO> result = Lists.newArrayList();
        Workbook wb = null;
        try {
            if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook(inputStream);
            } else if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException("读取的不是excel文件");
            }

            Sheet sheet = wb.getSheetAt(0);
            int rowSize = sheet.getLastRowNum() + 1;
            for (int j = 1; j < rowSize; j++) {//遍历行
                Row row = sheet.getRow(j);
                OrderExcelVO orderExcelVO = new OrderExcelVO();
                if (row == null) {//略过空行
                    continue;
                }
                orderExcelVO.setSellerName(getCellValue(row, 0).trim());
                orderExcelVO.setOrderCode(getCellValue(row, 1).trim());
                orderExcelVO.setItemNum(getCellValue(row, 3).trim());
                orderExcelVO.setSku(getCellValue(row, 4).trim());
                orderExcelVO.setOrderTime(getCellValue(row, 5).trim());
                orderExcelVO.setBuyerName(getCellValue(row, 8).trim());
                orderExcelVO.setCountry(getCellValue(row, 9).trim());
                orderExcelVO.setState(getCellValue(row, 11).trim());
                orderExcelVO.setCity(getCellValue(row, 12).trim());
                orderExcelVO.setBuyerAddress(getCellValue(row, 13).trim());
                orderExcelVO.setCode(getCellValue(row, 14).trim());
                orderExcelVO.setBuyerPhone(getCellValue(row, 15).trim());
                orderExcelVO.setAsin(getCellValue(row, 17).trim());
                result.add(orderExcelVO);
            }

            return result;
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }*/


/*
    public static byte[] exportEuExcel(List<OrderDB> data) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("订单信息");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 6000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 14000);
        sheet.setColumnWidth(8, 6000);
        sheet.setColumnWidth(9, 6000);
        sheet.setColumnWidth(10, 6000);
        sheet.setColumnWidth(11, 6000);
        sheet.setColumnWidth(12, 6000);
        sheet.setColumnWidth(13, 6000);
        sheet.setColumnWidth(14, 6000);
        sheet.setColumnWidth(15, 6000);
        sheet.setColumnWidth(16, 6000);
//        setHeader(wb, sheet);

        HSSFRow row2 = sheet.createRow(0);
        row2.createCell(0).setCellValue("订单号");
        row2.createCell(1).setCellValue("商品交易号");
        row2.createCell(2).setCellValue("商品SKU");
        row2.createCell(3).setCellValue("数量");
        row2.createCell(4).setCellValue("收件人姓名");
        row2.createCell(5).setCellValue("收件人地址1");
        row2.createCell(6).setCellValue("收件人地址2");
        row2.createCell(7).setCellValue("收件人地址3");
        row2.createCell(8).setCellValue("收件人城市");
        row2.createCell(9).setCellValue("收件人州");
        row2.createCell(10).setCellValue("收件人邮编");
        row2.createCell(11).setCellValue("收件人国家");
        row2.createCell(12).setCellValue("收件人电话");
        row2.createCell(13).setCellValue("收件人电子邮箱");
        row2.createCell(14).setCellValue("自定义信息1");
        row2.createCell(15).setCellValue("备注");
        row2.createCell(16).setCellValue("来源");
        row2.createCell(17).setCellValue("寄件地址");
        row2.createCell(18).setCellValue("业务类型");
        row2.createCell(19).setCellValue("增值服务");

        int index = 1;
        int column = 0;
        for (OrderDB vo : data) {
            column = 0;
            HSSFRow rowIndex = sheet.createRow(index++);
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getAmazonOrderId()));
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getAmazonOrderId()));
            rowIndex.createCell(column++).setCellValue(vo.getItemTotalNum());
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getName()));
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getAddressLine1()));
            rowIndex.createCell(column++).setCellValue(vo.getShippingAddressDB().getAddressLine2());
            rowIndex.createCell(column++).setCellValue(vo.getShippingAddressDB().getAddressLine3());
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getCity()));
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getStateOrRegion()));
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getPostalCode()));
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getCountryCode()));
            rowIndex.createCell(column++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getPhone()));
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("1");
            rowIndex.createCell(column++).setCellValue("");
        }

        HSSFSheet sheet2 = wb.createSheet("SKU列表");
        sheet2.setColumnWidth(0, 6000);
        sheet2.setColumnWidth(1, 4000);
        sheet2.setColumnWidth(2, 6000);
        sheet2.setColumnWidth(3, 6000);
        sheet2.setColumnWidth(4, 7000);
        sheet2.setColumnWidth(5, 6000);
        sheet2.setColumnWidth(6, 6000);
        sheet2.setColumnWidth(7, 14000);
        sheet2.setColumnWidth(8, 6000);
        sheet2.setColumnWidth(9, 6000);
        sheet2.setColumnWidth(10, 6000);
        sheet2.setColumnWidth(11, 6000);
        sheet2.setColumnWidth(12, 6000);
        sheet2.setColumnWidth(13, 6000);
        sheet2.setColumnWidth(14, 6000);
        sheet2.setColumnWidth(15, 6000);
        sheet2.setColumnWidth(16, 6000);
//        setHeader(wb, sheet);

        HSSFRow row3 = sheet2.createRow(0);
        row3.createCell(0).setCellValue("SKU编号");
        row3.createCell(1).setCellValue("商品中文名称");
        row3.createCell(2).setCellValue("商品英文名称");
        row3.createCell(3).setCellValue("单件重量（3位小数）");
        row3.createCell(4).setCellValue("单件报关价格(2位小数)");
        row3.createCell(5).setCellValue("原寄地");
        row3.createCell(6).setCellValue("计量单位");
        row3.createCell(7).setCellValue("保存至系统SKU");
        row3.createCell(8).setCellValue("税则号");
        row3.createCell(9).setCellValue("销售链接");
        row3.createCell(10).setCellValue("备注");

        index = 1;
        for (OrderDB vo : data) {
            column = 0;
            HSSFRow rowIndex = sheet2.createRow(index++);
            rowIndex.createCell(column++).setCellValue(vo.getAmazonOrderId());
            rowIndex.createCell(column++).setCellValue("服饰");
            rowIndex.createCell(column++).setCellValue("Clothes");
            rowIndex.createCell(column++).setCellValue("0.1");
            rowIndex.createCell(column++).setCellValue("10");
            rowIndex.createCell(column++).setCellValue("cn");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("1");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("");
            rowIndex.createCell(column++).setCellValue("");
        }
        return getBytes(wb);
    }

    public static byte[] exportSfExcel(List<OrderDB> data) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("顺丰快递表格");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 6000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 14000);
        sheet.setColumnWidth(8, 6000);
        sheet.setColumnWidth(9, 6000);
        sheet.setColumnWidth(10, 6000);
        sheet.setColumnWidth(11, 6000);
        sheet.setColumnWidth(12, 6000);
        sheet.setColumnWidth(13, 6000);
        sheet.setColumnWidth(14, 6000);
        sheet.setColumnWidth(15, 6000);
        sheet.setColumnWidth(16, 6000);
//        setHeader(wb, sheet);

        int columnIndex = 0;
        HSSFRow row2 = sheet.createRow(0);
        row2.createCell(columnIndex++).setCellValue("客户单号");
        row2.createCell(columnIndex++).setCellValue("顺丰单号");
        row2.createCell(columnIndex++).setCellValue("服务商单号");
        row2.createCell(columnIndex++).setCellValue("运输方式");
        row2.createCell(columnIndex++).setCellValue("寄件人公司名");
        row2.createCell(columnIndex++).setCellValue("寄件人姓名");
        row2.createCell(columnIndex++).setCellValue("寄件人省");
        row2.createCell(columnIndex++).setCellValue("寄件人城市");
        row2.createCell(columnIndex++).setCellValue("寄件人地址");
        row2.createCell(columnIndex++).setCellValue("寄件人电话");
        row2.createCell(columnIndex++).setCellValue("寄件人邮编");
        row2.createCell(columnIndex++).setCellValue("收件人公司名");
        row2.createCell(columnIndex++).setCellValue("收件人姓名");
        row2.createCell(columnIndex++).setCellValue("目的国家");
        row2.createCell(columnIndex++).setCellValue("州 \\ 省");
        row2.createCell(columnIndex++).setCellValue("城市");
        row2.createCell(columnIndex++).setCellValue("联系地址");
        row2.createCell(columnIndex++).setCellValue("收件人邮编");
        row2.createCell(columnIndex++).setCellValue("收件人电话");
        row2.createCell(columnIndex++).setCellValue("收件人手机");
        row2.createCell(columnIndex++).setCellValue("收件人邮箱");
        row2.createCell(columnIndex++).setCellValue("总重量（KG）");
        row2.createCell(columnIndex++).setCellValue("包裹 长（CM）");
        row2.createCell(columnIndex++).setCellValue("包裹 宽（CM）");
        row2.createCell(columnIndex++).setCellValue("包裹 高（CM）");
        row2.createCell(columnIndex++).setCellValue("是否退件");
        row2.createCell(columnIndex++).setCellValue("收件人身份证号/护照号");
        row2.createCell(columnIndex++).setCellValue("VAT税号");
        row2.createCell(columnIndex++).setCellValue("是否带电");
        row2.createCell(columnIndex++).setCellValue("保价服务");
        row2.createCell(columnIndex++).setCellValue("英文申报品名1");
        row2.createCell(columnIndex++).setCellValue("中文申报品名1");
        row2.createCell(columnIndex++).setCellValue("申报价值USD（单价）1");
        row2.createCell(columnIndex++).setCellValue("申报品数量1");
        row2.createCell(columnIndex++).setCellValue("海关货物编号1");
        row2.createCell(columnIndex++).setCellValue("配货名称1");
        row2.createCell(columnIndex++).setCellValue("配货备注1");
        row2.createCell(columnIndex++).setCellValue("订单中商品网址链接1");
        row2.createCell(columnIndex++).setCellValue("平台网址1");
        row2.createCell(columnIndex++).setCellValue("店铺名称1");
        row2.createCell(columnIndex++).setCellValue("英文申报品名2");
        row2.createCell(columnIndex++).setCellValue("中文申报品名2");
        row2.createCell(columnIndex++).setCellValue("申报价值USD（单价）2");
        row2.createCell(columnIndex++).setCellValue("申报品数量2");
        row2.createCell(columnIndex++).setCellValue("海关货物编号2");

        row2.createCell(columnIndex++).setCellValue("配货名称2");
        row2.createCell(columnIndex++).setCellValue("配货备注2");
        row2.createCell(columnIndex++).setCellValue("订单中商品网址链接2");
        row2.createCell(columnIndex++).setCellValue("平台网址2");
        row2.createCell(columnIndex++).setCellValue("店铺名称2");
        row2.createCell(columnIndex++).setCellValue("英文申报品名3");
        row2.createCell(columnIndex++).setCellValue("中文申报品名3");
        row2.createCell(columnIndex++).setCellValue("申报价值USD（单价）3");
        row2.createCell(columnIndex++).setCellValue("申报品数量3");
        row2.createCell(columnIndex++).setCellValue("海关货物编号3");
        row2.createCell(columnIndex++).setCellValue("配货名称3");
        row2.createCell(columnIndex++).setCellValue("配货备注3");
        row2.createCell(columnIndex++).setCellValue("订单中商品网址链接3");
        row2.createCell(columnIndex++).setCellValue("平台网址3");
        row2.createCell(columnIndex++).setCellValue("店铺名称3");
        row2.createCell(columnIndex++).setCellValue("英文申报品名4");
        row2.createCell(columnIndex++).setCellValue("中文申报品名4");
        row2.createCell(columnIndex++).setCellValue("申报价值USD（单价）4");
        row2.createCell(columnIndex++).setCellValue("申报品数量4");
        row2.createCell(columnIndex++).setCellValue("海关货物编号4");
        row2.createCell(columnIndex++).setCellValue("配货名称4");
        row2.createCell(columnIndex++).setCellValue("配货备注4");
        row2.createCell(columnIndex++).setCellValue("订单中商品网址链接4");
        row2.createCell(columnIndex++).setCellValue("平台网址4");
        row2.createCell(columnIndex++).setCellValue("店铺名称4");
        row2.createCell(columnIndex++).setCellValue("英文申报品名5");
        row2.createCell(columnIndex++).setCellValue("中文申报品名5");
        row2.createCell(columnIndex++).setCellValue("申报价值USD（单价）5");
        row2.createCell(columnIndex++).setCellValue("申报品数量5");
        row2.createCell(columnIndex++).setCellValue("海关货物编号5");
        row2.createCell(columnIndex++).setCellValue("配货名称5");
        row2.createCell(columnIndex++).setCellValue("配货备注5");
        row2.createCell(columnIndex++).setCellValue("订单中商品网址链接5");
        row2.createCell(columnIndex++).setCellValue("平台网址5");
        row2.createCell(columnIndex++).setCellValue("店铺名称5");

        int index = 1;
        for (OrderDB vo : data) {
            columnIndex = 0;
            HSSFRow rowIndex = sheet.createRow(index++);
            rowIndex.createCell(columnIndex++).setCellValue(getStringWithTrim(vo.getAmazonOrderId()));
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("E1 顺丰国际小包平邮");
            rowIndex.createCell(columnIndex++).setCellValue("SANHUI");
            rowIndex.createCell(columnIndex++).setCellValue("WU");
            rowIndex.createCell(columnIndex++).setCellValue("FUJIAN");
            rowIndex.createCell(columnIndex++).setCellValue("PUTIAN");
            rowIndex.createCell(columnIndex++).setCellValue("Li Cheng");
            rowIndex.createCell(columnIndex++).setCellValue("18059558599");
            rowIndex.createCell(columnIndex++).setCellValue("351144");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue(vo.getShippingAddressDB().getName());
            rowIndex.createCell(columnIndex++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getCountryCode()));
            rowIndex.createCell(columnIndex++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getStateOrRegion()));
            rowIndex.createCell(columnIndex++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getCity()));
            rowIndex.createCell(columnIndex++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getAddressLine1()));
            rowIndex.createCell(columnIndex++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getPostalCode()));
            rowIndex.createCell(columnIndex++).setCellValue(getStringWithTrim(vo.getShippingAddressDB().getPhone()));
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("0.1");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("Clothing");
            rowIndex.createCell(columnIndex++).setCellValue("服饰");
            rowIndex.createCell(columnIndex++).setCellValue("10");
            rowIndex.createCell(columnIndex++).setCellValue("1");
            rowIndex.createCell(columnIndex++).setCellValue("");
            rowIndex.createCell(columnIndex++).setCellValue("1");
        }

        return getBytes(wb);
    }*/

    private static String getCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return "";
        }
        try {
            String cellValue = cell.getStringCellValue();
            return cellValue;
        } catch (Exception e) {
            return String.valueOf(cell.getNumericCellValue());
        }
    }

    private static void setHeader(HSSFWorkbook wb, HSSFSheet sheet) {
        HSSFRow row1 = sheet.createRow(0);
        row1.setHeight((short) 500);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("订单详情");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 16));
        cell.setCellStyle(cellStyle);
    }

    private static void setHeader(HSSFWorkbook wb, HSSFSheet sheet, String fileName) {
        HSSFRow row1 = sheet.createRow(0);
        row1.setHeight((short) 500);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        HSSFCell cell = row1.createCell(0);
        cell.setCellValue(fileName);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        cell.setCellStyle(cellStyle);
    }

    private static void setAfturHeader(HSSFWorkbook wb, HSSFSheet sheet, String fileName) {
        HSSFRow row1 = sheet.createRow(0);
        row1.setHeight((short) 500);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        HSSFCell cell = row1.createCell(0);
        cell.setCellValue(fileName);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
        cell.setCellStyle(cellStyle);
    }

    private static byte[] getBytes(HSSFWorkbook workBook) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            workBook.write(byteOutputStream);
            byteOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteOutputStream.toByteArray();
    }

    public static String getStringWithTrim(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
