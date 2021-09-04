package com.bolsadeideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Factura factura = (Factura) model.get("factura");
		
		//creacion tabla
		PdfPTable tabla1 = new PdfPTable(1);
		
		//espacio salto despues de la tabla
		tabla1.setSpacingAfter(20);
		
		//creacion de celda para modificar
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase("Datos Del Cliente"));
		//background
		cell.setBackgroundColor(new Color(184,218,255));
		cell.setPadding(8f);
		
		//agregar celda a tabla1
		tabla1.addCell(cell);
		tabla1.addCell(factura.getCliente().getNombre() + " "+ factura.getCliente().getApellido());
		tabla1.addCell(factura.getCliente().getEmail());
		
		//creacion tabla 2
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		cell = new PdfPCell(new Phrase("Datos De La Factura"));
		cell.setBackgroundColor(new Color(195,230,203));
		cell.setPadding(8f);
		
		tabla2.addCell(cell);
		tabla2.addCell("Folio: "+ factura.getId());
		tabla2.addCell("Descripción: "+ factura.getDescripcion());
		tabla2.addCell("Fecha: "+ factura.getCreateAt());
		
		document.add(tabla1);
		document.add(tabla2);
		
		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float[] {3f,1,1,1});
		
		cell = new PdfPCell(new Phrase("Producto"));
		cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
		tabla3.addCell(cell);

		cell = new PdfPCell(new Phrase("Precio"));
		cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Cantidad"));
		cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
		tabla3.addCell(cell);

		cell = new PdfPCell(new Phrase("Total"));
		cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
		tabla3.addCell(cell);
		
		//iterar entre los items para mostrarlos en la tabla
		for(ItemFactura item: factura.getItems()) {
			tabla3.addCell(item.getProducto().getNombre());
			
			cell = new PdfPCell(new Phrase (item.getProducto().getPrecio().toString()));
			cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
			tabla3.addCell(cell);
			
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			
			cell = new PdfPCell(new Phrase(item.calcularImporte().toString()));
			cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
			tabla3.addCell(cell);
		}
		
		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase(factura.getTotal().toString()));
		cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		document.add(tabla3);
	}

}
