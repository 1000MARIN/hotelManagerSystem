package com.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.example.domain.HotelVO;

public class HotelDAO {
	// 싱글톤(singleton) 클래스 설계 : 객체 한개만 공유해서 사용하기
	private static HotelDAO instance = new HotelDAO();
	
	public static HotelDAO getInstance() {
		return instance;
	}
	// 생성자를 private로 외부로부터 숨김
	public HotelDAO() {
	}
	// ========= 싱글톤 설계 완료 =========
	
	// DB접속정보
	private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String user = "myuser";
	private final String passwd = "1234";

	// DB접속 후 커넥션 객체 가져오는 메소드
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;

		// 1단계. JDBC 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2단계. DB연결
		con = DriverManager.getConnection(url, user, passwd);

		return con;
	} // getConnection

	private void close(Connection con, PreparedStatement pstmt) {
		close(con, pstmt, null);
	}

	private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		// JDBC 자원 닫기 (사용의 역순으로 닫음)
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close
	
	// 예약 등록
	public void insertHotel(HotelVO hotelVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			
			String sql = "";
			sql += "INSERT INTO hotel(num, name, birth, phone, room, chin, chout, adult, child, breakfast, car, reg_date) ";
			sql += "VALUES (SEQ_HOTEL.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1, hotelVO.getName());
			pstmt.setString(2, hotelVO.getBirth());
			pstmt.setString(3, hotelVO.getPhone());
			pstmt.setString(4, hotelVO.getRoom());
			pstmt.setString(5, hotelVO.getChIn());
			pstmt.setString(6, hotelVO.getChOut());
			pstmt.setString(7, hotelVO.getAdult());
			pstmt.setString(8, hotelVO.getChild());
			pstmt.setString(9, hotelVO.getBreakfast());
			pstmt.setString(10, hotelVO.getCar());
			pstmt.setTimestamp(11, hotelVO.getRegDate());
			
			pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		
	} // insertHotel
	
	
	// 예약번호로 해당하는 회원정보 삭제하는 메소드
	// DELETE FROM member WHERE num = ?;
	public void deleteByNum(int num) {
		
		Connection con = null; // 접속
		PreparedStatement pstmt = null; // sql문장객체 타입
		
		try {
			con = getConnection();
			
			String sql = "DELETE FROM hotel WHERE num = ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		
	} // deleteById
	
	public void removeByNum(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			
			String sql = "";
			sql += "DELETE FROM hotel ";
			sql += "WHERE num = ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		
	} // removeByNum
	
	public int deleteAll() {
		int count = 0;
		
		// JDBC
		Connection con = null; // 접속
		PreparedStatement pstmt = null; // sql문장객체 타입
		
		try {
			con = getConnection();
			// sql문 준비
			String sql = "DELETE FROM member";
			// 3단계. pstmt 문장객체 생성
			pstmt = con.prepareStatement(sql);
			// 4단계. sql 문장 실행
			count = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return count;
	} // deleteAll

	public List<HotelVO> getGuests() {
		List<HotelVO> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql = "SELECT * FROM hotel ORDER BY num DESC ";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
		
		while (rs.next()) {
			HotelVO hotelVO = new HotelVO();
			hotelVO.setNum(rs.getInt("num"));
			hotelVO.setName(rs.getString("name"));
			hotelVO.setBirth(rs.getString("birth"));
			hotelVO.setPhone(rs.getString("phone"));
			hotelVO.setRoom(rs.getString("room"));
			hotelVO.setChIn(rs.getString("chin"));
			hotelVO.setChOut(rs.getString("chout"));
			hotelVO.setAdult(rs.getString("adult"));
			hotelVO.setChild(rs.getString("child"));
			hotelVO.setBreakfast(rs.getString("breakfast"));
			hotelVO.setCar(rs.getString("car"));
			hotelVO.setRegDate(rs.getTimestamp("reg_date"));
			
			list.add(hotelVO);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	} // getFriends
	
	public Vector getGuestsV() {
		Vector list = new Vector();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql = "SELECT * FROM hotel ORDER BY num DESC ";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Vector<String> resultV = new Vector<String>();
			resultV.add(String.valueOf(rs.getInt(1)));
			resultV.add(rs.getString(2));
			resultV.add(rs.getString(3));
			resultV.add(rs.getString(4));
			resultV.add(rs.getString(5));
			resultV.add(rs.getString(6));
			resultV.add(rs.getString(7));
			resultV.add(rs.getString(8));
			resultV.add(rs.getString(9));
			resultV.add(rs.getString(10));
			resultV.add(rs.getString(11));
			resultV.add(String.valueOf(rs.getTimestamp(12)));
			list.add(resultV);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	} // getFriends
	
	public Vector getGuestsVOption(String whereOption) {
		Vector list = new Vector();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql = 	"	SELECT * FROM hotel "
					+ 		"	" + whereOption +
					 		"	ORDER BY num DESC ";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
		
		while (rs.next()) {
			Vector<String> resultV = new Vector<String>();
			resultV.add(String.valueOf(rs.getInt(1)));
			resultV.add(rs.getString(2));
			resultV.add(rs.getString(3));
			resultV.add(rs.getString(4));
			resultV.add(rs.getString(5));
			resultV.add(rs.getString(6));
			resultV.add(rs.getString(7));
			resultV.add(rs.getString(8));
			resultV.add(rs.getString(9));
			resultV.add(rs.getString(10));
			resultV.add(rs.getString(11));
			resultV.add(String.valueOf(rs.getTimestamp(12)));
			list.add(resultV);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	} // getFriends
	
//	public HotelVO getHotelByNum(int num) {
//		HotelVO hotelVO = null;
	public List<HotelVO> getHotelByNum(int num) {
		List<HotelVO> list = new ArrayList<>();
		
		Connection con = null; // 접속
		PreparedStatement pstmt = null; // sql문장객체 타입
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql = "SELECT * FROM hotel WHERE num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				HotelVO hotelVO = new HotelVO();
				hotelVO.setNum(rs.getInt("num"));
				hotelVO.setName(rs.getString("name"));
				hotelVO.setBirth(rs.getString("birth"));
				hotelVO.setPhone(rs.getString("phone"));
				hotelVO.setRoom(rs.getString("room"));
				hotelVO.setChIn(rs.getString("chin"));
				hotelVO.setChOut(rs.getString("chout"));
				hotelVO.setAdult(rs.getString("adult"));
				hotelVO.setChild(rs.getString("child"));
				hotelVO.setBreakfast(rs.getString("breakfast"));
				hotelVO.setCar(rs.getString("car"));
				hotelVO.setRegDate(rs.getTimestamp("reg_date"));
				
				list.add(hotelVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	} // getHotelByNum
	
	
	// 예약 정보 수정하기
	public void updateByNum(HotelVO hotelVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			
			String sql = "UPDATE hotel ";
			sql += "SET name = ?, birth = ?, phone = ?, room = ?, chin = ?,  chout = ?, adult = ?, child = ?, breakfast = ?, car = ?, reg_date = ? ";
			sql += "WHERE num = ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hotelVO.getName());
			pstmt.setString(2, hotelVO.getBirth());
			pstmt.setString(3, hotelVO.getPhone());
			pstmt.setString(4, hotelVO.getRoom());
			pstmt.setString(5, hotelVO.getChIn());
			pstmt.setString(6, hotelVO.getChOut());
			pstmt.setString(7, hotelVO.getAdult());
			pstmt.setString(8, hotelVO.getChild());
			pstmt.setString(9, hotelVO.getBreakfast());
			pstmt.setString(10, hotelVO.getCar());
			pstmt.setTimestamp(11, hotelVO.getRegDate());
			pstmt.setInt(12, hotelVO.getNum());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		
	} // updateByNum
	
	
	
	
	public List<HotelVO> search(String field, String word) {
		
		List<HotelVO> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql = "";
			sql += "SELECT * ";
			sql += "FROM hotel ";
			sql += "WHERE " + field + " LIKE ? ";
			
			pstmt =  con.prepareStatement(sql);
			pstmt.setString(1, "%" + word + "%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				HotelVO hotelVO = new HotelVO();
				hotelVO.setNum(rs.getInt("num"));
				hotelVO.setName(rs.getString("name"));
				hotelVO.setBirth(rs.getString("birth"));
				hotelVO.setPhone(rs.getString("phone"));
				hotelVO.setRoom(rs.getString("room"));
				hotelVO.setChIn(rs.getString("chin"));
				hotelVO.setChOut(rs.getString("chout"));
				hotelVO.setAdult(rs.getString("adult"));
				hotelVO.setChild(rs.getString("child"));
				hotelVO.setBreakfast(rs.getString("breakfast"));
				hotelVO.setCar(rs.getString("car"));
				hotelVO.setRegDate(rs.getTimestamp("reg_date"));
				
				list.add(hotelVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	} // search
	
	// SELECT COUNT(*) AS cnt FROM member;
	public int getCountAll() {
		int count = 0;
	
		Connection con = null; // 접속
		PreparedStatement pstmt = null; // sql문장객체 타입
		ResultSet rs = null;
			
		try {
			con = getConnection();
			
			String sql = "SELECT count(*) AS cnt FROM member";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1); // rs.getInt("cnt")
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return count;
	} // getCountAll
		
	public int num() {
		int num = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
			
		try {
			con = getConnection();
			
			String sql = "";
			sql += "select SEQ_HOTEL.nextval from dual";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("NEXTVAL")-3;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return num;
	}
}
