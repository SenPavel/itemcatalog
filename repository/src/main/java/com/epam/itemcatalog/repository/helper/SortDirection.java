package com.epam.itemcatalog.repository.helper;

public enum SortDirection {
    ASCENDING("asc"), DESCENDING("desc");

    private String sortDirection;

    SortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public static String[] getSortDirections() {
        SortDirection[] sortDirections = SortDirection.values();
        String[] directions = new String[sortDirections.length];
        for (int i = 0; i < directions.length; i++) {
            directions[i] = sortDirections[i].getSortDirection();
        }
        return directions;
    }
}
