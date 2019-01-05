import java.io.InputStream;
import java.text.SimpleDateFormat;

import com.jfinal.club.common.kit.PIOExcelUtil;

public class ReadOrderDateAndDelivday {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InputStream input = ReadOrderDateAndDelivday.class.getResourceAsStream("excel/order_date.xlsx");
		if (input == null) {
			System.exit(0);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		PIOExcelUtil excel = new PIOExcelUtil(input, 0);
		
		
		int rows = excel.getRowNum() + 1;
		for (int i = 1; i < rows; i++) {

			// System.out.println(i + " work no " + excel.getCellVal(i, 4) + " address " +
			// excel.getCellVal(i, 19));
			String row = String.format(
					"update fy_business_order set order_date='%s',delivery_date='%s' where work_order_no ='%s' ; ",
					format.format(excel.getDateValue(i, 15)), format.format(excel.getDateValue(i, 16)),
					excel.getCellVal(i, 4));

			System.out.println(row);
		}
		if (input == null) {
			System.out.println("null");
		} else {
			input.close();
			System.out.println("get ");
		}
	}


}
