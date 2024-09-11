package com.flipkart.business;

import com.flipkart.bean.FlipFitSlots;
import com.flipkart.business.interfaces.IFlipFitSlots;
import com.flipkart.dao.*;
import com.flipkart.dao.interfaces.IFlipFitGymOwnerDAO;
import com.flipkart.dao.interfaces.IFlipFitSlotDAO;

public class FlipFitSlotsBusiness implements IFlipFitSlots {

//    private final IFlipFitSlotDAO flipFitSlotDAO;
//
//    public FlipFitSlotsBusiness(IFlipFitSlotDAO flipFitSlotDAO) {
//        this.flipFitSlotDAO = flipFitSlotDAO;
//    }


    public boolean updateAvailability(FlipFitSlots flipFitSlots) {
        System.out.println("Updating Slot Availability");
        FlipFitSlotDAOImpl flipFitSlotDAO = new FlipFitSlotDAOImpl();
        flipFitSlotDAO.changeSlot(flipFitSlots);
        return true;
    }

    public void getSlotDetails() {
        System.out.println("Getting Slot Details");
        FlipFitSlotDAOImpl flipFitSlotDAO = new FlipFitSlotDAOImpl();
    }

//    @Override
//    public FlipFitSlots addSlot(FlipFitSlots slot) {
//        return flipFitSlotDAO.addSlot(slot);
//    }
}
