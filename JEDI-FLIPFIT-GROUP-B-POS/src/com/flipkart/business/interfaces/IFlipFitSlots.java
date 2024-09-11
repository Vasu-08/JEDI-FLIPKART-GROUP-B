package com.flipkart.business.interfaces;

import com.flipkart.bean.FlipFitSlots;
import com.flipkart.dao.FlipFitSlotDAOImpl;

public interface IFlipFitSlots {
    public boolean updateAvailability(FlipFitSlots flipFitSlots);
    public void getSlotDetails();
}
