package com.example.repository;


import static org.junit.Assert.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.domain.HotelVO;

public class HotelDAOTests {

	// 픽스처(fixture)
	private HotelDAO hotelDAO;
	
	// 샘플데이터 준비
	private HotelVO hotelVO;
	
	@BeforeClass
	public static void beforeTest() {
		System.out.println("@BeforeClass - beforeTest() : 테스트클래스 로딩 후 객체생성 전에 static 멤버 준비 위해 먼저 호출됨.");
	}
	
	@Before
	public void setUp() {
		System.out.println("@Before - setUp() : 테스트클래스 객체생성 후 테스트 호출에 앞서 먼저 호출됨");
		hotelDAO = new HotelDAO();
		hotelDAO.deleteAll();
		hotelVO = new HotelVO();
		hotelVO.setNum(hotelDAO.num());
		hotelVO.setName("홍길동");
		hotelVO.setBirth("900101");
		hotelVO.setPhone("1111-1111");
		hotelVO.setRoom("프리미엄");
		hotelVO.setChIn("20210801");
		hotelVO.setChOut("20210810");
		hotelVO.setAdult("3");
		hotelVO.setChild("2");
		hotelVO.setBreakfast("5");
		hotelVO.setCar("O");
		hotelVO.setRegDate(new Timestamp(System.currentTimeMillis()));
	} // end of setUp
	
	@Test
	public void testConnection() { // DB접속 테스트
		// DB접속정보
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "myuser";
		String passwd = "1234";
		
		try {
			// 1단계. JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2단계. DB연결
			Connection con = DriverManager.getConnection(url, user, passwd);
			assertNotNull(con); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // testConnection
	
	@Test
	public void testDeleteByNUm() {
		hotelDAO.insertHotel(hotelVO);
		int num = hotelDAO.num(); 
		hotelDAO.removeByNum(num);
		
		int count = hotelDAO.getCountAll();
		assertEquals(0, count);
	} // testDeleteByNUm

	
	@Test
	public void testInsert() {// Insert 테스트
		//hotelDAO.deleteAll();
		hotelDAO.insertHotel(hotelVO);
		
		int num = hotelDAO.num();  // 현재 num 값 받아옴
		assertNotNull(hotelDAO.getHotelByNum(num));
		
		List<HotelVO> list = hotelDAO.getHotelByNum(num);
		for(HotelVO dbHotel : list) {
			assertEquals(hotelVO.getNum(), dbHotel.getNum() );
			assertEquals(hotelVO.getBirth(), dbHotel.getBirth() );
			assertEquals(1, list.size());
		}
		
		hotelDAO.removeByNum(num);
	} // testInsert

	@Test
	public void testUpdateByNum() {
		//hotelDAO.deleteAll();
		hotelDAO.insertHotel(hotelVO);
		
		int num = hotelDAO.num();  // 현재 num 값 받아옴
		assertNotNull(hotelDAO.getHotelByNum(num));
		
		HotelVO updateHotel = new HotelVO();
		updateHotel.setNum(num);
		updateHotel.setName("김철수");
		updateHotel.setBirth("980701");
		updateHotel.setPhone("3333-3333");
		updateHotel.setRoom("디럭스");
		updateHotel.setChIn("20211010");
		updateHotel.setChOut("20211108");
		updateHotel.setAdult("2");
		updateHotel.setChild("1");
		updateHotel.setBreakfast("3");
		updateHotel.setCar("X");
		updateHotel.setRegDate(new Timestamp(System.currentTimeMillis()));
		hotelDAO.updateByNum(updateHotel);
		
		List<HotelVO> list = hotelDAO.getHotelByNum(num);
		for(HotelVO dbHotel : list) {
			assertEquals(updateHotel.getNum(), dbHotel.getNum() );
			assertEquals(updateHotel.getBirth(), dbHotel.getBirth() );
			assertEquals(1, list.size());
		}
		hotelDAO.removeByNum(num);

	} // testUpdateByNum
	
	@Test
	public void testSelectByNum() {
		//hotelDAO.deleteAll();
		
		int num = hotelDAO.num();  // 현재 num 값 받아옴
		assertNotNull(hotelDAO.getHotelByNum(num));
		
		HotelVO newHotel = new HotelVO();
		newHotel.setNum(num);
		newHotel.setName("손흥민");
		newHotel.setBirth("990801");
		newHotel.setPhone("7777-7777");
		newHotel.setRoom("로얄 스위트");
		newHotel.setChIn("20210815");
		newHotel.setChOut("20210819");
		newHotel.setAdult("1");
		newHotel.setChild("0");
		newHotel.setBreakfast("0");
		newHotel.setCar("O");
		newHotel.setRegDate(new Timestamp(System.currentTimeMillis()));
		hotelDAO.updateByNum(newHotel);
		
		List<HotelVO> list = hotelDAO.getHotelByNum(num);
		for(HotelVO dbHotel : list) {
			assertEquals(newHotel.getNum(), dbHotel.getNum() );
			assertEquals(newHotel.getBirth(), dbHotel.getBirth() );
			assertEquals(1, list.size());
		}
		hotelDAO.removeByNum(num);

	} // testSelectByNum
	
	@After
	public void tearDown() {
		System.out.println("@After - tearDown() : 테스트 호출 후 정리작업 위해 호출됨. 다음 테스트를 위해 현재 객체를 폐기함.");
	}
	
	@AfterClass
	public static void afterTest() {
		System.out.println("@AfterClass - afterTest() : 테스트클래스가 메모리에서 해제되거 전에 static 정리작업 위해 마지막으로 호출됨.");
	}
	
}
