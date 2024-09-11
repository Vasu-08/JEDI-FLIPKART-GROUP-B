package com.flipkart.client;

import com.flipkart.bean.FlipFitPayments;
import com.flipkart.dao.FlipFitPaymentsDAOImpl;
import com.flipkart.dao.interfaces.IFlipFitPaymentsDAO;

import java.util.Scanner;

public class GymFlipFitPaymentMenu {
    private static final IFlipFitPaymentsDAO flipFitPaymentsDAO = new FlipFitPaymentsDAOImpl();

    public static boolean getFlipFitPaymentMenu(int userId){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("\nWe provide the following modes of payment:");
            System.out.println("        1. Credit Card");
            System.out.println("        2. Debit Card");
            System.out.println("        3. UPI");
            System.out.println("        4. NEFT/RTGS");
            System.out.print("Please enter your choice: ");
            int paymentChoice = sc.nextInt();

            System.out.print("Enter Transaction ID:   ");
            String transactionId = sc.next();

            if(transactionId.startsWith("FFGSB")){
                FlipFitPayments flipFitPayment = new FlipFitPayments();
                flipFitPayment.setUserID(userId);
                flipFitPayment.setPaymentType(paymentChoice);
                flipFitPayment.setPaymentInfo(transactionId);

                flipFitPaymentsDAO.setPaymentInfo(flipFitPayment);
                return true;
            } else {
                System.out.println("Invalid TransactionID. Please try again.");
            }
        }

    }
}
