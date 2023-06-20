package pdf_generate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PdfGenerateTest {

    @Test
    public void setQueryForEmployeetest(){
        String pdfData = "Wszystko";
        int id = 1;

        assertNotNull(PdfGenerate.setQueryForEmployee(pdfData, id));
    }

    @Test
    public void setQueryForManagerTest(){
        String pdfType = "Zadania";
        String pdfData = "Wszystko";
        int group = 1;

        assertNotNull(PdfGenerate.setQueryForManager(pdfType, pdfData, group));
    }

    @Test
    public void setQueryForAdminTest(){
        String pdfType = "Zadania";
        String pdfData = "Wszystko";

        assertNotNull(PdfGenerate.setQueryForAdmin(pdfType, pdfData));
    }
}
