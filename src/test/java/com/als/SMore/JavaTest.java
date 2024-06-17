package com.als.SMore;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

public class JavaTest {
    @Test
    void dateTest(){
        LocalDate date = LocalDate.now();
        System.out.println("======================================================");
        System.out.println(date.withDayOfMonth(1));

        System.out.println(YearMonth.of(2024,6).atDay(1));
        System.out.println("======================================================");

    }
}
