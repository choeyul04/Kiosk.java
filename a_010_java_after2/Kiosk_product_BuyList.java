package a_010_java_after2;

import java.sql.*;
import java.util.*;

class Product_BuyList{
    public int     ord_no;					//주문번호
    public int     ord_count;				//count
    public int     ord_pdt_id;				//상품코드
    public int     ord_buying_count;		//주문수량
    public int     ord_pdt_unit_price;		//상품단가
    public int     ord_price;				//금액

    public int     cnt;						//순서
    
    void printScore() {
        System.out.printf("%5d   %3d    %6d    %2d   %7d   %7d \n",
                ord_no, ord_count, ord_pdt_id, ord_buying_count, ord_pdt_unit_price, ord_price);
    }
}
public class Kiosk_product_BuyList {

    public static void main(String[] args) {
    	Scanner kpbl=new Scanner(System.in);
    	int List=0;
    	
        int num_count =0;
        do {
    
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql;
       
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String pw = "1234";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("클래스 로딩 성공");
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("DB 접속");
            sql="select count(*) num from tbl_order_total";
           
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            num_count = rs.getInt("num");
            System.out.println("등록된코드:"+num_count+"건");
           
            System.out.println("주문번호를 입력하세요. 주문 리스트를 출력합니다. 전체:1 종료:9");
            List=kpbl.nextInt();
            
            if(List==1) {
            	Kiosk_product_BuyTotal.main(args);
            }else if(List==9) {
            	Kiosk_MainMenu.main(args);
            }else if(List!=1||List!=9){
            	
            System.out.println("================================================");
            System.out.println("주문번호    순번    상품코드  수량     단가       금액");
            System.out.println("================================================");
           
                sql= "select * from tbl_order_list where ord_no="+List+"order by ord_no";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
               
                num_count = 0;
                int total=0;
                Product_BuyList p =new Product_BuyList();
                while(rs.next()) {
                    p.cnt = num_count+1;
                    num_count++;
                    p.ord_no = rs.getInt("ord_no");
                    p.ord_count = rs.getInt("ord_count");
                    p.ord_pdt_id = rs.getInt("ord_pdt_id");
                    p.ord_buying_count = rs.getInt("ord_buying_count");
                    p.ord_pdt_unit_price = rs.getInt("ord_pdt_unit_price");
                    p.ord_price = rs.getInt("ord_price");
                   
                    p.printScore();
                    
                    total = total + p.ord_price;
                }
                
                System.out.println("================================================");
                System.out.println("***매출합계: " + total);
                }																								//else 끝
    }catch(Exception e) {																						//try 끝
        e.printStackTrace();
    }       
        continue;
        }while(true);																							//do 끝
   }
}