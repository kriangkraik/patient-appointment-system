package com.example.system.enums;

import lombok.Getter;

/**
 * Enumeration representing the status of a user account.
 */
@Getter
public enum AccountStatus {

    /**
     * Account is active and can be used normally.
     */
    ACTIVE("Active", "Account is active and operational"),

    /**
     * Account is temporarily suspended.
     */
    SUSPENDED("Suspended", "Account is temporarily suspended"),

    /**
     * Account is permanently deactivated.
     */
    INACTIVE("Inactive", "Account is permanently deactivated"),

    /**
     * Account is pending activation (e.g., email verification).
     */
    PENDING("Pending", "Account is pending activation"),

    /**
     * Account is locked due to security reasons.
     */
    LOCKED("Locked", "Account is locked for security reasons");

    /**
     * -- GETTER --
     * Gets the user-friendly display name.
     *
     * @return display name of the status
     */
    private final String displayName;
    /**
     * -- GETTER --
     * Gets the detailed description of the status.
     *
     * @return description of the status
     */
    private final String description;

    /**
     * Constructor for AccountStatus enum.
     *
     * @param displayName user-friendly display name
     * @param description detailed description of the status
     */
    AccountStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Gets AccountStatus from string value (case-insensitive).
     *
     * @param status string representation of status
     * @return AccountStatus enum value
     * @throws IllegalArgumentException if status is not valid
     */
    public static AccountStatus fromString(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        try {
            return AccountStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account status: " + status);
        }
    }

    /**
     * Checks if the account status allows login.
     *
     * @return true if account can login, false otherwise
     */
    public boolean canLogin() {
        return this == ACTIVE;
    }

    /**
     * Checks if the account status is considered active.
     *
     * @return true if account is active, false otherwise
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * Checks if the account status requires admin intervention.
     *
     * @return true if requires admin action, false otherwise
     */
    public boolean requiresAdminIntervention() {
        return this == SUSPENDED || this == LOCKED;
    }

    @Override
    public String toString() {
        return displayName;
    }
}