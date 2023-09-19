package a_010_java_after2;

import java.util.Scanner;

public class Kiosk_MainMenu {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println("======================================");
			System.out.println("******** KIOSK 상품등록 및 매장 운영프로그램 ********");
			System.out.println("======================================");
			System.out.println("    1. 상품코드 등록");
			System.out.println("    2. 등록된 상품코드 조회");
			System.out.println("    3. KIOSK 매장 운영 프로그램(주문입력)");
			System.out.println("    4. 주문번호 별 매출내역서");
			System.out.println("    5. 매장 주문서별 LIST");
			System.out.println("    6. 매장 매출별 LIST");
			System.out.println("    0. 작업을 종료합니다.");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("작업 번호를 선택하세요");
			
			int i_num = input.nextInt();
			if(i_num == 1) {
				Kiosk_product_Insert.main(args);
			} else if(i_num == 2) {
				Kiosk_product_InquiryChoice.main(args);				
			} else if(i_num == 3) {
				Kiosk_product_BuyChoice.main(args);				
			} else if(i_num == 4) {
				Kiosk_product_BuyTotal.main(args);
			} else if(i_num == 5) {
				Kiosk_product_BuyList.main(args);
			} else if(i_num == 6) {
				Kiosk_product_SalesListTotal.main(args);
			} else if(i_num == 7) {
				Kiosk_product_SalesList.main(args);
			}	else if(i_num == 0) {
				System.out.println("작업을 종료합니다.");
				break;
			}
		}
	}
	
}
