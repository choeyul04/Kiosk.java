package a_010_java_after2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

class product_master{
    private String pdt_id_name;         //상품명
    private int pdt_id;						//상품코드 PK        
    private int pdt_unit_price;        	//단가        
    private int pdt_order_method; 		//주문방법        
    public int cnt;

    public String getPdt_id_name() {
        return pdt_id_name;
    }
    public void setPdt_id_name(String pdt_id_name) {
        this.pdt_id_name = pdt_id_name;
    }
    public int getPdt_id() {
        return pdt_id;
    }
    public void setPdt_id(int pdt_id) {
        this.pdt_id = pdt_id;
    }
    public int getPdt_unit_price() {
        return pdt_unit_price;
    }
    public void setPdt_unit_price(int pdt_unit_price) {
        this.pdt_unit_price = pdt_unit_price;
    }
    public int getPdt_order_method() {
        return pdt_order_method;
    }
    public void setPdt_order_method(int pdt_order_method) {
        this.pdt_order_method = pdt_order_method;
    }

    void printScore() {
        System.out.printf(" %3d %3s %3d %3d %3d \n",
                cnt, pdt_id_name, pdt_id, pdt_unit_price, pdt_order_method   );
    }    
}



public class Kiosk_product_Insert {
    public static void main(String[] args) {
        Scanner Insert = new Scanner(System.in);
        do {
        System.out.print("몇개의 상품코드를 등록하시겠습니까? : ");
        int num = Insert.nextInt();
       
        product_master stu[] = new product_master[num];
       
        for(int i=0; i<stu.length; i++) {
           
            stu[i] = new product_master();    // 매우 중요!!! 배열은 인덱스 모두에 객체생성 후 참조배열 연계
            System.out.print("상품 이름을 입력하세요: ");
            stu[i].setPdt_id_name(Insert.next());
            System.out.print("상품코드를 입력하세요: ");
            stu[i].setPdt_id(Insert.nextInt());
            System.out.print("단가를 입력하세요: ");
            stu[i].setPdt_unit_price(Insert.nextInt());
            System.out.print("주문 방법을 입력하세요: ");
            stu[i].setPdt_order_method(Insert.nextInt());
        }
       
        System.out.println("===============상품코드 등록 내용===============");
        System.out.printf(" NO 상품명 상품코드 단가 주문방법   \n");
        System.out.println("============================================");
        for (int i=0; i<stu.length; i++) {
            stu[i].cnt = i+1;
            stu[i].printScore();
        }
        System.out.println("===========================================");
        System.out.println("취소할거면 1번을 제외한 번호를 눌러주세요");
        num=Insert.nextInt();
        if(num==1) {
        //DB
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql;

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String pw = "1234";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
//            System.out.println("클래스 로딩 성공");
            conn = DriverManager.getConnection(url, id, pw);
//            System.out.println("DB 접속");
           
            //setCharacterEncoding("UTF-8);
            for(int i=0; i<stu.length; i++) {
                sql = "insert into tbl_product_master values (?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
               
                pstmt.setInt(1, stu[i].getPdt_id());
                pstmt.setString(2, stu[i].getPdt_id_name());
                pstmt.setInt(3, stu[i].getPdt_unit_price());
                pstmt.setInt(4, stu[i].getPdt_order_method());
               
                pstmt.executeUpdate();
            }
            System.out.println("==================================================");
            pstmt.close();
            conn.close();
        }catch(Exception e) {        
            e.printStackTrace();
        }
        }
        }while(true);
    }    
}