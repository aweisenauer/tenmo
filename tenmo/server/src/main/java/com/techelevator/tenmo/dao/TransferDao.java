package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    public List<Transfer> getAllTransfers();

    public Transfer getTransferByTransferId(int transferId);

    public List<Transfer> getTransferHistoryFromId(int fromId);

    public boolean createTransfer(Transfer transfer);
}
