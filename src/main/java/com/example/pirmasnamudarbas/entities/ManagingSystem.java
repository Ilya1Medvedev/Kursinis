package com.example.pirmasnamudarbas.entities;

import java.io.Serializable;
import java.util.List;

public class ManagingSystem  implements Serializable {
    private String version;
    private List<User> allSysUsers;
    private List<SportProgram> allSysSportPrograms;
    private List<Order> allSysOrders;

    public ManagingSystem(String version, List<User> allSysUsers, List<SportProgram> allSysSportPrograms, List<Order> allSysOrders) {
        this.version = version;
        this.allSysUsers = allSysUsers;
        this.allSysSportPrograms = allSysSportPrograms;
        this.allSysOrders = allSysOrders;
    }

    public String getVersion() {
        return version;
    }

    public List<User> getAllSysUsers() {
        return allSysUsers;
    }

    public List<Order> getAllSysOrders() {
        return allSysOrders;
    }

    public List<SportProgram> getAllSysSportPrograms() {
        return allSysSportPrograms;
    }
}
