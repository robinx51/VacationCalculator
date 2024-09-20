package com.vacationcalc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// пример: localhost:8080/calculate?salary=60000&days=7&startDate=2024-09-20
@RestController
public class VacationCalcController {
    private final Set<LocalDate> holidays;
    
    public VacationCalcController() {
        holidays = new HashSet<>();
        
        try {
            for (int i = 1; i < 9; i++) { // январь
                holidays.add(LocalDate.of(2024, 1, i) );
            }
            holidays.addAll(Set.of(
                LocalDate.of(2024, 2, 23), // февраль
                LocalDate.of(2024, 2, 24),
                LocalDate.of(2024, 2, 25),
                
                LocalDate.of(2024, 3, 8),  // март
                
                LocalDate.of(2024, 4, 29), // апрель
                LocalDate.of(2024, 4, 30),
                
                LocalDate.of(2024, 5, 1),  // май
                LocalDate.of(2024, 5, 4),
                LocalDate.of(2024, 5, 5),
                LocalDate.of(2024, 5, 9),
                LocalDate.of(2024, 5, 10),
                
                LocalDate.of(2024, 6, 12), // июнь
                
                LocalDate.of(2024, 11, 3), // ноябрь
                LocalDate.of(2024, 11, 4),

                LocalDate.of(2024, 12, 30),// декабрь
                LocalDate.of(2024, 12, 31) ));
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }
    
    // startDate format: yyyy-mm-dd; 
    // ответ - строка, количество отпускных фиксированное и меняется только дата выхода из отпуска при пересечении с выходными
    @GetMapping("/calculate")
    public String calculateVacation( 
            @RequestParam(name = "salary")                      double salary,
            @RequestParam(name = "days")                        int days,
            @RequestParam(name = "startDate", required = false) String startDate) {
        double dailySalary = salary / 29.3; // 29.3 - среднее кол-во дней в месяце (ст. 139 ТК РФ)
        double vacationAmount = Math.round(dailySalary * days * 100.0) / 100.0;
        if (startDate != null) {
            try {
                LocalDate start = LocalDate.parse(startDate);
                String response = "<h2>Дата начала отпуска: " + startDate 
                                + "<br>Дата выхода из отпуска: " + calculateDateOfRelease(start, days) 
                                + "<br>Сумма отпускных: " + vacationAmount + "<h2>";
                return response;
            } catch (java.time.format.DateTimeParseException ex) {
                System.out.println(ex.getMessage());
                return "<h2 style=\"color:red\">Неверный формат даты<br>Пример: 2024-01-01 <h2>";
            }
        } else {
            return "Сумма отпускных: " + vacationAmount;
        }
    }
    
    private LocalDate calculateDateOfRelease(LocalDate startDate, int days) {
        LocalDate releaseDate = startDate;
        
        for (int i = 0; i < days; i++) {
            if (isHoliday(releaseDate)) {
                days++;
            }
            releaseDate = releaseDate.plusDays(1);
        }
        return releaseDate;
    }
    
    // Учитывая, что рабочая неделя пятидневная
    private boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6 || holidays.contains(date);
    }
    
//    // ответ - число типа double, количество отпускных уменьшается при пересечнии с выходными
//    @GetMapping("/calculate")
//    public double calculateVacationAmount( 
//            @RequestParam(name = "salary")                      double salary,
//            @RequestParam(name = "days")                        int days,
//            @RequestParam(name = "startDate", required = false) String startDate) {
//        double dailySalary = salary / 29.3; // 29.3 - среднее кол-во дней в месяце (ст. 139 ТК РФ)
//        if (startDate != null) {
//            try {
//                LocalDate start = LocalDate.parse(startDate);
//                double vacationAmount = Math.round(dailySalary * days * 100.0) / 100.0;
//                
//                return vacationAmount * calculateWorkingDays(start, days);
//            } catch (java.time.format.DateTimeParseException ex) {
//                System.out.println(ex.getMessage());
//                return 0;
//            }
//        } else {
//            return Math.round(dailySalary * days * 100.0) / 100.0;
//        }
//    }
//    private long calculateWorkingDays(LocalDate startDate, int days) {
//        int workingDays = 0;
//        LocalDate currentDate = startDate;
//        
//        for (int i = 0; i < days; i++) {
//            if (isWorkingDay(currentDate)) {
//                workingDays++;
//            }
//            currentDate = currentDate.plusDays(1);
//        }
//        return workingDays;
//    }
//    private boolean isWorkingDay(LocalDate date) {
//        return !(date.getDayOfWeek().getValue() >= 6 || holidays.contains(date));
//    }
}