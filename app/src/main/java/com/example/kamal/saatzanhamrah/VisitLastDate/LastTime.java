package com.example.kamal.saatzanhamrah.VisitLastDate;

/**
 * Created by kamal on 12/19/2017.
 */

public class LastTime {
    private String startWorkDate;
    private String startWorkTime;
    private String endWorkDate;
    private String endWorkTime;
    private String workTime;
    private String confirm_employer;
    private boolean isSelected;



    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getConfirm_employer() {
        return confirm_employer;
    }

    public void setConfirm_employer(String confirm_employer) {
        this.confirm_employer = confirm_employer;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
