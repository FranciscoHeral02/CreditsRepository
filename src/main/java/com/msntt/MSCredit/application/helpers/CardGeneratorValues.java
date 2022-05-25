package com.msntt.MSCredit.application.helpers;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CardGeneratorValues {


    public static String CardNumberGenerate() {
        String start = new String();
        SecureRandom value = new SecureRandom(); // Compliant for security-sensitive use cases
        byte bytes[] = new byte[20];
        value.nextBytes(bytes);
        // Generar dos valores en base a los tipos de cuenta
        int v1 = value.nextInt(10);
        int v2 = value.nextInt(10);
     //   start += Integer.toString(v1) + Integer.toString(v2) + " ";

        int count = 0;
        int n = 0;
        for (int i = 0; i < 16; i++) {
            if (count == 4) {
                start += "-";
                count = 0;
            } else
                n = value.nextInt(10);
            start += Integer.toString(n);
            count++;

        }
        return start.replaceAll(" ", "");
    }
    public static String IdentityGenerate(String BP, String Account) {
        return (new StringBuilder()).append(BP).append(Account).toString();

    }
    public static String CardCVVGenerate() {
        String start =new String();
        SecureRandom value = new SecureRandom(); // Compliant for security-sensitive use cases
        byte bytes[] = new byte[20];
        value.nextBytes(bytes);
        // Generar dos valores en base a los tipos de cuenta
        int v1 = value.nextInt(10);
        int v2 = value.nextInt(10);
     //   start += Integer.toString(v1) + Integer.toString(v2);

        int count = 0;
        int n = 0;
        for (int i = 0; i < 3; i++) {

            n = value.nextInt(10);
            start += Integer.toString(n);
            count++;
        }
        return start.replaceAll(" ", "");
    }
    public static String CardExpiringDateGenerate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 62);
        DateFormat format = new SimpleDateFormat("MM/yy");
        String Datedue =format.format(calendar.getTime()); //Mes/año
      //  System.out.println("Updated Date = " + calendar.getTime());
    //    System.out.println(Datedue);
        return Datedue;
    }

}
