package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/transfers/info/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferDetailsByTransferId(@PathVariable int transferId){
        return transferDao.getTransferByTransferId(transferId);

    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void createTransfer(@RequestBody Transfer transfer){
        transferDao.createTransfer(transfer);
    }
}
