package coffeeshop.web.admin;

import coffeeshop.ejb.UserManager;
import coffeeshop.entity.Staff;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class AdminRemoveStaffController {

    @EJB
    private UserManager userManager;
    
    private Staff selectedStaff;

    public Staff getSelectedStaff() {
        return selectedStaff;
    }

    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
    }
    
    public List<Staff> getStaffs() {
        return userManager.getStaffs();
    }
    
    public void removeStaff() {
        userManager.removeStaff(selectedStaff);
    }
}
