package com.flipkart.business;

import java.util.List;

import com.flipkart.business.interfaces.IFlipFitGymOwner;
import com.flipkart.dao.FlipFitGymOwnerDAOImpl;
import com.flipkart.dao.FlipFitSlotDAOImpl;
import com.flipkart.dao.FlipFitUserDAOImpl;
import com.flipkart.dao.interfaces.IFlipFitGymOwnerDAO;
import com.flipkart.exceptions.InvalidChoiceException;
import com.flipkart.model.FlipFitGymCentre;
import com.flipkart.model.FlipFitGymOwner;
import com.flipkart.model.FlipFitPayments;
import com.flipkart.model.FlipFitSlots;
import com.flipkart.model.FlipFitUser;

public class FlipFitGymOwnerBusiness implements IFlipFitGymOwner {
    private final IFlipFitGymOwnerDAO flipFitGymOwnerDAO;

    public FlipFitGymOwnerBusiness(FlipFitGymOwnerDAOImpl FFOwner) {
        this.flipFitGymOwnerDAO = FFOwner;
    }

    public FlipFitGymCentre addCentre(FlipFitGymCentre flipFitGymCentre) {
        return flipFitGymOwnerDAO.addCentre(flipFitGymCentre);
    }

    public FlipFitSlots addSlot(FlipFitSlots flipFitSlot) {
        FlipFitSlotDAOImpl flipFitSlotDAOImpl = new FlipFitSlotDAOImpl();
        flipFitSlotDAOImpl.addSlot(flipFitSlot);
        return flipFitSlot;
    }

    public List<FlipFitGymCentre> viewCentres(FlipFitGymOwner flipFitGymOwner) {
        System.out.println("Centres listed:> ");
        return flipFitGymOwnerDAO.viewCentresByOwnerID(flipFitGymOwner);
    }

    // @Override
    // public List<FlipFitUser> viewFlipFitCustomers(FlipFitGymCentre
    // flipFitGymCentre) {
    // return flipFitGymOwnerDAO.viewFlipFitCustomers(flipFitGymCentre);
    // }
    public List<FlipFitPayments> viewPayments() {
        System.out.println("Payments listed:> ");
        return null;
    }

    public FlipFitGymOwner editDetails(FlipFitGymOwner owner) throws InvalidChoiceException {
        return flipFitGymOwnerDAO.editDetails(owner);
        // try
        // {
        //
        // default: {
        // throw new InvalidChoiceException("Invalid choice entered: " + choice);
        // }
        // }
        // return true;
        // }
        // catch (InvalidChoiceException e)
        // {
        // ExceptionHandler.InvalidChoiceEditDetailsMenu(e);
        // return false;
        // }
    }

    public FlipFitGymOwner registerOwner(FlipFitGymOwner GymOwner) {

        FlipFitUser user = new FlipFitUser();
        user.setPassword(GymOwner.getPassword());
        user.setEmailID(GymOwner.getEmailID());
        user.setPhoneNumber(GymOwner.getPhoneNumber());
        user.setUserName(GymOwner.getUserName());
        user.setRoleID(2);
        GymOwner.setRole(2);

        user = flipFitGymOwnerDAO.addUser(user);
        return flipFitGymOwnerDAO.addGymOwner(GymOwner, user);
    }

    @Override
    public FlipFitUser login(FlipFitUser flipFitUser) {
        FlipFitUserDAOImpl userDAO = new FlipFitUserDAOImpl();
        flipFitUser.setRoleID(2);
        userDAO.login(flipFitUser.getEmailID(), flipFitUser.getPassword());
        return flipFitUser;
    }
}
