import java.io.InputStream;

import com.jfinal.club.common.kit.PIOExcelUtil;

public class ReadWorknoAddress {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InputStream input = ReadWorknoAddress.class.getResourceAsStream("excel/order_date.xlsx");
		if (input == null) {
			System.exit(0);
		}
		PIOExcelUtil excel = new PIOExcelUtil(input, 0);
		
		
		int rows = excel.getRowNum() + 1;
		for (int i = 1; i < rows; i++) {

			// System.out.println(i + " work no " + excel.getCellVal(i, 4) + " address " +
			// excel.getCellVal(i, 19));
			String row = String.format("update fy_business_order set send_address='%s' where work_order_no ='%s' ; ",
					excel.getCellVal(i, 19), excel.getCellVal(i, 4));
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
