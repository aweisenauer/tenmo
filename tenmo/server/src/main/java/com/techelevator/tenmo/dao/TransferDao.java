package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    public List<Transfer> getAllTransfersByUsername(String username);

    public Transfer getTransferByTransferId(int transferId);

    public List<Transfer> getTransferHistoryFromId(int accountId);

    public String createTransfer(Transfer transfer);

    public void requestTransfer(Transfer transfer);
}
