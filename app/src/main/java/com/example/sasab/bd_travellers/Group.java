package com.example.sasab.bd_travellers;

import java.util.List;

/**
 * Created by sasab on 26-Apr-16.
 */
public class Group {
    public List<User> list;
    public String groupId;
    public String plans;
    List<Expenditure> expenditures;
}

class Expenditure{
    int ID;
    int expense;
    String category;
}
