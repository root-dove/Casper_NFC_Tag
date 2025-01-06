package org.example.nfc_tag.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "remaining_leave_days", nullable = false)
    private int remainingLeaveDays = 5; // 기본 연가 5일

    @Column(name = "late_penalty_points", nullable = false)
    private int latePenaltyPoints = 0; // 기본 벌점 0

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingLeaveDays() {
        return remainingLeaveDays;
    }

    public void setRemainingLeaveDays(int remainingLeaveDays) {
        this.remainingLeaveDays = remainingLeaveDays;
    }

    public int getLatePenaltyPoints() {
        return latePenaltyPoints;
    }

    public void setLatePenaltyPoints(int latePenaltyPoints) {
        this.latePenaltyPoints = latePenaltyPoints;
    }

    // Utility Methods
    /**
     * Reduce remaining leave days by 1 if there are any remaining.
     * @return true if leave day was successfully reduced, false otherwise.
     */
    public boolean useLeaveDay() {
        if (remainingLeaveDays > 0) {
            remainingLeaveDays--;
            return true;
        }
        return false;
    }

    /**
     * Increment the late penalty points by 1.
     */
    public void addLatePenalty() {
        latePenaltyPoints++;
    }
}
