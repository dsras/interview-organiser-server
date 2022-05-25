package com.accolite.intervieworganiser.dto;

/**
 * Stores information about a {@link com.accolite.intervieworganiser.entities.User}.
 * <p>User data transfer object that stores username, password and login type of user.</p>
 */
public class LoginUser {

    private String username;
    private String password;
    private String type;

    /**
     * Gets username of user
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of user
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password of user
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password of user
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the type of user
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of user
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }
}
