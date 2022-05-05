package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Employees;
import service.DbService;

@ManagedBean
@SessionScoped
public class UpdateController {

	private Employees employees;
	private DbService db = new DbService();

	public UpdateController() {
		search();
	}

	public Employees getEmployees() {
		return employees;
	}

	public void setEmployees(Employees employees) {
		this.employees = employees;
	}

	public String search() {
		String searchId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		employees=db.seacrhForUpdate(searchId);
		return "update.xhtml";
	}

	public String update() {
		db.updateEmployee(employees);
		return "home.xhtml";
	}

	public String delete() {
		String searchId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		db.deleteEmployee(searchId);
		return "home.xhtml";
	}
}
