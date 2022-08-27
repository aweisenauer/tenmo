package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;


    public TransferController(JdbcTransferDao transferDao, JdbcUserDao userDao, JdbcAccountDao accountDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;

    }

    @RequestMapping(path = "/transfers", method = RequestMethod.GET) //all transfers by logged in user only
    public List<Transfer> getAllTransfers() {
        List<Transfer> allTransfers = transferDao.getAllTransfers();
        return allTransfers;
    }

    @RequestMapping(path = "/transfers/", method = RequestMethod.GET) //check this out -principal
    public List<Transfer> getTransferHistoryFromId(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        List<Transfer> transfersByUserId = transferDao.getTransferHistoryFromId(userId);
        return transfersByUserId;
    }

    @RequestMapping(path = "/transfers/info/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferDetailsByTransferId(@PathVariable int transferId) {
        return transferDao.getTransferByTransferId(transferId);

    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void createTransfer(@RequestBody Transfer transfer, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        int accountId = accountDao.getAccountByUserId(userId).getAccountId();
if (transfer.getAmount()> accountDao.getBalance(userId)){
    System.out.println("CODE 2: Transaction Rejected - Insufficient Funds");

} else
        transferDao.createTransfer(transfer);
    }
}
