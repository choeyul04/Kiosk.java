package a_010_java_after2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class Productbuy {
	
    private int pdt_id;                	 //상품코드 PK
    private String pdt_id_name;     //상품명
    private int pdt_unit_price;       //단가
    private int pdt_order_method; //1단품 2세트 3추가
   
    public int cnt;
    public String method;
   
    public int b_id;
    public String b_id_name;
    public int b_unit_price;
    public int b_price;
    public int b_order_method;
    public int b_count;   
   
    //상단 메뉴의 Source -> Generate Getters and Setters 클릭 후 설정
    public int getPdt_id() {
        return pdt_id;
    }

    public void setPdt_id(int pdt_id) {
        this.pdt_id = pdt_id;
    }

    public String getPdt_id_name() {
        return pdt_id_name;
    }

    public void setPdt_id_name(String pdt_id_name) {
        this.pdt_id_name = pdt_id_name;
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
        System.out.printf("%3d %5d %5d %2d %1s %5s \n",
                cnt, pdt_id, pdt_unit_price, pdt_order_method, method, pdt_id_name);
    }
    void printBuy() {
        System.out.printf("%3d %5d %5d %2d %7d %2d %2s \n", cnt, b_id, b_unit_price, b_count, b_price, b_order_method, b_id_name);
    }
    
}
   
public class Kiosk_product_BuyChoice {

