package com.thing.entity;

public enum UserType {
    ADMIN("admin"),
    CUSTOMER("customer");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public static UserType getByName(String userTypeName) {
        for (UserType userType : values()) {
            if (userType.getName().equalsIgnoreCase(userTypeName)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("No user type found for name: " + userTypeName);
    }

    public String getName() {
        return name;
    }
}
