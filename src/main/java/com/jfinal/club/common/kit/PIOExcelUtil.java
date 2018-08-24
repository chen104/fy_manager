package com.jfinal.club.common.kit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel工具类 支持2003、2007、2010、2013 poi 3.17
 * 
 */
public class PIOExcelUtil {

	private Workbook wb = null;
	private Sheet sheet = null;

	/**
	 * 模版文件路径
	 */
	private String templateFilePath;
	/**
	 * 总行数 最大行的index值，实际行数要加1
	 */
	private int rowNum = 0;
	/**
	 * 总列数
	 */
	private int columnNum = 0;

	/**
	 * 要创建的excel版本
	 * 
	 * @author liubenlong
	 *
	 */
	public enum EXCELVERSION {
		/**
		 * 2003一下版本（含2003）
		 */
		EXCEL_VERSION_2003,
		/**
		 * 2007以上版本（含2007）
		 */
		EXCEL_VERSION_2007;
	}

	private EXCELVERSION currentVersion;

	/**
	 * 根据模板初始化
	 * 
	 * @param filePath
	 * @param sheetIndex
	 *            <p>
	 *            默认第一个sheet
	 *            </p>
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
	public PIOExcelUtil(String tmpleteFilePath, int sheetIndex) throws Exception {
		InputStream in = null;
		try {
			in = new FileInputStream(tmpleteFilePath);
			wb = WorkbookFactory.create(in);

			sheet = wb.getSheetAt(sheetIndex);

			rowNum = sheet.getLastRowNum();

			if (sheet.getRow(0) != null) {
				/*
				 * 此处并不能代表整个工作表的总列数！！！
				 */
				columnNum = sheet.getRow(0).getLastCellNum();
			}

			System.out.println("第" + sheetIndex + "个工作薄；总行数：" + rowNum + "；总列数：" + columnNum);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public PIOExcelUtil(File file, int sheetIndex) throws Exception {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			wb = WorkbookFactory.create(in);

			sheet = wb.getSheetAt(sheetIndex);

			rowNum = sheet.getLastRowNum();

			if (sheet.getRow(0) != null) {
				/*
				 * 此处并不能代表整个工作表的总列数！！！
				 */
				columnNum = sheet.getRow(0).getLastCellNum();
			}

			System.out.println("第" + sheetIndex + "个工作薄；总行数：" + rowNum + "；总列数：" + columnNum);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public PIOExcelUtil(InputStream input, int sheetIndex) throws Exception {
		InputStream in = null;
		try {
			in = input;
			wb = WorkbookFactory.create(in);

			sheet = wb.getSheetAt(sheetIndex);

			rowNum = sheet.getLastRowNum();

			if (sheet.getRow(0) != null) {
				/*
				 * 此处并不能代表整个工作表的总列数！！！
				 */
				columnNum = sheet.getRow(0).getLastCellNum();
			}

			System.out.println("第" + sheetIndex + "个工作薄；总行数：" + rowNum + "；总列数：" + columnNum);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public PIOExcelUtil() {

	}

	/**
	 * 最后总结一下HSSFDataFormat.GetFormat和HSSFDataFormat.GetBuiltinFormat的区别：
	 * 当使用Excel内嵌的（或者说预定义）的格式时，直接用HSSFDataFormat.GetBuiltinFormat静态方法即可。
	 * 当使用自己定义的格式时，必须先调用HSSFWorkbook.CreateDataFormat()，
	 * 因为这时在底层会先找有没有匹配的内嵌FormatRecord，如果没有就会新建一个FormatRecord，所以必须先调用这个方法，
	 * 然后你就可以用获得的HSSFDataFormat实例的GetFormat方法了，当然相对而言这种方式比较麻烦，
	 * 所以内嵌格式还是用HSSFDataFormat.GetBuiltinFormat静态方法更加直接一些。
	 * 不过自定义的格式也不是天马行空随便定义，还是要参照Excel的格式表示来定义，具体请看相关的Excel教程。
	 */

	/**
	 * 单元格文本样式
	 */
	public CellStyle getCellType4Text() {
		CellStyle cellStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		return cellStyle;
	}

	/**
	 * 单元格日期格式
	 */
	public CellStyle getCellType4Date() {
		CellStyle cellStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));
		return cellStyle;
	}

	/**
	 * 单元格格式:保留两位小数 目前只支持2003版本，2007版本未找到对应方法
	 * <p>
	 * <b>注意:填入表格的数据 不可以 是字符串，单元格也不可以设为文本格式</b>
	 * <p>
	 * <b>完整的Excel内嵌格式列表大家可以excel中单元格格式 自定义 中有哪些内容。百分比、货币格式等都可以在其中找到</b>
	 */
	public CellStyle getCellType4Number() {
		if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
			CellStyle cellStyle = wb.createCellStyle();
			short format = 0;
			format = HSSFDataFormat.getBuiltinFormat("0.00");
			cellStyle.setDataFormat(format);
			return cellStyle;
		}
		return null;
	}

	/**
	 * 大小写转换
	 * <p>
	 * <b>注意:填入表格的数据 不可以 是字符串，单元格也不可以设为文本格式</b>
	 * 
	 * @return
	 */
	public CellStyle getCellType4CaseConversion() {
		CellStyle cellStyle = wb.createCellStyle();

		DataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("[DbNum2][$-804]0"));
		return cellStyle;
	}

	/**
	 * 取Excel所有数据，包含header
	 * 
	 * @return List<String[]>
	 */
	public List<String[]> getAllData() {
		List<String[]> dataList = new ArrayList<String[]>();
		if (columnNum > 0) {
			for (int i = 0; i < rowNum + 1; i++) {
				String[] singleRow = new String[columnNum];
				for (int j = 0; j < columnNum; j++) {
					singleRow[j] = getCellVal(i, j);
				}
				dataList.add(singleRow);
			}
		}
		return dataList;
	}

	/**
	 * 获取单元格数据
	 * 
	 * @param columnNum
	 *            <P>
	 *            从0开始
	 *            </P>
	 * @param row
	 *            <P>
	 *            从0开始
	 *            </P>
	 * @return
	 */
	public String getCellVal(int rowNum, int columnNum) {

		String cellVal = null;
		if (sheet.getRow(rowNum) == null) {
			return null;
		}
		Cell cell = sheet.getRow(rowNum).getCell(columnNum, MissingCellPolicy.CREATE_NULL_AS_BLANK);

		switch (cell.getCellTypeEnum()) {
		case BLANK:
			cellVal = "";
			break;
		case BOOLEAN:
			cellVal = Boolean.toString(cell.getBooleanCellValue());
			break;
		// 数值
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {

				cellVal = String.valueOf(cell.getDateCellValue());
			} else {
				cell.setCellType(CellType.STRING);
				String temp = cell.getStringCellValue();
				// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
				if (temp.indexOf(".") > -1) {
					cellVal = String.valueOf(new Double(temp)).trim();
				} else {
					cellVal = temp.trim();
				}
			}
			break;
		case STRING:
			cellVal = cell.getStringCellValue().trim();
			break;
		case ERROR:
			cellVal = "";
			break;
		case FORMULA:
			cell.setCellType(CellType.STRING);
			cellVal = cell.getStringCellValue();
			if (cellVal != null) {
				cellVal = cellVal.replaceAll("#N/A", "").trim();
			}
			break;
		default:
			cellVal = "";
			break;
		}
		return cellVal;
	}

	public Date getDateValue(int rowNum, int columnNum) {
		Date cellVal;
		Cell cell = sheet.getRow(rowNum).getCell(columnNum, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		CellType type = cell.getCellTypeEnum();
		// System.out.println(type);
		if (type == CellType.STRING) {
			try {
				Date date = DateUtils.parseDate(cell.getStringCellValue().trim(), "yyyy-MM-dd hh:mm:ss");
				return date;
			} catch (ParseException e) {

			}
			try {
				Date date = DateUtils.parseDate(cell.getStringCellValue().trim(), "yyyy-MM-dd");
				return date;
			} catch (ParseException e) {

				return null;
			}
		} else if (type == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {

				cellVal = cell.getDateCellValue();
				return cellVal;
			} else {
				return null;
			}
		}
		return null;

	}

	/**
	 * 设置单元格数据 下标从0开使
	 * 
	 * @param rownum
	 * @param column
	 * @param value
	 */
	public void setCellVal(int rownum, int column, String value) {
		Row row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(column);
		if (null == cell) {
			cell = row.createCell(column);
		}
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格数据 下标从0开使
	 * 
	 * @param rownum
	 * @param column
	 * @param value
	 */
	public void setCellVal(int rownum, int column, Double value) {
		Row row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(column);
		if (null == cell) {
			cell = row.createCell(column);
		}
		if (value == null) {
			return;
		}
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格数据 下标从0开使
	 * 
	 * @param rownum
	 * @param column
	 * @param value
	 */
	public void setCellVal(int rownum, int column, Boolean value) {
		Row row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(column);
		if (null == cell) {
			cell = row.createCell(column);
		}
		if (value == null) {
			return;
		}
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格数据 下标从0开使
	 * 
	 * @param rownum
	 * @param column
	 * @param value
	 */
	public void setCellVal(int rownum, int column, Date value) {
		Row row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(column);
		if (null == cell) {
			cell = row.createCell(column);
		}
		if (value == null) {
			return;
		}
		cell.setCellValue(value);
	}

	/**
	 * 日期格式yyyy年x月x日
	 * @param rownum
	 * @param column
	 */
	public void setDateCellStyle(int rownum, int column) {
		Row row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(column);
		if (cell != null) {
			cell.setCellStyle(getCellType4Date());
		}

	}

	/**
	 * 日期格式yyyy年x月x日
	 * @param rownum
	 * @param column
	 */
	public void setDateCellStyle(int rownum, int column, String parrent) {
		Row row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(column);
		if (cell != null) {
			CellStyle cellStyle = wb.createCellStyle();
			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat(parrent));
			cell.setCellStyle(cellStyle);
		}

	}

	/**
	 * 设置单元格数据 简单公式 excel 中可以用的这里都可以用
	 * 
	 * @param rownum
	 * @param column
	 * @param value
	 * 
	 *            例：setCellValByFormula(6,6,"1+2*3")
	 *            setCellValByFormula(6,6,"A1*B1")
	 *            setCellValByFormula(6,6,"sum(A1:C1)")
	 *            <p>
	 *            <b>但要注意，在利用NPOI写程序时，行和列的计数都是从0开始计算的，
	 *            但在设置公式时又是按照Excel的单元格命名规则来的。</b>
	 */
	public void setCellValByFormula(int rownum, int column, String value) {
		sheet.getRow(rownum).getCell(column).setCellFormula(value);
	}

	/**
	 * 图形操作 目前仅支持2003版本
	 * <p>
	 * HSSFSimpleShape.OBJECT_TYPE_RECTANGLE //矩形
	 * <p>
	 * HSSFSimpleShape.OBJECT_TYPE_LINE //线
	 * <p>
	 * HSSFSimpleShape.OBJECT_TYPE_OVAL //(椭)圆
	 * <p>
	 * 等
	 */
	public void draw(short shapeType, int col1, int row1, int col2, int row2) {
		if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();

			HSSFClientAnchor a1 = new HSSFClientAnchor(0, 0, 0, 0, (short) col1, row1, (short) col2, row2);

			HSSFSimpleShape line1 = patriarch.createSimpleShape(a1);

			line1.setShapeType(shapeType);

			line1.setLineStyle(HSSFShape.LINESTYLE_SOLID);
			// 在NPOI中线的宽度12700表示1pt,所以这里是0.5pt粗的线条。
			line1.setLineWidth(6350);
		}
	}

	/**
	 * 图形操作 目前仅支持2003版本
	 */
	public void addPicture() {
		try {
			if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
				File file = new File("f:/q.jpg");

				BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				byte[] buf = new byte[inputStream.available()];
				inputStream.read(buf);

				int pictureIdx = wb.addPicture(buf, HSSFWorkbook.PICTURE_TYPE_JPEG);

				// Create the drawing patriarch. This is the top level container
				// for all shapes.
				Drawing drawing = sheet.createDrawingPatriarch();

				// add a picture
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 0, (short) 0, 0, (short) 1, 3);

				Picture picture = drawing.createPicture(anchor, pictureIdx);

				// 自动调节图片大小
				// picture.resize();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 锁定单元格，单元格内容不可修改
	 * <p>
	 * 只能控制某些单元格不被锁定。因为sheet.protectSheet("password");会锁定所有单元格
	 * </p>
	 */
	public void lockCell() {
		Row row1 = sheet.createRow(0);
		Cell cel1 = row1.createCell(0);
		Cell cel2 = row1.createCell(1);

		CellStyle unlocked = wb.createCellStyle();
		unlocked.setLocked(false);

		CellStyle locked = wb.createCellStyle();
		locked.setLocked(true);

		cel1.setCellValue("没被锁定");
		cel1.setCellStyle(unlocked);

		cel2.setCellValue("被锁定");
		cel2.setCellStyle(locked);

		sheet.protectSheet("password");// 密码
	}

	/**
	 * 锁定行或列，行或列不随滚动条滑动
	 */
	public void lockRowOrColumn() {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("冻结列");
		/*
		 * 第一个参数表示要冻结的列数； 第二个参数表示要冻结的行数，这里只冻结列所以为0； 第三个参数表示右边区域可见的首列序号，从1开始计算；
		 * 第四个参数表示下边区域可见的首行序号，也是从1开始计算，这里是冻结列，所以为0；
		 */
		// sheet.createFreezePane(1, 0, 1, 0);

		sheet.createFreezePane(1, 2, 1, 2);// 冻结两行，一列
	}

	/**
	 * 保存修改后的数据到文件
	 * 
	 * @param newFilePath
	 *            <P>
	 *            文件绝对路径 , 文件名不含后缀
	 *            </P>
	 * @throws IOException
	 */
	public void save2File(String newFilePath) {
		FileOutputStream out = null;
		try {
			if (null != newFilePath && !"".equals(newFilePath)) {
				// 校验excel版本
				if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003)
					newFilePath += ".xls";
				else
					newFilePath += ".xlsx";

				out = new FileOutputStream(newFilePath);
				out.flush();
				wb.write(out);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void save2File(File newFilePath) {
		FileOutputStream out = null;
		try {
			if (null != newFilePath && !"".equals(newFilePath)) {
				// 校验excel版本
				// if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003)
				// newFilePath += ".xls";
				// else
				// newFilePath += ".xlsx";

				out = new FileOutputStream(newFilePath);
				out.flush();
				wb.write(out);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// /**
	// * WEB环境中使用，文件下载
	// * @param response
	// * @param wb
	// * @param fileName
	// * @throws IOException
	// */
	// public void exportExcel(HttpServletResponse response, Workbook wb,String
	// fileName) throws IOException {
	// // 如果文件名有中文，必须URL编码
	// fileName = URLEncoder.encode(fileName, "UTF-8");
	// response.reset();
	// // ContentType 可以不设置
	// response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	// response.setHeader("Content-Disposition", "attachment;filename=" +
	// fileName);
	// wb.write(response.getOutputStream());
	// response.getOutputStream().flush();
	// response.getOutputStream().close();
	// }

	/**
	 * 方法名称：SetDataValidation 内容摘要：设置数据有效性 --列表 目前只支持2003
	 * 
	 * @param firstRow
	 * @param firstCol
	 * @param endCol
	 */
	public void SetValidationList(int firstRow, int lastRow, int firstCol, int lastCol, String[] textList) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		// 数据有效性对象
		HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
		data_validation.createErrorBox("输入值类型或大小有误！", "请从下拉列表中选择值。");
		sheet.addValidationData(data_validation);
	}

	/**
	 * 数据有效性--日期[1901以后] 目前只支持2003
	 * 
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 * @return
	 */
	public void setValidateDate(int firstRow, int lastRow, int firstCol, int lastCol) {
		DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.GREATER_THAN,
				"1901/01/01", "1901/01/01", "yyyy/MM/dd");
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
		ret.createErrorBox("输入值类型或大小有误！", "日期型，输入日期格式：“2012-02-02”或“2012/02/02”。");
		sheet.addValidationData(ret);
	}

	/**
	 *    * 根据公式设置数据有效性    *
	 * <p>
	 *    * //这里不管是哪一列都写A1    *
	 * <p>
	 *       * util.setValidateByFormula("COUNTIF(A1,\"<a target="_blank " href=
	 * "mailto:*@*.*\')=1',0">*@*.*\")=1",0</a>, 10, 3,3,"请输入正确邮箱格式！");    * @param
	 * firstRow    * @param lastRow    * @param firstCol    * @param lastCol   
	 * * @return   
	 */
	public void setValidateByFormula(String formul, int firstRow, int lastRow, int firstCol, int lastCol,
			String errorMessage) {
		// 创建公式约束（数据有效性）
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint(formul);
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		// 数据有效性对象
		DataValidation validation = new HSSFDataValidation(regions, constraint);
		validation.createErrorBox("输入值类型或大小有误！", errorMessage);
		sheet.addValidationData(validation);
	}

	/**
	 * 数据有效性-->0的数字 目前只支持2003
	 * 
	 * @param beginRow
	 * @param beginCol
	 * @param endRow
	 * @param endCol
	 * @return
	 */
	public void setValidateDecimal(int firstRow, int lastRow, int firstCol, int lastCol) {
		// 创建一个规则：>0的数字
		DVConstraint constraint = DVConstraint.createNumericConstraint(DVConstraint.ValidationType.DECIMAL,
				DVConstraint.OperatorType.GREATER_OR_EQUAL, "0", "0");
		// 设定在哪个单元格生效
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		// 创建规则对象
		HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
		ret.createErrorBox("输入值类型或大小有误！", "数值型，请输入大于0 的数值。");
		sheet.addValidationData(ret);
	}

	/**
	 * 数据有效性--长度 目前只支持2003
	 * 
	 * @param beginRow
	 * @param beginCol
	 * @param endRow
	 * @param endCol
	 * @return
	 */
	public void setValidateLength(int firstRow, int lastRow, int firstCol, int lastCol) {
		DataValidation ret = null;
		if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
			// 创建一个规则：1-100的数字
			DVConstraint constraint = DVConstraint.createNumericConstraint(
					DataValidationConstraint.ValidationType.TEXT_LENGTH, DataValidationConstraint.OperatorType.BETWEEN,
					"0", "10");
			// 设定在哪个单元格生效
			CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
			// 创建规则对象
			ret = new HSSFDataValidation(regions, constraint);
			ret.createErrorBox("输入值长度有误！", "长度介于[0,10]");
		}
		sheet.addValidationData(ret);
	}

	/**
	 * 给单元格添加提示内容
	 * <p>
	 * <b>这里不会将原有的数据校验覆盖</b>
	 * 
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 * @param title
	 * @param text
	 */
	public void setMessage(int firstRow, int lastRow, int firstCol, int lastCol, String title, String text) {
		/*
		 * 这里创建一个空的数据有效性校验 。这里不会将原有的数据校验覆盖
		 */
		DVConstraint constraint = DVConstraint.createNumericConstraint(DVConstraint.ValidationType.ANY,
				DVConstraint.OperatorType.IGNORED, null, null);
		// 设定在哪个单元格生效
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		// 创建规则对象
		HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
		/*
		 * 设置提示内容,标题,内容
		 */
		ret.createPromptBox("mm", "www.tangxiangcun.com");
		sheet.addValidationData(ret);
	}

	/**
	 * 获取某一行数据
	 * 
	 * @param rowIndex
	 *            计数从0开始，rowIndex为0代表header行
	 * @return
	 */
	public String[] getRowData(int rowIndex) {
		String[] dataArray = null;
		if (rowIndex > columnNum) {
			return dataArray;
		} else {
			String[] singleRow = new String[columnNum];
			for (int j = 0; j < columnNum; j++) {
				singleRow[j] = getCellVal(rowIndex, j);
			}
			return singleRow;
		}
	}

	/**
	 * 设置单元格为文本样式
	 * 
	 * @param cell
	 */
	public void setCell2Text(Cell cell) {
		cell.setCellStyle(getCellType4Text());
	}

	/**
	 * 获取某一列数据
	 * 
	 * @param columnIndex
	 * @return
	 */
	public String[] getColumnData(int columnIndex) {
		String[] dataArray = new String[rowNum + 1];
		if (columnIndex > columnNum) {
			return dataArray;
		} else {
			for (int i = 0; i < rowNum + 1; i++) {
				dataArray[i] = getCellVal(i, columnIndex);
			}
		}
		return dataArray;
	}

	public String getColumnCom(int columnIndex) {
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(columnIndex);
		Comment c = cell.getCellComment();
		if (null == c)
			return null;
		return c.getString().toString().replaceAll("[\\t\\n\\r]", "");
	}

	/**
	 * 创建excel--带数据
	 * 
	 * @param version
	 * @param filePath
	 * @param headList
	 * @param headstyle
	 * @param bodyList
	 * @param bodystyle
	 * @throws IOException
	 */
	public void createExcel(String filePath, List<String> headList, HSSFCellStyle headstyle, List<String[]> bodyList,
			HSSFCellStyle bodystyle) {

		sheet = wb.createSheet();

		Row headRow = sheet.createRow(0);
		for (int i = 0; i < headList.size(); i++) {
			Cell cell = headRow.createCell(i);
			cell.setCellValue(headList.get(i));
			if (null != headstyle)
				cell.setCellStyle(headstyle);
		}

		for (int i = 1; i < bodyList.size() + 1; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < bodyList.get(i - 1).length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(bodyList.get(i - 1)[j]);
				if (null != bodystyle)
					cell.setCellStyle(bodystyle);
			}
		}

		save2File(filePath);
	}

	/**
	 * 给excel赋值
	 * 
	 * @param dataList
	 * @param style
	 * @param sheetIndex
	 * @param firstRow
	 *            起始行，从0开始
	 */
	public void setValue(List<String[]> dataList, CellStyle style, int sheetIndex, int firstRow) {

		sheet = wb.getSheetAt(sheetIndex);
		if (null == sheet)
			sheet = wb.createSheet();

		for (int i = 0; i < dataList.size(); i++) {
			Row row = sheet.createRow(i + firstRow);
			String[] cellDatas = dataList.get(i);
			for (int j = 0; j < cellDatas.length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(cellDatas[j]);
				if (null != style)
					cell.setCellStyle(style);
			}
		}

		sheet.autoSizeColumn(2);// 列宽自适应
	}

	public List<Map<String, Object>> toMapList() {
		try {
			List<Map<String, Object>> mlis = new ArrayList<Map<String, Object>>();
			Row header = sheet.getRow(0);
			for (int i = 1; i < this.getRowNum(); i++) { // 循环打印Excel表中的内容
				Map<String, Object> m = new HashMap<String, Object>();
				for (int j = 0; j < this.getColumnNum(); j++) {
					Comment c = header.getCell(i).getCellComment();
					m.put((c != null) ? c.getString().toString().replaceAll("[\\t\\n\\r]", "") : this.getCellVal(0, j),
							this.getCellVal(i, j));
				}
				mlis.add(m);
			}
			return mlis;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, Object> getRowMap(int r) {
		try {
			Row header = sheet.getRow(0);
			Map<String, Object> m = new HashMap<String, Object>();
			for (int j = 0; j < this.getColumnNum(); j++) {
				Comment c = header.getCell(j).getCellComment();
				m.put((c != null) ? c.getString().toString().replaceAll("[\\t\\n\\r]", "") : this.getCellVal(0, j),
						this.getCellVal(r, j));
			}
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建空的workBook 直接创建，不适用模板的话，一些配置（比如数据有效应校验、标注等）office和WPS都支持
	 * 
	 * @param version
	 *            excel版本
	 * @param sheetName
	 *            sheet名称，允许创建0-n个sheet
	 */
	public void createWorkBook(PIOExcelUtil.EXCELVERSION version, String[] sheetName) {

		this.currentVersion = version;

		if (EXCELVERSION.EXCEL_VERSION_2003 == currentVersion) {
			wb = new HSSFWorkbook();
		} else {
			wb = new XSSFWorkbook();
		}

		if (null != sheetName && sheetName.length > 0) {
			for (String sheetname : sheetName) {
				sheet = wb.createSheet(sheetname);
			}
		} else {
			sheet = wb.createSheet();
		}
	}

	/**
	 * 添加批注
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param richText
	 * @param author
	 */
	public void addComment(int rowIndex, int columnIndex, String richText, String author) {
		ClientAnchor anchor = null;
		RichTextString richTextString = null;

		// 创建绘图对象
		Drawing p = sheet.createDrawingPatriarch();
		// 创建单元格对象,批注插入到4行,1列,B5单元格
		Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
		// 获取批注对象
		// HSSFClientAnchor的参数说明：
		// 参数 说明
		// dx1 第1个单元格中x轴的偏移量
		// dy1 第1个单元格中y轴的偏移量
		// dx2 第2个单元格中x轴的偏移量
		// dy2 第2个单元格中y轴的偏移量
		// col1 第1个单元格的列号
		// row1 第1个单元格的行号
		// col2 第2个单元格的列号
		// row2 第2个单元格的行号
		// (int dx1, int dy1, int dx2, int dy2, short col1, int row1, short
		// col2, int row2)
		if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
			anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6);
			richTextString = new HSSFRichTextString(richText);
		} else {
			anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6);
			richTextString = new XSSFRichTextString(richText);
		}

		Comment comment = p.createCellComment(anchor);
		// 输入批注信息
		comment.setString(richTextString);
		// 添加作者,选中B5单元格,看状态栏
		comment.setAuthor(author);
		// 是否可见（可见值一打开excel表格就显示）；默认隐藏
		comment.setVisible(true);
		// 将批注添加到单元格对象中
		cell.setCellComment(comment);
	}

	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(int sheetIndex) {
		this.sheet = wb.getSheetAt(sheetIndex);
	}

	/**
	 * 获取总行数 最大行的index值，实际行数要加1
	 * 
	 * @return
	 */
	public int getRowNum() {
		return rowNum;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public EXCELVERSION getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(EXCELVERSION currentVersion) {
		this.currentVersion = currentVersion;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public int getTotalRows() {
		return this.getRowNum();
	}

	public String getData(int r, int c) {
		return this.getCellVal(r, c);
	}

	public void close() {
		try {
			this.wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getTitles(int titleStartRow) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, int[]> getTitlesMap(int titleStartRow) {
		return null;
	}

	public <S> S autoSetInsertAndOerveriEDTByName(int r, S model, boolean needAdd)
			throws IllegalArgumentException, IllegalAccessException {
		return null;
	}

	public static void main(String[] args) throws Exception {
		PIOExcelUtil util = new PIOExcelUtil("E:\\104\\进销存系统\\表单 新\\表单 新\\供应表格\\1.1 商品汇总表\\数据模板.xlsx", 0);
		int num = util.getRowNum();
		for (int i = 1; i < num; i++) {
			String c0 = util.getCellVal(i, 0);
			String c4 = util.getCellVal(i, 4);
		}
		int total = util.getTotalRows();
		util.close();
	}
}
