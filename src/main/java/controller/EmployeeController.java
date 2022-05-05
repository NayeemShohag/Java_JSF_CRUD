package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Employees;
import service.DbService;

@ManagedBean(name = "employeecntrl")
@SessionScoped
public class EmployeeController {

	Logger log = Logger.getLogger("EmployeeController");

	private Employees employees = new Employees();
	private String seacrhId;
	private boolean search = false;
	List<Employees> list;
	DbService db = new DbService();

	public Employees getEmployees() {
		return employees;
	}

	public void setEmployees(Employees employees) {
		this.employees = employees;
	}

	public String getSeacrhId() {
		return seacrhId;
	}

	public void setSeacrhId(String seacrhId) {
		this.seacrhId = seacrhId;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public List<Employees> getAll() {

		list = new ArrayList<Employees>();

		if (search) {

			list = db.searchById(seacrhId);
		} else {
			list = db.searchAll();
		}

		return list;
	}
	
	public void deleteAll()
	{
		db.deleteAll();
	}

	public void searchEmployee() {

		if (seacrhId != "" && seacrhId != null) {
			this.search = true;
		} else {
			this.search = false;
		}
	}

}