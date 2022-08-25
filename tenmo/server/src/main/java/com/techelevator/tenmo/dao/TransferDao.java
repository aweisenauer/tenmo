package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    public List<Transfer> getAllTransfers();

    public Transfer getTransferByTransferId(int transferId);

    public List<Transfer> getTransferHistoryFromId(int fromId);

    //public List<Transfer> getTransferHistoryByToId(int toId);

    //public List<Transfer> getAllTransfersByAccountId(int accountId);
 public void createTransfer(Transfer transfer);
}
