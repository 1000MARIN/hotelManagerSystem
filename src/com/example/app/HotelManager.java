package com.example.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.example.domain.HotelVO;
import com.example.repository.DateLabelFormatter;
import com.example.repository.HotelDAO;
import javax.swing.SpringLayout;

public class HotelManager extends JFrame {
	
	private Vector<String> columnNames = new Vector<>();
	
	private HotelDAO hotelDAO = new HotelDAO();
	private Container container;
	private HotelManager frame;
	
	private JPanel panelMain;
	private JSplitPane splitPane;
	private JPanel panelLeft;
	private JPanel panelRight;
	private JLabel lblName;
	private JTextField tfName;
	private JLabel lblBirth;
	private JTextField tfBirth;
	private JLabel lblphone;
	private JTextField tfPhone1;
	private JLabel lblRoom;
	private JLabel lblIn;
	private JLabel lblOut;
	private JLabel lblBreak;
	private JLabel lblCar;
	private JRadioButton rdbtnCar;
	private JRadioButton rdbtnWalk;
	private JLabel lblManInfo;
	private JLabel lblRoomInfo;
	private JButton btnAdd;
	private JButton btnTotal;
	private JButton btnModify;
	private JButton btnRemove;
	private JComboBox cbSearch;
	private JTextField tfSearch;
	private JButton btnSearch;
	private JTable table;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox cbRoom;
	private JLabel lblCount;
	private JLabel lblAdult;
	private JSpinner spAdult;
	private JLabel lblChild;
	private JSpinner spBreakfast;
	private JSpinner spChild;
	private JLabel lblBreakfastM;
	private JLabel lblAdultM;
	private JLabel lblChildM;
	private JLabel lblReNum;
	private JTextField tfReNum;
	private DefaultTableModel model;
	private DefaultTableCellRenderer 	colrend;
	private TableColumn					column;
	private Vector hotelV = new Vector();
	private final JComboBox cbPhone = new JComboBox();
	private JTextField tfPhone2;
	private String[] YEARS	= null;
	private String[] MONTHS = null;
	private String[] DAYS = null;
	private int	month = Integer.parseInt(String.valueOf(new Timestamp(System.currentTimeMillis())).substring(5,7));	// 현재 날짜의 월 글자 받아오기
	private int	day = Integer.parseInt(String.valueOf(new Timestamp(System.currentTimeMillis())).substring(8,10));	// 현재 날짜의 일 글자 받아오기
	private final JLabel lblNewLabel_4 = new JLabel("(주민번호 앞 6자리)");
	private JDatePickerImpl datePickerImpl;
	private JDatePickerImpl datePickerImpl_1;
	

