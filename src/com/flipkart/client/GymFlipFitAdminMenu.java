package com.flipkart.client;

import com.flipkart.bean.*;
import com.flipkart.business.FlipFitAdminBusiness;
import com.flipkart.dao.FlipFitAdminDAOImpl;
import com.flipkart.exceptions.ExceptionHandler;
import com.flipkart.exceptions.InvalidChoiceException;
import java.util.List;
import java.util.Scanner;
import com.flipkart.constant.ColorConstants;

public class GymFlipFitAdminMenu {

    /**
     * getAdminView
     * @throws InvalidChoiceException
     */
    public static void getAdminView() throws InvalidChoiceException {
        try {
            Scanner sc = new Scanner(System.in);
            FlipFitAdminDAOImpl adminUser = new FlipFitAdminDAOImpl();
            FlipFitAdminBusiness adminService = new FlipFitAdminBusiness(adminUser);

            System.out.println(ColorConstants.CYAN + "===========================" + ColorConstants.RESET);
            System.out.println(ColorConstants.CYAN + "        Admin Menu          " + ColorConstants.RESET);
            System.out.println(ColorConstants.CYAN + "===========================" + ColorConstants.RESET);

            System.out.println(ColorConstants.YELLOW + """
                        Choose an option:
                         1. View Pending Requests
                         2. View Approved Owners
                         3. View all FlipFit Customers
                         4. View all Centres Using OwnerId
                         5. Logout
                        """ + ColorConstants.RESET);

            int choice = sc.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println(ColorConstants.BLUE + "=========== View Pending Requests =========== " + ColorConstants.RESET);

                    List<FlipFitGymOwner> flipFitGymOwnerList = adminService.getPendingOwnerList();
                    for (FlipFitGymOwner flipFitGymOwner : flipFitGymOwnerList) {
                        System.out.println(ColorConstants.GREEN + "Owner ID: " + flipFitGymOwner.getUserId() + " Aadhar: " + flipFitGymOwner.getAadharNumber() + ColorConstants.RESET);
                    }

                    System.out.print(ColorConstants.PURPLE + "Type the ownerId of the owner you wish to approve:> " + ColorConstants.RESET);
                    int ownerId = sc.nextInt();

                    adminUser.validateOwner(ownerId);
                    System.out.println(ColorConstants.GREEN + "GymOwner with ID " + ownerId + " approved" + ColorConstants.RESET);

                    break;
                }

                case 2: {
                    System.out.println(ColorConstants.BLUE + "=========== View Approved Owners =========== " + ColorConstants.RESET);

                    List<FlipFitGymOwner> flipFitGymOwnerList = adminService.getApprovedOwnerList();
                    for (FlipFitGymOwner flipFitGymOwner : flipFitGymOwnerList) {
                        System.out.println(ColorConstants.GREEN + "Owner ID: " + flipFitGymOwner.getUserId() + " Aadhar: " + flipFitGymOwner.getAadharNumber() + ColorConstants.RESET);
                    }

                    break;
                }

                case 3: {
                    System.out.println(ColorConstants.BLUE + "=========== View all FlipFit Customers =========== " + ColorConstants.RESET);

                    List<FlipFitGymCustomer> customersList = adminService.getUserList();
                    for (FlipFitGymCustomer customers : customersList) {
                        System.out.println(ColorConstants.GREEN + "CustomerID: " + customers.getUserId() + " CustomerName: " + customers.getUserName() + ColorConstants.RESET);
                    }

                    break;
                }

                case 4: {
                    System.out.println(ColorConstants.BLUE + "=========== View Centres Using OwnerId =========== " + ColorConstants.RESET);

                    System.out.print(ColorConstants.PURPLE + "Type the ownerId of the owner for which you wish to view Centres:> " + ColorConstants.RESET);
                    Scanner in = new Scanner(System.in);
                    int ownerId = in.nextInt();

                    List<FlipFitGymCentre> flipFitGymCentres = adminService.getGymCentreUsingOwnerId(ownerId);
                    if (flipFitGymCentres.isEmpty()) {
                        System.out.println(ColorConstants.RED + "No centres found for owner ID " + ownerId + ColorConstants.RESET);
                    } else {
                        System.out.println(ColorConstants.GREEN + "Printing All Centres of Owner " + ColorConstants.RESET);
                        for (FlipFitGymCentre gymCentre : flipFitGymCentres) {
                            System.out.println(ColorConstants.GREEN + "CentreID: " + gymCentre.getCentreID() + " City: " + gymCentre.getCity() + " Capacity: " + gymCentre.getCapacity() + ColorConstants.RESET);
                        }
                    }

                    break;
                }

                case 5: {
                    System.out.println("Successfully logged out");
                    return;
                }

                default: {
                    throw new InvalidChoiceException(ColorConstants.RED + "Invalid choice entered: " + choice + ColorConstants.RESET);
                }
            }
        } catch (InvalidChoiceException e) {
            ExceptionHandler.InvalidChoiceMainMenu(e);
        }
    }
}