    public static void main(String[] args) {
    	//String buffer;						//nextLine 관련 skip 보완
        final int N_ARY = 50;				//상수 선언
        int num_count = 0;					//등록돈 데이터베이스 자료 건수
        
        int in_i, in_j, in_index;
       
        int[] b_id 					= new int[N_ARY];			//상품코드
        int[] b_unit_price 		= new int[N_ARY];			//단가
        int[] b_price 				= new int[N_ARY];			//단가*수량
        int[] b_count 				= new int[N_ARY];			//수량
        int[] b_order_method	= new int[N_ARY];			//계산방법(1현금, 2카드)
        String[] b_id_name 		= new String[N_ARY];		//상품명
        
        Scanner input = new Scanner(System.in);
       
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
            sql="select count(*) num from tbl_product_master";
           
           
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            num_count = rs.getInt(1);
           
            Productbuy p[]= new Productbuy [num_count];		//p[] = Productbuy[num_count]의 주소
           
            sql="select * from tbl_product_master order by pdt_order_method, pdt_id";
           
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
           
            int i_cnt=0;
            while(rs.next()) {
                p[i_cnt] = new Productbuy();		//쌤이 중요하다 했음 암튼 중요함***(시험에 나옴)
                
                p[i_cnt].setPdt_id(rs.getInt("pdt_id"));
                p[i_cnt].setPdt_unit_price(rs.getInt("pdt_unit_price"));
                p[i_cnt].setPdt_order_method(rs.getInt("pdt_order_method"));
                if(rs.getInt("pdt_order_method")==1) {
                    p[i_cnt].method = "단품";
                }else if(rs.getInt("pdt_order_method")==2) {
                    p[i_cnt].method = "세트";
                }else if(rs.getInt("pdt_order_method")==3) {
                    p[i_cnt].method = "추가";
                }
                p[i_cnt].setPdt_id_name(rs.getString("pdt_id_name"));
                i_cnt++;
               
            }
           
            in_index=0;
            while(true) {
                System.out.print("단품상품-1 세트상품-2 추가주문상품-3 전체-4 종료-9:");
                in_i = input.nextInt();
                if(in_i > 4 || in_i < 1) {
                    System.out.println("Kisok Main Menu 화면으로 갑니다");
                    Kiosk_MainMenu.main(args);
                    //System.exit(0);
                }
                System.out.println("=============상품코드===================");
                System.out.println(" NO 상품코드   단가  주문방법  상품명");
                System.out.println("=====================================");
                for(int i=0; i<p.length; i++) {
                    int i_m = p[i].getPdt_order_method();
                    if(in_i==1 && i_m==1) {
                        p[i].printScore();								//시험 나올듯
                    } else if(in_i==2 && i_m==2) {
                        p[i].printScore();
                    } else if(in_i==3 && i_m==3) {
                        p[i].printScore();
                    } else if(in_i==4) {
                        p[i].printScore();
                    }
            }
                
                while(true) {
                    System.out.println("===================================");
                    System.out.println("상품코드와 수량을 입력 예: 100 3");
                    System.out.println("전화면:0 확인:1 카드:2 현금:3 취소:4 종료:9");
                    in_i=input.nextInt();
                   
                    if(in_i == 0 || in_i == 9) {
                        break;
                    }else if(in_i == 4) {
                        in_index=0;
                        for(int i = 0; i < N_ARY; i++) {
                            b_id[i]					=0;
                            b_unit_price[i]		=0;
                            b_price[i]				=0;
                            b_count[i]				=0;
                            b_order_method[i]	=0;
                        }
                    }else if(in_i==1) {
                        System.out.println("=============상품코드 내용===================");
                        System.out.println(" NO 상품코드  단가  수량  금액 주문  상품명");
                        System.out.println("=====================================");
                        int tot =0;
                        for(int i=0; i<b_id.length;i++) {
                            if(b_id[i] !=0) {
                                p[i].cnt						=i+1;
                                p[i].b_id						=b_id[i];
                                p[i].b_unit_price			=b_unit_price[i];
                                p[i].b_price					=b_price[i];
                                p[i].b_count				=b_count[i];
                                p[i].b_order_method	=b_order_method[i];
                                p[i].b_id_name			=b_id_name[i];
                               
                                tot += p[i].b_price;
                               
                               
                            }
                        }
                        System.out.println(" ");
                        System.out.println("*********주문금액 합계:" + tot);
                    }else if(in_i==2 || in_i==3) {
                        System.out.println("=============상품코드 내용===================");
                        System.out.println(" NO 상품코드   단가  수량 금액 주문  상품명");
                        System.out.println("=====================================");
                       
                        num_count=0;
                        sql="select max(tot_ord_no) from tbl_order_total";
                        pstmt = conn.prepareStatement(sql);
                         rs = pstmt.executeQuery();
                        rs.next();
                        num_count = rs.getInt(1);
                        if(num_count==0) num_count=100;
                        num_count++;
                       
                        int tot =0;
                        for(int i=0; i<b_id.length; i++) {
                        	if(b_id[i] != 0) {
                        		p[i].cnt 						= i+1;							//순서
                        		p[i].b_id 					= b_id[i];						//상품코드
                        		p[i].b_unit_price 			= b_unit_price[i];			//단가
                        		p[i].b_price 				= b_price[i];					//금액(단가*수량)
                        		p[i].b_count 				= b_count[i];					//수량
                        		p[i].b_order_method 	= b_order_method[i];		//주문방법
                        		p[i].b_id_name 			= b_id_name[i];				//상품명
                        		p[i].printBuy();												//출력품
                        		tot+= p[i].b_price;
                        		
                        		sql = "insert into tbl_order_list values (?, ?, ?, ?, ?, ?)";
                        		pstmt = conn.prepareStatement(sql);
                        		pstmt.setInt(1, num_count);				//주문번호
                        		pstmt.setInt(2, i+1);						//주번리스트(serial count)
                        		pstmt.setInt(3, p[i].b_id);				//상품코드
                        		pstmt.setInt(4, p[i].b_count);			//수량
                        		pstmt.setInt(5, p[i].b_unit_price);		//단가
                        		pstmt.setInt(6, p[i].b_price);			//금액(단가*수량)
                        		pstmt.executeUpdate();
                        		//clear
                        		b_id[i] = 0;
                        		b_unit_price[i] = 0;
                        		b_price[i] = 0;
                        		b_count[i] = 0;
                        		b_order_method[i] = 0;
                        	}
                        	in_index = 0;
                        }
                       if(tot > 0) {
                    	   sql = "insert into tbl_order_total values (?, ?, ?, ?, ?, sysdate)";
                    	   pstmt = conn.prepareStatement(sql);
                    	   pstmt.setInt(1, num_count);
                    	   pstmt.setInt(2, tot);
                    	   pstmt.setInt(3, in_i);
                    	   pstmt.setInt(4, tot);
                    	   pstmt.setInt(5, 0);
                    	   
                    	   pstmt.executeUpdate();
                       }
                       System.out.println("  ");
                       System.out.println("**********주문금액 합계: " + tot);
                    } else {
                    	in_j = 0;
                    	while(in_j==0) {
                    		in_j = input.nextInt();
                    	}
                    	for(int i=0; i<p.length; i++) {
                    		int iom = p[i].getPdt_id();
                    		if(in_i == iom) {
                    			b_id[in_index]						= in_i;									//상품코드
                    			b_unit_price[in_index] 			= p[i].getPdt_unit_price();		//단가
                    			b_price[in_index] 				= p[i].getPdt_unit_price()*in_j;		//금액
                    			b_id_name[in_index] 			= p[i].getPdt_id_name();			//상품명
                    			b_order_method[in_index]	= p[i].getPdt_order_method();	//상품명
                    			b_count[in_index]				= in_j;									//수량
                    			in_index++;
                    		}
                    	}
                    	System.out.println("input_i: "+ in_i +" j:" + in_j);
                    }	//in_i 전화면:0 확인:1 카드:2 현금:3 취소:4 종료:9"
                }	//while end
            }	//while end
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }

}