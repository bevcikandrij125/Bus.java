package dao;

import dao.AdminsFirstChoiceInt.Create;
import dao.AdminsFirstChoiceInt.See_satistics;
import dao.AdminsFirstChoiceInt.Show_all;
import dao.AdminsFirstChoiceInt.Update;
import exception.CustomerException;

public interface AdminDao extends CustomerDao, Show_all, Create, Update, See_satistics {

}