	public static void main(String[] args) {
		new HotelManager();
		
	}
	public HotelManager() {
		super("Hotel Manager System");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		setResizable(false);
		
		Container container = getContentPane();
		
		
		// 연도 콤보박스
		String str = "";
		int years  = Integer.parseInt(String.valueOf(new Timestamp(System.currentTimeMillis())).substring(0, 4)); // 현재 날짜의 년 글자 받아오기
		
		for (int i = years; i <= years+10; i++) {
			if(i != years+10) {
				str += i + "," ;
			} else {
				str += i;
			}
		}
		YEARS = str.split(","); // 배열을 줄바꿈
		
		
		// 월 콤보박스
		String str2 = "";
		
		for(int i = 1; i <= 12; i++) {
			if(i != 12) {
				str2 += i + "," ;
			} else {
				str2 += i;
			}
		}
		MONTHS = str2.split(","); // 배열을 줄바꿈
		
		// 일 콤보박스
		String str3 = "";
		
		for(int i = 1; i <= 31; i++) {
			if(i != 31) {
				str3 += i + "," ;
			} else {
				str3 += i;
			}
		}
		DAYS = str3.split(","); // 배열을 줄바꿈
		
		
		/*for (int i = 0; i < YEARS.length; i++) {
			
		}
		YEARS =*/  
		
		//int month = Integer.parseInt(String.valueOf(new Timestamp(System.currentTimeMillis())).substring(5,7));
		
		
		//System.out.println(month);
		
		// 파비콘 설정
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("img/h1.png"); // 원하는 아이콘 불러오기
		setIconImage(img);
		
		getContentPane().add(getSplitPane(), BorderLayout.CENTER);
		setLocationRelativeTo(null);	// 화면의 가운데 창 띄우기
		setVisible(true);
	}
	
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setLeftComponent(getPanelLeft());
			splitPane.setRightComponent(getPanelRight());
			splitPane.setDividerLocation(400);
		}
		return splitPane;
	}
	
	private JPanel getPanelLeft() {
		if (panelLeft == null) {
			panelLeft = new JPanel();
			panelLeft.setBackground(SystemColor.textHighlightText);
			panelLeft.setLayout(null);
			panelLeft.add(getLblName());
			panelLeft.add(getTfName());
			panelLeft.add(getLblBirth());
			panelLeft.add(getTfBirth());
			panelLeft.add(getLblphone());
			panelLeft.add(getTfPhone1());
			panelLeft.add(getLblRoom());
			panelLeft.add(getLblIn());
			panelLeft.add(getLblOut());
			panelLeft.add(getLblBreak());
			panelLeft.add(getLblCar());
			panelLeft.add(getRdbtnCar());
			panelLeft.add(getRdbtnWalk());
			panelLeft.add(getLblManInfo());
			panelLeft.add(getLblRoomInfo());
			panelLeft.add(getBtnAdd());
			panelLeft.add(getBtnTotal());
			panelLeft.add(getBtnModify());
			panelLeft.add(getBtnRemove());
			panelLeft.add(getCbSearch());
			panelLeft.add(getTfSearch());
			panelLeft.add(getBtnSearch());
			panelLeft.add(getCbRoom());
			panelLeft.add(getLblCount());
			panelLeft.add(getLblAdult());
			panelLeft.add(getSpAdult());
			panelLeft.add(getLblChild());
			panelLeft.add(getSpBreakfast());
			panelLeft.add(getSpChild());
			panelLeft.add(getLblBreakfastM());
			panelLeft.add(getLblAdultM());
			panelLeft.add(getLblChildM());
			panelLeft.add(getLblReNum());
			panelLeft.add(getTfReNum());
			cbPhone.setBackground(UIManager.getColor("CheckBox.background"));
			cbPhone.setMaximumRowCount(5);
			cbPhone.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			cbPhone.setModel(new DefaultComboBoxModel(new String[] {"010", "02", "031", "032", "041", "042", "051", "052", "061", "062"}));
			cbPhone.setBounds(190, 163, 55, 28);
			panelLeft.add(cbPhone);
			panelLeft.add(getTfPhone2());
			lblNewLabel_4.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
			lblNewLabel_4.setBounds(95, 116, 100, 18);
			
			panelLeft.add(lblNewLabel_4);
			panelLeft.add(getDatePickerImpl());
			panelLeft.add(getDatePickerImpl_1());
		}
		return panelLeft;
	}
	
	private void setListener(JComboBox combox, int days) {
		if(combox.getSelectedIndex() != days) {
			combox.setSelectedIndex(days-1);
		}
	}
	
	private JPanel getPanelRight() {
		if (panelRight == null) {
			panelRight = new JPanel();
			JScrollPane scp = new JScrollPane(getTable());
			scp.setPreferredSize(new Dimension(774, 756)); // 테이블 크기 설정
			panelRight.add(scp);
			columnNames.add("예약번호");
			columnNames.add("이름");
			columnNames.add("생년월일");
			columnNames.add("전화번호");
			columnNames.add("룸");
			columnNames.add("체크 인 ");
			columnNames.add("체크 아웃");
			columnNames.add("성인");
			columnNames.add("어린이");
			columnNames.add("조식");
			columnNames.add("차량");
			columnNames.add("예약 등록 일자");
		}
		return panelRight;
	}
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("이름");
			lblName.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblName.setBounds(19, 62, 97, 22);
		}
		return lblName;
	}
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBorder(null);
			tfName.setBackground(UIManager.getColor("CheckBox.background"));
			tfName.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			tfName.setBounds(258, 61, 94, 28);
			tfName.setColumns(10);
			
		}
		return tfName;
	}
	private JLabel getLblBirth() {
		if (lblBirth == null) {
			lblBirth = new JLabel("생년월일");
			lblBirth.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblBirth.setBounds(19, 112, 86, 22);
		}
		return lblBirth;
	}
	private JTextField getTfBirth() {
		if (tfBirth == null) {
			tfBirth = new JTextField();
			tfBirth.setBorder(null);
			tfBirth.setBackground(UIManager.getColor("CheckBox.background"));
			tfBirth.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			tfBirth.setBounds(258, 111, 94, 28);
			tfBirth.setColumns(10);
		}
		return tfBirth;
	}
	private JLabel getLblphone() {
		if (lblphone == null) {
			lblphone = new JLabel("전화번호");
			lblphone.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblphone.setBounds(19, 162, 80, 22);
		}
		return lblphone;
	}
	private JTextField getTfPhone1() {
		if (tfPhone1 == null) {
			tfPhone1 = new JTextField();
			tfPhone1.setBorder(null);
			tfPhone1.setBackground(UIManager.getColor("CheckBox.background"));
			tfPhone1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			tfPhone1.setBounds(253, 163, 45, 28);
			tfPhone1.setColumns(4);
		}
		return tfPhone1;
	}
	private JLabel getLblRoom() {
		if (lblRoom == null) {
			lblRoom = new JLabel("객실 타입");
			lblRoom.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblRoom.setBounds(19, 262, 97, 22);
		}
		return lblRoom;
	}
	private JLabel getLblIn() {
		if (lblIn == null) {
			lblIn = new JLabel("체크 인");
			lblIn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblIn.setBounds(19, 312, 65, 22);
		}
		return lblIn;
	}
	private JLabel getLblOut() {
		if (lblOut == null) {
			lblOut = new JLabel("체크 아웃");
			lblOut.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblOut.setBounds(200, 312, 80, 22);
		}
		return lblOut;
	}
	private JLabel getLblBreak() {
		if (lblBreak == null) {
			lblBreak = new JLabel("조식");
			lblBreak.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblBreak.setBounds(19, 412, 45, 22);
		}
		return lblBreak;
	}
	private JLabel getLblCar() {
		if (lblCar == null) {
			lblCar = new JLabel("방문 방법");
			lblCar.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblCar.setBounds(20, 462, 86, 22);
		}
		return lblCar;
	}
	private JRadioButton getRdbtnCar() {
		if (rdbtnCar == null) {
			rdbtnCar = new JRadioButton("차량");
			rdbtnCar.setBackground(UIManager.getColor("Button.highlight"));
			buttonGroup.add(rdbtnCar);
			rdbtnCar.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			rdbtnCar.setBounds(220, 462, 65, 26);
		}
		return rdbtnCar;
	}
	private JRadioButton getRdbtnWalk() {
		if (rdbtnWalk == null) {
			rdbtnWalk = new JRadioButton("도보");
			rdbtnWalk.setBackground(UIManager.getColor("Button.highlight"));
			buttonGroup.add(rdbtnWalk);
			rdbtnWalk.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			rdbtnWalk.setBounds(297, 462, 55, 26);
		}
		return rdbtnWalk;
	}
	private JLabel getLblManInfo() {
		if (lblManInfo == null) {
			lblManInfo = new JLabel("예약자 정보");
			lblManInfo.setFont(new Font("맑은 고딕", Font.BOLD, 22));
			lblManInfo.setBounds(12, 12, 137, 37);
		}
		return lblManInfo;
	}
	private JLabel getLblRoomInfo() {
		if (lblRoomInfo == null) {
			lblRoomInfo = new JLabel("객실 정보");
			lblRoomInfo.setFont(new Font("맑은 고딕", Font.BOLD, 22));
			lblRoomInfo.setBounds(12, 212, 137, 26);
		}
		return lblRoomInfo;
	}
	
	private JButton getBtnTotal() {
		if (btnTotal == null) {
			btnTotal = new JButton("예약 현황");
			btnTotal.setForeground(SystemColor.text);
			btnTotal.setBackground(SystemColor.textHighlight);
			btnTotal.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selected();
				}
			});
			btnTotal.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnTotal.setBounds(80, 560, 251, 28);
		}
		return btnTotal;
	}
	private JButton getBtnModify() {
		if (btnModify == null) {
			btnModify = new JButton("예약 변경");
			btnModify.setForeground(SystemColor.textHighlightText);
			btnModify.setBackground(new Color(255, 153, 51));
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String word4 = tfReNum.getText();
					if (word4.isEmpty()) {
						JOptionPane.showMessageDialog(HotelManager.this, "예약번호를 입력하세요.", "입력 에러",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int num = Integer.parseInt(tfReNum.getText());
					
					int result = JOptionPane.showConfirmDialog(HotelManager.this, 
							num + "번 예약을 정말 변경 하겠습니까?", "예약 변경", 
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE );
				
					switch (result) {
					case JOptionPane.NO_OPTION:
					case JOptionPane.CANCEL_OPTION:
						return ;
					}
					
					HotelVO hotelVO = new HotelVO();
					
					hotelVO.setNum(Integer.parseInt(tfReNum.getText()));
					hotelVO.setName(tfName.getText().trim());
					hotelVO.setBirth(tfBirth.getText().trim());
					hotelVO.setPhone(cbPhone.getSelectedItem().toString() + "-"+tfPhone1.getText().trim() + "-"+tfPhone2.getText().trim());
					hotelVO.setRoom(cbRoom.getSelectedItem().toString());
					hotelVO.setChIn(datePickerImpl.getJFormattedTextField().getText().trim().replaceAll("-", ""));
					hotelVO.setChOut(datePickerImpl_1.getJFormattedTextField().getText().trim().replaceAll("-", ""));
					hotelVO.setAdult(spAdult.getValue().toString());
					hotelVO.setChild(spChild.getValue().toString());
					hotelVO.setBreakfast(spBreakfast.getValue().toString());
					hotelVO.setCar(rdbtnCar.isSelected() ? "O" : "X");
					hotelVO.setRegDate(new Timestamp(System.currentTimeMillis()));
					
					hotelDAO.updateByNum(hotelVO);
					
					JOptionPane.showMessageDialog(frame, num + "번 예약 변경이 완료되었습니다.", "예약변경", JOptionPane.INFORMATION_MESSAGE);
	
					btnTotal.doClick();

				}
			});
			btnModify.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnModify.setBounds(80, 660, 116, 28);
		}
		return btnModify;
	}
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("예약 취소");
			btnRemove.setForeground(SystemColor.textHighlightText);
			btnRemove.setBackground(new Color(255, 0, 102));
			btnRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String word3 = tfReNum.getText();
					if (word3.isEmpty()) {
						JOptionPane.showMessageDialog(HotelManager.this, "예약번호를 입력하세요.", "입력 에러",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					int num = Integer.parseInt(tfReNum.getText());
					
					int result = JOptionPane.showConfirmDialog(HotelManager.this, 
							num + "번 예약을 정말 취소하겠습니까?", "예약 취소", 
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE );
				
					switch (result) {
					case JOptionPane.NO_OPTION:
					case JOptionPane.CANCEL_OPTION:
						return ;
					}
					
					hotelDAO.removeByNum(num);
					
					JOptionPane.showMessageDialog(HotelManager.this, num + " 번 예약을 취소 하였습니다.", " 예약 취소 성공", JOptionPane.INFORMATION_MESSAGE);

					
					btnTotal.doClick();
				}
			});
			btnRemove.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnRemove.setBounds(215, 660, 116, 28);
		}
		return btnRemove;
	}
	private JComboBox getCbSearch() {
		if (cbSearch == null) {
			cbSearch = new JComboBox();
			cbSearch.setBackground(UIManager.getColor("CheckBox.background"));
			cbSearch.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			cbSearch.setModel(new DefaultComboBoxModel(new String[] {"검색...", "예약번호", "이름", "생년월일", "전화번호"}));
			cbSearch.setBounds(80, 710, 86, 28);
		}
		return cbSearch;
	}
	private JTextField getTfSearch() {
		if (tfSearch == null) {
			tfSearch = new JTextField();
			tfSearch.setBorder(null);
			tfSearch.setBackground(UIManager.getColor("CheckBox.background"));
			tfSearch.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			tfSearch.setColumns(10);
			tfSearch.setBounds(175, 710, 79, 28);
		}
		return tfSearch;
	}
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton("조회");
			btnSearch.setForeground(SystemColor.textHighlightText);
			btnSearch.setBackground(Color.DARK_GRAY);
			btnSearch.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedIndex = cbSearch.getSelectedIndex();
					if (selectedIndex == 0) {
						JOptionPane.showMessageDialog(HotelManager.this, "선택항목을 선택하세요.", "검색 에러",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					String word = tfSearch.getText();
					if (word.isEmpty()) {
						JOptionPane.showMessageDialog(HotelManager.this, "검색어를 입력하세요.", "검색 에러",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					String field = ""; 
					switch (selectedIndex) {
					case 1:
						field = "num";
						break;
					case 2:
						field = "name";
						break;
					case 3:
						field = "birth";
						break;
					case 4:
						field = "phone";
						break;
					}	
					
					selected(" where "+ field + " like '%" + word + "%' ");
					
				}
			});
			btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnSearch.setBounds(263, 710, 68, 28);
		}
		return btnSearch;
	}
	
	private JTable getTable() {

		if (table == null) {
			
			model = new DefaultTableModel();
			table = new JTable(model);
			table.setForeground(SystemColor.text);
			table.setBackground(new Color(0, 51, 102));
			table.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			colrend = new DefaultTableCellRenderer();
		}
		
		return table;
		
	}
	
	private void selected() {
		
		hotelV  = hotelDAO.getGuestsV();
		model.fireTableDataChanged();
		model.setDataVector(hotelV, columnNames);
		
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			
			colrend.setHorizontalAlignment(JLabel.CENTER);
			column.setCellRenderer(colrend); // 칼럼 전체 가운데 정렬
			
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			//table.getColumnModel().getColumn(0).setCellRenderer(colrend); // 각 칼럼 하나 가운대 정렬 
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(2).setPreferredWidth(70);
			table.getColumnModel().getColumn(3).setPreferredWidth(140);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(6).setPreferredWidth(100);
			table.getColumnModel().getColumn(7).setPreferredWidth(60);
			table.getColumnModel().getColumn(8).setPreferredWidth(60);
			table.getColumnModel().getColumn(9).setPreferredWidth(60);
			table.getColumnModel().getColumn(10).setPreferredWidth(60);
			table.getColumnModel().getColumn(11).setPreferredWidth(200);
			
		}
		
	} // end of selected
	
	private void selected(String whereOption) {
		
		hotelV  = hotelDAO.getGuestsVOption(whereOption);
		model.fireTableDataChanged();
		model.setDataVector(hotelV, columnNames);
		
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			
			colrend.setHorizontalAlignment(JLabel.CENTER);
			column.setCellRenderer(colrend); // 칼럼 전체 가운데 정렬
			
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			//table.getColumnModel().getColumn(0).setCellRenderer(colrend); // 각 칼럼 하나 가운대 정렬 
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(2).setPreferredWidth(70);
			table.getColumnModel().getColumn(3).setPreferredWidth(140);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(6).setPreferredWidth(100);
			table.getColumnModel().getColumn(7).setPreferredWidth(60);
			table.getColumnModel().getColumn(8).setPreferredWidth(60);
			table.getColumnModel().getColumn(9).setPreferredWidth(60);
			table.getColumnModel().getColumn(10).setPreferredWidth(60);
			table.getColumnModel().getColumn(11).setPreferredWidth(200); // getColumn(11번째컬럼).setPreferredWidth(가로200사이즈)
			
		}
		
	} // end of selected
	
	private Vector<Vector<Object>> getVectorFromList(List<HotelVO> list) {
		// List<MemberVO> -> Vector<Vector<Object>> 로 변환하기
		Vector<Vector<Object>> vector = new Vector<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		
		for (HotelVO guest : list) {
			Vector<Object> rowVector = new Vector<>();
			rowVector.add(guest.getNum());
			rowVector.add(guest.getName());
			rowVector.add(guest.getBirth());
			rowVector.add(guest.getPhone());
			rowVector.add(guest.getRoom());
			rowVector.add(guest.getChIn());
			rowVector.add(guest.getChOut());
			rowVector.add(guest.getAdult());
			rowVector.add(guest.getChild());
			rowVector.add(guest.getBreakfast());
			rowVector.add(guest.getCar());
			rowVector.add(sdf.format(guest.getRegDate()));
			
			vector.add(rowVector);
		} // for
		
		return vector;
	} // getVectorFromList
	
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton("예약 등록");
			btnAdd.setForeground(new Color(255, 255, 255));
			btnAdd.setBackground(new Color(0, 204, 102));
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					// 방법1
					String word = tfName.getText();
					if (word.isEmpty()) {
						JOptionPane.showMessageDialog(HotelManager.this, "이름을 입력하세요.", "입력 에러",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// 방법2
					String word1 = tfBirth.getText();
					int birthLength = word1.length();
					if (birthLength == 0) { // id.equals("")
						JOptionPane.showMessageDialog(frame, "생년월일을 입력하세요.", "입력 에러", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (!(birthLength == 6)) {
						JOptionPane.showMessageDialog(frame, "주민번호 앞 6자리를 입력하세요.", "입력 에러", JOptionPane.ERROR_MESSAGE);
						return;
					}
//					if (word1.isEmpty()) {
//						JOptionPane.showMessageDialog(HotelManager.this, "생년월일을 입력하세요", "입력 에러",
//								JOptionPane.ERROR_MESSAGE);
//						return;
//					}
					
					
					String 		name 		= tfName.getText().trim();
					String 		birth 		= tfBirth.getText().trim();
					String 		phone 		= cbPhone.getSelectedItem().toString() + "-"+tfPhone1.getText().trim()+"-"+tfPhone2.getText().trim();
					String 		room 		= cbRoom.getSelectedItem().toString();
					String		chIn		= datePickerImpl.getJFormattedTextField().getText().trim().replaceAll("-", "");
					String		chOut		= datePickerImpl_1.getJFormattedTextField().getText().trim().replaceAll("-", "");
					String		adult		= spAdult.getValue().toString();
					String		child		= spChild.getValue().toString();
					String		breakfast	= spBreakfast.getValue().toString();
					String 		car 	= rdbtnCar.isSelected() ? "O" : "X" ;
					Timestamp 	regDate 	= new Timestamp(System.currentTimeMillis());
					
					//System.out.println(chIn);
					
					HotelVO hotelVO = new HotelVO(ABORT, name, birth, phone, room, chIn, chOut, adult, child, breakfast, car, regDate);
							
					hotelDAO.insertHotel(hotelVO);
					
					JOptionPane.showMessageDialog(frame,  "예약이 성공적으로 완료되었습니다.", "예약 완료", JOptionPane.INFORMATION_MESSAGE);
					
					buttonGroup.clearSelection();
					
					
					btnTotal.doClick();
					
				}
			});
			btnAdd.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnAdd.setBounds(80, 510, 251, 28);
		}
		return btnAdd;
	}
	private JComboBox getCbRoom() {
		if (cbRoom == null) {
			cbRoom = new JComboBox();
			cbRoom.setModel(new DefaultComboBoxModel(new String[] {"디럭스", "프리미엄", "로얄 스위트"}));
			cbRoom.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			cbRoom.setBounds(222, 262, 130, 28);
		}
		return cbRoom;
	}
	private JLabel getLblCount() {
		if (lblCount == null) {
			lblCount = new JLabel("숙박 인원");
			lblCount.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblCount.setBounds(19, 362, 97, 22);
		}
		return lblCount;
	}
	private JLabel getLblAdult() {
		if (lblAdult == null) {
			lblAdult = new JLabel("성인");
			lblAdult.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			lblAdult.setBounds(178, 362, 24, 18);
		}
		return lblAdult;
	}
	private JSpinner getSpAdult() {
		if (spAdult == null) {
			spAdult = new JSpinner();
			spAdult.setForeground(UIManager.getColor("CheckBox.background"));
			spAdult.setBackground(UIManager.getColor("CheckBox.background"));
			spAdult.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spAdult.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			spAdult.setBounds(206, 356, 38, 32);
		}
		return spAdult;
	}
	private JLabel getLblChild() {
		if (lblChild == null) {
			lblChild = new JLabel("어린이");
			lblChild.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			lblChild.setBounds(275, 362, 38, 18);
		}
		return lblChild;
	}
	private JSpinner getSpBreakfast() {
		if (spBreakfast == null) {
			spBreakfast = new JSpinner();
			spBreakfast.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spBreakfast.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			spBreakfast.setBounds(205, 406, 38, 32);
		}
		return spBreakfast;
	}
	private JSpinner getSpChild() {
		if (spChild == null) {
			spChild = new JSpinner();
			spChild.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spChild.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			spChild.setBounds(315, 356, 38, 32);
		}
		return spChild;
	}
	private JLabel getLblBreakfastM() {
		if (lblBreakfastM == null) {
			lblBreakfastM = new JLabel("명");
			lblBreakfastM.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			lblBreakfastM.setBounds(243, 412, 13, 18);
		}
		return lblBreakfastM;
	}
	private JLabel getLblAdultM() {
		if (lblAdultM == null) {
			lblAdultM = new JLabel("명");
			lblAdultM.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			lblAdultM.setBounds(243, 362, 24, 18);
		}
		return lblAdultM;
	}
	private JLabel getLblChildM() {
		if (lblChildM == null) {
			lblChildM = new JLabel("명");
			lblChildM.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			lblChildM.setBounds(353, 362, 13, 18);
		}
		return lblChildM;
	}
	private JLabel getLblReNum() {
		if (lblReNum == null) {
			lblReNum = new JLabel("예약 번호");
			lblReNum.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			lblReNum.setBounds(80, 610, 97, 22);
		}
		return lblReNum;
	}
	private JTextField getTfReNum() {
		if (tfReNum == null) {
			tfReNum = new JTextField();
			tfReNum.setBorder(null);
			tfReNum.setBackground(UIManager.getColor("CheckBox.background"));
			tfReNum.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			tfReNum.setColumns(10);
			tfReNum.setBounds(215, 610, 116, 28);
		}
		return tfReNum;
	}
	private JTextField getTfPhone2() {
		if (tfPhone2 == null) {
			tfPhone2 = new JTextField();
			tfPhone2.setBorder(null);
			tfPhone2.setBackground(UIManager.getColor("CheckBox.background"));
			tfPhone2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			tfPhone2.setColumns(4);
			tfPhone2.setBounds(307, 163, 45, 28);
		}
		return tfPhone2;
	}
	private JDatePickerImpl getDatePickerImpl() {
		if (datePickerImpl == null) {
			UtilDateModel model = new UtilDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			datePickerImpl = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			datePickerImpl.getJFormattedTextField().setBackground(UIManager.getColor("CheckBox.background"));
			datePickerImpl.setBounds(85, 312, 103, 28);
		}
		return datePickerImpl;
	}
	private JDatePickerImpl getDatePickerImpl_1() {
		if (datePickerImpl_1 == null) {
			
			UtilDateModel model = new UtilDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			
			datePickerImpl_1 = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			datePickerImpl_1.getJFormattedTextField().setBackground(UIManager.getColor("CheckBox.background"));
			SpringLayout springLayout = (SpringLayout) datePickerImpl_1.getLayout();
			springLayout.putConstraint(SpringLayout.WEST, datePickerImpl_1.getJFormattedTextField(), 0, SpringLayout.WEST, datePickerImpl_1);
			datePickerImpl_1.setBounds(286, 312, 103, 28);
		}
		return datePickerImpl_1;
	}
}
