package com.book.book_network.email;


import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("acctivate account");

    private final String name ;

    EmailTemplateName(String name){
        this.name = name;
    }

}
