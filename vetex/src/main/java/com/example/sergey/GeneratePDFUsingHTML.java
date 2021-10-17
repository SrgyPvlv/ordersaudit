package com.example.sergey;

import java.io.File;
import java.io.IOException;

import com.itextpdf.html2pdf.HtmlConverter;

public class GeneratePDFUsingHTML {
	
	public void createPdf(String src, String dest) throws IOException {
    HtmlConverter.convertToPdf(new File(src), new File(dest));
}
}