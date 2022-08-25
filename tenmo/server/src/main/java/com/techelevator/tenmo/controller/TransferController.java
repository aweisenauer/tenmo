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

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() {
        List<Transfer> allTransfers = transferDao.getAllTransfers();
        return allTransfers;
    }

    @RequestMapping(path = "/transfers/{fromId}", method = RequestMethod.GET)
    public List<Transfer> getTransferHistoryFromId(@PathVariable int fromId){
        List<Transfer> transfersByUserId = transferDao.getTransferHistoryFromId(fromId);
        return transfersByUserId;
    }

    @RequestMapping(path = "/transfers/to/{toId}", method = RequestMethod.GET)
    public List<Transfer> getTransferHistoryToId(@PathVariable int toId) {
        List<com.techelevator.tenmo.model.Transfer> transfersByUserId = transferDao.getTransferHistoryFromId(toId);
        return transfersByUserId;

    }
}
