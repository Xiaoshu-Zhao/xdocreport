package fr.opensagres.xdocreport.samples.odtandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Project;
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Role;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class ODTTableWithoutFieldsMetadataWithVelocity2XHTML {

	public static void main(String[] args) {
		try {
			// 1) Load Docx file by filling Freemarker template engine and cache
			// it to the registry
			InputStream in = ODTTableWithoutFieldsMetadataWithVelocity2XHTML.class
					.getResourceAsStream("ODTTableWithoutFieldsMetadataWithVelocity.odt");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create fields metadata to manage lazy loop (#forech velocity)
			// for table row.
			// FieldsMetadata metadata = new FieldsMetadata();
			// metadata.addFieldAsList("developers.name");
			// metadata.addFieldAsList("developers.lastName");
			// metadata.addFieldAsList("developers.mail");
			// report.setFieldsMetadata(metadata);

			// 3) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);
			// Register developers list
			List<Developer> developers = new ArrayList<Developer>();

			developers.add(new Developer("ZERR", "Angelo",
					"angelo.zerr@gmail.com").addRole(new Role("Architecte"))
					.addRole(new Role("Developer")));

			developers.add(new Developer("Leclercq", "Pascal",
					"pascal.leclercq@gmail.com")
					.addRole(new Role("Architecte")).addRole(
							new Role("Developer")));

			developers.add(new Developer("Bousta", "Amine", "")
					.addRole(new Role("Developer")));

			context.put("developers", developers);

			// 4) Generate report by merging Java model with the ODT
			OutputStream out = new FileOutputStream(new File(
					"ODTTableWithoutFieldsMetadataWithVelocity_Out.html"));
			// report.process(context, out);
			Options options = Options.getTo(ConverterTypeTo.XHTML).via(
					ConverterTypeVia.ODFDOM);
			report.convert(context, options, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
