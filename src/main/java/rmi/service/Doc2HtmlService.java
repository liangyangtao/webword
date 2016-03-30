package rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import rmi.model.Doc2HtmlEntity;

public abstract interface Doc2HtmlService
  extends Remote
{
  public abstract List<Doc2HtmlEntity> GetList(String paramString)
    throws RemoteException;
}

