import com.vacationcalc.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VacationCalcTest {
    
    @Test
    public void testVacationPayment() {
        double salary = 60000;
        int days = 7;

        VacationCalcController controller = new VacationCalcController();
        double result = controller.calculateVacationAmount(salary, days, null);
        assertEquals(14334.47, result, 0.01);
    }
    
    @Test
    public void testVacationPaymentWithStartDate() {
        double salary = 60000;
        int days = 7;
        String date = "2024-06-10"; // 12.06 - праздник, 15.06, 16.06 - выходные
        
        VacationCalcController controller = new VacationCalcController();
        double result = controller.calculateVacationAmount(salary, days, date);
        assertEquals(8191.13, result, 0.01);
    }
    
    @Test
    public void testIncorrectDateVacation() {
        double salary = 60000;
        int days = 7;
        String date = "2024-6-1"; // неверный формат ввода
        
        VacationCalcController controller = new VacationCalcController();
        double result = controller.calculateVacationAmount(salary, days, date);
        assertEquals(0, result, 0);
    }
    
}
