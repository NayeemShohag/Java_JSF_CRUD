package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import connection.DBConnection;
import model.Employees;

public class DbService {

	private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO EMPLOYEES (ID,FIRSTNAME,LASTNAME,TITLE,DIVISION,BUILDING,ROOM) VALUES (?,?,?,?,?,?,?)";

	private static final String SELECT_EMPLOYEES_BY_ID = "SELECT * FROM EMPLOYEES WHERE id=?";
	private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM EMPLOYEES";
	private static final String DELETE_EMPLOYEES_SQL = "DELETE from EMPLOYEES WHERE id = ?";
	private static final String DELETE_ALL_EMPLOYEES_SQL = "DELETE from EMPLOYEES";
	private static final String UPDATE_EMPLOYEES_SQL = "UPDATE EMPLOYEES SET firstname=?,lastname=?,title=?,division=?,building=?,room=? WHERE id = ?";
	
	
	public void fileToDB(Document document) {
		NodeList nodeList = document.getElementsByTagName("employee");
		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			org.w3c.dom.Node node = nodeList.item(temp);

			System.out.println("\nCurrent element: " + node.getNodeName());
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element element = (Element) node;
				Connection conn = DBConnection.getInstance();
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(INSERT_EMPLOYEES_SQL);
					preparedStatement.setString(1,nodeList.item(temp).getAttributes().getNamedItem("id").getNodeValue());
					preparedStatement.setString(2, element.getElementsByTagName("firstname").item(0).getTextContent());
					preparedStatement.setString(3, element.getElementsByTagName("lastname").item(0).getTextContent());
					preparedStatement.setString(4, element.getElementsByTagName("title").item(0).getTextContent());
					preparedStatement.setString(5, element.getElementsByTagName("division").item(0).getTextContent());
					preparedStatement.setString(6, element.getElementsByTagName("building").item(0).getTextContent());
					preparedStatement.setString(7, element.getElementsByTagName("room").item(0).getTextContent());
					preparedStatement.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		}
	}

	public List<Employees> searchAll() {
		Connection conn = DBConnection.getInstance();
		PreparedStatement preparedStatement;
		List list = new ArrayList<Employees>();
		try {
			preparedStatement = preparedStatement = conn.prepareStatement(SELECT_ALL_EMPLOYEES);
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String id = rs.getString("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String title = rs.getString("title");
				String division = rs.getString("division");
				String building = rs.getString("building");
				String room = rs.getString("room");
				list.add(new Employees(id, firstName, lastName, title, division, building, room));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List<Employees> searchById(String seacrhId) {

		Connection conn = DBConnection.getInstance();
		PreparedStatement preparedStatement;
		List list = new ArrayList<Employees>();
		try {
			preparedStatement = preparedStatement = conn.prepareStatement(SELECT_EMPLOYEES_BY_ID);
			preparedStatement.setString(1, seacrhId);
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String id = rs.getString("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String title = rs.getString("title");
				String division = rs.getString("division");
				String building = rs.getString("building");
				String room = rs.getString("room");
				list.add(new Employees(id, firstName, lastName, title, division, building, room));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Employees seacrhForUpdate(String searchId) {
		Connection conn = DBConnection.getInstance();

		PreparedStatement preparedStatement = null;
		Employees employees = null;

		try {
			preparedStatement = conn.prepareStatement(SELECT_EMPLOYEES_BY_ID);
			preparedStatement.setString(1, searchId);

			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			String id = null;
			String firstName = null;
			String lastName = null;
			String title = null;
			String division = null;
			String building = null;
			String room = null;
			while (rs.next()) {
				id = rs.getString("id");
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				title = rs.getString("title");
				division = rs.getString("division");
				building = rs.getString("building");
				room = rs.getString("room");
			}
			employees = new Employees(id, firstName, lastName, title, division, building, room);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employees;
	}

	public void updateEmployee(Employees employees) {
		Connection conn = DBConnection.getInstance();

		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn.prepareStatement(UPDATE_EMPLOYEES_SQL);
			preparedStatement.setString(1, employees.getFirstName());
			preparedStatement.setString(2, employees.getLastName());
			preparedStatement.setString(3, employees.getTitle());
			preparedStatement.setString(4, employees.getDivision());
			preparedStatement.setString(5, employees.getBuilding());
			preparedStatement.setString(6, employees.getRoom());
			preparedStatement.setString(7, employees.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteEmployee(String searchId) {

		Connection conn = DBConnection.getInstance();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn.prepareStatement(DELETE_EMPLOYEES_SQL);
			preparedStatement.setString(1, searchId);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void deleteAll()
	{

		Connection conn = DBConnection.getInstance();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn.prepareStatement(DELETE_ALL_EMPLOYEES_SQL);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
