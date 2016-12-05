package com.scc.webtemplate.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.scc.webtemplate.domain.Product;
import com.scc.webtemplate.repository.ProductRepository;

@Controller
@RequestMapping("/pages")
public class ProductsController {

	@Autowired
	private ProductRepository productRepository;	    	
	
	@RequestMapping("products")
	public ModelMap findAllProducts(Pageable pageable, @RequestParam(required = false) String name) {
		ModelMap modelMap = new ModelMap();
		if(StringUtils.hasText(name)) {
			modelMap.addAttribute("products", productRepository.findByQuery(pageable, name));
		} else {
			modelMap.addAttribute("products", productRepository.findAll(pageable));
		}
		return modelMap;
	}

	@RequestMapping(value = "product", method = RequestMethod.GET)
	public ModelMap showProduct(@RequestParam(required = false) String id) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("product", new Product());
		if (StringUtils.hasText(id)) {
			Product product = productRepository.findOne(id);
			if (product instanceof Product) {
				modelMap.addAttribute("product", product);
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute Product product) {
		productRepository.save(product);
		return "redirect:products";
	}
	
	@RequestMapping(value = "product", method = RequestMethod.DELETE)
	public String deleteProduct(@ModelAttribute Product product) {
		productRepository.delete(product);
		return "redirect:products";
	}	
	
    @RequestMapping("pdf")
    public void generatePdf(HttpServletResponse response) throws IOException{
    	response.setContentType("application/pdf");
    	List<Product> products = productRepository.findAll();
    	String title = "Products";
    	String header[] = { "id", "name", "qty", "price" };
    	float[] columnWidths = new float[] { 5f, 30f, 10f, 20f };
    	String[][] data = productsToString(products, header);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.setPageSize(PageSize.A4);
            document.setMargins(10, 10, 10, 10);
            // Font litle = new Font(Font.COURIER, 7, Font.NORMAL);
            Font norm = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL);
            Font normBold = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
            Font TitleFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
            
            document.add(Chunk.NEWLINE);
            Paragraph judul = new Paragraph(title, TitleFont);
            judul.setAlignment(Element.ALIGN_CENTER);

            document.add(judul);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(header.length);
            table.setWidthPercentage(100f);

            for (String head : header) {
                table.addCell(new PdfPCell(new Phrase(head, normBold)));
            }
            for (String[] obj : data) {
                for (int i = 0; i < header.length; i++) {
                    table.addCell(new PdfPCell(new Phrase(obj[i], norm)));
                }
            }
            table.setWidths(columnWidths);
            document.add(table);
            document.close();
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        } 
    }    
    
    private static String[][] productsToString(List<Product> products, String[] header) {
        String[][] data = new String[products.size()][header.length];
        int i = 0;
        for (Product product : products) {
            data[i][0] = product.getId();
            data[i][1] = product.getName();
            data[i][2] = Integer.toString(product.getQty());
            data[i][3] = Integer.toString(product.getPrice());
            i++;
        }
        return data;
    }    
    
	@RequestMapping("excel")
	public void downloadExcel() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(" Orders Info ");
		XSSFRow row;
		Map<String, Object[]> orderInfo = new TreeMap<String, Object[]>();
		orderInfo.put("1", new Object[] { "PRODUCT ID", "NAME", "QTY", "PRICE" });
		int i = 2;
		for(Product product: productRepository.findAll()) {
			String strI = String.valueOf(i);
			orderInfo.put(strI, new Object[] { product.getId(), product.getName(), product.getQty(), product.getPrice() });
			i += 1;
		}
		Set<String> keyid = orderInfo.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = orderInfo.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("C:/Downloads/Products.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("Products.xlsx written successfully");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}    
    
}







































