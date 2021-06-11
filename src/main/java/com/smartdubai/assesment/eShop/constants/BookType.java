package com.smartdubai.assesment.eShop.constants;

public enum BookType {
    FICTION("FICTION"), COMIC("COMIC");

    private final String bookType;

    BookType (final String bookType) {

        this.bookType = bookType;
    }

    public String getBookType(){
        return bookType;
    }



}
