package com.andela.mrm.room_setup.data;

/**
 * The enum Setup page.
 */
public enum SetupPage {
    /**
     * The Country.
     */
    COUNTRY("Which country are you in?"),
    /**
     * The Building.
     */
    BUILDING("Which building are you in?"),
    /**
     * The Floor.
     */
    FLOOR("What floor are you on?"),
    /**
     * The Room.
     */
    ROOM("Which room should this belong to?");

    private String pageHeaderText;

    /**
     * Gets page header text for a set up page.
     *
     * @param pageHeaderText the page header text
     */
    SetupPage(String pageHeaderText) {
        this.pageHeaderText = pageHeaderText;
    }

    /**
     * Gets page header text for a set up page.
     *
     * @return the page header text
     */
    public String getPageHeaderText() {
        return pageHeaderText;
    }
}
