package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    public List<Transfer> getAllTransfers();
    public Transfer getTransferId(int transferId);

    public List<Transfer> getAllTransfersByAccountId(int accountId);
    public List<Transfer> getTransferByFromId(int fromId);
    public List<Transfer> getTransfersByToId(int toId);


}
