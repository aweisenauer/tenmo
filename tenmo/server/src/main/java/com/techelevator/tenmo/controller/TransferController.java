package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransferController {
    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;


    public TransferController(JdbcTransferDao transferDao, JdbcUserDao userDao, JdbcAccountDao accountDao){
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;

    }

    @RequestMapping(path = "/transfers/{userId}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(@PathVariable int userId){
        List<Transfer> transfersByUserId = transferDao.getAllTransfersByUserId(userId);
        return transfersByUserId;

    }


}
