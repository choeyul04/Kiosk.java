package a_010_java_after2;

import java.sql.*;
import java.util.*;
import java.util.Date;

class Product_BuyTotal{
    public int     tot_ord_no;                    //주문번호
    public int     tot_ord_price;                //금액
    public int     tot_buying_method;            //지급방법 카드1 현금2
    public int     tot_in_money;                //받은 돈
    public int     tot_out_money;                //거스름 돈
    public Date    tot_system_date;                //system_date

    public int     cnt;                        //순서
    public String  method;


   
    void printScore() {
        System.out.printf("%3d   %5d    %5d %2d     %1d     %1d \n",
                cnt,tot_ord_no,tot_ord_price,tot_buying_method,tot_in_money,tot_out_money);
    }
}
public class Kiosk_product_BuyTotal {

    public static void main(String[] args) {
        int tot_price = 0; 
        Scanner input = new Scanner(System.in);
       
        int num_count =0;
        do {
        int in=4;
        if(in==9) {
            System.out.println("Kisok Main Menu 화면으로 갑니다");
            Kiosk_MainMenu.main(args);
        }
        else if(in>4 || in <1) {
            System.out.println("프로그램 종료합니다");
            System.exit(0);
        }
       
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
            if(in > 0 && in <=3) {
                sql="select count(*) num from tbl_order_total where tot_buying_method=" + in;
               
            }
           
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            num_count = rs.getInt("num");
           
            System.out.println("=========================================");
            System.out.println(" NO 주문번호    금액  지급방법  받은 돈  거스름 돈");
            System.out.println("=========================================");
           
            if(in > 0 && in <= 3) {
                sql="select * from tbl_order_total where tot_buying_method=" + in +"order by tot_ord_no";
            }else {
                sql= "select * from tbl_order_total order by tot_buying_method, tot_ord_no";
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
           
            num_count = 0;
           
            Product_BuyTotal p =new Product_BuyTotal();
            while(rs.next()) {
                p.cnt = num_count+1;
                num_count++;
                p.tot_ord_no = rs.getInt("tot_ord_no");
                p.tot_ord_price = rs.getInt("tot_ord_price");
                p.tot_buying_method = rs.getInt("tot_buying_method");
                if(rs.getInt("tot_buying_method")==1) {
                    p.method="카드";
                } else if(rs.getInt("tot_buying_method")==2) {
                    p.method="현금";
                }
                p.tot_in_money = rs.getInt("tot_in_money");
                p.tot_out_money = rs.getInt("tot_out_money");
                tot_price += rs.getInt("tot_ord_price");
                p.printScore();
        }
            System.out.println("======================================");
            System.out.println("***매출합계:"+tot_price);
    }catch(Exception e) {
        e.printStackTrace();
    }
        break;
        }while(true);
   }
}