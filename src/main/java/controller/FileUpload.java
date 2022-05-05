package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import service.DbService;

@ManagedBean
@SessionScoped
public class FileUpload {

	Logger log = Logger.getLogger("FileUpload");
	private Part uploadedFile;

	private DbService db = new DbService();

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void savefile() {

		System.out.println("=========================================>" + uploadedFile.getSubmittedFileName());

		try {
			InputStream in = uploadedFile.getInputStream();
			// System.out.println(new String(in.readAllBytes()));

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			String str = new String(in.readAllBytes());
			StringReader sr = new StringReader(str);
			InputSource is = new InputSource(sr);
			Document document = builder.parse(is);
			System.out.println("Root element: " + document.getDocumentElement().getNodeName());
			db.fileToDB(document);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
