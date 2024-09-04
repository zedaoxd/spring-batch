package br.com.bruno.faturacartaocredito.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomFormater {
    private final static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private final static NumberFormat instance = NumberFormat.getInstance();

    public static String formatDate(Date date) {
        return formatDate.format(date);
    }

    public static String formatNumber(BigDecimal amount) {
        return "R$ " + instance.format(amount);
    }
}
